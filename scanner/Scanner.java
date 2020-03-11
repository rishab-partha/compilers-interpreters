package scanner;
import java.io.*;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens after removing both inline and multi line
 * comments. A token is defined as:
 *  1. A word which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters and
 *     numbers
 *  2. A number defined as a non-empty sequence of digits
 *  3. An operand such as multiplication. This includes multicharacter operands like
 *     += or !=.
 *  4. A delimiter such as a semicolon.
 * @author Mr. Page
 * @author Rishab Parthasarathy
 * @version 2.4.2020
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
     *  initialized by the getNextChar method. This enables the look ahead process 
     *  by initializing the character being looked at one ahead. 
     * @param in is the reader object supplied by the program constructing
     *        this Scanner object.
     * @postcondition the reader is at the first character of the document provided by
     *                the reader
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }

    /**
     *  Constructor for Scanner objects.  The parameter should be an input stream.
     *  The instance field for the Reader is initialized to the input parameter,
     *  and the endOfFile indicator is set to false.  The currentChar field is
     *  initialized by the getNextChar method. This enables the look ahead process 
     *  by initializing the character being looked at one ahead. 
     *  @param in is the input stream object supplied by the program constructing
     *        this Scanner object.
     *  @postcondition the input stream is at the first character of the document provided by
     *        the input stream
     */
    public Scanner(InputStream inStream)
    {
        this.in = new BufferedReader(new InputStreamReader(inStream));
        endOfFile = false;
        getNextChar();
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream. It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java character. In the scenario of an input error,
     * the method terminates operation and exits.
     * 
     * @postcondition The input stream is advanced one character if it is not at
     *                end of file, and the currentChar instance field is set to the character 
     *                read from the input stream.  The flag endOfFile is set true if the input 
     *                stream is exhausted. In the case of an error, the system aborts.
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
     * ScanErrorException, but if the characters are equal, it advances the input stream
     * by one. This method is O(1).
     * 
     * @param s the character to be checked in comparison to the current character
     * @postcondition The input stream is advanced one character if it is not at
     *                end of file and if the characters match up. The currentChar instance 
     *                field is set to the character read from the input stream.  The flag
     *                endOfFile is set true if the input stream is exhausted. If the
     *                characters are not equivalent, a ScanErrorException is thrown.
     * @exception ScanErrorException if the two characters are not equivalent.
     */
    private void eat(char s) throws ScanErrorException
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
        return s == ';' || s == '(' || s == ')' || s == '{' || s == '}' || s == '[' 
            || s == ']'; 
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
        return s == '+' || s == '-' || s == '/' || s == '*' || s == ':' || s == '%' 
            || s == '=' || s == '!' || s == '<' || s == '>';
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
     * Scans a number by iterating through the input stream. In this definition,
     * a number is a sequence of digits that is of positive length. The regular
     * expression is (digit)(digit)*.
     * 
     * @return the number parsed by the Scanner
     * @precondition the current character is a digit
     * @postcondition the method scanNumber has parsed the whole number token after
     *                the current character. This is done by iterating until encountering
     *                a character that is not a digit or until encountering the end of file.
     * @throws ScanErrorException if the current character is not a digit.
     */
    private String scanNumber() throws ScanErrorException
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
    /**
     * Scans an identifier by iterating through the input stream. In this definition,
     * an identifier is a letter followed by a sequence of letters and digits
     * that is of nonnegative length. The regular expression is (letter)(digit | letter)*.
     * 
     * @return the identifier parsed by the Scanner
     * @precondition the current character is a letter
     * @postcondition the method ScanIdentifier has parsed the whole identifier token after
     *                the current character. This is done by iterating until encountering
     *                a character that is not a letter or digit or until the end of file.
     * @throws ScanErrorException if the current character is not a letter.
     */
    private String scanIdentifier() throws ScanErrorException
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
    /**
     * Scans an operand by iterating through the input stream. In this definition,
     * an operand is one of +, -, /, *, :, %, !, =, <, or > with or without an equals
     * sign following it.
     * 
     * @return the operand parsed by the Scanner
     * @precondition the current character is an operand
     * @postcondition the method scanOperand has parsed the whole operand token after
     *                the current character. This is done by checking the next character
     *                after the current character and checking whether it is an equal sign.
     *                If so, it is added to the operand, and otherwise, the method leaves the
     *                current character marker at the character after the operand.
     * @throws ScanErrorException if the current character is not an operand.
     */
    private String scanOperand() throws ScanErrorException
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
    /**
     * Parses whitespace until the end of the file or the next non whitespace character.
     * 
     * @postcondition the current character is either end of file or not whitespace
     * @return true if the file has not ended, false otherwise
     * @throws ScanErrorException if the currentChar does not match up with the eat parameter
     */
    private boolean parseWhiteSpace() throws ScanErrorException
    {
        while (isWhiteSpace(currentChar) && hasNext())
        {
            eat(currentChar);
        }
        if (! hasNext())
        {
            return false;
        }
        return true;
    }
    /**
     * Scans a delimiter by observing one character. In this definition,
     * a delimiter is one of the characters ;, (, ), {, }, or [, ].
     * 
     * @return the delimiter parsed by the Scanner
     * @precondition the current character is a delimiter
     * @postcondition the method scanDelimiter has parsed the whole delimiter token after
     *                the current character. This is done by reading the one character that
     *                marked as the current character and moving the marker indicating the 
     *                current character up by one space.
     * @throws ScanErrorException if the current character is not a delimiter.
     */
    private String scanDelimiter() throws ScanErrorException
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
     * Scans an inline comment until the end of a line.
     * 
     * @postcondition the end of the file or the end of a line is reached
     * @throws ScanErrorException if there is a parsing error
     */
    private void scanInline() throws ScanErrorException
    {
        while ((currentChar != '\n') && (currentChar != '\r') && hasNext())
        {
            eat(currentChar);
        }
    }

    /**
     * Parses the next Token in the document. A token is defined as
     *  1. A word which is defined as a non-empty sequence of characters that 
     *     begins with an alpha character and then consists of alpha characters and
     *     numbers
     *  2. A number defined as a non-empty sequence of digits
     *  3. An operand such as multiplication. This includes multicharacter operands like
     *     += or !=.
     *  4. A delimiter such as a semicolon.
     * First, nextToken eliminates all the whitespace. Then, nextToken looks at possible
     * comments based on the first character. After that, it eliminates each comment based
     * on the type as inline or multiline. Then, nextToken parses the document using the eat method 
     * and helper token reading methods while dividing the input into different types of tokens 
     * using the token identification helper methods. If a ScanErrorException is thrown, nextToken
     * prints the error and exits from the system.
     * 
     * @postcondition The program has either parsed and reached either the end of the token 
     *                or the end of file or crashed.
     * @return the next token in the document as a string
     */
    public String nextToken()
    {
        try
        {
            if (! parseWhiteSpace())
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
                    scanInline();
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
                if (!parseWhiteSpace())
                {
                    return "END";
                }
            }
            if (cur == '/')
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
            else if (isLetter(currentChar))
            {
                return scanIdentifier();
            }
            else if (isDigit(currentChar))
            {
                return scanNumber();
            }
            else if (isOperatorEqual(currentChar))
            {
                return scanOperand();
            }
            else if (isDelimiter(currentChar))
            {
                return scanDelimiter();
            }
            else if (currentChar == '.')
            {
                endOfFile = true;
                return "END";
            }
            else
            {
                throw new ScanErrorException("Unrecognized character in the input stream: " +
                    currentChar);
            }
        }
        catch (ScanErrorException e)
        {
            // char c = currentChar;
            // eat(currentChar);
            // return "Unrecognized Token: " + c;
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }
}
