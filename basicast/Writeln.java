package basicast;
import basicenv.Environment;
/**
 * Class Writeln denotes a statement that prints the value of
 * an expression if the expression is contained within the 
 * command WRITELN.
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Writeln extends Statement
{
    private Expression exp;
    /**
     * This constructor initializes the object contained within the 
     * WRITELN statement whose value will be printed.
     * 
     * @param e the expression within the writeln
     */
    public Writeln(Expression e)
    {
        exp = e;
    }
    /**
     * Method exec executes the WRITELN command by evaluating the expression
     * within the given environment and printing the value to the console.  
     * 
     * @param env the environment within which operations are occuring
     * @precondition the expression is executeable
     * @postcondition the value of the expression has been printed to the console
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }

    /**
     * Method list prints out the Writeln statement in the format:
     * 
     *      PRINT exp
     * 
     * where exp is the expression.
     * 
     * @postcondition the Writeln statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.print("PRINT ");
        exp.list();
        System.out.println();
    }
}