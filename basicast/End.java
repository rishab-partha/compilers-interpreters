package basicast;
import basicenv.Environment;
/**
 * Class End encodes a statement of "END" that signifies that
 * execution of the stored statements should end.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class End extends Statement
{
    /**
     * The constructor serves to create the End object.
     */
    public End()
    {    
    }
    /**
     * Method exec executes the END statement by setting the flag within
     * the environment that indicates whether the program has ended to true.
     * 
     * @param env the Environment in which operations are occuring
     * @postcondition the environment has recorded that the program should end
     */
    public void exec(Environment env)
    {
        env.setEnd();
    }
    /**
     * Method list prints out the end statement in the format:
     * 
     *      END
     * 
     * @postcondition the end statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.println("END");
    }
}