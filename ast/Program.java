package ast;
import environment.Environment;
import java.util.*;
/**
 * Program serves as essentially the wrapper for the whole Abstract Syntax Tree (AST).
 * Program stores a list of ProcedureDeclarations and then a statement because the format
 * of a PASCAL program is procedures, and then one statement. 
 * 
 * @author Rishab Parthasarathy
 * @version 04.10.2020
 */
public class Program 
{
    private List<ProcedureDeclaration> pds;
    private Statement st;
    /**
     * The constructor initializes the list of ProcedureDeclarations for the class and then
     * also initializes the final statement to be evaluated after all the procedures have
     * been declared.
     * 
     * @param ls The list of procedures stored as ProcedureDeclarations
     * @param s The statement occuring at the end
     */
    public Program(List<ProcedureDeclaration> ls, Statement s)
    {
        pds = ls;
        st = s;
    }
    /**
     * Method exec executes the program as a whole by first declaring each procedure in the
     * environment and then executing the final statement in the same environment.
     * 
     * @param env The environment in which operations are occurring
     * @precondition The statement is evaluatable
     * @postcondition The program has run through and produced the correct results
     */
    public void exec(Environment env)
    {
        for (ProcedureDeclaration pd: pds)
        {
            pd.exec(env);
        }
        st.exec(env);
    }
}