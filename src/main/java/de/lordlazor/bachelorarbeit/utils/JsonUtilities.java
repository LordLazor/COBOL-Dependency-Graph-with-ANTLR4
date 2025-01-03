package de.lordlazor.bachelorarbeit.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonUtilities {

  // This class is used to create a JSON file that is used to create the graph in the frontend

  private HashMap<String, Object> jsonMap = new HashMap<>();
  private ArrayList<HashMap<String, Object>> nodes = new ArrayList<>();
  private ArrayList<HashMap<String, Object>> links = new ArrayList<>();

  public HashMap<String, Object> getJsonMap() {
    return jsonMap;
  }

  public void setJsonMap(HashMap<String, Object> jsonMap) {
    this.jsonMap = jsonMap;
  }

  public ArrayList<HashMap<String, Object>> getNodes() {
    return nodes;
  }

  public void setNodes(ArrayList<HashMap<String, Object>> nodes) {
    this.nodes = nodes;
  }

  public ArrayList<HashMap<String, Object>> getLinks() {
    return links;
  }

  public void setLinks(ArrayList<HashMap<String, Object>> links) {
    this.links = links;
  }



  public void addNode(String id, int group) {
    // If node already exist in nodes list, do not add it again
    for (HashMap<String, Object> node : nodes) {
      if (node.get("id").equals(id)) {
        return;
      }
    }

    HashMap<String, Object> node = new HashMap<>();
    node.put("id", id);
    node.put("group", group);
    nodes.add(node);
  }

  public void addLink(String source, String target) {
    // If link already exist in links list, do not add it again
    for (HashMap<String, Object> link : links) {
      if (link.get("source").equals(source) && link.get("target").equals(target)) {
        return;
      }
    }

    HashMap<String, Object> link = new HashMap<>();
    link.put("source", source);
    link.put("target", target);
    links.add(link);
  }

  public void createJsonFile(String filePath) throws IOException {
    addNode("Root", 0); // Root node

    for (HashMap<String, Object> link : links) {
      String source = (String) link.get("source");
      String target = (String) link.get("target");
      addNode(source, 26);
      addNode(target, 26);
    }

    jsonMap.put("nodes", nodes);
    jsonMap.put("links", links);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(new File(filePath), jsonMap);
  }

  public static String readJsonFile(String filePath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File(filePath);

    Object json = objectMapper.readValue(file, Object.class);

    return objectMapper.writeValueAsString(json);
  }
}