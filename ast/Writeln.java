package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * Class Writeln denotes a statement that prints the value of
 * an expression if the expression is contained within the 
 * command WRITELN.
 * @author Rishab Parthasarathy
 * @version 05.08.2020
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

    /**
     * Method compile converts the WRITELN statement to MIPS code. First, it emits code
     * compiling the expression before emitting code that moves the value into an
     * argument register. Then, code is emitted that prepares for integer printing and that
     * prints the integer.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The Full WRITELN statement code has been emitted
     */
    public void compile(Emitter e)
    {
        exp.compile(e); 
        e.emit("move $a0, $v0 #Move value into argument register");
        e.emit("li $v0, 1 #Prepare for printing");
        e.emit("syscall");
        e.newline();
    }
}