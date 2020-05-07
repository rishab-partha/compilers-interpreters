import java.io.*;
import scanner.*;
import parser.*;
import environment.*;
import emitter.Emitter;
/**
 * This Main class is responsible for testing the scanner by
 * choosing a source document and forcing the scanner to parse it.
 * 
 * @author Rishab Parthasarathy
 * @version 2.4.2020
 */
public class Main
{
    /**
     * Method main initializes a BufferedReader on a file and then feeds it to a Scanner.
     * In order to test the scanner, the method forces the Scanner to read the document
     * for as long as it can. The Scanner then tests the Parser, which parses Statements
     * from the text in the form of an abstract syntax tree. Then, the tester evaluates each
     * AST to evaluate accuracy.
     * 
     * @param args a String array of command line arguments
     * @throws IOException if the File cannot be found and if input goes wrong
     */
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("parser//parsertest10.txt"));
        Scanner sc = new Scanner(in);
        Parser p = new Parser(sc);
        Environment env = new Environment(null);
        Emitter e = new Emitter("output.asm");
        p.parseProgram().compile(e);
    }   
}