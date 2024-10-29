package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85BaseVisitor;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryContext;
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
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LinkageSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureCopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.QualifiedDataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.WorkingStorageSectionContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import de.lordlazor.bachelorarbeit.utils.VisitorUtilities;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visitor extends Cobol85BaseVisitor<Object> {

  private JsonUtilities jsonUtilities;
  private VisitorUtilities visitorUtilities = new VisitorUtilities();

  public Visitor(JsonUtilities jsonUtilities) {
    this.jsonUtilities = jsonUtilities;
  }

  /**
   * Get the program name from the program unit context by traversing through the parents of the current context.
   */

  @Override
  public Object visitParagraphName(ParagraphNameContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      String paragraphName = ctx.children.get(0).getText();
      jsonUtilities.addNode(programName, 1);
      jsonUtilities.addNode(paragraphName, 2);
      jsonUtilities.addLink(programName, paragraphName, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitParagraphName(ctx);
  }

  @Override
  public Object visitProcedureCopyStatement(ProcedureCopyStatementContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      String copyName = ctx.children.get(1).getText();
      jsonUtilities.addNode(programName, 1);
      jsonUtilities.addNode(copyName, 3);
      jsonUtilities.addLink(programName, copyName, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitProcedureCopyStatement(ctx);
  }

  @Override
  public Object visitCopyStatement(CopyStatementContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      String copyName = ctx.children.get(1).getText();
      jsonUtilities.addNode(programName, 1);
      jsonUtilities.addNode(copyName, 3);
      jsonUtilities.addLink(programName, copyName, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitCopyStatement(ctx);
  }

  @Override
  public Object visitCallStatement(CallStatementContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      String calledProgramName = ctx.children.get(1).getText();
      calledProgramName = calledProgramName.replace("'", "");
      calledProgramName = calledProgramName.replace("\"", "");
      jsonUtilities.addNode(programName, 1);
      jsonUtilities.addNode(calledProgramName, 4);
      jsonUtilities.addLink(programName, calledProgramName, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitCallStatement(ctx);
  }


  // FileControlClauseContext
  @Override
  public Object visitFileControlEntry(FileControlEntryContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);

      // Select Clause for Getting FD Name

      SelectClauseContext selectClauseContext = null;

      for (int i = 0; i < ctx.children.size(); i++){
        if (ctx.children.get(i) instanceof SelectClauseContext) {
          selectClauseContext = (SelectClauseContext) ctx.children.get(i);
        }
      }

      if (selectClauseContext == null) {
        throw new ContextNotFoundException("selectClauseContext is null");
      }

      FileNameContext fileNameContext = null;

      for (int i = 0; i < selectClauseContext.children.size(); i++){
        if (selectClauseContext.children.get(i) instanceof FileNameContext) {
          fileNameContext = (FileNameContext) selectClauseContext.children.get(i);
        }
      }

      if (fileNameContext == null) {
        throw new ContextNotFoundException("fileNameContext is null");
      }

      String fdName = fileNameContext.children.get(0).getChild(0).getText();


      // File Control Clause

      FileControlClauseContext fileControlClauseContext = null;

      for (int i = 0; i < ctx.children.size(); i++){
        if (ctx.children.get(i) instanceof FileControlClauseContext) {
          fileControlClauseContext = (FileControlClauseContext) ctx.children.get(i);
        }
      }

      if (fileControlClauseContext == null) {
        throw new ContextNotFoundException("fileControlClauseContext is null");
      }

      AssignClauseContext assignClauseContext = null;

      for (int i = 0; i < fileControlClauseContext.children.size(); i++){
        if (fileControlClauseContext.children.get(i) instanceof AssignClauseContext) {
          assignClauseContext = (AssignClauseContext) fileControlClauseContext.children.get(i);
        }
      }

      if (assignClauseContext == null) {
        throw new ContextNotFoundException("assignClauseContext is null");
      }

      LiteralContext literalContext = null;

      for (int i = 0; i < assignClauseContext.children.size(); i++){
        if (assignClauseContext.children.get(i) instanceof LiteralContext) {
          literalContext = (LiteralContext) assignClauseContext.children.get(i);
        }
      }

      if (literalContext == null) {
        throw new ContextNotFoundException("literalContext is null");
      }

      String fileControlClause = literalContext.children.get(0).getText();

      if (fileControlClause == null) {
        throw new VisitorFileNotFoundException("fileControlClause is null");
      }

      fileControlClause = fileControlClause.replace("'", "");
      fileControlClause = fileControlClause.replace("\"", "");
      jsonUtilities.addNode(programName, 1);
      jsonUtilities.addNode(fileControlClause, 6);
      jsonUtilities.addNode(fdName, 5);
      jsonUtilities.addLink(programName, fdName, 1);
      jsonUtilities.addLink(fdName, fileControlClause, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException | VisitorFileNotFoundException |
             ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitFileControlEntry(ctx);
  }



  @Override
  public Object visitFileSection(FileSectionContext ctx){
    try {
      String programName = visitorUtilities.getProgramName(ctx);



      FileDescriptionEntryContext fileDescriptionEntryContext = null;

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof FileDescriptionEntryContext) {
          fileDescriptionEntryContext = (FileDescriptionEntryContext) ctx.children.get(i);
        }
      }


      // File Section is empty
      if (fileDescriptionEntryContext == null) {
        return super.visitFileSection(ctx);
      }


      FileNameContext fileNameContext = null;
      List<DataDescriptionEntryContext> dataDescriptionEntryContexts = new ArrayList<>();

      for (int i = 0; i < fileDescriptionEntryContext.children.size(); i++) {
        if (fileDescriptionEntryContext.children.get(i) instanceof DataDescriptionEntryContext) {
          dataDescriptionEntryContexts.add(
              (DataDescriptionEntryContext) fileDescriptionEntryContext.children.get(i));
        } else if (fileDescriptionEntryContext.children.get(i) instanceof FileNameContext) {
          fileNameContext = (FileNameContext) fileDescriptionEntryContext.children.get(i);
        }
      }


      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();

      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      for (int i = 0; i < dataDescriptionEntryContexts.size(); i++) {
        if (dataDescriptionEntryContexts.get(i).children.get(0) instanceof DataDescriptionEntryFormat1Context) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) dataDescriptionEntryContexts.get(i).children.get(0));
        } else if (dataDescriptionEntryContexts.get(i).children.get(0) instanceof DataDescriptionEntryFormat2Context) {
          dataDescriptionEntryFormat2Contexts.add(
              (DataDescriptionEntryFormat2Context) dataDescriptionEntryContexts.get(i).children.get(0));
        } else if (dataDescriptionEntryContexts.get(i).children.get(0) instanceof DataDescriptionEntryFormat3Context) {
          dataDescriptionEntryFormat3Contexts.add(
              (DataDescriptionEntryFormat3Context) dataDescriptionEntryContexts.get(i).children.get(0));

          format1Andformat3Links.put(dataDescriptionEntryFormat3Contexts.size() - 1,
              dataDescriptionEntryFormat1Contexts.size() - 1);
        }
      }



        // Get FileName
      if (fileNameContext == null) {
        throw new ContextNotFoundException("fileNameContext is null");
      }

      String fdName = fileNameContext.children.get(0).getChild(0).getText();
      jsonUtilities.addNode(fdName, 5);
      jsonUtilities.addLink(programName, fdName, 1);

      jsonUtilities.addNode(programName, 1);

      jsonUtilities.addLink("Root", programName, 1);


      // Get Variables

      List<List<String>> variables = new ArrayList<>();

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

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();
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

      List<List<String>> nodes = getNodes(variables, "9", "10");
      List<List<String>> links = getLinks(variables, fdName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);


      variablesToJson(programName, nodes, links);


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
      String programName = visitorUtilities.getProgramName(ctx);

      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();

      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat1Context) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) ctx.children.get(i).getChild(0));
        }
        else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat2Context) {
          dataDescriptionEntryFormat2Contexts.add(
              (DataDescriptionEntryFormat2Context) ctx.children.get(i).getChild(0));
        } else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat3Context) {
          dataDescriptionEntryFormat3Contexts.add(
              (DataDescriptionEntryFormat3Context) ctx.children.get(i).getChild(0));

          format1Andformat3Links.put(dataDescriptionEntryFormat3Contexts.size() - 1, dataDescriptionEntryFormat1Contexts.size() - 1);
        }

      }

      List<List<String>> variables = new ArrayList<>();

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

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();
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

      List<List<String>> nodes = getNodes(variables, "11", "12");
      List<List<String>> links = getLinks(variables, programName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      variablesToJson(programName, nodes, links);


    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitLinkageSection(ctx);
  }

  private void variablesToJson(String programName, List<List<String>> nodes, List<List<String>> links) {
    for(List<String> node : nodes){
      jsonUtilities.addNode(node.get(0), Integer.parseInt(node.get(1)));
    }

    for(List<String> link : links){
      jsonUtilities.addLink(link.get(0), link.get(1), 1);
    }

    jsonUtilities.addNode(programName, 1);

    jsonUtilities.addLink("Root", programName, 1);
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




  // Getting Variables of Working Storage Section
  @Override
  public Object visitWorkingStorageSection(WorkingStorageSectionContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts = new ArrayList<>();
      List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts = new ArrayList<>();

      Map<Integer, Integer> format1Andformat3Links = new HashMap<>();

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat1Context) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) ctx.children.get(i).getChild(0));
        }
        else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat2Context) {
          dataDescriptionEntryFormat2Contexts.add(
              (DataDescriptionEntryFormat2Context) ctx.children.get(i).getChild(0));
        } else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat3Context) {
          dataDescriptionEntryFormat3Contexts.add(
              (DataDescriptionEntryFormat3Context) ctx.children.get(i).getChild(0));

          format1Andformat3Links.put(dataDescriptionEntryFormat3Contexts.size() - 1, dataDescriptionEntryFormat1Contexts.size() - 1);
        }

      }

      List<List<String>> variables = new ArrayList<>();

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

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();
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

      List<List<String>> nodes = getNodes(variables, "7", "8");
      List<List<String>> links = getLinks(variables, programName, updatedFormat1AndFormat3Links, dataDescriptionEntryFormat1Contexts);

      variablesToJson(programName, nodes, links);


    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitWorkingStorageSection(ctx);
  }

}
