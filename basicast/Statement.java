package basicast;
import basicenv.Environment;
/**
 * The abstract class Statement is the base for all commands 
 * and statements within the AST. These can include Writing to
 * the Console, Executing Assignment Commands, and Performing
 * If Statements.
 * @author Rishab Parthasarathy
 * @version 05.27.2020
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
     * Method list prints out the statement in the appropriate format.
     * 
     * @postcondition the statement has been printed to standard output with the proper
     *                formatting
     */
    public abstract void list();
}