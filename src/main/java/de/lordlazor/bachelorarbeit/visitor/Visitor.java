package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85BaseVisitor;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureCopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.WorkingStorageSectionContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import de.lordlazor.bachelorarbeit.utils.VisitorUtilities;
import java.util.ArrayList;
import java.util.List;

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

      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();
      FileNameContext fileNameContext = null;


      for (int i = 0; i < fileDescriptionEntryContext.children.size(); i++) {
        if (fileDescriptionEntryContext.children.get(i) instanceof DataDescriptionEntryContext) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) fileDescriptionEntryContext.children.get(i).getChild(0));
        } else if (fileDescriptionEntryContext.children.get(i) instanceof FileNameContext) {
          fileNameContext = (FileNameContext) fileDescriptionEntryContext.children.get(i);
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


      List<List<String>> nodes = new ArrayList<>();
      List<List<String>> links = new ArrayList<>();

      for (int i = variables.size() - 1; i >= 0; i--) {
        int currentLevelNumber = Integer.parseInt(variables.get(i).get(0));

        List<String> node = new ArrayList<>();

        // 9: FILEVARIABLE; 10: FILESUBVARIABLE
        if (currentLevelNumber == 1){
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("9");
        } else {
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("10");
        }

        nodes.add(node);

        if(currentLevelNumber != 1){
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
        } else {
          List<String> link = new ArrayList<>();
          link.add(fdName);
          link.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          links.add(link);
        }

      }

      for(List<String> node : nodes){
        jsonUtilities.addNode(node.get(0), Integer.parseInt(node.get(1)));
      }

      for(List<String> link : links){
        jsonUtilities.addLink(link.get(0), link.get(1), 1);
      }



    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    } catch (ContextNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitFileSection(ctx);
  }



  // Getting Variables of Working Storage Section
  @Override
  public Object visitWorkingStorageSection(WorkingStorageSectionContext ctx) {
    try {
      String programName = visitorUtilities.getProgramName(ctx);
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat1Context) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) ctx.children.get(i).getChild(0));
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

      // TODO: Maybe add PICTURE CLAUSES to identify the type of the variable

      List<List<String>> nodes = new ArrayList<>();
      List<List<String>> links = new ArrayList<>();

      for (int i = variables.size() - 1; i >= 0; i--) {
        int currentLevelNumber = Integer.parseInt(variables.get(i).get(0));

        List<String> node = new ArrayList<>();

        // 7: VARIABLE; 8: SUBVARIABLE
        if (currentLevelNumber == 1){
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("7");
        } else {
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("8");
        }

        nodes.add(node);

        if(currentLevelNumber != 1){
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
        } else {
          List<String> link = new ArrayList<>();
          link.add(programName);
          link.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          links.add(link);
        }

      }

      for(List<String> node : nodes){
        jsonUtilities.addNode(node.get(0), Integer.parseInt(node.get(1)));
      }

      for(List<String> link : links){
        jsonUtilities.addLink(link.get(0), link.get(1), 1);
      }

      jsonUtilities.addNode(programName, 1);

      jsonUtilities.addLink("Root", programName, 1);


    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitWorkingStorageSection(ctx);
  }

}
