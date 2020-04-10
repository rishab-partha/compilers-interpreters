package ast;
import environment.Environment;
import java.util.*;
/**
 * ProcedureCallStmt defines a call to a previously defined procedure in the form
 * of an statement of the form: id(params). In this case, id is the name of
 * the procedure and params is the comma separated list of inputs, which can
 * be expressions. ProcedureCall looks to the environment and matches up parameters
 * before creating child environments and executes the procedure, calculating return
 * values along the way. However, because this is a statement, the return value is ignored.
 * @author Rishab Parthasarathy
 * @version 04.10.2020
 */
public class ProcedureCallStmt extends Statement
{
    private String name;
    private List<Expression> exps;
    /**
     * This constructor initializes the ProcedureCallStmt by initializing the name of the
     * function called and the list of expressions that serves as the list of parameters.
     * 
     * @param s The name of the procedure called
     * @param es The list of expressions to plug in as parameters
     */
    public ProcedureCallStmt(String s, List<Expression> es)
    {
        name = s;
        exps = es;
    }
    /**
     * Method executes executes the procedure call first by declaring a child environment to
     * global environment. Then, the procedure declaration and list of parameters is retrieved
     * from the symbol table, and the parameters are matched up with the value of the expressions
     * calculated within the parent environment. Then, variables with the parameters' names and
     * expressions' values are declared in the child environment, and a variable with the name
     * of the procedure is declared to be 0 in the child environment, which serves as a return
     * value. Then, the function statement is evaluated in the child environment. Because of
     * the nature of a statement, the return value is ignored and never retrieved. If the 
     * parameter lists cannot be matched up, the method throws an IllegalArgumentException.
     * 
     * @param env The parent environment in which operations are occurring
     * @precondition The parameter lists match up and the procedure is evaluatable
     * @postcondition The procedure has been evaluated correctly in a child environment
     */
    public void exec(Environment env)
    {
        Environment par = env.getPar();
        Environment env1 = new Environment(par);
        ProcedureDeclaration dec = env.getProcedure(name);
        List<String> params = dec.getParams();
        if(exps.size() != params.size())
        {
            throw new IllegalArgumentException("Got " + exps.size() + 
                                               " parameters; expected " +
                                               params.size() + " parameters.");
        }
        for (int i = 0; i < params.size(); i++)
        {
            env1.declareVariable(params.get(i), exps.get(i).eval(env));
        }
        env1.declareVariable(name, 0);
        Statement st = dec.getStatement();
        st.exec(env1);
    }
}