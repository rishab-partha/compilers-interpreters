package basicast;
import basicenv.Environment;
/**
 * Class Rem encodes a statement that is a comment.
 * 
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Rem extends Statement
{
    private String comment;

    /**
     * The constructor initializes the comment stored by the REM statement.
     * @param s the value of the comment
     */
    public Rem (String s)
    {
        comment = s;
    }

    /**
     * The method exec does not do anything because a comment does not have executable
     * effect.
     * 
     * @param env the Environment in which operations occur
     */
    public void exec(Environment env)
    {
    }

    /**
     * Method list prints out the REM statement in the format:
     * 
     *      REM cmt
     * 
     * where cmt is the string comment.
     * 
     * @postcondition the REM statement has been printed to standard output with the above
     *                formatting
     */
    public void list()
    {
        System.out.println("REM " + comment);
    }
}