package ast;
import environment.Environment;
import java.util.*;
import emitter.Emitter;
/**
 * Program serves as essentially the wrapper for the whole Abstract Syntax Tree (AST).
 * Program stores a list of ProcedureDeclarations and then a statement because the format
 * of a PASCAL program is procedures, and then one statement. 
 * 
 * @author Rishab Parthasarathy
 * @version 05.08.2020
 */
public class Program 
{
    private List<String> variables;
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
    public Program(List<String> vars, List<ProcedureDeclaration> ls, Statement s)
    {
        variables = vars;
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
    /**
     * Method compile converts the program into MIPS. First, it emits a .data section, which
     * contains a newline character for printing and variables with unique names based on the
     * variables designated by the program. In the .data section, all variables are initialized
     * to 0. Then, it emits a .text 0x00400000 and .globl main statements before the main: label.
     * After the main label, the program compiles the statement and then emits code for the
     * program to end.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The Full program code has been emitted properly
     */
    public void compile(Emitter e)
    {
        e.emit(".data");
        e.emit("newline: #Define a newline character");
        e.emit(".asciiz \"\\n\"");
        for (String s: variables)
        {
            e.emit("var" + s + ": #Define a new variable and initialize to 0");
            e.emit(".word 0");
        }
        e.emit(".text 0x00400000");
        e.emit(".globl main");
        e.emit("main:");
        e.emit("#main procedure");
        st.compile(e);
        e.emit("li $v0, 10 #End program");
        e.emit("syscall");
    }
}