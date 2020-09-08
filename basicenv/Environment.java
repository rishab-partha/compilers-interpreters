package basicenv;
import java.util.Map;
import java.util.HashMap;
/**
 * The class Environment denotes the place where the variables and line number
 * are stored. This allows variables and line number to be handled independently
 * of the parsing and abstract syntax tree, which allows for easier handling and lookup.
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Environment
{
    private Map<String, Integer> variables;
    private boolean hasEnded;
    private int nextLine;
    /**
     * This constructor initializes the map of variables to be empty.
     * 
     * @postcondition the map of variables is initialized
     */
    public Environment()
    {
        variables = new HashMap<>();
    }
    /**
     * This method sets the value of a String variable identifier to a integer value
     * within the symbol table.
     * 
     * @param variable The String identifier of the variable
     * @param value The new value of the variable
     * @postcondition The correct map contains the given value as the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        variables.put(variable, value);
    }
    /**
     * This method tries to retrieve the value associated with a variable from the map
     * of variables. If the variable has been created, this method just returns the value
     * associated with it. If the variable has not been created, 0 is put into the symbol 
     * table and returned.
     * 
     * @param variable the name of the variable to be retrieved
     * @postcondition the value of the variable is properly retrieved
     * @return the value of the variable, or 0 if it is not defined anywhere
     */
    public int getVariable(String variable)
    {
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        else
        {
            variables.put(variable, 0);
            return 0;
        }
    }
    /**
     * Method setEnd sets the flag hasEnded to true.
     * 
     * @postcondition hasEnded is true
     */
    public void setEnd()
    {
        hasEnded = true;
    }

    /**
     * Method getEnd gets whether the running has ended.
     * 
     * @return whether the running of the program has ended or not
     */
    public boolean getEnd()
    {
        return hasEnded;
    }
    /**
     * Method clear deletes all stored variables and sets the ending flag to false.
     * 
     * @postcondition all variables have been cleared and the ending flag is false
     */
    public void clear()
    {
        variables.clear();
        hasEnded = false;
    }

    /**
     * Method setLineNum sets the current line number to a certain value.
     * 
     * @param val the value to set the current line number to
     * @postcondition the current line number is set to the given value
     */
    public void setLineNum(int val)
    {
        nextLine = val;
    }

    /**
     * Method getLineNum finds the current line number.
     * 
     * @return the current line number
     */
    public int getLineNum()
    {
        return nextLine;
    }
}