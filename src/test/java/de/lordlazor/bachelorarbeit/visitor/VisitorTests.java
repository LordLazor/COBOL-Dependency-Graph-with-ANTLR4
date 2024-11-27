package de.lordlazor.bachelorarbeit.visitor;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.utils.VisitorUtilites;
import de.lordlazor.bachelorarbeit.visitor.Visitor;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.IOException;
import java.util.Map;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class VisitorTests {
  private String programsPath = "src/test/java/de/lordlazor/bachelorarbeit/visitor/programs/";
  private String jsonsPath = "src/test/java/de/lordlazor/bachelorarbeit/visitor/jsons/";


  // Test for word: ACCEPT
  @Test
  public void testAcceptProgram() throws IOException {
    String name = "accept";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for word: ADD
  @Test
  public void testAddProgram() throws IOException {
    String name = "add";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for word: CALL
  @Test
  public void testCallProgram() throws IOException {
    String name = "call";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for word: COPY
  @Test
  public void testCopyProgram() throws IOException {
    String name = "copy";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for word: DIVIDE
  @Test
  public void testDivideProgram() throws IOException {
    String name = "divide";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: ADD within EVALUATE
  @Test
  public void testEvaluateAddProgram() throws IOException {
    String name = "evaluate_add";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: DIVIDE within EVALUATE
  @Test
  public void testEvaluateDivideProgram() throws IOException {
    String name = "evaluate_divide";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MULTIPLY within EVALUATE
  @Test
  public void testEvaluateMultiplyProgram() throws IOException {
    String name = "evaluate_multiply";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SUBTRACT within EVALUATE
  @Test
  public void testEvaluateSubtractProgram() throws IOException {
    String name = "evaluate_subtract";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for File Description (FD) and corresponding Filename
  @Test
  public void testFileDescriptionProgram() throws IOException {
    String name = "fdunddateiname";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for FILE SECTION
  @Test
  public void testFileSectionProgram() throws IOException {
    String name = "filesection";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: GO TO
  @Test
  public void testGoToProgram() throws IOException {
    String name = "goto";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: IDENTIFICATION DIVISION
  @Test
  public void testIdentificationDivisionProgram() throws IOException {
    String name = "identificationdivision";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>(){});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: IF
  @Test
  public void testIfProgram() throws IOException {
    String name = "if";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }
  // Test for: ADD withing IF
  @Test
  public void testIfAddProgram() throws IOException {
    String name = "if_add";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: DIVIDE within IF
  @Test
  public void testIfDivideProgram() throws IOException {
    String name = "if_divide";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MULTIPLY within IF
  @Test
  public void testIfMultiplyProgram() throws IOException {
    String name = "if_multiply";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SUBTRACT within IF
  @Test
  public void testIfSubtractProgram() throws IOException {
    String name = "if_subtract";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: LINKAGE SECTION
  @Test
  public void testLinkageSectionProgram() throws IOException {
    String name = "linkagesection";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for LOCAL-STORAGE SECTION
  @Test
  public void testLocalStorageSectionProgram() throws IOException {
    String name = "localstoragesection";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MOVE
  @Test
  public void testMoveProgram() throws IOException {
    String name = "move";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MULTIPLY
  @Test
  public void testMultiplyProgram() throws IOException {
    String name = "multiply";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: Paragraph
  @Test
  public void testParagraphProgram() throws IOException {
    String name = "paragraph";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: PERFORM
  @Test
  public void testPerformProgram() throws IOException {
    String name = "perform";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SET
  @Test
  public void testSetProgram() throws IOException {
    String name = "set";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SUBTRACT
  @Test
  public void testSubtractProgram() throws IOException {
    String name = "subtract";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: WORKING-STORAGE SECTION
  @Test
  public void testWorkingStorageSectionProgram() throws IOException {
    String name = "workingstoragesection";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MOVE within IF
  @Test
  public void testIfMoveProgram() throws IOException {
    String name = "if_move";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: MOVE within EVALUATE
  @Test
  public void testEvaluateMoveProgram() throws IOException {
    String name = "evaluate_move";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SET within IF
  @Test
  public void testIfSetProgram() throws IOException {
    String name = "if_set";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: SET within EVALUATE
  @Test
  public void testEvaluateSetProgram() throws IOException {
    String name = "evaluate_set";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: GO TO within IF
  @Test
  public void testIfGoToProgram() throws IOException {
    String name = "if_goto";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: GO TO within EVALUATE
  @Test
  public void testEvaluateGoToProgram() throws IOException {
    String name = "evaluate_goto";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: PERFORM within IF
  @Test
  public void testIfPerformProgram() throws IOException {
    String name = "if_perform";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: PERFORM within EVALUATE
  @Test
  public void testEvaluatePerformProgram() throws IOException {
    String name = "evaluate_perform";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: ACCEPT within IF
  @Test
  public void testIfAcceptProgram() throws IOException {
    String name = "if_accept";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: ACCEPT within EVALUATE
  @Test
  public void testEvaluateAcceptProgram() throws IOException {
    String name = "evaluate_accept";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: CALL within IF
  @Test
  public void testIfCallProgram() throws IOException {
    String name = "if_call";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: CALL within EVALUATE
  @Test
  public void testEvaluateCallProgram() throws IOException {
    String name = "evaluate_call";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: COPY within IF
  @Test
  public void testIfCopyProgram() throws IOException {
    String name = "if_copy";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(JsonUtilities.readJsonFile(jsonsPath + name + ".json"), new TypeReference<Map<String, Object>>() {});

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }

  // Test for: COPY within EVALUATE
  @Test
  public void testEvaluateCopyProgram() throws IOException {
    String name = "evaluate_copy";

    CharStream paragraphStream = CharStreams.fromFileName(programsPath + name + ".cob");

    Cobol85Lexer lexer = new Cobol85Lexer(paragraphStream);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    JsonUtilities jsonUtilities = new JsonUtilities();
    Visitor visitor = new Visitor(jsonUtilities);

    ParseTree tree = parser.startRule();

    visitor.visit(tree);
    Map<String, Object> map = new ObjectMapper().readValue(
        JsonUtilities.readJsonFile(jsonsPath + name + ".json"),
        new TypeReference<Map<String, Object>>() {
        });

    assertThat(jsonUtilities.getNodes()).isNotNull();
    assertThat(jsonUtilities.getLinks()).isNotNull();
    assertThat(jsonUtilities.getNodes()).isEqualTo(map.get("nodes"));
    assertThat(jsonUtilities.getLinks()).isEqualTo(map.get("links"));

    VisitorUtilites.resetCounters();
  }
}
