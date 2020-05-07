package emitter;
import java.io.*;

public class Emitter
{
	private PrintWriter out;
	private int counter = 0;
	//creates an emitter for writing to a new file with given name
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

	//prints one line of code to file (with non-labels indented)
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	public void push(String address)
	{
		emit("subu $sp $sp 4");
		emit("sw $" + address +  " ($sp)");
	}
	public void pop(String address)
	{
		emit("lw $" + address + " ($sp)");
		emit("addu $sp $sp 4");
	}

	public void newline()
	{
		emit("la $a0 newline");
		emit("li $v0 4");
		emit("syscall");
	}
	public int nextLabelID()
	{
		counter++;
		return counter;
	}
	//closes the file.  should be called after all calls to emit.
	public void close()
	{
		out.close();
	}
} 