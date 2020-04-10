package ast;
import environment.Environment;
import java.util.*;
/**
 * The ProcedureDeclaration class encodes a statement
 * where a procedure is defined through a name, a list of
 * parameters, and a statement for how to execute the procedure.
 * @author Rishab Parthasarathy
 * @version 04.10.2020
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement st;
    private List<String> params;
    /**
     * The Constructor initializes the name of the procedure, the list of parameters to
     * the procedure, and the statement that defines the evaluation of the procedure.
     * 
     * @param v The name of the procedure
     * @param e The statement to be evaluated when calling the function
     * @param paramList The list of parameters
     */
    public ProcedureDeclaration(String v, Statement e, List<String> paramList)
    {
        name = v;
        st = e;
        params = paramList;
    }
    /**
     * Method exec assigns the procedure's name to its own ProcedureDeclaration within the
     * global environment so that it can be stored within the global symbol table and can be used
     * by procedure calls.
     *
     * @param env the environment within which operations are occuring
     * @postcondition The name of the procedure has been associated with its own
     *                ProcedureDeclaration within the global environment
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }
    /**
     * This method is a getter for the statement so that procedure calls can retrieve
     * the statement and then execute it.
     * 
     * @return the statement of the procedure
     */
    public Statement getStatement()
    {
        return st;
    }
    /**
     * This method is a getter for the list of parameters of the procedure so that
     * procedure calls can match up parameters and evaluate the parameters within the
     * statement itself.
     * 
     * @return the list of parameters
     */
    public List<String> getParams()
    {
        return params;
    }
}