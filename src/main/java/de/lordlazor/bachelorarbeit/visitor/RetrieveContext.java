package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferencePhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingParameterContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CobolWordContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat2Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat3Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class RetrieveContext {

  public CobolWordContext getCobolWordContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof CobolWordContext) {
        return (CobolWordContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("cobolWordContext is null");

  }

  public SelectClauseContext getSelectClauseContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof SelectClauseContext) {
        return (SelectClauseContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("selectClauseContext is null");

  }

  public CallUsingPhraseContext getCallUsingPhraseContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof CallUsingPhraseContext) {
        return (CallUsingPhraseContext) ctx.children.get(i);
      }
    }

    return null; // Does not throw error because Call Using can be empty and is checked in the visitor
    //throw new ContextNotFoundException("callUsingPhraseContext is null");

  }

  public CallUsingParameterContext getCallUsingParameterContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof CallUsingParameterContext) {
        return (CallUsingParameterContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("callUsingParameterContext is null");

  }

  public CallByReferencePhraseContext getCallByReferencePhraseContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof CallByReferencePhraseContext) {
        return (CallByReferencePhraseContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("callByReferencePhraseContext is null");

  }

  public FileNameContext getFileNameContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof FileNameContext) {
        return (FileNameContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("fileNameContext is null");

  }

  public FileControlClauseContext getFileControlClauseContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof FileControlClauseContext) {
        return (FileControlClauseContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("fileControlClauseContext is null");

  }

  public AssignClauseContext getAssignClauseContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof AssignClauseContext) {
        return (AssignClauseContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("assignClauseContext is null");

  }

  public LiteralContext getLiteralContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof LiteralContext) {
        return (LiteralContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("literalContext is null");

  }

  public FileDescriptionEntryContext getFileDescriptionEntryContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof FileDescriptionEntryContext) {
        return (FileDescriptionEntryContext) ctx.children.get(i);
      }
    }
    return null;
    // Does not throw error because File Section can be empty

  }

  public IdentificationDivisionContext getIdentificationDivisionContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof IdentificationDivisionContext) {
        return (IdentificationDivisionContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("identificationDivisionContext is null");
  }

  public ProgramIdParagraphContext getProgramIdParagraphContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ProgramIdParagraphContext) {
        return (ProgramIdParagraphContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("programIdParagraphContext is null");
  }

  public ProgramNameContext getProgramNameContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ProgramNameContext) {
        return (ProgramNameContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("programNameContext is null");
  }

  public void getDataDescriptionEntryFormatContexts(ParserRuleContext ctx, Map<Integer, Integer> format1Andformat3Links, List<DataDescriptionEntryFormat1Context> dataDescriptionEntryFormat1Contexts, List<DataDescriptionEntryFormat2Context> dataDescriptionEntryFormat2Contexts, List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts) {
    for (int i = 0; i < ctx.children.size(); i++) {
      if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat1Context) {
        dataDescriptionEntryFormat1Contexts.add(
            (DataDescriptionEntryFormat1Context) ctx.children.get(i).getChild(0));
      } else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat2Context) {
        dataDescriptionEntryFormat2Contexts.add(
            (DataDescriptionEntryFormat2Context) ctx.children.get(i).getChild(0));
      } else if (ctx.children.get(i).getChild(0) instanceof DataDescriptionEntryFormat3Context) {
        dataDescriptionEntryFormat3Contexts.add(
            (DataDescriptionEntryFormat3Context) ctx.children.get(i).getChild(0));

        format1Andformat3Links.put(dataDescriptionEntryFormat3Contexts.size() - 1,
            dataDescriptionEntryFormat1Contexts.size() - 1);
      }
    }
  }

}
