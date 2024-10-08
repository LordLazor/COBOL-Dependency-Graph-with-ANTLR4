package de.lordlazor.bachelorarbeit.controller;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.grammar.Visitor;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WebController {

  private static String UPLOADED_FOLDER = "src/main/resources/uploads/";



  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping("/upload")
  public String upload(Model model) {
    return "upload";
  }

  @GetMapping("/view")
  public String view(Model model){
    return "view";
  }

  @PostMapping("/upload/file")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
    if (file.isEmpty()) {
      redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
      return "redirect:/upload";
    }

    try {

      InputStream inputStream = file.getInputStream();

      CharStream stream = CharStreams.fromStream(inputStream);

      Cobol85Lexer lexer = new Cobol85Lexer(stream); // TODO: Can be filename or folder name; selected by frontend
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      Cobol85Parser parser = new Cobol85Parser(tokens);

      ParseTree tree = parser.startRule();

      JsonUtilities jsonUtilities = new JsonUtilities();

      Visitor visitor = new Visitor(jsonUtilities);
      visitor.visit(tree);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

      String formattedDateTime = LocalDateTime.now().format(formatter);

      jsonUtilities.createJsonFile("src/main/resources/out/output_"+ formattedDateTime + ".json");


      redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
      redirectAttributes.addFlashAttribute("error", true);
    }

    return "redirect:/upload";
  }


}
