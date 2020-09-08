package basicast;
import basicenv.Environment;
import java.util.*;
/**
 * Program serves as essentially the wrapper for the whole Abstract Syntax Tree (AST).
 * Program stores a list of statements mapped to line numbers, and the program also stores
 * a sorted list of line numbers for execution of the lines in sorted order. The Program
 * class supports printing, executing from top to bottom, adding statements, and clearing
 * all statements.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Program 
{
    private Map<Integer, Statement> curProg;
    private List<Integer> lineNums;
    /**
     * The constructor initializes the map of line numbers to statements and the 
     * sorted list of line numbers.
     * 
     * @postcondition the map of line numbers to statements and the sorted list of line numbers
     *                are initialized to empty
     */
    public Program()
    {
        curProg = new HashMap<>();
        lineNums = new ArrayList<>();
    }
    /**
     * Method exec executes the program as a whole by first clearing all variables in
     * the environment. Then, the program tries to iterate down the list of line numbers
     * and execute the statements. To coordinate its line numbers with the environment,
     * for every iteration of execution, the program queries the environment's next line
     * number to find what number to go to. If the number is out of range and is not the 
     * line number -1, the system prints that the line number is not recognized. If the line
     * number is -1, the program exits smoothly.
     * 
     * @param env The environment in which operations are occurring
     * @precondition The statements are evaluatable and do not have infinite loops
     * @postcondition The program has run through and produced the correct results
     */
    public void exec(Environment env)
    {
        env.clear();
        if (lineNums.size() == 0)
        {
            return;
        }
        int curIndex = 0;
        env.setLineNum(lineNums.get(curIndex));
        while (!env.getEnd())
        {
            int lineNum = env.getLineNum();
            curIndex = lineNums.indexOf(lineNum);
            if (curIndex == -1 && lineNum == -1)
            {
                return;
            }
            else if (curIndex == -1)
            {
                System.out.println("TRIED TO GOTO LINE #" + lineNum);
                return;
            }
            Statement st = curProg.get(lineNum);
            curIndex++;
            lineNum = curIndex>=lineNums.size()?-1:lineNums.get(curIndex);
            env.setLineNum(lineNum);
            st.exec(env);
        }
    }

    /**
     * Method list prints out the Program in the format:
     * 
     *      ln1 stmt1
     *        ...
     *      lnk stmtk
     * 
     * where the lns are line numbers and stmts are statements.
     * 
     * @postcondition the program has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        for (int i = 0; i < lineNums.size(); i++)
        {
            int lineNum = lineNums.get(i);
            System.out.print(lineNum + " ");
            curProg.get(lineNum).list();
        }
    }

    /**
     * The method clear clears the Program by deleting all the stored statements and
     * line numbers.
     * 
     * @postcondition the program has cleared all the stored data
     */
    public void clear()
    {
        curProg.clear();
        lineNums.clear();
    }

    /**
     * Method add adds a statement and corresponding line number to the program.
     * If the program does not already have the line number, the line number is added
     * to the list of line numbers. Then, the statement is put into the map of line
     * numbers to statements.
     * 
     * @param lineNum The line number of the statement to be added
     * @param st the statement to be added to the program
     * @postcondition the statement has been added to the map, and if the line number is
     *                not already in the program, it is added to the list.
     */
    public void add(int lineNum, Statement st)
    {
        if (!curProg.containsKey(lineNum))
        {
            int i = 0;
            while (i < lineNums.size() && lineNum > lineNums.get(i))
            {
                i++;
            }
            lineNums.add(i, lineNum);
        }
        curProg.put(lineNum, st);
    }
}