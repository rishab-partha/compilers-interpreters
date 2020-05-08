package ast;
import environment.Environment;
import emitter.Emitter;
import java.lang.RuntimeException;
/**
 * The class Expression is the abstract base for all
 * numerical and logical expressions that require evaluation.
 * These can involve all arithmetic operations, modulus, and 
 * all comparative operators along with parentheses and negative
 * signs.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
 */
public abstract class Expression
{
    /**
     * Method eval provides a base for all class that extend
     * Expression to calculate the value of the expression in
     * a given environment.
     * 
     * @param env the environment within which operations are occuring
     * @return the value of the operation, which is 0 or 1 for boolean values
     */
    public abstract int eval(Environment env);

    /**
     * Method compile provides a base for the methodology that will output
     * MIPS Assembly code for all Expressions. The method has a RuntimeException
     * so that if compile is called for a class without compile implemented, an
     * exception is thrown.
     * 
     * @param e The Emitter that emits the MIPS code
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement Me!");
    }
}