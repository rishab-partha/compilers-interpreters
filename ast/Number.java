package ast;
import environment.Environment;
/**
 * The Number Class represents the simplest expression, which
 * is just an integer by itself.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
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
}