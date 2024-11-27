package de.lordlazor.bachelorarbeit.visitor;

import de.lordlazor.bachelorarbeit.exceptions.ContextNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.JsonNodeNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.ProgramNameNotFoundException;
import de.lordlazor.bachelorarbeit.exceptions.VisitorFileNotFoundException;
import de.lordlazor.bachelorarbeit.grammar.Cobol85BaseVisitor;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AcceptStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddFromContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AddToStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AndOrConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ArithmeticExpressionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.AssignClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.BasisContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferenceContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallByReferencePhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingParameterContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CallUsingPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CobolWordContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CombinableConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ConditionNameReferenceContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.CopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat2Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataDescriptionEntryFormat3Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DataRenamesClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DivideByGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DivideGivingPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.DivideStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.EvaluateSelectContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.EvaluateStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.EvaluateWhenPhraseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileControlEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileDescriptionEntryContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.FileSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.GoToStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.GoToStatementSimpleContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IdentifierContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IfStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.IfThenContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LinkageSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LiteralContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.LocalStorageSectionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MoveStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MoveToStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultDivsContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyGivingOperandContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyGivingResultContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyRegularContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyRegularOperandContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.MultiplyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ParagraphNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.PerformProcedureStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.PerformStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.PowersContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureCopyStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProcedureNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramIdParagraphContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.ProgramNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.QualifiedDataNameContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.QualifiedDataNameFormat1Context;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.RelationArithmeticComparisonContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.RelationConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SelectClauseContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SetStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SetToContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SetToStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SimpleConditionContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.StatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractFromGivingStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractFromStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractMinuendContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractMinuendGivingContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractStatementContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.SubtractSubtrahendContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.TableCallContext;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser.WorkingStorageSectionContext;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import de.lordlazor.bachelorarbeit.utils.VisitorUtilites;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ParserRuleContext;

public class Visitor extends Cobol85BaseVisitor<Object> {
  /*
  This class is used to visit the parse tree and extract information from it to create a JSON file that can be used to visualize the program as a graph
  Each visitXXX-Method is called when the visitor encounters a node of the parse tree that matches the corresponding rule in the grammar
  After visiting a node, the visitor extracts information from the nodes children and adds it to the JSON file
  */

  private final RetrieveProgramName retrieveProgramName;
  private final RetrieveContext retrieveContext;
  private final NodeLinkManager nodeLinkManager;

  public Visitor(JsonUtilities jsonUtilities) {
    this.nodeLinkManager = new NodeLinkManager(jsonUtilities);

    this.retrieveProgramName = new RetrieveProgramName();
    this.retrieveContext = new RetrieveContext();
  }

  // Any parent of context instance of EvaluateStatementContext (true/false)
  public boolean isEvaluateStatement(ParserRuleContext ctx) {
    while (ctx != null) {
      if (ctx instanceof EvaluateStatementContext) {
        return true;
      }
      ctx = ctx.getParent();
    }
    return false;
  }

  // Any parent of context instance of IfStatementContext (true/false)
  public boolean isIfStatement(ParserRuleContext ctx) {
    while (ctx != null) {
      if (ctx instanceof IfStatementContext) {
        return true;
      }
      ctx = ctx.getParent();
    }
    return false;
  }




  @Override
  public Object visitEvaluateStatement(EvaluateStatementContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      String currentEvaluateNodeName = "EVALUATE:" + VisitorUtilites.currentEvaluate;


      for (int i = 0; i < ctx.children.size(); i++) {
        // Get Evaluate Select Context
        if (ctx.children.get(i) instanceof EvaluateSelectContext evaluateSelectContext) {
          for(int j = 0; j < evaluateSelectContext.children.size(); j++) {
            if(evaluateSelectContext.children.get(j) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentEvaluateNodeName, 23);
              nodeLinkManager.addLink(programName, currentEvaluateNodeName);
              nodeLinkManager.addLink(currentEvaluateNodeName, variableWithLevelNumber);
            }
          }
        } else if(ctx.children.get(i) instanceof EvaluateWhenPhraseContext evaluateWhenPhraseContext) {
          for(int j = 0; j < evaluateWhenPhraseContext.children.size(); j++) {
            if(evaluateWhenPhraseContext.children.get(j) instanceof StatementContext statementContext) {

              extractInsideControlStructure(statementContext, programName, currentEvaluateNodeName);
            }
          }
        }
      }


      VisitorUtilites.currentEvaluate += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitEvaluateStatement(ctx);
  }



  @Override
  public Object visitAddStatement(AddStatementContext ctx) {
    try{
      if (isEvaluateStatement(ctx)) {
        return super.visitAddStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitAddStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      if (ctx.children.get(1) instanceof  AddToStatementContext) {

        AddToStatementContext addToStatementContext = retrieveContext.getAddToStatementContext(ctx);

        String currentAddNodeName = "ADD:" + VisitorUtilites.currentAdd;

        for (int i = 0; i < addToStatementContext.children.size(); i++) {
          if (addToStatementContext.children.get(i) instanceof AddFromContext
              || addToStatementContext.children.get(i) instanceof AddToContext) {
            if (addToStatementContext.children.get(i).getChild(0) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentAddNodeName, 15);
              nodeLinkManager.addLink(programName, currentAddNodeName);
              nodeLinkManager.addLink(currentAddNodeName, variableWithLevelNumber);
            }
          }
        }
      } else if (ctx.children.get(1) instanceof AddToGivingStatementContext) {

        AddToGivingStatementContext addToGivingStatementContext = retrieveContext.getAddToGivingStatementContext(ctx);

        String currentAddNodeName = "ADD:" + VisitorUtilites.currentAdd;

        for (int i = 0; i < addToGivingStatementContext.children.size(); i++) {
          if (addToGivingStatementContext.children.get(i) instanceof AddFromContext
              || addToGivingStatementContext.children.get(i) instanceof AddToGivingContext
              || addToGivingStatementContext.children.get(i) instanceof AddGivingContext) {
            if (addToGivingStatementContext.children.get(i).getChild(0) instanceof IdentifierContext identifierContext) {

              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentAddNodeName, 15);
              nodeLinkManager.addLink(programName, currentAddNodeName);
              nodeLinkManager.addLink(currentAddNodeName, variableWithLevelNumber);
            }
          }
        }

      }
      VisitorUtilites.currentAdd  += 1;

    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    } catch (ProgramNameNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitAddStatement(ctx);
  }

  private void retrieveMultiplyRegularOperandContext(MultiplyRegularContext multiplyRegularContext, String programName, String currentMultiplyNodeName)
      throws ContextNotFoundException {
    MultiplyRegularOperandContext multiplyRegularOperandContext = retrieveContext.getMultiplyRegularOperandContext(multiplyRegularContext);

    IdentifierContext identifierContext = retrieveContext.getIdentifierContext(multiplyRegularOperandContext);

    String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

    nodeLinkManager.addNodeWithoutRoot(currentMultiplyNodeName, 17);
    nodeLinkManager.addLink(programName, currentMultiplyNodeName);
    nodeLinkManager.addLink(currentMultiplyNodeName, variableWithLevelNumber);
  }

  @Override
  public Object visitSubtractStatement(SubtractStatementContext ctx){
    try{
      if (isEvaluateStatement(ctx)) {
        return super.visitSubtractStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitSubtractStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);
      if (ctx.children.get(1) instanceof SubtractFromStatementContext) {

        SubtractFromStatementContext subtractFromStatementContext = retrieveContext.getSubtractFromStatementContext(ctx);

        String currentSubtractNodeName = "SUBTRACT:" + VisitorUtilites.currentSubtract;

        for (int i = 0; i < subtractFromStatementContext.children.size(); i++) {
          if (subtractFromStatementContext.children.get(i) instanceof SubtractSubtrahendContext
              || subtractFromStatementContext.children.get(i) instanceof SubtractMinuendContext) {

            if(subtractFromStatementContext.children.get(i).getChild(0) instanceof IdentifierContext identifierContext) {

              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentSubtractNodeName, 16);
              nodeLinkManager.addLink(programName, currentSubtractNodeName);
              nodeLinkManager.addLink(currentSubtractNodeName, variableWithLevelNumber);
            }
          }
        }
      } else if (ctx.children.get(1) instanceof SubtractFromGivingStatementContext) {
        SubtractFromGivingStatementContext subtractFromGivingStatementContext = retrieveContext.getSubtractFromGivingStatementContext(ctx);

        String currentSubtractNodeName = "SUBTRACT:" + VisitorUtilites.currentSubtract;

        for (int i = 0; i < subtractFromGivingStatementContext.children.size(); i++) {
          if (subtractFromGivingStatementContext.children.get(i) instanceof SubtractSubtrahendContext
              || subtractFromGivingStatementContext.children.get(i) instanceof SubtractMinuendGivingContext
              || subtractFromGivingStatementContext.children.get(i) instanceof SubtractGivingContext) {
            if(subtractFromGivingStatementContext.children.get(i).getChild(0) instanceof IdentifierContext identifierContext) {

            String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

            nodeLinkManager.addNodeWithoutRoot(currentSubtractNodeName, 16);
            nodeLinkManager.addLink(programName, currentSubtractNodeName);
            nodeLinkManager.addLink(currentSubtractNodeName, variableWithLevelNumber);
            }
          }
        }
      }
      VisitorUtilites.currentSubtract  += 1;

    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    } catch (ProgramNameNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitSubtractStatement(ctx);
  }

  @Override
  public Object visitMultiplyStatement(MultiplyStatementContext ctx){
    try{

      if (isEvaluateStatement(ctx)) {
        return super.visitMultiplyStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitMultiplyStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentMultiplyNodeName = "MULTIPLY:" + VisitorUtilites.currentMultiply;

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof IdentifierContext) {
          String variableWithLevelNumber = extractVariableWithLevelNumber((IdentifierContext) ctx.children.get(i));

          nodeLinkManager.addNodeWithoutRoot(currentMultiplyNodeName, 17);
          nodeLinkManager.addLink(programName, currentMultiplyNodeName);
          nodeLinkManager.addLink(currentMultiplyNodeName, variableWithLevelNumber);
        } else if (ctx.children.get(i) instanceof MultiplyRegularContext multiplyRegularContext) {
          retrieveMultiplyRegularOperandContext(multiplyRegularContext, programName, currentMultiplyNodeName);
        } else if (ctx.children.get(i) instanceof MultiplyGivingContext multiplyGivingContext){
          for(int j = 0; j < multiplyGivingContext.children.size(); j++){
            if (multiplyGivingContext.children.get(j) instanceof MultiplyGivingOperandContext
                || multiplyGivingContext.children.get(j) instanceof MultiplyGivingResultContext) {

              if(multiplyGivingContext.children.get(j).getChild(0) instanceof IdentifierContext identifierContext){
                String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

                nodeLinkManager.addNodeWithoutRoot(currentMultiplyNodeName, 17);
                nodeLinkManager.addLink(programName, currentMultiplyNodeName);
                nodeLinkManager.addLink(currentMultiplyNodeName, variableWithLevelNumber);
            }
            }
          }

        }
      }
      VisitorUtilites.currentMultiply  += 1;

    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    } catch (ProgramNameNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitMultiplyStatement(ctx);
  }

  @Override
  public Object visitDivideStatement(DivideStatementContext ctx){
    try{

      if (isEvaluateStatement(ctx)) {
        return super.visitDivideStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitDivideStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentDivideNodeName = "DIVIDE:" + VisitorUtilites.currentDivide;

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof IdentifierContext) {
          String variableWithLevelNumber = extractVariableWithLevelNumber((IdentifierContext) ctx.children.get(i));

          nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
          nodeLinkManager.addLink(programName, currentDivideNodeName);
          nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
        } else if (ctx.children.get(i) instanceof DivideByGivingStatementContext divideByGivingStatementContext) {
          for(int j = 0; j < divideByGivingStatementContext.children.size(); j++){
            if (divideByGivingStatementContext.children.get(j) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
              nodeLinkManager.addLink(programName, currentDivideNodeName);
              nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
            } else if(divideByGivingStatementContext.children.get(j) instanceof DivideGivingPhraseContext divideGivingPhraseContext){
              IdentifierContext identifierContext = retrieveContext.getIdentifierContext((ParserRuleContext) divideGivingPhraseContext.children.get(1));

              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
              nodeLinkManager.addLink(programName, currentDivideNodeName);
              nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
            }
          }


        }
      }
      VisitorUtilites.currentDivide  += 1;

    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    } catch (ProgramNameNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitDivideStatement(ctx);
  }


  private String extractVariableWithLevelNumber(IdentifierContext identifierContext) {
    if(identifierContext.children.get(0) instanceof QualifiedDataNameContext){
      QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) identifierContext.children.get(0);

      QualifiedDataNameFormat1Context qualifiedDataNameFormat1Context = (QualifiedDataNameFormat1Context) qualifiedDataNameContext.children.get(0);

      DataNameContext dataNameContext = (DataNameContext) qualifiedDataNameFormat1Context.children.get(0);

      CobolWordContext cobolWordContext = (CobolWordContext) dataNameContext.children.get(0);

      String variableNameWithoutLevelNumber = cobolWordContext.children.get(0).getText();

      return nodeLinkManager.searchNodeMatchesName(variableNameWithoutLevelNumber);
    } else if(identifierContext.children.get(0) instanceof TableCallContext tableCallContext){
      QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) tableCallContext.children.get(0);

      QualifiedDataNameFormat1Context qualifiedDataNameFormat1Context = (QualifiedDataNameFormat1Context) qualifiedDataNameContext.children.get(0);

      DataNameContext dataNameContext = (DataNameContext) qualifiedDataNameFormat1Context.children.get(0);

      CobolWordContext cobolWordContext = (CobolWordContext) dataNameContext.children.get(0);

      String variableNameWithoutLevelNumber = cobolWordContext.children.get(0).getText();

      return nodeLinkManager.searchNodeMatchesName(variableNameWithoutLevelNumber);
    }

    return "Not found";

  }

  @Override
  public Object visitProgramIdParagraph(ProgramIdParagraphContext ctx) {
    try {
    ProgramNameContext programNameContext = retrieveContext.getProgramNameContext(ctx);

    CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(programNameContext);

    String programName = cobolWordContext.children.get(0).getText();

    nodeLinkManager.addNode(programName, 1);
    } catch (ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitProgramIdParagraph(ctx);
  }

  @Override
  public Object visitParagraphName(ParagraphNameContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);
      String paragraphName = ctx.children.get(0).getText();
      nodeLinkManager.addNodeWithoutRoot(paragraphName, 2);
      nodeLinkManager.addLink(programName, paragraphName);
    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitParagraphName(ctx);
  }

  @Override
  public Object visitSetStatement(SetStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitSetStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitSetStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentSetNodeName = "SET:" + VisitorUtilites.currentSet;

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof SetToStatementContext setToStatementContext) {
          SetToContext setToContext = retrieveContext.getSetToContext(setToStatementContext);
          IdentifierContext identifierContext = retrieveContext.getIdentifierContext(setToContext);

          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

          nodeLinkManager.addNodeWithoutRoot(currentSetNodeName, 22);
          nodeLinkManager.addLink(programName, currentSetNodeName);
          nodeLinkManager.addLink(currentSetNodeName, variableWithLevelNumber);
        }
      }

      VisitorUtilites.currentSet += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitSetStatement(ctx);
  }

  @Override
  public Object visitGoToStatement(GoToStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitGoToStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitGoToStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentGoToNodeName = "GOTO:" + VisitorUtilites.currentGoTo;

      for (int i = 0; i < ctx.children.size(); i++) {
        if (ctx.children.get(i) instanceof GoToStatementSimpleContext goToStatementSimpleContext) {
          ProcedureNameContext procedureNameContext = retrieveContext.getProcedureNameContext(goToStatementSimpleContext);
          ParagraphNameContext paragraphNameContext = retrieveContext.getParagraphNameContext(procedureNameContext);
          CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(paragraphNameContext);

          String paragraphName = cobolWordContext.children.get(0).getText();

          nodeLinkManager.addNodeWithoutRoot(currentGoToNodeName, 21);
          nodeLinkManager.addLink(programName, currentGoToNodeName);
          nodeLinkManager.addLink(currentGoToNodeName, paragraphName);
          nodeLinkManager.addNodeWithoutRoot(paragraphName, 2);

        }
      }

      VisitorUtilites.currentGoTo += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitGoToStatement(ctx);
  }

  @Override
  public Object visitProcedureCopyStatement(ProcedureCopyStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitProcedureCopyStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitProcedureCopyStatement(ctx);
      }

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
  public Object visitPerformStatement(PerformStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitPerformStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitPerformStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentPerformNodeName = "PERFORM:" + VisitorUtilites.currentPerform;

      for(int i = 0; i < ctx.children.size(); i++){
        if(ctx.children.get(i) instanceof PerformProcedureStatementContext performProcedureStatementContext){
          ProcedureNameContext procedureNameContext = retrieveContext.getProcedureNameContext(performProcedureStatementContext);
          ParagraphNameContext paragraphNameContext = retrieveContext.getParagraphNameContext(procedureNameContext);
          CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(paragraphNameContext);

          String paragraphName = cobolWordContext.children.get(0).getText();

          nodeLinkManager.addNodeWithoutRoot(currentPerformNodeName, 20);
          nodeLinkManager.addLink(programName, currentPerformNodeName);
          nodeLinkManager.addLink(currentPerformNodeName, paragraphName);
        }
      }

      VisitorUtilites.currentPerform += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitPerformStatement(ctx);
  }

  @Override
  public Object visitAcceptStatement(AcceptStatementContext ctx){
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitAcceptStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitAcceptStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentAcceptNodeName = "ACCEPT:" + VisitorUtilites.currentAccept;

      for(int i = 0; i < ctx.children.size(); i++){
        if(ctx.children.get(i) instanceof IdentifierContext identifierContext){
          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

          nodeLinkManager.addNodeWithoutRoot(currentAcceptNodeName, 25);
          nodeLinkManager.addLink(programName, currentAcceptNodeName);
          nodeLinkManager.addLink(currentAcceptNodeName, variableWithLevelNumber);
        }
      }

      VisitorUtilites.currentAccept += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitAcceptStatement(ctx);
  }

  @Override
  public Object visitMoveStatement(MoveStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitMoveStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitMoveStatement(ctx);
      }

      String programName = retrieveProgramName.getProgramName(ctx);

      String currentMoveNodeName = "MOVE:" + VisitorUtilites.currentMove;

      for(int i = 0; i < ctx.children.size(); i++) {
        if(ctx.children.get(i) instanceof MoveToStatementContext moveToStatementContext) {
          for (int j = 0; j < moveToStatementContext.children.size(); j++) {
            if (moveToStatementContext.children.get(j) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentMoveNodeName, 24);
              nodeLinkManager.addLink(programName, currentMoveNodeName);
              nodeLinkManager.addLink(currentMoveNodeName, variableWithLevelNumber);
            }
          }
        }
      }

      VisitorUtilites.currentMove += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      throw new RuntimeException(e);
    }

    return super.visitMoveStatement(ctx);
  }

  private void writeAddIdentifierContext(IdentifierContext identifierContext, String currentAddNodeName, String programName, String currentUsedNodeName) {

    String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

    nodeLinkManager.addNodeWithoutRoot(currentAddNodeName, 15);
    nodeLinkManager.addLink(programName, currentAddNodeName);
    nodeLinkManager.addLink(currentAddNodeName, variableWithLevelNumber);
    nodeLinkManager.addLink(currentUsedNodeName, currentAddNodeName);
  }

  private void writeSubtractIdentifierContext(IdentifierContext identifierContext, String currentSubtractNodeName, String programName, String currentUsedNodeName){
    String variableWithLevelNumber = extractVariableWithLevelNumber(
        identifierContext);

    nodeLinkManager.addNodeWithoutRoot(currentSubtractNodeName, 16);
    nodeLinkManager.addLink(programName, currentSubtractNodeName);
    nodeLinkManager.addLink(currentSubtractNodeName, variableWithLevelNumber);
    nodeLinkManager.addLink(currentUsedNodeName, currentSubtractNodeName);
  }

  private void extractInsideControlStructure(StatementContext statementContext, String programName, String currentUsedNodeName)
      throws ContextNotFoundException {

    if (statementContext.getChild(0) instanceof ProcedureCopyStatementContext procedureCopyStatementContext) {
      String copyName = procedureCopyStatementContext.children.get(1).getText();
      nodeLinkManager.addNodeAndLink(programName, copyName, 3);
      nodeLinkManager.addLink(currentUsedNodeName, copyName);
    }

    if (statementContext.getChild(0) instanceof CallStatementContext callStatementContext) {
      String calledProgramName = callStatementContext.children.get(1).getText().replace("'", "").replace("\"", "");
      nodeLinkManager.addNodeAndLink(programName, calledProgramName, 4);
      nodeLinkManager.addLink(currentUsedNodeName, calledProgramName);

      CallUsingPhraseContext callUsingPhraseContext = retrieveContext.getCallUsingPhraseContext(callStatementContext);

      if (callUsingPhraseContext != null) {
        CallUsingParameterContext callUsingParameterContext = retrieveContext.getCallUsingParameterContext(callUsingPhraseContext);

        CallByReferencePhraseContext callByReferencePhraseContext = retrieveContext.getCallByReferencePhraseContext(callUsingParameterContext);

        for (int i = 0; i < callByReferencePhraseContext.children.size(); i++) {
          CallByReferenceContext callByReferenceContext = (CallByReferenceContext) callByReferencePhraseContext.children.get(i);

          IdentifierContext identifierContext = (IdentifierContext) callByReferenceContext.children.get(0);

          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

          nodeLinkManager.addLink(calledProgramName, variableWithLevelNumber);
        }
      }
    }

    if(statementContext.getChild(0) instanceof AcceptStatementContext acceptStatementContext) {
      String currentAcceptNodeName = "ACCEPT:" + VisitorUtilites.currentAccept;

      for(int i = 0; i < acceptStatementContext.children.size(); i++){
        if(acceptStatementContext.children.get(i) instanceof IdentifierContext identifierContext){
          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

          nodeLinkManager.addNodeWithoutRoot(currentAcceptNodeName, 25);
          nodeLinkManager.addLink(programName, currentAcceptNodeName);
          nodeLinkManager.addLink(currentAcceptNodeName, variableWithLevelNumber);
          nodeLinkManager.addLink(currentUsedNodeName, currentAcceptNodeName);
        }
      }

      VisitorUtilites.currentAccept += 1;
    }

    if(statementContext.getChild(0) instanceof PerformStatementContext performStatementContext){
      String currentPerformNodeName = "PERFORM:" + VisitorUtilites.currentPerform;

      for(int i = 0; i < performStatementContext.children.size(); i++){
        if(performStatementContext.children.get(i) instanceof PerformProcedureStatementContext performProcedureStatementContext){
          ProcedureNameContext procedureNameContext = retrieveContext.getProcedureNameContext(performProcedureStatementContext);
          ParagraphNameContext paragraphNameContext = retrieveContext.getParagraphNameContext(procedureNameContext);
          CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(paragraphNameContext);

          String paragraphName = cobolWordContext.children.get(0).getText();

          nodeLinkManager.addNodeWithoutRoot(currentPerformNodeName, 20);
          nodeLinkManager.addLink(programName, currentPerformNodeName);
          nodeLinkManager.addLink(currentPerformNodeName, paragraphName);
          nodeLinkManager.addLink(currentUsedNodeName, currentPerformNodeName);
        }
      }

      VisitorUtilites.currentPerform += 1;
    }

    if(statementContext.getChild(0) instanceof GoToStatementContext goToStatementContext){
      String currentGoToNodeName = "GOTO:" + VisitorUtilites.currentGoTo;

      for (int i = 0; i < goToStatementContext.children.size(); i++) {
        if (goToStatementContext.children.get(i) instanceof GoToStatementSimpleContext goToStatementSimpleContext) {
          ProcedureNameContext procedureNameContext = retrieveContext.getProcedureNameContext(goToStatementSimpleContext);
          ParagraphNameContext paragraphNameContext = retrieveContext.getParagraphNameContext(procedureNameContext);
          CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(paragraphNameContext);

          String paragraphName = cobolWordContext.children.get(0).getText();

          nodeLinkManager.addNodeWithoutRoot(currentGoToNodeName, 21);
          nodeLinkManager.addLink(programName, currentGoToNodeName);
          nodeLinkManager.addLink(currentGoToNodeName, paragraphName);
          nodeLinkManager.addNodeWithoutRoot(paragraphName, 2);
          nodeLinkManager.addLink(currentUsedNodeName, currentGoToNodeName);
        }
      }

      VisitorUtilites.currentGoTo += 1;
    }

    if (statementContext.getChild(0) instanceof SetStatementContext setStatementContext){
      String currentSetNodeName = "SET:" + VisitorUtilites.currentSet;

      for (int i = 0; i < setStatementContext.children.size(); i++) {
        if (setStatementContext.children.get(i) instanceof SetToStatementContext setToStatementContext) {
          SetToContext setToContext = retrieveContext.getSetToContext(setToStatementContext);
          IdentifierContext identifierContext = retrieveContext.getIdentifierContext(setToContext);

          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

          nodeLinkManager.addNodeWithoutRoot(currentSetNodeName, 22);
          nodeLinkManager.addLink(programName, currentSetNodeName);
          nodeLinkManager.addLink(currentSetNodeName, variableWithLevelNumber);
          nodeLinkManager.addLink(currentUsedNodeName, currentSetNodeName);
        }
      }

      VisitorUtilites.currentSet += 1;
    }

    if(statementContext.getChild(0) instanceof MoveStatementContext moveStatementContext){
      String currentMoveNodeName = "MOVE:" + VisitorUtilites.currentMove;

      for(int i = 0; i < moveStatementContext.children.size(); i++) {
        if(moveStatementContext.children.get(i) instanceof MoveToStatementContext moveToStatementContext) {
          for (int j = 0; j < moveToStatementContext.children.size(); j++) {
            if (moveToStatementContext.children.get(j) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentMoveNodeName, 24);
              nodeLinkManager.addLink(programName, currentMoveNodeName);
              nodeLinkManager.addLink(currentMoveNodeName, variableWithLevelNumber);
              nodeLinkManager.addLink(currentUsedNodeName, currentMoveNodeName);
            }
          }
        }
      }

      VisitorUtilites.currentMove += 1;
    }

    if (statementContext.getChild(0) instanceof AddStatementContext addStatementContext) {
      if (addStatementContext.children.get(1) instanceof  AddToStatementContext addToStatementContext) {

        String currentAddNodeName = "ADD:" + VisitorUtilites.currentAdd;

        for (int l = 0; l < addToStatementContext.children.size(); l++) {
          if (addToStatementContext.children.get(l) instanceof AddFromContext
              || addToStatementContext.children.get(l) instanceof AddToContext) {
            if (addToStatementContext.children.get(l).getChild(0) instanceof IdentifierContext identifierContext) {
              writeAddIdentifierContext(identifierContext, currentAddNodeName, programName, currentUsedNodeName);
            }
          }
        }
      } else if (addStatementContext.children.get(1) instanceof AddToGivingStatementContext addToGivingStatementContext) {
        String currentAddNodeName = "ADD:" + VisitorUtilites.currentAdd;

        for (int l = 0; l < addToGivingStatementContext.children.size(); l++) {
          if (addToGivingStatementContext.children.get(l) instanceof AddFromContext
              || addToGivingStatementContext.children.get(l) instanceof AddToGivingContext
              || addToGivingStatementContext.children.get(l) instanceof AddGivingContext) {
            if (addToGivingStatementContext.children.get(l).getChild(0) instanceof IdentifierContext identifierContext) {
              writeAddIdentifierContext(identifierContext, currentAddNodeName, programName, currentUsedNodeName);
            }
          }
        }

      }
      VisitorUtilites.currentAdd  += 1;
    }
    // Subtract Statement inside IF
    if (statementContext.getChild(
        0) instanceof SubtractStatementContext subtractStatementContext) {
      if (subtractStatementContext.children.get(
          1) instanceof SubtractFromStatementContext subtractFromStatementContext) {
        String currentSubtractNodeName = "SUBTRACT:" + VisitorUtilites.currentSubtract;

        for (int l = 0; l < subtractFromStatementContext.children.size(); l++) {
          if (subtractFromStatementContext.children.get(
              l) instanceof SubtractSubtrahendContext
              || subtractFromStatementContext.children.get(
              l) instanceof SubtractMinuendContext) {

            if (subtractFromStatementContext.children.get(l).getChild(0) instanceof IdentifierContext identifierContext) {
              writeSubtractIdentifierContext(identifierContext, currentSubtractNodeName, programName, currentUsedNodeName);
            }
          }
        }
      } else if (subtractStatementContext.children.get(
          1) instanceof SubtractFromGivingStatementContext subtractFromGivingStatementContext) {

        String currentSubtractNodeName = "SUBTRACT:" + VisitorUtilites.currentSubtract;

        for (int l = 0; l < subtractFromGivingStatementContext.children.size(); l++) {
          if (subtractFromGivingStatementContext.children.get(
              l) instanceof SubtractSubtrahendContext
              || subtractFromGivingStatementContext.children.get(
              l) instanceof SubtractMinuendGivingContext
              || subtractFromGivingStatementContext.children.get(
              l) instanceof SubtractGivingContext) {
            if (subtractFromGivingStatementContext.children.get(l).getChild(0) instanceof IdentifierContext identifierContext) {
              writeSubtractIdentifierContext(identifierContext, currentSubtractNodeName, programName, currentUsedNodeName);
            }
          }
        }
      }
      VisitorUtilites.currentSubtract += 1;


    }
    // Divide Statement inside IF
    if (statementContext.getChild(
        0) instanceof DivideStatementContext divideStatementContext) {
      String currentDivideNodeName = "DIVIDE:" + VisitorUtilites.currentDivide;

      for (int l = 0; l < divideStatementContext.children.size(); l++) {
        if (divideStatementContext.children.get(l) instanceof IdentifierContext) {
          String variableWithLevelNumber = extractVariableWithLevelNumber(
              (IdentifierContext) divideStatementContext.children.get(l));

          nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
          nodeLinkManager.addLink(programName, currentDivideNodeName);
          nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
          nodeLinkManager.addLink(currentUsedNodeName, currentDivideNodeName);
        } else if (divideStatementContext.children.get(
            l) instanceof DivideByGivingStatementContext divideByGivingStatementContext) {
          for (int k = 0; k < divideByGivingStatementContext.children.size(); k++) {
            if (divideByGivingStatementContext.children.get(
                k) instanceof IdentifierContext identifierContext) {
              String variableWithLevelNumber = extractVariableWithLevelNumber(
                  identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
              nodeLinkManager.addLink(programName, currentDivideNodeName);
              nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
              nodeLinkManager.addLink(currentUsedNodeName, currentDivideNodeName);
            } else if (divideByGivingStatementContext.children.get(
                k) instanceof DivideGivingPhraseContext divideGivingPhraseContext) {
              IdentifierContext identifierContext = retrieveContext.getIdentifierContext(
                  (ParserRuleContext) divideGivingPhraseContext.children.get(1));

              String variableWithLevelNumber = extractVariableWithLevelNumber(
                  identifierContext);

              nodeLinkManager.addNodeWithoutRoot(currentDivideNodeName, 18);
              nodeLinkManager.addLink(programName, currentDivideNodeName);
              nodeLinkManager.addLink(currentDivideNodeName, variableWithLevelNumber);
              nodeLinkManager.addLink(currentUsedNodeName, currentDivideNodeName);
            }
          }


        }
      }
      VisitorUtilites.currentDivide += 1;

    }

    // Multiply Statement inside IF
    if (statementContext.getChild(
        0) instanceof MultiplyStatementContext multiplyStatementContext) {
      String currentMultiplyNodeName = "MULTIPLY:" + VisitorUtilites.currentMultiply;

      for (int l = 0; l < multiplyStatementContext.children.size(); l++) {
        if (multiplyStatementContext.children.get(l) instanceof IdentifierContext) {
          String variableWithLevelNumber = extractVariableWithLevelNumber(
              (IdentifierContext) multiplyStatementContext.children.get(l));

          nodeLinkManager.addNodeWithoutRoot(currentMultiplyNodeName, 17);
          nodeLinkManager.addLink(programName, currentMultiplyNodeName);
          nodeLinkManager.addLink(currentMultiplyNodeName, variableWithLevelNumber);
          nodeLinkManager.addLink(currentUsedNodeName, currentMultiplyNodeName);
        } else if (multiplyStatementContext.children.get(l) instanceof MultiplyRegularContext multiplyRegularContext) {
          retrieveMultiplyRegularOperandContext(multiplyRegularContext, programName, currentMultiplyNodeName);
          nodeLinkManager.addLink(currentUsedNodeName, currentMultiplyNodeName);
        } else if (multiplyStatementContext.children.get(
            l) instanceof MultiplyGivingContext multiplyGivingContext) {
          for (int k = 0; k < multiplyGivingContext.children.size(); k++) {
            if (multiplyGivingContext.children.get(
                k) instanceof MultiplyGivingOperandContext
                || multiplyGivingContext.children.get(
                k) instanceof MultiplyGivingResultContext) {

              if (multiplyGivingContext.children.get(k)
                  .getChild(0) instanceof IdentifierContext identifierContext) {
                String variableWithLevelNumber = extractVariableWithLevelNumber(
                    identifierContext);

                nodeLinkManager.addNodeWithoutRoot(currentMultiplyNodeName, 17);
                nodeLinkManager.addLink(programName, currentMultiplyNodeName);
                nodeLinkManager.addLink(currentMultiplyNodeName, variableWithLevelNumber);
                nodeLinkManager.addLink(currentUsedNodeName, currentMultiplyNodeName);
              }
            }
          }

        }
      }
      VisitorUtilites.currentMultiply += 1;
    }
  }

  private void retrieveInsideAndOrCondition(RelationConditionContext relationConditionContext, String currentIfNodeName, String programName) throws ContextNotFoundException {
    RelationArithmeticComparisonContext relationArithmeticComparisonContext = retrieveContext.getRelationArithmeticComparisonContext(relationConditionContext);

    for(int k = 0; k < relationArithmeticComparisonContext.children.size(); k++){
      if (relationArithmeticComparisonContext.children.get(k) instanceof ArithmeticExpressionContext arithmeticExpressionContext) {
        MultDivsContext multDivsContext = retrieveContext.getMultDivsContext(arithmeticExpressionContext);
        PowersContext powersContext = retrieveContext.getPowersContext(multDivsContext);
        BasisContext basisContext = retrieveContext.getBasisContext(powersContext);
        if (basisContext.children.get(0) instanceof IdentifierContext identifierContext) {

          String variableWithLevelNumber = extractVariableWithLevelNumber(
              identifierContext);

          nodeLinkManager.addNodeWithoutRoot(currentIfNodeName, 19);
          nodeLinkManager.addLink(programName, currentIfNodeName);
          nodeLinkManager.addLink(currentIfNodeName, variableWithLevelNumber);
        }
      }
    }
  }

  @Override
  public Object visitIfStatement(IfStatementContext ctx) {
    try {
      String programName = retrieveProgramName.getProgramName(ctx);

      String currentIfNodeName = "IF:" + VisitorUtilites.currentIf;

      ConditionContext conditionContext = retrieveContext.getConditionContext(ctx);

      for (int i = 0; i < conditionContext.children.size(); i++){
        if (conditionContext.children.get(i) instanceof CombinableConditionContext combinableConditionContext) {
          SimpleConditionContext simpleConditionContext = retrieveContext.getSimpleConditionContext(combinableConditionContext);
          if (simpleConditionContext.children.get(0) instanceof  RelationConditionContext relationConditionContext) {
            retrieveInsideAndOrCondition(relationConditionContext, currentIfNodeName, programName);
          } else if(simpleConditionContext.children.get(0) instanceof  ConditionNameReferenceContext conditionNameReferenceContext){
            ConditionNameContext conditionNameContext = retrieveContext.getConditionNameContext(conditionNameReferenceContext);
            CobolWordContext cobolWordContext = retrieveContext.getCobolWordContext(conditionNameContext);
            String variableWithoutLevelNumber = cobolWordContext.children.get(0).getText();

            String variableWithLevelNumber = nodeLinkManager.searchNodeMatchesName(variableWithoutLevelNumber);

            nodeLinkManager.addNodeWithoutRoot(currentIfNodeName, 19);
            nodeLinkManager.addLink(programName, currentIfNodeName);
            nodeLinkManager.addLink(currentIfNodeName, variableWithLevelNumber);
          }
        }
        else if(conditionContext.children.get(i) instanceof AndOrConditionContext andOrConditionContext){
          for(int j = 0; j < andOrConditionContext.children.size(); j++){
            if(andOrConditionContext.children.get(j) instanceof CombinableConditionContext combinableConditionContext){
              SimpleConditionContext simpleConditionContext = retrieveContext.getSimpleConditionContext(combinableConditionContext);
              if(simpleConditionContext.children.get(0) instanceof RelationConditionContext relationConditionContext){
                retrieveInsideAndOrCondition(relationConditionContext, currentIfNodeName, programName);
              }
            }
          }
        }
      }

      // Then Part of If
      IfThenContext ifThenContext = retrieveContext.getIfThenContext(ctx);
      if(ifThenContext.children != null) {
        for (int i = 0; i < ifThenContext.children.size(); i++) {
          if (ifThenContext.children.get(i) instanceof StatementContext statementContext) {
            extractInsideControlStructure(statementContext, programName, currentIfNodeName);
          }
        }
      }

      VisitorUtilites.currentIf += 1;

    } catch (ProgramNameNotFoundException | ContextNotFoundException e) {
      e.printStackTrace();
    }
    return super.visitIfStatement(ctx);
  }

  @Override
  public Object visitCallStatement(CallStatementContext ctx) {
    try {
      if (isEvaluateStatement(ctx)) {
        return super.visitCallStatement(ctx);
      }

      if (isIfStatement(ctx)) {
        return super.visitCallStatement(ctx);
      }

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

          String variableWithLevelNumber = extractVariableWithLevelNumber(identifierContext);

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

      fileControlClause = fileControlClause.replace("'", "").replace("\"", "");
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
      Map<Integer, Integer> format1AndFormat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(fileDescriptionEntryContext, format1AndFormat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1AndFormat3Links);

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
      Map<Integer, Integer> format1AndFormat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1AndFormat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1AndFormat3Links);

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
      Map<Integer, Integer> format1AndFormat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1AndFormat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1AndFormat3Links);

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
            variables.get(i).get(0) + ": " + variables.get(i).get(1) + " RENAMES " + variables.get(i).get(2) + " THROUGH " + variables.get(i).get(3));
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
            variables.get(i).get(0) + ": " + variables.get(i).get(1) + " RENAMES " + variables.get(i).get(2) + " THROUGH " + variables.get(i).get(3));
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

      if (dataDescriptionEntryFormat2Context.children.get(2) instanceof DataRenamesClauseContext dataRenamesClauseContext) {
        if (dataRenamesClauseContext.children.get(1) instanceof QualifiedDataNameContext) {
          QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) dataRenamesClauseContext.children.get(1);
          String renamedName = qualifiedDataNameContext.children.get(0).getChild(0).getChild(0).getChild(0).getText();
          variable.add(renamedName);
        }

        if (dataRenamesClauseContext.children.get(3) instanceof QualifiedDataNameContext) {
          QualifiedDataNameContext qualifiedDataNameContext = (QualifiedDataNameContext) dataRenamesClauseContext.children.get(3);
          String throughName = qualifiedDataNameContext.children.get(0).getChild(0).getChild(0).getChild(0).getText();
          variable.add(throughName);
        }
      }

      variables.add(variable);
    }
  }

  private void update(List<List<String>> variables, List<DataDescriptionEntryFormat3Context> dataDescriptionEntryFormat3Contexts, Map<String, Integer> updatedFormat1AndFormat3Links, Map<Integer, Integer> format1AndFormat3Links){
    int format3i = 0;
    for (DataDescriptionEntryFormat3Context dataDescriptionEntryFormat3Context : dataDescriptionEntryFormat3Contexts) {
      List<String> variable = new ArrayList<>();
      variable.add(dataDescriptionEntryFormat3Context.children.get(0).getText());

      if (dataDescriptionEntryFormat3Context.children.get(1) instanceof ConditionNameContext) {
        variable.add(
            dataDescriptionEntryFormat3Context.children.get(1).getChild(0).getChild(0).getText());
      }

      updatedFormat1AndFormat3Links.put(variable.get(0) + ": " + variable.get(1), format1AndFormat3Links.get(format3i));
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
      Map<Integer, Integer> format1AndFormat3Links = new HashMap<>();

      retrieveContext.getDataDescriptionEntryFormatContexts(ctx, format1AndFormat3Links, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts, dataDescriptionEntryFormat3Contexts);


      List<List<String>> variables = new ArrayList<>();

      getDifferentVariableTypes(variables, dataDescriptionEntryFormat1Contexts, dataDescriptionEntryFormat2Contexts);

      Map<String, Integer> updatedFormat1AndFormat3Links = new HashMap<>();

      update(variables, dataDescriptionEntryFormat3Contexts, updatedFormat1AndFormat3Links, format1AndFormat3Links);

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
