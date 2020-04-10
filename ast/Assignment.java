package ast;
import environment.Environment;
/**
 * The Assignment class encodes a type of statement
 * where a String variable name is associated with the value of 
 * an expression within the given environment.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;
    /**
     * The Constructor initializes the string value of the value name and
     * the expression to which the variable is to be equated through input parameters.
     * 
     * @param v The name of the variable
     * @param e The expression equal to the value of the variable
     */
    public Assignment(String v, Expression e)
    {
        var = v;
        exp = e;
    }
    /**
     * Method exec assigns the variable to the value of the expression within the environment
     * where the variable is stored. 
     *
     * @param env the environment within which the variable is stored
     * @precondition The expression is evaluatable
     * @postcondition The value of the variable has been assigned to the value of the expression
     *                in the correct environment
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
}