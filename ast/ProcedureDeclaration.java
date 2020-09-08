package ast;
import environment.Environment;
import java.util.*;
import emitter.Emitter;
/**
 * The ProcedureDeclaration class encodes a statement
 * where a procedure is defined through a name, a list of
 * parameters, and a statement for how to execute the procedure.
 * To compile a procedure declaration, the emitter defines the 
 * procedure by pushing a return value to the stack, defining context, and pushing
 * local variables to the stack under a procedure label. Then, the statement
 * is compiled, and the return value, local variables, and parameters are popped off
 * the stack. Finally, the procedure returns and resets procedure context.
 * 
 * @author Rishab Parthasarathy
 * @version 05.21.2020
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private Statement st;
    private List<String> params;
    private List<String> vars;
    /**
     * The Constructor initializes the name of the procedure, the list of parameters to
     * the procedure, and the statement that defines the evaluation of the procedure.
     * 
     * @param v The name of the procedure
     * @param e The statement to be evaluated when calling the function
     * @param paramList The list of parameters
     */
    public ProcedureDeclaration(String v, Statement e, List<String> paramList, List<String> varsList)
    {
        name = v;
        st = e;
        params = paramList;
        vars = varsList;
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
    /**
     * This method finds the list of local variables for the emitter to use.
     * 
     * @return the list of local variables
     */
    public List<String> getVars()
    {
        return vars;
    }
    /**
     * This method finds the name of the procedure so that the emitter can identify
     * the variable that defines return value.
     * 
     * @return the name of the procedure
     */
    public String getName()
    {
        return name;
    }
    /**
     * Method compiles the declaration in a few steps. First, it defines a label. Then,
     * it pushes the return value to the stack and defines procedure context. Afterward,
     * it pushes local variables and return address to the stack and compiles the statement.
     * Then, the method pops the return address, local variables, return value, and parameters
     * off the stack. Finally, it emits a jump return and clears procedure context.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The procedure has been properly declared in MIPS, and the stack has been
     *                prepared to be restored to pre-call state when the procedure is called.
     */
    public void compile (Emitter e)
    {
        e.emit("proc" + name + ":");
        e.push("zero");
        e.emit("#return value");
        e.setProcedureContext(this);
        for (int i = 0; i < vars.size(); i++)
        {
            e.push("zero");
            e.emit("#local var");
        }
        e.push("ra");
        e.emit("#return address");
        st.compile(e);
        e.pop("ra");
        e.emit("#return address");
        for (int i = 0; i < vars.size(); i++)
        {
            e.pop("t0");
            e.emit("#local var");
        }
        e.pop("v0");
        e.emit("#return val");
        for (int i = 0; i < params.size(); i++)
        {
            e.pop("t0");
            e.emit("#param");
        }
        e.emit("jr $ra #return");
        e.clearProcedureContext();
    }
}