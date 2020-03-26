package ast;
import environment.Environment;
/**
 * The class Expression is the abstract base for all
 * numerical and logical expressions that require evaluation.
 * These can involve all arithmetic operations, modulus, and 
 * all comparative operators along with parentheses and negative
 * signs.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public abstract class Expression
{
    /**
     * Method eval provides a base for all class that extend
     * Expression to calculate the value of the expression in
     * a given environment.
     * 
     * @param env the environment within which operations are occuring
     * @return the value of the operation, which is 0 or 1 for boolean values
     */
    public abstract int eval(Environment env);
}