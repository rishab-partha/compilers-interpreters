package emitter;
import java.io.*;
/**
 * Emitter prints MIPS code to a file. It has functionalities that allows
 * easy printing of the commands:
 * 1. Printing a Newline Character in MIPS
 * 2. Pushing a Value onto the Stack
 * 3. Popping a Value off the Stack
 * 4. Systematic Naming of If Endings
 * 
 * @author Ms. Datar
 * @author Rishab Parthasarathy
 * @version 05.07.2020
 */
public class Emitter
{
	private PrintWriter out;
	private int counter = 0;
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
	 * 
	 * @param address The address of the value to get pushed onto the stack
	 * @postcondition the stack pointer points to the address of the new top of the stack
	 */
	public void push(String address)
	{
		emit("subu $sp $sp 4");
		emit("sw $" + address +  " ($sp) #push onto the stack");
	}

	/**
	 * Method pop pops the value on top of the stack to a certain address. The pointer for the top
	 * of the stack moves to point at the value beneath the popped one, which is the new top.
	 * 
	 * @param address The address to pop into
	 * @postcondition the stack pointer points to the address of the new top of the stack
	 */
	public void pop(String address)
	{
		emit("lw $" + address + " ($sp)");
		emit("addu $sp $sp 4 #pop off the stack");
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
} 