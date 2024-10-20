package de.lordlazor.bachelorarbeit.utils;

import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureCopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramUnitContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.WorkingStorageSectionContext;
import org.antlr.v4.runtime.ParserRuleContext;

public class VisitorUtilities {

  // ParagraphNameContext
  public String getProgramName(ParagraphNameContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // CopyStatementContext
  public String getProgramName(CopyStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // ProcedureCopyStatementContext
  public String getProgramName(ProcedureCopyStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // CallStatementContext
  public String getProgramName(CallStatementContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // WorkingStorageSectionContext
  public String getProgramName(WorkingStorageSectionContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // FileSectionContext
  public String getProgramName(FileSectionContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  // FileControlClauseContext
  public String getProgramName(FileControlEntryContext ctx)
      throws ProgramNameNotFoundException {
    Object parent = ctx.getParent();
    return getProgramName(parent);
  }

  private String getProgramName(Object parent)
      throws ProgramNameNotFoundException {

    while (!(parent instanceof ProgramUnitContext)) {
      parent = ((ParserRuleContext) parent).getParent();
    }

    if (parent instanceof ProgramUnitContext) {
      ProgramUnitContext programUnit = (ProgramUnitContext) parent;

      //IdentificationDivisionContext
      IdentificationDivisionContext identificationDivisionContext = null;

      for (int i = 0; i < programUnit.children.size(); i++) {
        ParserRuleContext child = (ParserRuleContext) programUnit.children.get(i);
        if (child instanceof IdentificationDivisionContext) {
          identificationDivisionContext = (IdentificationDivisionContext) child;
          break;
        }
      }

      if (identificationDivisionContext == null) {
        throw new ProgramNameNotFoundException("identificationDivisionContext is null");
      }

      //ProgramIdParagraphContext
      ProgramIdParagraphContext programIdParagraphContext = null;

      for (int i = 0; i < identificationDivisionContext.children.size(); i++) {
        if (identificationDivisionContext.children.get(i) instanceof ProgramIdParagraphContext) {
          programIdParagraphContext = (ProgramIdParagraphContext) identificationDivisionContext.children.get(i);;
        }
      }

      if (programIdParagraphContext == null) {
        throw new ProgramNameNotFoundException("programIdParagraphContext is null");
      }

      //ProgramNameContext
      ProgramNameContext programNameContext = null;

      for (int i = 0; i < programIdParagraphContext.children.size(); i++) {
        if (programIdParagraphContext.children.get(i) instanceof ProgramNameContext) {
          programNameContext = (ProgramNameContext) programIdParagraphContext.children.get(i);
        }
      }

      return programNameContext.children.get(0).getChild(0).getText();

    }


    throw new ProgramNameNotFoundException("ProgramUnitContext not found");
  }


}
