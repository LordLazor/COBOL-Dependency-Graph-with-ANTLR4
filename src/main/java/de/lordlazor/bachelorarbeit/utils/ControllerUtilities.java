package de.lordlazor.bachelorarbeit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtilities {

  private final String OUTPUT_FOLDER = "src/main/resources/out/";
  private final String OTHER_FILES_FOLDER = "other_files/";

  public String getOutputFolder() {
    return OUTPUT_FOLDER;
  }

  public String getOtherFilesFolder() {
    return OTHER_FILES_FOLDER;
  }

  public Map<String, Object> updateJson(String filename, Map<String, Object> checkboxData) throws IOException {
    filename = getOutputFolder() + filename;
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

    nodes.removeIf(node -> unselectedNodes.contains(node.get("group")));

    Set<String> remainingNodeIds = nodes.stream()
        .map(node -> (String) node.get("id"))
        .collect(Collectors.toSet());

    links.removeIf(link -> !remainingNodeIds.contains(link.get("source")) || !remainingNodeIds.contains(link.get("target")));

    jsonMap.put("nodes", nodes);
    jsonMap.put("links", links);
    return jsonMap;
  }

  public String findProgramID(BufferedReader reader) throws IOException {
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

  public List<String> allFiles(){
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


  public Map<String, String> getProgramFiles(String fileDataFolder) {
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

  public String extractJsonFileName(String filename) {
    Pattern pattern = Pattern.compile("(.+)\\.json");
    Matcher matcher = pattern.matcher(filename);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return null;
  }

}
