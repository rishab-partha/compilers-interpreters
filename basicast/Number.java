package basicast;
import basicenv.Environment;
/**
 * The Number Class represents the simplest expression, which
 * is just an integer by itself.
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Number extends Expression
{
    private int val;
    /**
     * The constructor initializes the value stored within the number.
     * 
     * @param v The value of the number
     */
    public Number(int v)
    {
        val = v;
    }
    /**
     * Method eval evaluates the value of the Number by providing the integer stored within it.
     * 
     * @param env the environment within which operations are occuring
     * @return the value of the Number
     */
    public int eval(Environment env)
    {
        return val;
    }

    /**
     * Method list prints out the number in the format:
     * 
     *      num
     * 
     * where num is the value of the number
     * 
     * @postcondition the number has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.print(val);
    }
}