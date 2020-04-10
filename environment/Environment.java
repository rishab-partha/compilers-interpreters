package environment;
import ast.ProcedureDeclaration;
import java.util.Map;
import java.util.HashMap;
/**
 * The class Environment denotes the place where the variables and procedures
 * are stored. This allows variables and procedures to be handled independently
 * of the parsing and abstract syntax tree, which allows for easier handling and lookup.
 * The environment also has a parent in order to support child environments and local
 * variables, and the retrieval methods are also modeled around parent and child
 * environments.
 * @author Rishab Parthasarathy
 * @version 04.10.2020
 */
public class Environment
{
    private Map<String, Integer> variables;
    private Map<String, ProcedureDeclaration> procedures;
    Environment par;
    /**
     * This constructor initializes the map of variables and procedures to be empty.
     * Also, the constructor initializes the parent environment, which is null if this
     * is the global environment.
     * 
     * @param p the parent environment
     * @postcondition the map of variables and procedures are initialized and empty
     */
    public Environment(Environment p)
    {
        variables = new HashMap<>();
        procedures = new HashMap<>();
        par = p;
    }
    /**
     * This method sets the value of a String variable identifier to a integer value
     * within the symbol table. If the current environment does not already contain
     * the variable and some environment recursively above the current environment
     * does, the variable is put into the symbol table of the lowest environment
     * that has this variable. If this is not true, the variable is put into the
     * current environment's symbol table.
     * 
     * @param variable The String identifier of the variable
     * @param value The new value of the variable
     * @postcondition The correct map contains the given value as the value of the variable
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
    /**
     * Method declareVariable sets a variable name to a value within the current environment's
     * symbol table.
     * 
     * @param variable the name of the variable
     * @param value the value of the variable
     * @postcondition The current environment has associated the variable name and valueS
     */
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }
    /**
     * Method containsVariable checks whether this environment or any environment above this
     * one has a certain variable.
     * 
     * @param variable The name of the variable to look for
     * @return true if this environment or any one above it has the variable; else,
     *         return false
     */
    private boolean containsVariable(String variable)
    {
        boolean ret = false;
        if (par != null)
        {
            ret = par.containsVariable(variable);
        }
        return ret || variables.containsKey(variable);
    }
    /**
     * This method tries to retrieve the value associated with a variable from the map
     * of variables. If the variable has been created, this method just returns the value
     * associated with it. If the variable has not been created, there are two possibilities.
     * If there is no parent, 0 is put into the symbol table and returned. Else, the value of
     * the variable stored within the parent environment is returned.
     * 
     * @param variable the name of the variable to be retrieved
     * @precondition the variable is in this environment or the parent
     * @postcondition the value of the variable is properly retrieved
     * @return the value of the variable, or 0 if it is not defined anywhere
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
    /**
     * Method setProcedure goes to the global symbol table and associates a
     * procedure name with a procedure declaration by putting them in a map.
     * 
     * @param procedure The name of the procedure
     * @param dec The declaration containing parameters and statement
     * @postcondition the procedure name is associated with the declaration
     *                in the global symbol table
     */
    public void setProcedure(String procedure, ProcedureDeclaration dec)
    {
        if (par != null)
        {
            par.setProcedure(procedure, dec);
            return;
        }
        procedures.put(procedure, dec);
    }
    /**
     * Method getProcedure retrieves a procedure declaration from the global symbol
     * table. If the procedure is not defined, an Illegal Argument Exception is thrown.
     *
     * @param procedure The name of the procedure
     * @precondition the procedure is stored within the global symbol table
     * @postcondtion the procedure's declaration is properly retrieved
     * @return the procedure's declaration
     */
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
    /**
     * Method getPar finds the global environment by iterating up as much as possible.
     * 
     * @return the global environment
     */
    public Environment getPar()
    {
        if (par == null)
        {
            return this;
        }
        return par.getPar();
    }
}