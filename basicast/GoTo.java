package basicast;
import basicenv.Environment;

/**
 * Class GoTo encodes a GOTO statement of the format:
 *      GOTO ln
 * where ln is a line number. This GOTO statement forces the program to 
 * jump to the line number listed.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class GoTo extends Statement
{
    private int jumpLine;
    
    /**
     * The constructor initializes the line number to jump to to a certain value.
     * 
     * @param jL the value of the jump line number
     * @postcondition the jump line number has been initialized to the provided value
     */
    public GoTo(int jL)
    {
        jumpLine = jL;
    }

    /**
     * Method exec executes the GOTO statement by setting the next line number in the
     * environment to the jump line number.
     * 
     * @param env the Environment in which operations are occuring
     * @postcondition the next line number in the Environment is set to the jump line number
     */
    public void exec(Environment env)
    {
        env.setLineNum(jumpLine);
    }
    
    /**
     * Method list prints out the GoTo statement in the format:
     * 
     *      GOTO ln
     * 
     * where ln is the line number of jumping to.
     * 
     * @postcondition the GoTo statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.println("GOTO " + jumpLine);
    }
}