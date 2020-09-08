import java.io.*;
import basicscanner.*;
import basicparser.*;
import basicenv.*;
/**
 * This BasicMain class is responsible for testing the BASIC interpreter
 * by scanning, parsing, and executing BASIC code in the terminal.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class BasicMain
{
    /**
     * Method main initializes a BufferedReader on stdin and then feeds it to a Scanner.
     * In order to test the scanner, the method forces the Scanner to read the document
     * for as long as it can. The Scanner then tests the Parser, which parses Statements
     * from the text in the form of an abstract syntax tree. As the statements are parsed,
     * the code is interpreted and output is produced on the command line.
     * 
     * @param args a String array of command line arguments
     * @precondition the input is proper BASIC
     * @postcondition The proper output has been produced
     * @throws IOException if the File cannot be found and if input goes wrong
     */
    public static void main(String[] args) throws IOException
    {
        System.out.println("Minimal BASIC -- Type HELP for help.");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));//new BufferedReader(new FileReader("parser//parsertest13.txt"));
        Scanner sc = new Scanner(in);
        Environment env = new Environment();
        Parser p = new Parser(sc, env);
        p.parseProgram();
    }   
}