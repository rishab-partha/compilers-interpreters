package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * Class Ternary encodes a ternary operation of the format:
 *      ?cond:e1:e2
 * where e1 and e2 are expressions and cond is a condition. If the condition is true,
 * e1 is evaluated, and otherwise, e2 is evaluated. In essence, the ternary operator is
 * a more concise, but more limited if statement.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Ternary extends Expression
{
    Condition c;
    Expression e1;
    Expression e2;

    /**
     * The constructor for Ternary initializes the condition and both expressions.
     * 
     * @param cond The condition that determines which expression to evaluate
     * @param exp1 The expression evaluated if the condition is true
     * @param exp2 The expression evaluated if the condition is false
     */
    public Ternary(Condition cond, Expression exp1, Expression exp2)
    {
        c = cond;
        e1 = exp1;
        e2 = exp2;
    }

    /**
     * Method eval evaluates the ternary operator. First, it evaluates the condition.
     * If the condition is true, the first expression is evaluated. Otherwise, the
     * second expression is evaluated. 
     * 
     * @param env the Environment in which the operations are evaluated
     * @precondition the condition and expressions are evaluatable
     * @return the value of the first expression if the condition is true; else,
     *         the value of the second expression
     */
    public int eval(Environment env)
    {
        if (c.eval(env) == 1)
        {
            return e1.eval(env);
        }
        return e2.eval(env);
    }
    /**
     * Method compile compiles the Ternary statement into MIPS. It first uses the emitter
     * to generate a unique if statement ID. Then, it emits the branch condition before
     * emitting the first condition. Then, a jump to the end of the if is emitted. Afterwards,
     * a label for the else if is emitted and the second expression is compiled. Finally, a
     * label for the end if is emitted for the jump in the branch to function.
     * 
     * @param e the Emitter that emits the MIPS code
     * @postcondition the MIPS version of the Ternary statement has been properly emitted with
     *                specific jump labels
     */
    public void compile(Emitter e)
    {
        int i = e.nextLabelID();
        c.compile(e, "elseif" + i);
        e1.compile(e);
        e.emit("j endif" + i);
        e.emit("elseif" + i + ":");
        e.emit("#jump statement for the " + i + "th else");
        e2.compile(e);
        e.emit("endif" + i + ":");
        e.emit("#jump statement for the " + i + "th if");
    }
}