package ast;
import environment.Environment;
import java.util.*;
import emitter.Emitter;
/**
 * ProcedureCall defines a call to a previously defined procedure in the form
 * of an expression of the form: id(params). In this case, id is the name of
 * the procedure and params is the comma separated list of inputs, which can
 * be expressions. ProcedureCall looks to the environment and matches up parameters
 * before creating child environments and executes the procedure, calculating return
 * values along the way. To compile a procedure call, it writes MIPS code to push
 * the parameter values to the stack before calling the procedure. Once the procedure
 * is done running, it notifies the emitter that all vars have been cleared off the stack.
 * 
 * @author Rishab Parthasarathy
 * @version 05.21.2020
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> exps;
    /**
     * This constructor initializes the ProcedureCall by initializing the name of the
     * function called and the list of expressions that serves as the list of parameters.
     * 
     * @param s The name of the procedure called
     * @param es The list of expressions to plug in as parameters
     */
    public ProcedureCall(String s, List<Expression> es)
    {
        name = s;
        exps = es;
    }
    /**
     * Method eval evaluates the procedure call first by declaring a child environment to
     * global environment. Then, the procedure declaration and list of parameters is retrieved
     * from the symbol table, and the parameters are matched up with the value of the expressions
     * calculated within the parent environment. Then, variables with the parameters' names and
     * expressions' values are declared in the child environment, and a variable with the name
     * of the procedure is declared to be 0 in the child environment, which serves as a return
     * value. Then, the function statement is evaluated in the child environment and the return
     * value is retrieved. If the parameter lists cannot be matched up, the method throws an
     * IllegalArgumentException.
     * 
     * @param env The parent environment in which operations are occurring
     * @precondition The parameter lists match up and the procedure is evaluatable
     * @postcondition The procedure has been evaluated correctly in a child environment
     * @return the return value of the procedure
     */
    public int eval(Environment env)
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
        return env1.getVariable(name);
    }
    /**
     * Method compile compiles a procedure call by first saving the current excess stack
     * height. Then, it calculates the values of the parameters and pushes them to the
     * stack. Afterwards, the method emits a jump and link to the procedure. Finally, the
     * procedure restores the stack height to what it was before the procedure call.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The procedure has been properly called in MIPS, and the stack has been
     *                restored to its pre-call state.
     */
    public void compile(Emitter e)
    {
        e.saveStackHeight();
        for (Expression exp: exps)
        {
            exp.compile(e);
            e.push("v0");
            e.emit("#push parameter");
        }
        e.emit("jal proc" + name);
        e.restoreStackHeight();
    }
}