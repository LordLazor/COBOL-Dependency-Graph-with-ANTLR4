package de.lordlazor.bachelorarbeit.grammar;

import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramUnitContext;
import org.antlr.v4.runtime.ParserRuleContext;

public class Visitor extends Cobol85BaseVisitor<Object> {

  /**
   * Get the program name from the program unit context by traversing through the parents of the current context.
   */
  private String getProgramName(Cobol85Parser.ParagraphNameContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
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

      System.out.println("Paragraph name: " + paragraphName + " in program: " + programName);

    } catch (ProgramNameNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitParagraphName(ctx);
  }
}
