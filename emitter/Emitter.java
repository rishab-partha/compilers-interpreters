package emitter;
import ast.ProcedureDeclaration;
import java.util.List;
import java.io.*;
/**
 * Emitter prints MIPS code to a file. It has functionalities that allows
 * easy printing of the commands:
 * 1. Printing a Newline Character in MIPS
 * 2. Pushing a Value onto the Stack
 * 3. Popping a Value off the Stack
 * 4. Systematic Naming of If Endings
 * 5. Defining ProcedureContext
 * 6. Finding the Offset from the stack
 * 
 * @author Ms. Datar
 * @author Rishab Parthasarathy
 * @version 05.21.2020
 */
public class Emitter
{
	private PrintWriter out;
	private int counter = 0;
	private ProcedureDeclaration pd;
	private int excessStackHeight;
	private int savedStackHeight;
	/**
	 * The constructor creates an Emitter that prints to a file with a certain name.
	 * 
	 * @param outputFileName The name of the output MIPS file
	 * @throws RuntimeException if the file cannot be written to
	 */
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method emit prints one line of MIPS code to the file. If the code
	 * is not a label, it is automatically indented.
	 *
	 * @param code The line of code to be printed
	 * @postcondition The file contains the new line of code on its individual line
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}
	/**
	 * Method push pushes the value at a certain address onto the stack. The pointer
	 * for the top of the stack moves to point at the new value on top of the stack.
	 * The push method also increases the excess stack height by 4.
	 * 
	 * @param address The address of the value to get pushed onto the stack
	 * @postcondition the stack pointer points to the address of the new top of the stack
	 */
	public void push(String address)
	{
		emit("subu $sp $sp 4");
		emit("sw $" + address +  " ($sp) #push onto the stack");
		excessStackHeight += 4;
	}

	/**
	 * Method saveStackHeight saves the current excess stack height into savedStackHeight.
	 * This is used when procedures are called inside other procedures because the stack
	 * height before parameters are popped on needs to be remembered.
	 * 
	 * @postcondition savedStackHeight contains the current value of excessStackHeight
	 */
	public void saveStackHeight()
	{
		savedStackHeight = excessStackHeight;
	}

	/**
	 * Method restoreStackHeight restores the old stack height from savedStackHeight. This is 
	 * used after procedure calls within procedures in order to find the stack height from
	 * before the call's parameters were popped on. 
	 * 
	 * @postcondition the value of excessStackHeight is the stored value of savedStackHeight
	 */
	public void restoreStackHeight()
	{
		excessStackHeight = savedStackHeight;
	}

	/**
	 * Method pop pops the value on top of the stack to a certain address. The pointer for the top
	 * of the stack moves to point at the value beneath the popped one, which is the new top. The 
	 * method also decreases the excess stack height by 4.
	 * 
	 * @param address The address to pop into
	 * @postcondition the stack pointer points to the address of the new top of the stack
	 */
	public void pop(String address)
	{
		emit("lw $" + address + " ($sp)");
		emit("addu $sp $sp 4 #pop off the stack");
		excessStackHeight -= 4;
	}

	/**
	 * Method newline emits the code required to print a newline character in MIPS.
	 * 
	 * @postcondition the code to print a newline has been emitted to the file
	 */
	public void newline()
	{
		emit("la $a0 newline");
		emit("li $v0 4");
		emit("syscall #print newline");
	}

	/**
	 * Method nextLabelID generates the ID number for each IF and WHILE statement.
	 * 
	 * @return the ID number of the current statement
	 */
	public int nextLabelID()
	{
		counter++;
		return counter;
	}
	/**
	 * Method close closes the file and should be called after the Emitter is done emitting.
	 * 
	 * @postcondition The file is closed
	 */
	public void close()
	{
		out.close();
	}

	/**
	 * Method setProcedureContext resets the procedure context to the given procedure declaration
	 * and also resets the excess stack height to 0.
	 * 
	 * @param proc the new procedure context
	 * @postcondition procedure context and excess stack height have been set to the new value and
	 * 				  0, respectively
	 */
	public void setProcedureContext(ProcedureDeclaration proc)
	{
		pd = proc;
		excessStackHeight = 0;
	}
	
	/**
	 * Method clearProcedureContext clears the procedure context to null in order to signify no
	 * procedure.
	 * 
	 * @postcondition the procedure context is null
	 */
	public void clearProcedureContext()
	{
		pd = null;
	}

	/**
	 * Method isLocalVariable figures out whether a certain variable name is local or global.
	 * If the procedure context exists and the variable name is either a local variable,
	 * parameter, or return value of the procedure context, the method identifies the variable
	 * as local. Otherwise, the variable is identified as global.
	 * 
	 * @param varName the name of the variable to be analyzed for whether it is local
	 * @return true if the variable is local, else, false
	 */
	public boolean isLocalVariable(String varName)
	{
		if (pd == null)
		{
			return false;
		}
		List<String> params = pd.getParams();
		List<String> vars = pd.getVars();
		for (String p : params)
		{
			if (varName.equals(p))
			{
				return true;
			}
		}
		for (String v : vars)
		{
			if (varName.equals(v))
			{
				return true;
			}
		}
		if (pd.getName().equals(varName))
		{
			return true;
		}
		return false;
	}
	/**
	 * Method getOffset finds the offset of the position of a certain variable in the stack
	 * from the top. It does this by using the excessStackHeight and figuring out whether
	 * the variable is a return value, a local variable, or parameter. If the variable is
	 * a return value, the method looks right at excessStackHeight. If the variable is a 
	 * local var, the method looks at offsets less than the excess stack height. Finally,
	 * if the variable is a parameter, the method looks at offsets greater than the excess
	 * stack height.
	 * 
	 * @param varName the name of the variable whose offset is being found
	 * @precondition varName is either a parameter, local variable, or return value of the
	 * 				 current procedure context
	 * @postcondition the offset of varName in the stack has been properly calculated
	 * @return the offset of varName in the stack if varName is defined in the context, else
	 * 		   -1
	 */
	public int getOffset(String varName)
	{
		if (pd.getName().equals(varName))
		{
			return excessStackHeight;
		}
		List<String> params = pd.getParams();
		List<String> vars = pd.getVars();
		int len2 = params.size();
		int len1 = vars.size();
		for (int i = 0; i < len1; i++)
		{
			if (varName.equals(vars.get(i)))
			{
				return excessStackHeight - 4*(i + 1);
			}
		}
		for (int i = 0; i < len2; i++)
		{
			if (varName.equals(params.get(i)))
			{
				return excessStackHeight + 4*(len2 - i);
			}
		}
		return -1;
	}

} 