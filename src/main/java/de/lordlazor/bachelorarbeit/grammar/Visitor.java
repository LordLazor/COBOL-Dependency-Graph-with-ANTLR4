package de.lordlazor.bachelorarbeit.grammar;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramUnitContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
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
      jsonUtilities.addNode(programName, 1); // TODO: change groups
      jsonUtilities.addNode(paragraphName, 1); // TODO: change groups
      jsonUtilities.addLink(programName, paragraphName, 1); // TODO: change values
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
      jsonUtilities.addNode(programName, 1); // TODO: change groups
      jsonUtilities.addNode(copyName, 1); // TODO: change groups
      jsonUtilities.addLink(programName, copyName, 1); // TODO: change values
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
      jsonUtilities.addNode(programName, 1); // TODO: change groups
      jsonUtilities.addNode(copyName, 1); // TODO: change groups
      jsonUtilities.addLink(programName, copyName, 1); // TODO: change values
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
      jsonUtilities.addNode(programName, 1); // TODO: change groups
      jsonUtilities.addNode(calledProgramName, 1); // TODO: change groups
      jsonUtilities.addLink(programName, calledProgramName, 1); // TODO: change values
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
      jsonUtilities.addNode(programName, 1); // TODO: change groups
      jsonUtilities.addNode(fileControlClause, 1); // TODO: change groups
      jsonUtilities.addLink(programName, fileControlClause, 1); // TODO: change values
    } catch (ProgramNameNotFoundException | VisitorFileNotFoundException |
             ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitFileControlClause(ctx);
  }

}
