package ast;
import environment.Environment;
import emitter.Emitter;
import java.util.Scanner;
/**
 * The Readln Class represents the statement of reading a number from the command line.
 * 
 * @author Rishab Parthasarathy
 * @version 05.21.2020
 */
public class Readln extends Expression
{
    /**
     * The constructor is default as a placeholder.
     */
    public Readln()
    {
    }
    /**
     * Method eval evaluates the value of the Readln by reading a number from the environment's
     * Scanner.
     * 
     * @param env the environment within which operations are occuring
     * @return the value of the number read in from stdin
     */
    public int eval(Environment env)
    {
        Scanner scinput = env.getSc();
        int val = scinput.nextInt();
        return val;
    }

    /**
     * Method compile writes the MIPS code that reads an integer into address $v0.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition MIPS code for reading an integer into $v0 is complete.
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0, 5");
        e.emit("syscall #Input to $v0");
    }
}