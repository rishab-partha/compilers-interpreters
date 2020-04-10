package environment;
import ast.ProcedureDeclaration;
import java.util.Map;
import java.util.HashMap;
/**
 * The class Environment denotes the place where the variables are stored. This
 * allows variables to be handled independently of the parsing and abstract 
 * syntax tree, which allows for easier handling and lookup.
 * @author Rishab Parthasarathy
 * @version 03.25.2020
 */
public class Environment
{
    private Map<String, Integer> variables;
    private Map<String, ProcedureDeclaration> procedures;
    Environment par;
    /**
     * This constructor initializes the map of variables to be empty.
     * 
     * @postcondition the map of variables is initialized and empty
     */
    public Environment(Environment p)
    {
        variables = new HashMap<>();
        procedures = new HashMap<>();
        par = p;
    }
    /**
     * This method sets the value of a String variable identifier to a integer value
     * within the map. 
     * 
     * @param variable The String identifier of the variable
     * @param value The new value of the variable
     * @postcondition The map contains the given value as the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        if (!variables.containsKey(variable) && par != null && par.containsVariable(variable))
        {
            par.setVariable(variable, value);
            return;
        }
        variables.put(variable, value);
    }
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }
    public boolean containsVariable(String variable)
    {
        return variables.containsKey(variable);
    }
    /**
     * This method tries to retrieve the value associated with a variable from the map
     * of variables. If the variable has been created, this method just returns the value
     * associated with it. Otherwise, it throws an IllegalArgumentException that states that
     * the variable is undefined.
     * 
     * @param variable the name of the variable to be retrieved
     * @precondition the variable is in the map
     * @postcondition the value of the variable is properly retrieved
     * @return the value of the variable
     */
    public int getVariable(String variable)
    {
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        else if (par == null)
        {
            variables.put(variable, 0);
            return 0;
        }
        return par.getVariable(variable);
    }
    public void setProcedure(String procedure, ProcedureDeclaration dec)
    {
        if (par != null)
        {
            par.setProcedure(procedure, dec);
            return;
        }
        procedures.put(procedure, dec);
    }
    public ProcedureDeclaration getProcedure(String procedure)
    {
        if (par != null)
        {
            return par.getProcedure(procedure);
        }
        ProcedureDeclaration ret;
        if (procedures.containsKey(procedure))
        {
            ret = procedures.get(procedure);
        }
        else
        {
            throw new IllegalArgumentException("Procedure " + procedure + " is not defined.");
        }
        return ret;
    }
}