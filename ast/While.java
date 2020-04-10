package ast;
import environment.Environment;
/**
 * The While class encodes a while loop of the form
 * WHILE cond DO stmt. Starting from the first iteration,
 * the condition is tested, and while the condition remains true,
 * the statement within the do is executed.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class While extends Statement
{
    private Condition c;
    private Statement s;
    /**
     * The condition and statement within the DO block are initialized. 
     * 
     * @param co The condition that decides the behavior of the WHILE loop
     * @param st The statement within the DO block
     */
    public While(Condition cond, Statement st)
    {
        c = cond;
        s = st;
    }
    /**
     * Method exec executes the WHILE loop. How it does so is for every iteration,
     * it checks whether the condition is true, and while the condition is still true,
     * the statement within the DO is executed.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The condition and statements are executeable
     * @postcondition The proper statements are executed
     **/
    public void exec(Environment env)
    {
        Environment env1 = new Environment(env);
        while(c.eval(env) == 1)
        {
            s.exec(env1);
        }
    }
}