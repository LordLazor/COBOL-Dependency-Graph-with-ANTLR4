package de.lordlazor.bachelorarbeit;

import static org.assertj.core.api.Assertions.assertThat;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.grammar.Visitor;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class VisitorTests {

  private JsonUtilities jsonUtilities;
  private Visitor visitor;




  @Test
  public void testGetProgramNameAndParagraphVisitor() {
    CharStream paragraphStream = CharStreams.fromString("""
        IDENTIFICATION DIVISION.
        PROGRAM-ID. ParagraphenProg.
        ENVIRONMENT DIVISION.
        CONFIGURATION SECTION.
        INPUT-OUTPUT SECTION.
                
        DATA DIVISION.
        FILE SECTION.
        WORKING-STORAGE SECTION.
        LINKAGE SECTION.
                
        PROCEDURE DIVISION.
        PARA-1.
          DISPLAY "Hier passiert was".
          PARA-2.
                      
        PARA-2.
          DISPLAY "Hier passiert auch was".
          STOP RUN.
        """);

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    jsonUtilities = new JsonUtilities();
    visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getNodes().size()).isEqualTo(3);
    assertThat(jsonUtilities.getNodes().get(0).get("id")).isEqualTo("ParagraphenProg");
    assertThat(jsonUtilities.getNodes().get(1).get("id")).isEqualTo("PARA-1");
    assertThat(jsonUtilities.getNodes().get(2).get("id")).isEqualTo("PARA-2");
  }

  @Test
  public void testGetProgramNameAndCopy() {
    CharStream copyStream = CharStreams.fromString("""
        IDENTIFICATION DIVISION.
        PROGRAM-ID. CopyProg.
        ENVIRONMENT DIVISION.
        CONFIGURATION SECTION.
        INPUT-OUTPUT SECTION.
        COPY Somebookname.
        DATA DIVISION.
        FILE SECTION.
        WORKING-STORAGE SECTION.
        LINKAGE SECTION.
                
        PROCEDURE DIVISION.
        
        """);

    Cobol85Lexer lexer = new Cobol85Lexer(copyStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    jsonUtilities = new JsonUtilities();
    visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getNodes().size()).isEqualTo(2);
    assertThat(jsonUtilities.getNodes().get(0).get("id")).isEqualTo("CopyProg");
    assertThat(jsonUtilities.getNodes().get(1).get("id")).isEqualTo("Somebookname");
  }

  @Test
  public void testGetProgramNameAndProcedureCopy() {
    CharStream procedureCopyStream = CharStreams.fromString("""
        IDENTIFICATION DIVISION.
        PROGRAM-ID. ProcedureCopyProg.
        ENVIRONMENT DIVISION.
        CONFIGURATION SECTION.
        INPUT-OUTPUT SECTION.
        DATA DIVISION.
        FILE SECTION.
        WORKING-STORAGE SECTION.
        LINKAGE SECTION.
                
        PROCEDURE DIVISION.
        COPY Somebookname.
        
        """);

    Cobol85Lexer lexer = new Cobol85Lexer(procedureCopyStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    jsonUtilities = new JsonUtilities();
    visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getNodes().size()).isEqualTo(2);
    assertThat(jsonUtilities.getNodes().get(0).get("id")).isEqualTo("ProcedureCopyProg");
    assertThat(jsonUtilities.getNodes().get(1).get("id")).isEqualTo("Somebookname");
  }

}
