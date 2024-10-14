package de.lordlazor.bachelorarbeit.grammar;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramUnitContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.ParserRuleContext;

public class Visitor extends Cobol85BaseVisitor<Object> {

  private JsonUtilities jsonUtilities;

  public Visitor(JsonUtilities jsonUtilities) {
    this.jsonUtilities = jsonUtilities;
  }

  /**
   * Get the program name from the program unit context by traversing through the parents of the current context.
   */

  // ParagraphNameContext

  public String getProgramName(Cobol85Parser.ParagraphNameContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // CopyStatementContext
  public String getProgramName(Cobol85Parser.CopyStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // ProcedureCopyStatementContext
  public String getProgramName(Cobol85Parser.ProcedureCopyStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // CallStatementContext
  public String getProgramName(Cobol85Parser.CallStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  private String getProgramName(Object parent)
      throws ProgramNameNotFoundException {

    while (!(parent instanceof Cobol85Parser.ProgramUnitContext)) {
      parent = ((ParserRuleContext) parent).getParent();
    }

    if (parent instanceof Cobol85Parser.ProgramUnitContext) {
      Cobol85Parser.ProgramUnitContext programUnit = (ProgramUnitContext) parent;

      //IdentificationDivisionContext
      Cobol85Parser.IdentificationDivisionContext identificationDivisionContext = null;

      for (int i = 0; i < programUnit.children.size(); i++) {
        ParserRuleContext child = (ParserRuleContext) programUnit.children.get(i);
        if (child instanceof Cobol85Parser.IdentificationDivisionContext) {
          identificationDivisionContext = (IdentificationDivisionContext) child;
          break;
        }
      }

      if (identificationDivisionContext == null) {
        throw new ProgramNameNotFoundException("identificationDivisionContext is null");
      }

      //ProgramIdParagraphContext
      Cobol85Parser.ProgramIdParagraphContext programIdParagraphContext = null;

      for (int i = 0; i < identificationDivisionContext.children.size(); i++) {
        if (identificationDivisionContext.children.get(i) instanceof Cobol85Parser.ProgramIdParagraphContext) {
          programIdParagraphContext = (ProgramIdParagraphContext) identificationDivisionContext.children.get(i);;
        }
      }

      if (programIdParagraphContext == null) {
        throw new ProgramNameNotFoundException("programIdParagraphContext is null");
      }

      //ProgramNameContext
      Cobol85Parser.ProgramNameContext programNameContext = null;

      for (int i = 0; i < programIdParagraphContext.children.size(); i++) {
        if (programIdParagraphContext.children.get(i) instanceof Cobol85Parser.ProgramNameContext) {
          programNameContext = (Cobol85Parser.ProgramNameContext) programIdParagraphContext.children.get(i);
        }
      }

      return programNameContext.children.get(0).getChild(0).getText();

    }


    throw new ProgramNameNotFoundException("ProgramUnitContext not found");
  }

  @Override
  public Object visitParagraphName(Cobol85Parser.ParagraphNameContext ctx) {
    try {
      String programName = getProgramName(ctx);
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
  public Object visitProcedureCopyStatement(Cobol85Parser.ProcedureCopyStatementContext ctx) {
    try {
      String programName = getProgramName(ctx);
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
  public Object visitCopyStatement(Cobol85Parser.CopyStatementContext ctx) {
    try {
      String programName = getProgramName(ctx);
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
  public Object visitCallStatement(Cobol85Parser.CallStatementContext ctx) {
    try {
      String programName = getProgramName(ctx);
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

  @Override
  public Object visitFileControlClause(Cobol85Parser.FileControlClauseContext ctx) {
    try {
      String programName = getProgramName(ctx);

      AssignClauseContext assignClauseContext = null;

      for (int i = 0; i < ctx.children.size(); i++){
        if (ctx.children.get(i) instanceof Cobol85Parser.AssignClauseContext) {
          assignClauseContext = (AssignClauseContext) ctx.children.get(i);
        }
      }

      if (assignClauseContext == null) {
        throw new ContextNotFoundException("assignClauseContext is null");
      }

      LiteralContext literalContext = null;

      for (int i = 0; i < assignClauseContext.children.size(); i++){
        if (assignClauseContext.children.get(i) instanceof Cobol85Parser.LiteralContext) {
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
      jsonUtilities.addNode(fileControlClause, 5);
      jsonUtilities.addLink(programName, fileControlClause, 1);
      jsonUtilities.addLink("Root", programName, 1);
    } catch (ProgramNameNotFoundException | VisitorFileNotFoundException |
             ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitFileControlClause(ctx);
  }


  // Getting Variables of Working Storage Section
  @Override
  public Object visitWorkingStorageSection(Cobol85Parser.WorkingStorageSectionContext ctx) {
    try {
      String programName = getProgramName(ctx);
      List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts = new ArrayList<>();

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof Cobol85Parser.DataDescriptionEntryContext) {
          dataDescriptionEntryFormat1Contexts.add(
              (DataDescriptionEntryFormat1Context) ctx.children.get(i).getChild(0));
        }
      }

      List<List<String>> variables = new ArrayList<>();

      for (DataDescriptionEntryFormat1Context dataDescriptionEntryFormat1Context : dataDescriptionEntryFormat1Contexts) {
        List<String> variable = new ArrayList<>();
        variable.add(dataDescriptionEntryFormat1Context.children.get(0).getText());
        variable.add(dataDescriptionEntryFormat1Context.children.get(1).getChild(0).getChild(0).getText());
        variables.add(variable);
      }

      // TODO: Maybe add PICTURE CLAUSES to identify the type of the variable

      List<List<String>> nodes = new ArrayList<>();
      List<List<String>> links = new ArrayList<>();

      for (int i = variables.size() - 1; i >= 0; i--) {
        int currentLevelNumber = Integer.parseInt(variables.get(i).get(0));

        List<String> node = new ArrayList<>();

        // 6: VARIABLE; 7: SUBVARIABLE
        if (currentLevelNumber == 1){
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("6");
        } else {
          node.add(variables.get(i).get(0) + ": " + variables.get(i).get(1));
          node.add("7");
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
