package ast;
import environment.Environment;
/**
 * Class Variable represents an expression that has its value
 * stored within the environment. The class merely stores the 
 * string identifier of the variable and uses the environment
 * to find the true integer value of the variable.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class Variable extends Expression
{
    private String varName;
    /**
     * The constructor initializes the identifier for the variable.
     * 
     * @param v The identifier of the variable
     */
    public Variable(String v)
    {
        varName = v;
    }
    /**
     * Method eval evaluates the value of the variable by querying for the value
     * of the variable within the environment. If the variable does not exist, an
     * exception is thrown.
     * 
     * @param env the environment within which operations are occuring
     * @precondition the variable name exists within the environment
     * @postcondition the correct variable value is found
     * @return the value of the variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(varName);
    }
}