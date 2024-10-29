package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.util.List;

public class NodeLinkManager {
  private JsonUtilities jsonUtilities;

  public NodeLinkManager(JsonUtilities jsonUtilities) {
    this.jsonUtilities = jsonUtilities;
  }

  public void addNodeAndLink(String parent, String child, int group) {
    jsonUtilities.addNode(parent, 1);
    jsonUtilities.addNode(child, group);
    jsonUtilities.addLink(parent, child, 1);
    jsonUtilities.addLink("Root", parent, 1);
  }

  public void addFileControlEntry(String programName, String fdName, String fileControlClause) {
    jsonUtilities.addNode(programName, 1);
    jsonUtilities.addNode(fileControlClause, 6);
    jsonUtilities.addNode(fdName, 5);
    jsonUtilities.addLink(programName, fdName, 1);
    jsonUtilities.addLink(fdName, fileControlClause, 1);
    jsonUtilities.addLink("Root", programName, 1);
  }

  public void addFileDescriptionName(String programName, String fdName) {
    jsonUtilities.addNode(fdName, 5);
    jsonUtilities.addLink(programName, fdName, 1);

    jsonUtilities.addNode(programName, 1);

    jsonUtilities.addLink("Root", programName, 1);
  }

  public void addVariables(String programName, List<List<String>> nodes, List<List<String>> links) {
    for(List<String> node : nodes){
      jsonUtilities.addNode(node.get(0), Integer.parseInt(node.get(1)));
    }

    for(List<String> link : links){
      jsonUtilities.addLink(link.get(0), link.get(1), 1);
    }

    jsonUtilities.addNode(programName, 1);

    jsonUtilities.addLink("Root", programName, 1);
  }

}

