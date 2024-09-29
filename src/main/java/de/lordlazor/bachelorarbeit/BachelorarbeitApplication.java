package de.lordlazor.bachelorarbeit;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.grammar.Visitor;
import de.lordlazor.bachelorarbeit.utils.JsonUtilities;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BachelorarbeitApplication {

  public static void main(String[] args) throws IOException {

    String filePath = "src/main/resources/examples/ProcedureXCopyXParagraph.cbl"; // TODO: Can be filename or folder name; selected by frontend
    CharStream stream = CharStreams.fromFileName(filePath); // TODO: Can be filename or folder name; selected by frontend

    Cobol85Lexer lexer = new Cobol85Lexer(stream); // TODO: Can be filename or folder name; selected by frontend
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);

    ParseTree tree = parser.startRule();

    JsonUtilities jsonUtilities = new JsonUtilities();

    Visitor visitor = new Visitor(jsonUtilities);
    visitor.visit(tree);
    jsonUtilities.createJsonFile("src/main/resources/out/output.json"); // TODO: File name will be timestamped



    // TODO: Uncomment this line to run the application later on (Spring Boot frontend)
    //SpringApplication.run(BachelorarbeitApplication.class, args);
  }

}
