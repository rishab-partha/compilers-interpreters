package ast;
import environment.Environment;
/**
 * The abstract class Statement is the base for all commands 
 * and statements within the AST. These can include Writing to
 * the Console, Executing Assignment Commands, and Performing
 * If Statements and While Loops.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
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
}