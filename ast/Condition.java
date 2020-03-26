package ast;
import environment.Environment;
/**
 * The Condition class encodes a type of expression
 * where two Expressions are separated by a relational operator,
 * which are equality, inequality, less than, greater than, less
 * than or equal to, and greater than or equal to. In order to
 * facilitate use as an Expression, which has an integer value, the 
 * Condition class outputs boolean values as 0s and 1s.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
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
}