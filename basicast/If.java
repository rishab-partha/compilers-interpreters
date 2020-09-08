package basicast;
import basicenv.Environment;
/**
 * The If class encodes an if statement of the form:
 * 
 *      IF cond THEN ln
 * where cond is the condition and ln is a line number.
 * If the condition is true, the program jumps to line ln,
 * and if the condition is false, the program continues executing by going
 * to the next line.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class If extends Statement
{
    private Condition c;
    private int line;
    /**
     * The condition and line number within the THEN are initalized.
     * 
     * @param co The condition that decides the behavior of the IF statement
     * @param i The line number to jump to in the THEN
     */
    public If(Condition co, int i)
    {
        c = co;
        line = i;
    }
    /**
     * Method exec executes the IF statement. First, it tries to evaluate the condition.
     * If the condition is true, the method sets the next line number in the environment to
     * the jump line number in the THEN block. Otherwise, nothing occurs.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The condition is executeable
     * @postcondition The proper line number is set to the next
     **/
    public void exec(Environment env)
    {
        if (c.eval(env) == 1)
        {
            env.setLineNum(line);
        }
    }

    /**
     * Method list prints out the IF statement in the format:
     * 
     *      IF cond THEN ln
     * 
     * where cond is the condition and ln is the jump line number.
     * 
     * @postcondition the IF statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.print("IF ");
        c.list();
        System.out.println(" THEN " + line);
    }
}