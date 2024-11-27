package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.util.HashMap;
import java.util.List;

public class NodeLinkManager {

  // This class is used to manage the nodes and links of the JSON file that is used to create the graph.

  private JsonUtilities jsonUtilities;


  public NodeLinkManager(JsonUtilities jsonUtilities) {
    this.jsonUtilities = jsonUtilities;
  }

  public void addNodeAndLink(String parent, String child, int group) {
    jsonUtilities.addNode(parent, 1);
    jsonUtilities.addNode(child, group);
    jsonUtilities.addLink(parent, child);
    jsonUtilities.addLink("Root", parent);
  }


  public void addFileControlEntry(String programName, String fdName, String fileControlClause) {
    jsonUtilities.addNode(programName, 1);
    jsonUtilities.addNode(fileControlClause, 6);
    jsonUtilities.addNode(fdName, 5);
    jsonUtilities.addLink(programName, fdName);
    jsonUtilities.addLink(fdName, fileControlClause);
    jsonUtilities.addLink("Root", programName);
  }

  public void addLink(String source, String target) {
    jsonUtilities.addLink(source, target);
  }

  public void addFileDescriptionName(String programName, String fdName) {
    jsonUtilities.addNode(fdName, 5);
    jsonUtilities.addLink(programName, fdName);

    jsonUtilities.addNode(programName, 1);

    jsonUtilities.addLink("Root", programName);
  }

  public void addNodeWithoutRoot(String id, int group) {
    jsonUtilities.addNode(id, group);
  }
  public void addNode(String id, int group) {
    jsonUtilities.addNode(id, group);
    jsonUtilities.addNode("Root", 0);
    jsonUtilities.addLink("Root", id);
  }

  public void addVariables(String programName, List<List<String>> nodes, List<List<String>> links) {
    for(List<String> node : nodes){
      jsonUtilities.addNode(node.get(0), Integer.parseInt(node.get(1)));
    }

    for(List<String> link : links){
      jsonUtilities.addLink(link.get(0), link.get(1));
    }

    jsonUtilities.addNode(programName, 1);

    jsonUtilities.addLink("Root", programName);
  }

  public String searchNodeMatchesName(String name) {
    List<HashMap<String, Object>> nodes = jsonUtilities.getNodes();
    for (HashMap<String, Object> node : nodes) {
      String id = node.get("id").toString();
      if (id.length() > 4 && id.substring(4).equals(name)) {
        return id;
      }
    }
    return name;
  }

}

