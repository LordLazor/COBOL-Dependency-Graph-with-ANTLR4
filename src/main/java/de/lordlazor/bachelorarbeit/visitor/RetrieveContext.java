package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.BasisContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferencePhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingParameterContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CobolWordContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat2Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat3Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DivideByGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentificationDivisionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentifierContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultDivsContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyRegularOperandContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.PowersContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.RelationArithmeticComparisonContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.RelationConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SimpleConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractFromGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractFromStatementContext;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class RetrieveContext {

  public ProcedureNameContext getProcedureNameContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ProcedureNameContext) {
        return (ProcedureNameContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("procedureNameContext is null");
  }

  public ParagraphNameContext getParagraphNameContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ParagraphNameContext) {
        return (ParagraphNameContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("paragraphNameContext is null");
  }

  public MultDivsContext getMultDivsContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof MultDivsContext) {
        return (MultDivsContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("multDivsContext is null");
  }
  public ConditionNameContext getConditionNameContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ConditionNameContext) {
        return (ConditionNameContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("conditionNameContext is null");
  }

  public PowersContext getPowersContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof PowersContext) {
        return (PowersContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("powersContext is null");
  }

  public BasisContext getBasisContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof BasisContext) {
        return (BasisContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("basisContext is null");
  }

  public SimpleConditionContext getSimpleConditionContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof SimpleConditionContext) {
        return (SimpleConditionContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("simpleConditionContext is null");
  }

  public RelationConditionContext getRelationConditionContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof RelationConditionContext) {
        return (RelationConditionContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("relationConditionContext is null");
  }

  public RelationArithmeticComparisonContext getRelationArithmeticComparisonContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof RelationArithmeticComparisonContext) {
        return (RelationArithmeticComparisonContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("relationArithmeticComparisonContext is null");
  }

  public ConditionContext getConditionContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof ConditionContext) {
        return (ConditionContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("conditionContext is null");
  }
  public DivideByGivingStatementContext getDivideByGivingStatementContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof DivideByGivingStatementContext) {
        return (DivideByGivingStatementContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("divideByGivingStatementContext is null");
  }

  public MultiplyRegularOperandContext getMultiplyRegularOperandContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof MultiplyRegularOperandContext) {
        return (MultiplyRegularOperandContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("multiplyRegularOperandContext is null");
  }

  public SubtractFromGivingStatementContext getSubtractFromGivingStatementContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof SubtractFromGivingStatementContext) {
        return (SubtractFromGivingStatementContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("subtractFromGivingStatementContext is null");
  }
  public AddToGivingStatementContext getAddToGivingStatementContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof AddToGivingStatementContext) {
        return (AddToGivingStatementContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("addToGivingStatementContext is null");
  }
  public SubtractFromStatementContext getSubtractFromStatementContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof SubtractFromStatementContext) {
        return (SubtractFromStatementContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("subtractFromStatementContext is null");
  }

  public IdentifierContext getIdentifierContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof IdentifierContext) {
        return (IdentifierContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("identifierContext is null");
  }

  public AddToStatementContext getAddToStatementContext(ParserRuleContext ctx)
      throws ContextNotFoundException {
    for (int i = 0; i < ctx.children.size(); i++){
      if (ctx.children.get(i) instanceof AddToStatementContext) {
        return (AddToStatementContext) ctx.children.get(i);
      }
    }

    throw new ContextNotFoundException("addToStatementContext is null");
  }

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
