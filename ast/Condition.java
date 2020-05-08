package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * The Condition class encodes a type of expression
 * where two Expressions are separated by a relational operator,
 * which are equality, inequality, less than, greater than, less
 * than or equal to, and greater than or equal to. In order to
 * facilitate use as an Expression, which has an integer value, the 
 * Condition class outputs boolean values as 0s and 1s.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
 */
public class Condition extends Expression
{
    private String operator;
    private Expression e1;
    private Expression e2;
    /**
     * The Constructor initializes the value of the operator and the
     * Expressions on either side, which represent the two values
     * that the relational operator must combine.
     * 
     * @param o The string containing the operator
     * @param exp1 The expression on the left side of the operator
     * @param exp2 The expression on the right side of the operator
     */
    public Condition(String o, Expression exp1, Expression exp2)
    {
        operator = o;
        e1 = exp1;
        e2 = exp2;
    }
    /**
     * Method eval evaluates the Condition first by evaluating
     * the expressions on both sides and then evaluating equality, inequality, 
     * less than, greater than, less than or equal to, and greater than or equal to
     * for the operators =, <>, <, >, <=, and >=, respectively. 
     * If the operator is not one of these, the method throws an
     * IllegalArgumentException and states that the operator is not recognized.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The expression is evaluatable and the operator is recognized
     * @postcondition The value of the expression is calculated accurately
     * @return 0 if the expression with the relational operator is false, else
     *         return 1.
     */
    public int eval(Environment env)
    {
        int val1 = e1.eval(env);
        int val2 = e2.eval(env);
        int ret = 0;
        if (operator.equals("="))
        {
            if (val1 == val2)
            {
                ret = 1;
            }
        }
        else if (operator.equals("<>"))
        {
            if (val1 != val2)
            {
                ret = 1;
            }
        }
        else if (operator.equals("<"))
        {
            if (val1 < val2)
            {
                ret = 1;
            }
        }
        else if (operator.equals(">"))
        {
            if (val1 > val2)
            {
                ret = 1;
            }
        }
        else if (operator.equals("<="))
        {
            if (val1 <= val2)
            {
                ret = 1;
            }
        }
        else if (operator.equals(">="))
        {
            if (val1 >= val2)
            {
                ret = 1;
            }
        }
        else
        {
            throw new IllegalArgumentException("Operator " + operator + " not recognized.");
        }
        return ret;
    }
    
    /**
     * Method compile compiles the Binary Operation by first compiling the operation on the left.
     * Then, the value is pushed onto the stack, and the operation on the right is compiled. Then,
     * the value of the left operation is popped off the stack. Based on the operator, the MIPS
     * condition is either bne for =, beq for <>, bge for <, bgt for <=, ble for >, or blt for >=.
     * Then, the comparison is emitted with a target label as the jump address.
     * 
     * @param e The Emitter that emits the MIPS code
     * @param targlabel The label to jump to if the condition is false
     * @postcondition The MIPS code for the condition is outputted and will accurately execute it.
     */
    public void compile(Emitter e, String targlabel)
    {
        e1.compile(e);
        e.push("v0");
        e2.compile(e);
        e.pop("t0");
        if (operator.equals("="))
        {
            e.emit("bne $t0, $v0, " + targlabel);
        }
        else if (operator.equals("<>"))
        {
            e.emit("beq $t0, $v0, " + targlabel);
        }
        else if (operator.equals("<"))
        {
            e.emit("bge $t0, $v0, " + targlabel);
        }
        else if (operator.equals(">"))
        {
            e.emit("ble $t0, $v0, " + targlabel);
        }
        else if (operator.equals("<="))
        {
            e.emit("bgt $t0, $v0, " + targlabel);
        }
        else if (operator.equals(">="))
        {
            e.emit("blt $t0, $v0, " + targlabel);
        }
        else
        {
            throw new IllegalArgumentException("Operator " + operator + " not recognized.");
        }
        e.emit("#Condition for an if statement or while loop");
    }
}