package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * The While class encodes a while loop of the form
 * WHILE cond DO stmt. Starting from the first iteration,
 * the condition is tested, and while the condition remains true,
 * the statement within the do is executed.
 * @author Rishab Parthasarathy
 * @version 05.08.2020
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
     * the statement within the DO is executed. Also, this method creates a recursive
     * child environment in order to facilitate usage of local variables.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The condition and statements are executeable
     * @postcondition The proper statements are executed in the child environment
     **/
    public void exec(Environment env)
    {
        Environment env1 = new Environment(env);
        while(c.eval(env) == 1)
        {
            s.exec(env1);
        }
    }
    /**
     * Method compile converts the while loop into MIPS. It first does this by using the
     * Emitter to generate a unique ID for the while loop. Then, a while label is defined
     * and the condition is compiled. Then, the DO statement is compiled under the
     * condition and a jump back to the beginning of the while loop is emitted at the end
     * of the while label. Finally, a ending label for the while loop is emitted that is empty.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The Full while loop code has been emitted with unique jump labels
     */
    public void compile(Emitter e)
    {
        int i = e.nextLabelID();
        e.emit("while" + i + ":");
        e.emit("#jump statement for the " + i + "th while");
        c.compile(e, "endwhile" + i);
        s.compile(e);
        e.emit("j while" + i + " #loop to the beginning of the while");
        e.emit("endwhile" + i + ":");
        e.emit("#empty end of the while loop");
    }
}