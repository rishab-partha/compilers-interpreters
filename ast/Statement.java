package ast;
import environment.Environment;
import emitter.Emitter;
import java.lang.RuntimeException;
/**
 * The abstract class Statement is the base for all commands 
 * and statements within the AST. These can include Writing to
 * the Console, Executing Assignment Commands, and Performing
 * If Statements and While Loops.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
 */
public abstract class Statement
{
    /**
     * The method exec provides a base for the execution of a statement
     * within a certain environment that stores variables.
     * 
     * @param env the environment within which operations are occuring
     */
    public abstract void exec(Environment env);
    
    /**
     * Method compile provides a base for the methodology that will output
     * MIPS Assembly code for all Statements. The method has a RuntimeException
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