package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.JsonNodeNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85BaseVisitor;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferenceContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferencePhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingParameterContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CobolWordContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat2Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat3Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataRenamesClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentifierContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LinkageSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LocalStorageSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureCopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.QualifiedDataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.QualifiedDataNameFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.WorkingStorageSectionContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visitor extends Cobol85BaseVisitor<Object> {
  private final RetrieveProgramName retrieveProgramName;
  private final RetrieveContext retrieveContext;
  private final NodeLinkManager nodeLinkManager;

  public Visitor(JsonUtilities jsonUtilities) {
    this.nodeLinkManager = new NodeLinkManager(jsonUtilities);

    this.retrieveProgramName = new RetrieveProgramName();
    this.retrieveContext = new RetrieveContext();
  }


  /**
   * Get the program name from the program unit context by traversing through the parents of the current context.
   */

  @Override
  public Object visitParagraphName(ParagraphNameContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);
      String paragraphName = ctx.children.get(0).getText();
      nodeLinkManager.addNodeAndLink(programName, paragraphName, 2);
    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitParagraphName(ctx);
  }

  @Override
  public Object visitProcedureCopyStatement(ProcedureCopyStatementContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);
      String copyName = ctx.children.get(1).getText();
      nodeLinkManager.addNodeAndLink(programName, copyName, 3);
    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitProcedureCopyStatement(ctx);
  }

  @Override
  public Object visitCopyStatement(CopyStatementContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);
      String copyName = ctx.children.get(1).getText();
      nodeLinkManager.addNodeAndLink(programName, copyName, 3);
    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitCopyStatement(ctx);
  }

  @Override
  public Object visitCallStatement(CallStatementContext ctx) {
    try {

      // Get the called program
      String programName = retrieveProgramName.getProgramName(ctx);
      String calledProgramName = ctx.children.get(1).getText().replace("'", "").replace("\"", "");
      nodeLinkManager.addNodeAndLink(programName, calledProgramName, 4);

      // Get all Variables that are passed to the called program
      CallUsingPhraseContext callUsingPhraseContext = retrieveContext.getCallUsingPhraseContext(ctx);

      // if callUsingPhraseContext is null, then there are no variables passed to the called program
      if (callUsingPhraseContext != null) {
        CallUsingParameterContext callUsingParameterContext = retrieveContext.getCallUsingParameterContext(callUsingPhraseContext);

        CallByReferencePhraseContext callByReferencePhraseContext = retrieveContext.getCallByReferencePhraseContext(callUsingParameterContext);

        for (int i = 0; i < callByReferencePhraseContext.children.size(); i++) {
          CallByReferenceContext callByReferenceContext = (CallByReferenceContext) callByReferencePhraseContext.children.get(i);

          IdentifierContext identifierContext = (IdentifierContext) callByReferenceContext.children.get(0);

          QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) identifierContext.children.get(0);

          QualifiedDataNameFormat1Context qualifiedDataNameFormat1Context = (QualifiedDataNameFormat1Context) qualifiedDataNameContext.children.get(0);

          DataNameContext dataNameContext = (DataNameContext) qualifiedDataNameFormat1Context.children.get(0);

          CobolWordContext cobolWordContext = (CobolWordContext) dataNameContext.children.get(0);

          String variableNameWithoutLevelNumber = cobolWordContext.children.get(0).getText();

          String variableWithLevelNumber = nodeLinkManager.searchNodeContainsName(variableNameWithoutLevelNumber);

          if (variableWithLevelNumber == null) {
            throw new JsonNodeNotFoundException("variableWithLevelNumber is null");
          }

          nodeLinkManager.addLink(calledProgramName, variableWithLevelNumber);
        }
      }



    } catch (ProgramNameNotFoundException | ContextNotFoundException | JsonNodeNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitCallStatement(ctx);
  }




  // FileControlClauseContext
  @Override
  public Object visitFileControlEntry(FileControlEntryContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      // Select Clause for Getting FD Name

      SelectClauseContext selectClauseContext = retrieveContext.getSelectClauseContext(ctx);

      FileNameContext fileNameContext = retrieveContext.getFileNameContext(selectClauseContext);

      String fdName = fileNameContext.children.get(0).getChild(0).getText();

      // File Control Clause

      FileControlClauseContext fileControlClauseContext = retrieveContext.getFileControlClauseContext(ctx);

      AssignClauseContext assignClauseContext = retrieveContext.getAssignClauseContext(fileControlClauseContext);

      LiteralContext literalContext = retrieveContext.getLiteralContext(assignClauseContext);

      String fileControlClause = literalContext.children.get(0).getText();

      if (fileControlClause == null) {
        throw new VisitorFileNotFoundException("fileControlClause is null");
      }

      fileControlClause = fileControlClause.replace("'", "").replace("\"", "");;
      nodeLinkManager.addFileControlEntry(programName, fdName, fileControlClause);
    } catch (ProgramNameNotFoundException | VisitorFileNotFoundException |
             ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitFileControlEntry(ctx);
  }




  @Override
  public Object visitFileSection(FileSectionContext ctx){
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      FileDescriptionEntryContext fileDescriptionEntryContext = retrieveContext.getFileDescriptionEntryContext(ctx);

      // File Section is empty
      if (fileDescriptionEntryContext == null) {
        return super.visitFileSection(ctx);
      }

      // Get FileName

      FileNameContext fileNameContext = retrieveContext.getFileNameContext(fileDescriptionEntryContext);
      String fdName = fileNameContext.children.get(0).getChild(0).getText();

      nodeLinkManager.addFileDescriptionName(programName, fdName);




      // Get Variables
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();
      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(fileDescriptionEntryContext, format1Andformat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1Andformat3Links);

      List<List<String>> nodes = getNodes(variables, "9", "10");
      List<List<String>> links = getLinks(variables, fdName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      nodeLinkManager.addVariables(programName, nodes, links);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    } catch (ContextNotFoundException e) {
      throw new RuntimeException(e);
    }
    return super.visitFileSection(ctx);
  }


  // Getting Variables of Linkage Section
  @Override
  public Object visitLinkageSection(LinkageSectionContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();
      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1Andformat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1Andformat3Links);

      List<List<String>> nodes = getNodes(variables, "11", "12");
      List<List<String>> links = getLinks(variables, programName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      nodeLinkManager.addVariables(programName, nodes, links);


    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    } catch (ContextNotFoundException e) {
      throw new RuntimeException(e);
    }
    return super.visitLinkageSection(ctx);
  }

  // Getting Variables of Local Storage Section
  @Override
  public Object visitLocalStorageSection(LocalStorageSectionContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();
      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1Andformat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1Andformat3Links);

      List<List<String>> nodes = getNodes(variables, "13", "14");
      List<List<String>> links = getLinks(variables, programName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      nodeLinkManager.addVariables(programName, nodes, links);


    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    } catch (ContextNotFoundException e) {
      throw new RuntimeException(e);
    }
    return super.visitLocalStorageSection(ctx);
  }


  private List<List<String>> getNodes(List<List<String>> variables, String variableNodeNumber, String subVariableNodeNumber) {
    List<List<String>> nodes = new ArrayList<>();


    for (int i = variables.size() - 1; i >= 0; i--) {
      int currentLevelNumber = Integer.parseInt(variables.get(i).get(0));

      List<String> node = new ArrayList<>();

      if (currentLevelNumber == 1 || currentLevelNumber == 77) {
        node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
        node.add(variableNodeNumber);
      } else if (currentLevelNumber == 66) {
        node.add(
            variables.get(i).get(0) + ": " + variables.get(i).get(1) + " RENAMES " + variables.get(
                i).get(2));
        node.add(variableNodeNumber);

      } else {
        node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
        node.add(subVariableNodeNumber);
      }

      nodes.add(node);
    }

    return nodes;
  }

  private List<List<String>> getLinks(List<List<String>> variables, String programName,
      Map<String, Integer> updatedFormat1AndFormat3Links,
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts) {
    List<List<String>> links = new ArrayList<>();

    for (int i = variables.size() - 1; i >= 0; i--) {
      int currentLevelNumber = Integer.parseInt(variables.get(i).get(0));

      if (currentLevelNumber != 1 && currentLevelNumber != 77 && currentLevelNumber != 66
          && currentLevelNumber != 88) {
        {
          for (int j = i - 1; j >= 0; j--) {
            int parentLevelNumber = Integer.parseInt(variables.get(j).get(0));
            if (parentLevelNumber < currentLevelNumber) {
              List<String> link = new ArrayList<>();
              link.add(variables.get(j).get(0) + ": " + variables.get(j).get(1));
              link.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
              links.add(link);
              break;
            }
          }
        }
      } else if (currentLevelNumber == 66) {
        List<String> link = new ArrayList<>();
        link.add(programName);
        link.add(
            variables.get(i).get(0) + ": " + variables.get(i).get(1) + " RENAMES " + variables.get(
                i).get(2));
        links.add(link);
      } else if (currentLevelNumber == 88) {
        String linkName88 = variables.get(i).get(0) + ": " + variables.get(i).get(1);
        int linkIndex = updatedFormat1AndFormat3Links.get(linkName88);

        DataDescriptionEntryFormat1Context dataDescriptionEntryFormat1Context = dataDescriptionEntryFormat1Contexts.get(
            linkIndex);

        String levelNumber = dataDescriptionEntryFormat1Context.children.get(0).getText();
        String variableName = "";
        if (dataDescriptionEntryFormat1Context.children.get(1) instanceof DataNameContext) {
          variableName =
              dataDescriptionEntryFormat1Context.children.get(1).getChild(0).getChild(0).getText();
        } else {
          variableName = dataDescriptionEntryFormat1Context.children.get(1).getText();
        }

        List<String> link = new ArrayList<>();
        link.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
        link.add(levelNumber + ": " + variableName);
        links.add(link);

      } else {
        List<String> link = new ArrayList<>();
        link.add(programName);
        link.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
        links.add(link);
      }

    }

    return links;
  }



  private void getDifferentVariableTypes(List<List<String>> variables, List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts, List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts) {
    for (DataDescriptionEntryFormat1Context dataDescriptionEntryFormat1Context : dataDescriptionEntryFormat1Contexts) {
      List<String> variable = new ArrayList<>();
      variable.add(dataDescriptionEntryFormat1Context.children.get(0).getText());
      if (dataDescriptionEntryFormat1Context.children.get(1) instanceof DataNameContext) {
        variable.add(
            dataDescriptionEntryFormat1Context.children.get(1).getChild(0).getChild(0).getText());
      } else {
        variable.add(dataDescriptionEntryFormat1Context.children.get(1).getText());
      }
      variables.add(variable);
    }

    for (DataDescriptionEntryFormat2Context dataDescriptionEntryFormat2Context : dataDescriptionEntryFormat2Contexts) {
      List<String> variable = new ArrayList<>();
      variable.add(dataDescriptionEntryFormat2Context.children.get(0).getText());
      if (dataDescriptionEntryFormat2Context.children.get(1) instanceof DataNameContext) {
        variable.add(
            dataDescriptionEntryFormat2Context.children.get(1).getChild(0).getChild(0).getText());
      } else {
        variable.add(dataDescriptionEntryFormat2Context.children.get(1).getText());
      }

      if (dataDescriptionEntryFormat2Context.children.get(2) instanceof DataRenamesClauseContext) {
        DataRenamesClauseContext dataRenamesClauseContext = (DataRenamesClauseContext) dataDescriptionEntryFormat2Context.children.get(2);
        if (dataRenamesClauseContext.children.get(1) instanceof QualifiedDataNameContext) {
          QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) dataRenamesClauseContext.children.get(1);
          String renamedName = qualifiedDataNameContext.children.get(0).getChild(0).getChild(0).getChild(0).getText();
          variable.add(renamedName);
        }
      }

      variables.add(variable);
    }
  }

  private void update(List<List<String>> variables, List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts, Map<String, Integer> updatedFormat1AndFormat3Links, Map<Integer, Integer> format1Andformat3Links){
    int format3i = 0;
    for (DataDescriptionEntryFormat3Context dataDescriptionEntryFormat3Context : dataDescriptionEntryFormat3Contexts) {
      List<String> variable = new ArrayList<>();
      variable.add(dataDescriptionEntryFormat3Context.children.get(0).getText());

      if (dataDescriptionEntryFormat3Context.children.get(1) instanceof ConditionNameContext) {
        variable.add(
            dataDescriptionEntryFormat3Context.children.get(1).getChild(0).getChild(0).getText());
      }

      updatedFormat1AndFormat3Links.put(variable.get(0) + ": " + variable.get(1), format1Andformat3Links.get(format3i));
      format3i++;

      variables.add(variable);

    }
  }

  // Getting Variables of Working Storage Section
  @Override
  public Object visitWorkingStorageSection(WorkingStorageSectionContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();
      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1Andformat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1Andformat3Links);

      List<List<String>> nodes = getNodes(variables, "7", "8");
      List<List<String>> links = getLinks(variables, programName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      nodeLinkManager.addVariables(programName, nodes, links);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    } catch (ContextNotFoundException e) {
      throw new RuntimeException(e);
    }
    return super.visitWorkingStorageSection(ctx);
  }

}
