package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * Class Writeln denotes a statement that prints the value of
 * an expression if the expression is contained within the 
 * command WRITELN.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class Writeln extends Statement
{
    private Expression exp;
    /**
     * This constructor initializes the object contained within the 
     * WRITELN statement whose value will be printed.
     * 
     * @param e the expression within the writeln
     */
    public Writeln(Expression e)
    {
        exp = e;
    }
    /**
     * Method exec executes the WRITELN command by evaluating the expression
     * within the given environment and printing the value to the console.  
     * 
     * @param env the environment within which operations are occuring
     * @precondition the expression is executeable
     * @postcondition the value of the expression has been printed to the console
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }

    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0, $v0");
        e.emit("li $v0, 1");
        e.emit("syscall");
        e.newline();
    }
}