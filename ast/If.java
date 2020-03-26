package ast;
import environment.Environment;
/**
 * The If class encodes an if statement of the form
 * IF cond THEN stmt (ELSE stmt) where the else statement is optional.
 * If the condition is true, the statement within the then is executed,
 * and if the condition is false and the else exists, the statement within
 * the else is executed.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class If extends Statement
{
    private Condition c;
    private Statement s1;
    private Statement s2;
    /**
     * The condition and statement within the Then block and the
     * Else block are initialized. If the else block does not exist
     * a null is provided.
     * 
     * @param co The condition that decides the behavior of the IF statement
     * @param st1 The statement within the then statement
     * @param st2 The statement within the else statement (null if it doesn't exist)
     */
    public If(Condition co, Statement st1, Statement st2)
    {
        c = co;
        s1 = st1;
        s2 = st2;
    }
    /**
     * Method exec executes the IF statement. First, it tries to evaluate the condition.
     * If the condition is true, the statement within the THEN block is executed. If the
     * condition is false, the statement within the ELSE block is executed if the ELSE block
     * exists. Otherwise, nothing is done.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The condition and statements are executeable
     * @postcondition The proper statements are executed
     **/
    public void exec(Environment env)
    {
        if (c.eval(env) == 1)
        {
            s1.exec(env);
        }
        else if (s2 != null)
        {
            s2.exec(env);
        }
    }
}