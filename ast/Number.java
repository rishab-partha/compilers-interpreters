package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * The Number Class represents the simplest expression, which
 * is just an integer by itself.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
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
     * Method compile writes the MIPS code that loads the integer number into address $v0.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition MIPS code for loading the value into $v0 is complete.
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0, " + val + " #Set $v0 to a value");
    }
}