package ast;
import environment.Environment;
import java.util.*;
/**
 * The Assignment class encodes a type of statement
 * where a String variable name is associated with the value of 
 * an expression within the given environment.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement st;
    private List<String> params;
    /**
     * The Constructor initializes the string value of the value name and
     * the expression to which the variable is to be equated through input parameters.
     * 
     * @param v The name of the variable
     * @param e The expression equal to the value of the variable
     */
    public ProcedureDeclaration(String v, Statement e, List<String> paramList)
    {
        name = v;
        st = e;
        params = paramList;
    }
    /**
     * Method exec assigns the variable to the value of the expression within the environment
     * that the operations are occuring within. 
     *
     * @param env the environment within which operations are occuring
     * @precondition The expression is evaluatable
     * @postcondition The value of the variable has been assigned to the value of the expression
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }
    public Statement getStatement()
    {
        return st;
    }
    public List<String> getParams()
    {
        return params;
    }
}