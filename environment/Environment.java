package environment;

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
    /**
     * This constructor initializes the map of variables to be empty.
     * 
     * @postcondition the map of variables is initialized and empty
     */
    public Environment()
    {
        variables = new HashMap<>();
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
        variables.put(variable, value);
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
        int ret;
        if (variables.containsKey(variable))
        {
            ret = variables.get(variable);
        }
        else
        {
            throw new IllegalArgumentException("Variable " + variable + " not recognized");
        }
        return ret;
    }
}