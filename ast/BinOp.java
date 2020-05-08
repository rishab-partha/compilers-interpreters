package ast;
import environment.Environment;
import emitter.Emitter;
/**
 * The BinOp class encodes a type of expression
 * where two Expressions are separated by a binary operator,
 * which are addition, subtraction, multiplication, division, and modulus.
 * @author Rishab Parthasarathy
 * @version 05.07.2020
 */
public class BinOp extends Expression
{
    private String operator;
    private Expression e1;
    private Expression e2;
    /**
     * The Constructor initializes the value of the operator and the
     * Expressions on either side, which represent the two values
     * that the binary operator must combine.
     * 
     * @param o The string containing the operator
     * @param exp1 The expression on the left side of the operator
     * @param exp2 The expression on the right side of the operator
     */
    public BinOp(String o, Expression exp1, Expression exp2)
    {
        operator = o;
        e1 = exp1;
        e2 = exp2;
    }
    /**
     * Method eval evaluates the Binary Operation first by evaluating
     * the expressions on both sides and then performing addition, subtraction, 
     * multiplication, division, and modulus for the operators +, -, *, /, and %,
     * respectively. If the operator is not one of these, the method throws an
     * IllegalArgumentException and states that the operator is not recognized.
     * 
     * @param env the environment within which operations are occuring
     * @precondition The expression is evaluatable and the operator is recognized
     * @postcondition The value of the expression is calculated accurately
     * @return the value of the two expressions combined using the binary operator
     */
    public int eval(Environment env)
    {
        int val1 = e1.eval(env);
        int val2 = e2.eval(env);
        int ret = 0;
        if (operator.equals("+"))
        {
            ret = val1 + val2;
        }
        else if (operator.equals("-"))
        {
            ret = val1 - val2;
        }
        else if (operator.equals("*"))
        {
            ret = val1 * val2;
        }
        else if (operator.equals("/"))
        {
            ret = val1 / val2;
        }
        else if (operator.equals("%"))
        {
            ret = val1 % val2;
        }
        else
        {
            throw new IllegalArgumentException("Operator " + operator + " not recognized.");
        }
        return ret;
    }

    /**
     * Method compile compiles the Binary Operation by first compiling the operation on the left.
     * Then, the value is pushed onto the stack, and the operation on the right is compiled. Then,
     * the value of the left operation is popped off the stack and the value of the operation,
     * which can be either +, -, *, /, or %, is put into $v0.
     * 
     * @param e The Emitter that emits the MIPS code
     * @postcondition The MIPS code for the binop is emitted and will store the value in $v0
     */
    public void compile(Emitter e)
    {
        e1.compile(e);
        e.push("v0");
        e2.compile(e);
        e.pop("t0");
        if (operator.equals("+"))
        {
            e.emit("addu $v0, $t0, $v0");
        }
        else if (operator.equals("-"))
        {
            e.emit("subu $v0, $t0, $v0");
        }
        else if (operator.equals("*"))
        {
            e.emit("mul $v0, $t0, $v0");
        }
        else if (operator.equals("/"))
        {
            e.emit("div $v0, $t0, $v0");
        }
        else if (operator.equals("%"))
        {
            e.emit("div $t1, $t0, $v0");
            e.emit("mul $v0, $v0, $t1");
            e.emit("sub $v0, $t0, $v0");
        }
        else
        {
            throw new IllegalArgumentException("Operator " + operator + " not recognized.");
        }
        e.emit("#Binary operation complete");
    }
}