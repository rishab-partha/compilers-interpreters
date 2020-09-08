package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * Class Variable represents an expression that has its value
 * stored within the environment. The class merely stores the 
 * string identifier of the variable and uses the environment
 * to find the true integer value of the variable.
 * @author Rishab Parthasarathy
 * @version 05.21.2020
 */
public class Variable extends Expression
{
    private String varName;
    /**
     * The constructor initializes the identifier for the variable.
     * 
     * @param v The identifier of the variable
     */
    public Variable(String v)
    {
        varName = v;
    }
    /**
     * Method eval evaluates the value of the variable by querying for the value
     * of the variable within the environment. If the variable does not exist, an
     * exception is thrown.
     * 
     * @param env the environment within which operations are occuring
     * @precondition the variable name exists within the environment
     * @postcondition the correct variable value is found
     * @return the value of the variable
     */
    public int eval(Environment env)
    {
        return env.getVariable(varName);
    }
    /**
     * Method compile converts the variable into MIPS. First, code is emitted that
     * loads the address of the variable into a register. Then, code is emitted that
     * transfers the value of the variable into the register $v0. If the variable is
     * local, it is found on the stack, and otherwise, it is found as a label.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The variable expression code has been emitted 
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(varName))
        {
            e.emit("lw $v0, " + e.getOffset(varName) + "($sp) #Load local var from stack");
        }
        else
        {
            e.emit("la $t0, var" + varName + " #Load address of variable");
            e.emit("lw $v0, ($t0) #Load value of variable");
        }
    }
}