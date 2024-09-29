package de.lordlazor.bachelorarbeit;

import de.lordlazor.bachelorarbeit.grammar.Cobol85Lexer;
import de.lordlazor.bachelorarbeit.grammar.Cobol85Parser;
import de.lordlazor.bachelorarbeit.grammar.Visitor;
import java.io.IOException;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BachelorarbeitApplication {

  public static void main(String[] args) throws IOException {
    Cobol85Lexer lexer = new Cobol85Lexer(CharStreams.fromFileName("src/main/resources/examples/Paragraphs.cbl"));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    Cobol85Parser parser = new Cobol85Parser(tokens);
    ParseTree tree = parser.startRule();

    Visitor visitor = new Visitor();
    visitor.visit(tree);

    // TODO: Uncomment this line to run the application later on
    //SpringApplication.run(BachelorarbeitApplication.class, args);
  }

}
