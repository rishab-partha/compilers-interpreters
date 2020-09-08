package basicast;
import basicenv.Environment;
import basicscanner.Scanner;
/**
 * The Readln Class represents the statement of reading a number from the command line.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Readln extends Statement
{
    private Scanner sc;
    private String varName;
    /**
     * The constructor takes in the Scanner from which to read input and the name of the
     * variable to read input into.
     * 
     * @param s the Scanner from which to read input
     * @param id the name of the variable to read input into
     * @postcondition the Scanner and variable name have been initialized
     */
    public Readln(Scanner s, String id)
    {
        sc = s;
        varName = id;
    }
    /**
     * Method eval evaluates the value of the Readln by reading a number from the Scanner.
     * If the value read is not a number, a NumberFormatException is thrown. The method
     * prints "? " to the console to ask for input.
     * 
     * @param env the environment within which operations are occuring
     * @return the value of the number read in from stdin
     */
    public void exec(Environment env)
    {
        System.out.print("? ");
        String s = sc.nextToken();
        int i = Integer.parseInt(s);
        sc.nextToken();
        env.setVariable(varName, i);
    }

    /**
     * Method list prints out the Readln statement in the format:
     * 
     *      INPUT var
     * 
     * where var is the variable name.
     * 
     * @postcondition the Readln statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.println("INPUT " + varName);
    }
}