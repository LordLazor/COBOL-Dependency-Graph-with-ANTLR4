package de.lordlazor.bachelorarbeit.controller;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.utils.VisitorUtilites;
import de.lordlazor.bachelorarbeit.visitor.Visitor;
import de.lordlazor.bachelorarbeit.utils.ControllerUtilities;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UploadController {

  // Controller for the POST requests of /upload/...

  private final ControllerUtilities controllerUtilities;

  public UploadController(ControllerUtilities controllerUtilities) {
    this.controllerUtilities = controllerUtilities;
  }

  @PostMapping("/upload/folder")
  public String uploadFolder(@RequestParam("folder") MultipartFile[] files, @RequestParam("textInput") String textInput, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("type", "folder");
    if (files.length == 0) {
      redirectAttributes.addFlashAttribute("message", "Please select a folder to upload");
      return "redirect:/upload/folder";
    }

      try {
        VisitorUtilites.resetCounters();

        List<CharStream> streams = new ArrayList<>();
        String folderName = null;

        for (MultipartFile file : files) {
          if (folderName == null) {
            Path path = Paths.get(file.getOriginalFilename());
            folderName = path.getParent().toString();
          }

          if (!file.getOriginalFilename().endsWith(".cbl") && !file.getOriginalFilename()
              .endsWith(".cob")) {
            continue;
          }

          InputStream inputStream = file.getInputStream();
          streams.add(CharStreams.fromStream(inputStream));
        }

        processFiles(streams, textInput, files, redirectAttributes, folderName, "folder");

      } catch (IOException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "Folder upload failed: " + e.getMessage());
        redirectAttributes.addFlashAttribute("error", true);
      }

    return "redirect:/upload/folder";
  }

  @PostMapping("/upload/file")
  public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("textInput") String textInput, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("type", "file");
    if (file.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
      return "redirect:/upload/file";
    }
      try {
        VisitorUtilites.resetCounters();

        InputStream inputStream = file.getInputStream();
        CharStream stream = CharStreams.fromStream(inputStream);
        List<CharStream> streams = new ArrayList<>();
        streams.add(stream);

        processFiles(streams, textInput, new MultipartFile[]{file}, redirectAttributes,
            file.getOriginalFilename(), "file");

      } catch (IOException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
        redirectAttributes.addFlashAttribute("error", true);
      }

    return "redirect:/upload/file";
  }

  private void processFiles(List<CharStream> streams, String textInput, MultipartFile[] files, RedirectAttributes redirectAttributes, String folderName, String type) throws IOException {
    JsonUtilities jsonUtilities = new JsonUtilities();
    Cobol85Lexer lexer;
    CommonTokenStream tokens;
    Cobol85Parser parser;
    ParseTree tree;
    Visitor visitor;

    for (CharStream stream : streams) {
      String content = stream.toString();

      // Remove lines containing '*', '/', or '*>' as those are the comments
      String filteredContent = content.lines()
          .filter(line -> !line.contains("*") && !line.contains("/") && !line.contains("*>"))
          .reduce("", (acc, line) -> acc + line + "\n");


      CharStream filteredStream = CharStreams.fromString(filteredContent);

      lexer = new Cobol85Lexer(filteredStream);
      tokens = new CommonTokenStream(lexer);
      parser = new Cobol85Parser(tokens);
      tree = parser.startRule();
      visitor = new Visitor(jsonUtilities);
      visitor.visit(tree);
    }

    String outputFolder = controllerUtilities.getOutputFolder();
    String otherFiles = controllerUtilities.getOtherFilesFolder();
    String formattedDateTime = textInput.isBlank() ? LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")) : textInput;
    jsonUtilities.createJsonFile(outputFolder + formattedDateTime + ".json");
    Files.createDirectories(Paths.get(outputFolder + formattedDateTime));
    Files.createDirectories(Paths.get(outputFolder + formattedDateTime + "/" + otherFiles));

    for (MultipartFile file : files) {
      if (file.getOriginalFilename().endsWith(".cob") || file.getOriginalFilename().endsWith(".cbl")) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String extractedFilename = controllerUtilities.findProgramID(reader);

        if (extractedFilename == null) {
          redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
          return;
        }

        Files.copy(file.getInputStream(), Paths.get(outputFolder + formattedDateTime + "/" + extractedFilename));
      } else {
        String extractedFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
        Files.copy(file.getInputStream(), Paths.get(outputFolder + formattedDateTime + "/" + otherFiles + "/" + extractedFilename));
      }
    }

    redirectAttributes.addFlashAttribute("message", "You successfully uploaded the " + type + " '" + folderName + "'");
  }
}