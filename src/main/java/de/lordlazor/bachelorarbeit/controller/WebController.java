package de.lordlazor.bachelorarbeit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.grammar.Visitor;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class WebController {

  private static String OUTPUT_FOLDER = "src/main/resources/out/";

  // All json files in the output folder
  private List<String> allFiles(){
    List<String> files = new ArrayList<>();
    File folder = new File(OUTPUT_FOLDER);
    File[] listOfFiles = folder.listFiles();

    assert listOfFiles != null;
    for (File file : listOfFiles) {
      if (file.isFile()) {
        files.add(file.getName());
      }
    }

    return files;
  }

  private Map<String, String> getProgramFiles(String fileDataFolder) {
    Map<String, String> programFiles = new HashMap<>();
    File folder = new File(fileDataFolder);
    File[] listOfFiles = folder.listFiles();

    assert listOfFiles != null;
    for (File file : listOfFiles) {
      if (file.isFile()) {
        try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
          String line;
          StringBuilder stringBuilder = new StringBuilder();
          while ((line = reader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
          }
          programFiles.put(file.getName(), stringBuilder.toString());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return programFiles;

  }


  @GetMapping("/")
  public String index(Model model) {
    return "index";
  }

  @GetMapping("/upload/file")
  public String upload(Model model) {
    model.addAttribute("type", "file");
    return "upload";
  }

  @GetMapping("/upload/folder")
  public String uploadFolder(Model model) {
    model.addAttribute("type", "folder");
    return "upload";
  }

  @GetMapping("/view")
  public String view(Model model){
    model.addAttribute("filenames", allFiles());
    return "view";
  }

  private static String extractTimestamp(String filename) {
    Pattern pattern = Pattern.compile("output_(\\d{8}_\\d{6})\\.json");
    Matcher matcher = pattern.matcher(filename);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }

    @PostMapping("/view/viewgraph")
  public String viewGraph(@RequestParam("filename") String filename, RedirectAttributes redirectAttributes)
      throws IOException {
    String fileTimestamp = extractTimestamp(filename);

    if (fileTimestamp == null) {
      redirectAttributes.addFlashAttribute("message", "Invalid filename");
      return "redirect:/view";
    }
    String rawFilename = filename;
    filename = OUTPUT_FOLDER + filename;
    String jsonData = JsonUtilities.readJsonFile(filename);

    String fileDataFolder = OUTPUT_FOLDER + fileTimestamp;
    Map<String, String> programFiles = getProgramFiles(fileDataFolder);

    redirectAttributes.addFlashAttribute("selectedOption", rawFilename);
    redirectAttributes.addFlashAttribute("jsonData", jsonData);
    redirectAttributes.addFlashAttribute("filenames", allFiles());
    redirectAttributes.addFlashAttribute("programFiles", programFiles);
    return "redirect:/view";
  }

  @PostMapping("/upload/folder")
  public String uploadFolder(@RequestParam("folder")  MultipartFile[] files, RedirectAttributes redirectAttributes) {
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

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

      String formattedDateTime = LocalDateTime.now().format(formatter);

      jsonUtilities.createJsonFile("src/main/resources/out/output_"+ formattedDateTime + ".json");

      Files.createDirectories(Paths.get(OUTPUT_FOLDER + formattedDateTime));

      BufferedReader reader;

      for (MultipartFile file : files) {
        if (file.getOriginalFilename().endsWith(".cob") || file.getOriginalFilename().endsWith(".cbl")) {
          reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
          String extractedFilename = findProgramID(reader);

          if (extractedFilename == null) {
            redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
            return "redirect:/upload/folder";
          }

          Files.copy(file.getInputStream(), Paths.get(OUTPUT_FOLDER + formattedDateTime + "/" + extractedFilename));
        }
      }

      redirectAttributes.addFlashAttribute("message", "You successfully uploaded the folder '" + folderName + "'");

    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("message", "Folder upload failed: " + e.getMessage());
      redirectAttributes.addFlashAttribute("error", true);
    }

    return "redirect:/upload/folder";
  }

  private String findProgramID(BufferedReader reader) throws IOException {
    String line;
    Pattern pattern = Pattern.compile("PROGRAM-ID\\.\\s*(\\w+)");
    while ((line = reader.readLine()) != null) {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find()) {
        return matcher.group(1);
      }
    }
    return null;
  }

  @PostMapping("/upload/file")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
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

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

      String formattedDateTime = LocalDateTime.now().format(formatter);

      jsonUtilities.createJsonFile("src/main/resources/out/output_"+ formattedDateTime + ".json");

      Files.createDirectories(Paths.get(OUTPUT_FOLDER + formattedDateTime));

      BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
      String extractedFilename = findProgramID(reader);

      if (extractedFilename == null) {
        redirectAttributes.addFlashAttribute("message", "PROGRAM-ID not found in the file");
        return "redirect:/upload/file";
      }


      Files.copy(file.getInputStream(), Paths.get(OUTPUT_FOLDER + formattedDateTime + "/" +extractedFilename));


      redirectAttributes.addFlashAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");

    } catch (IOException e) {
      e.printStackTrace();
      redirectAttributes.addFlashAttribute("message", "File upload failed: " + e.getMessage());
      redirectAttributes.addFlashAttribute("error", true);
    }

    return "redirect:/upload/file";
  }

  @PostMapping("/view/updateGraph")
  @ResponseBody
  public String updateGraph(@RequestBody Map<String, Object> checkboxData) throws IOException {
    String filename = (String) checkboxData.get("filename");
    checkboxData.remove("filename");
    String fileTimestamp = extractTimestamp(filename);

    filename = OUTPUT_FOLDER + filename;
    String jsonData = JsonUtilities.readJsonFile(filename);

    List<Integer> unselectedNodes = new ArrayList<>();
    checkboxData.forEach((key, value) -> {
      if (!((boolean) value)) {
        unselectedNodes.add(Integer.parseInt(key));
      }
    });

    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> jsonMap = objectMapper.readValue(jsonData, Map.class);
    List<Map<String, Object>> nodes = (List<Map<String, Object>>) jsonMap.get("nodes");
    List<Map<String, Object>> links = (List<Map<String, Object>>) jsonMap.get("links");

    // Remove nodes with groups in unselectedNodes
    nodes.removeIf(node -> unselectedNodes.contains(node.get("group")));

    // Collect remaining node IDs
    Set<String> remainingNodeIds = nodes.stream()
        .map(node -> (String) node.get("id"))
        .collect(Collectors.toSet());

    // Remove links where either source or target node does not exist in remaining nodes
    links.removeIf(link -> !remainingNodeIds.contains(link.get("source")) || !remainingNodeIds.contains(link.get("target")));

    jsonMap.put("nodes", nodes);
    jsonMap.put("links", links);
    return objectMapper.writeValueAsString(jsonMap);
  }


}
