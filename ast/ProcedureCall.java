package ast;
import environment.Environment;
import java.util.*;
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> exps;
    public ProcedureCall(String s, List<Expression> es)
    {
        name = s;
        exps = es;
    }
    public int eval(Environment env)
    {
        Environment env1 = new Environment(env);
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
}