package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * The Assignment class encodes a type of statement
 * where a String variable name is associated with the value of 
 * an expression within the given environment.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
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

    /**
     * Method compile translates the assignment operation into MIPS code. First, it translates the
     * expression into MIPS with its value in $v0 and then loads the value into the variable.
     * 
     * @param e The Emitter emitting the information
     * @postcondition The assignment operation has been translated to MIPS
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t0, var" + var);
        e.emit("sw $v0, ($t0) #set variable " + var + " to $v0");
    }
}