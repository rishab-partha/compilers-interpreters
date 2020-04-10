package ast;
import environment.Environment;
import java.util.*;
public class Program 
{
    private List<ProcedureDeclaration> pds;
    private Statement st;
    public Program(List<ProcedureDeclaration> ls, Statement s)
    {
        pds = ls;
        st = s;
    }
    public void exec(Environment env)
    {
        for (ProcedureDeclaration pd: pds)
        {
            pd.exec(env);
        }
        st.exec(env);
    }
}