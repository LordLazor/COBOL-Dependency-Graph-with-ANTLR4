package de.lordlazor.bachelorarbeit.controller;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
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

  private final ControllerUtilities controllerUtilities;

  public UploadController(ControllerUtilities controllerUtilities) {
    this.controllerUtilities = controllerUtilities;
  }

  @PostMapping("/upload/folder")
  public String uploadFolder(@RequestParam("folder")  MultipartFile[] files, @RequestParam("textInput") String textInput, RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("type", "folder");
    if (files.length == 0) {
      redirectAttributes.addFlashAttribute("message", "Please select a folder to upload");
      return "redirect:/upload/folder";
    }

    try {
      List<CharStream> streams = new ArrayList<>();
      String folderName = null;

      for (MultipartFile file : files) {
        if (folderName == null) {
          Path path = Paths.get(file.getOriginalFilename());
          folderName = path.getParent().toString();
        }

        if (!file.getOriginalFilename().endsWith(".cbl") && !file.getOriginalFilename().endsWith(".cob")) {
          continue; // TODO: Implement copy book support => rn grammar is problematic so that the copy book cant be parsed (prob. wont be able in the future)
        }

        InputStream inputStream = file.getInputStream();
        streams.add(CharStreams.fromStream(inputStream));

      }

      JsonUtilities jsonUtilities = new JsonUtilities();

      Cobol85Lexer lexer;
      CommonTokenStream tokens;
      Cobol85Parser parser;

      ParseTree tree;

      Visitor visitor;

      for (CharStream stream : streams) {
        lexer = new Cobol85Lexer(stream);
        tokens = new CommonTokenStream(lexer);
        parser = new Cobol85Parser(tokens);

        tree = parser.startRule();

        visitor = new Visitor(jsonUtilities);
        visitor.visit(tree);
      }

      if (textInput.isBlank()){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

        String formattedDateTime = LocalDateTime.now().format(formatter);

        jsonUtilities.createJsonFile(controllerUtilities.getOutputFolder() + formattedDateTime + ".json");

        Files.createDirectories(Paths.get(controllerUtilities.getOutputFolder() + formattedDateTime));

        BufferedReader reader;

        for (MultipartFile file : files) {
          if (file.getOriginalFilename().endsWith(".cob") || file.getOriginalFilename().endsWith(".cbl")) {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String extractedFilename = controllerUtilities.findProgramID(reader);

            if (extractedFilename == null) {
              redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
              return "redirect:/upload/folder";
            }

            Files.copy(file.getInputStream(), Paths.get(controllerUtilities.getOutputFolder() + formattedDateTime + "/" + extractedFilename));
          }
        }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded the folder '" + folderName + "'");

      } else {

        jsonUtilities.createJsonFile(controllerUtilities.getOutputFolder() + textInput + ".json");
        Files.createDirectories(Paths.get(controllerUtilities.getOutputFolder() + textInput));

        BufferedReader reader;

        for (MultipartFile file : files) {
          if (file.getOriginalFilename().endsWith(".cob") || file.getOriginalFilename().endsWith(".cbl")) {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String extractedFilename = controllerUtilities.findProgramID(reader);

            if (extractedFilename == null) {
              redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
              return "redirect:/upload/folder";
            }

            Files.copy(file.getInputStream(), Paths.get(controllerUtilities.getOutputFolder() + textInput + "/" + extractedFilename));
          }
        }

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded the folder '" + textInput + "'");

      }


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

      InputStream inputStream = file.getInputStream();

      CharStream stream = CharStreams.fromStream(inputStream);

      Cobol85Lexer lexer = new Cobol85Lexer(stream);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      Cobol85Parser parser = new Cobol85Parser(tokens);

      ParseTree tree = parser.startRule();

      JsonUtilities jsonUtilities = new JsonUtilities();

      Visitor visitor = new Visitor(jsonUtilities);
      visitor.visit(tree);

      if (textInput.isBlank()){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

        String formattedDateTime = LocalDateTime.now().format(formatter);

        jsonUtilities.createJsonFile(controllerUtilities.getOutputFolder() + formattedDateTime + ".json");

        Files.createDirectories(Paths.get(controllerUtilities.getOutputFolder() + formattedDateTime));


        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String extractedFilename = controllerUtilities.findProgramID(reader);

        if (extractedFilename == null) {
          redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
          return "redirect:/upload/file";
        }


        Files.copy(file.getInputStream(), Paths.get(controllerUtilities.getOutputFolder() + formattedDateTime + "/" +extractedFilename));


        redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
      } else {

        jsonUtilities.createJsonFile("src/main/resources/out/" + textInput + ".json");
        Files.createDirectories(Paths.get(controllerUtilities.getOutputFolder() + textInput));

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String extractedFilename = controllerUtilities.findProgramID(reader);

        if (extractedFilename == null) {
          redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
          return "redirect:/upload/file";
        }

        Files.copy(file.getInputStream(), Paths.get(controllerUtilities.getOutputFolder()+ textInput + "/" + extractedFilename));


        redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + textInput + "'");

      }


    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
      redirectAttributes.addFlashAttribute("error", true);
    }

    return "redirect:/upload/file";
  }

}
