package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramUnitContext;
import org.antlr.v4.runtime.ParserRuleContext;

public class RetrieveProgramName {

  private RetrieveContext retrieveContext = new RetrieveContext();

  public String getProgramName(ParserRuleContext ctx)
      throws ProgramNameNotFoundException, ContextNotFoundException {

    while (!(ctx instanceof ProgramUnitContext programUnit)) {
      ctx = ctx.getParent();
    }

    IdentificationDivisionContext identificationDivisionContext = retrieveContext.getIdentificationDivisionContext(programUnit);

    ProgramIdParagraphContext programIdParagraphContext = retrieveContext.getProgramIdParagraphContext(identificationDivisionContext);

    ProgramNameContext programNameContext = retrieveContext.getProgramNameContext(programIdParagraphContext);

    return programNameContext.children.get(0).getChild(0).getText();


  }


}
