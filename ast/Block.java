package ast;
import java.util.List;
import environment.Environment;
/**
 * The Block class encodes a statement enclosed by BEGIN
 * and END that serves to compact multiple statements into
 * one statement, or block of statements.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class Block extends Statement
{
    private List<Statement> sts;
    /**
     * The Constructor initializes the List of statements contained within
     * the block class through the use of an input parameter.
     * 
     * @param statements The list of all statements contained within the block statement
     */
    public Block(List<Statement> statements)
    {
        sts = statements;
    }
    /**
     * Method exec executes the block statement by iterating through each statement
     * and executing each statement individually.
     *
     * @param env the environment within which operations are occuring
     * @precondition The statements are executable
     * @postcondition Each statement has been executed as expected
     */
    public void exec(Environment env)
    {
        for (Statement s : sts)
        {
            s.exec(env);
        }
    }
}