import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens after removing
 * comments. A token is defined as:
 *  1. A word which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters and
 *     numbers
 *  2. A number defined as a non-empty sequence of digits
 *  3. An operand such as multiplication
 *  4. A delimiter such as a semicolon
 * @author Mr. Page
 * @author Rishab Parthasarathy
 * @version 1.27.2020
 *
 */

public class Scanner
{
    private Reader in;
    private char currentChar; 
    private boolean endOfFile;

    /**
     * Constructor for Scanner objects.  The Reader object should be one of
     *  1. A StringReader
     *  2. A BufferedReader wrapped around an InputStream
     *  3. A BufferedReader wrapped around a FileReader
     *  The instance field for the Reader is initialized to the input parameter,
     *  and the endOfFile indicator is set to false.  The currentChar field is
     *  initialized by the getNextChar method.
     * @param in is the reader object supplied by the program constructing
     *        this Scanner object.
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java character.
     * @postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1) 
                endOfFile = true;
            else 
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * The eat method tests whether the character provided is equivalent to the 
     * character stored in the Scanner to insure the security of the eat method and
     * parsing from external intervention. If the characters are unequal, it throws an 
     * IllegalArgumentException, but if the characters are equal, it advances the input stream
     * by one. This method is O(1).
     * @param s the character to be checked in comparison to the current character
     * @postcondition: The input stream is advanced one character if it is not at
     * end of file along with the characters matching up
     * and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     * @exception ScanErrorException if the two characters are not equivalent.
     */
    private void eat(char s)
    {
        if (s != currentChar)
        {
            throw new ScanErrorException("You called method eat with parameter " +
                s + " when it expected a value of " + currentChar);
        }
        getNextChar();
    }

    /**
     * Checks whether a given character is a letter using 
     * the ascii value of the characer.
     *
     * @param s One character.
     * @return true if the character is a letter; else,
     *         return false.
     */
    public static boolean isLetter(char s)
    {
        return s >= 'A' &&  s <= 'z'
            && (s <= 'Z' || s >= 'a');
    }

    /**
     * Checks whether a given character is a digit using 
     * the ascii value of the characer.
     *
     * @param s One character.
     * @return true if the character is a digit; else,
     *         return false.
     */
    public static boolean isDigit(char s)
    {
        return s >= '0' && s <= '9';
    }

    /**
     * Checks whether a given character is white space
     * using the ascii value of the characer.
     *
     * @param s one character.
     * @return true if the character is white space; else,
     *         return false.
     */
    public static boolean isWhiteSpace(char s)
    {
        return s == ' ' || s == '\t' || s == '\n' || s == '\r';
    }
    /**
     * Checks whether a given character is a delimiter using 
     * the ascii value of the characer.
     *
     * @param s One character.
     * @return true if the character is a delimiter; else,
     *         return false.
     */
    public static boolean isDelimiter(char s)
    {
        return s == ';' || s == '(' || s == ')' || s == '{' || s == '}' || s == '[' || s == ']' || s == '.'; 
    }
    /**
     * Checks whether a given character is an operator using 
     * the ascii value of the characer.
     *
     * @param s One character.
     * @return true if the character is an operator; else,
     *         return false.
     */
    public static boolean isOperatorEqual(char s)
    {
        return s == '+' || s == '-' || s == '/' || s == '*' || s == ':' || s == '%' || s == '=' || s == '!' || 
        s == '<' || s == '>';
    }

    /**
     * Finds whether there are still more tokens to parse
     * by looking if the end of file has been reached yet.
     *
     * @return true if there are more tokens to parse; else,
     *         return false.
     */
    public boolean hasNext()
    {
        return ! endOfFile;
    }
    /**
     * Scans a number by iterating through the input stream.
     * 
     * @param cur the current starting character of the number
     * @precondition the character cur is a 
     */
    private String scanNumber()
    {
        String ret = "";
        if (isDigit(currentChar))
        {
            ret += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return ret;
            }
        }
        else
        {
            throw new ScanErrorException("Did not find a number.");
        }
        while (isDigit(currentChar))
        {
            ret += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return ret;
            }
        }
        return ret;
    }

    private String scanIdentifier()
    {
        String ret = "";
        if (isLetter(currentChar))
        {
            ret += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return ret;
            }
        }
        else
        {
            throw new ScanErrorException("Did not find a letter.");
        }
        while (isDigit(currentChar) || isLetter(currentChar))
        {
            ret += currentChar;
            if(hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return ret;
            }
        }
        return ret;
    }

    private String scanOperand()
    {
        String ret = "";
        if (isOperatorEqual(currentChar))
        {
            ret += currentChar;
            if (hasNext())
            {
                eat(currentChar);
            }
            else
            {
                return ret;
            }
        }
        else
        {
            throw new ScanErrorException("Did not find a operand.");
        }
        if (currentChar == '=')
        {
            ret += currentChar;
            if (hasNext())
            {
                eat(currentChar);
            }
        }
        return ret;
    }

    private String scanDelimiter()
    {
        String ret = "";
        if (isDelimiter(currentChar))
        {
            ret += currentChar;
        }
        else
        {
            throw new ScanErrorException("Did not find a delimiter.");
        }
        if (hasNext())
        {
            eat(currentChar);
        }
        return ret;
    }

    /**
     * Parses the next Token in the document. A token is defined as
     *  1. A non-empty sequence of characters starting with
     *  an alphanumeric character and consisting of alpha characters,
     *  numbers, and special characters (hyphens and apostrophes).
     *  2. A delimiter marking the end of the sentence, which can
     *  be a period, a question mark, or an exclamation mark.
     *  3. A delimiter marking the end of the file, which is 
     *  a special end of file token.
     *  4. A delimiter marking the end of a phrase, which can be
     *  a colon, semicolon, or comma.
     *  5. One character consisting of a number.
     *  6. Any character that does not fit into any of the above classes.
     *  nextToken parses the document using the eat method and divides the
     *  input into different types of tokens using the token identification
     *  helper methods.
     *  @return the next token in the document as a Token Object which contains
     *  the type of token it is (END_OF_FILE, END_OF_SENTENCE, END_OF_PHRASE, DIGIT,
     *  UNKNOWN, or WORD) and the string value of the token itself.
     */
    public String nextToken()
    {
        while (isWhiteSpace(currentChar) && hasNext())
        {
            eat(currentChar);
        }
        if (! hasNext())
        {
            return "END";
        }
        char cur = ' ';
        while (currentChar == '/')
        {
            cur = currentChar;
            eat(currentChar);
            if (currentChar == '/')
            {
                while ((currentChar != '\n') && (currentChar != '\r') && hasNext())
                {
                    eat(currentChar);
                }
                if (hasNext())
                {
                    eat(currentChar);
                }
            }
            else if (cur == '/' && currentChar == '*')
            {
                while (!(cur == '*' && currentChar == '/') && hasNext())
                {
                    cur = currentChar;
                    eat(currentChar);
                }
                if (hasNext())
                {
                    eat(currentChar);
                }
            }
            while (isWhiteSpace(currentChar) && hasNext())
            {
                eat(currentChar);
            }
            if (! hasNext())
            {
                return "END";
            }
        }
        if (isLetter(currentChar))
        {
            return scanIdentifier();
        }
        else if (isDigit(currentChar))
        {
            return scanNumber();
        }
        else if (cur == '/')
        {
            if (currentChar == '=')
            {
                if (hasNext())
                {
                    eat(currentChar);
                }
                return "/=";
            }
            else
            {
                return "/";
            }
        }
        else if (isOperatorEqual(currentChar))
        {
            return scanOperand();
        }
        else
        {
            return scanDelimiter();
        }
    }
}
