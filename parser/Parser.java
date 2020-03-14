package parser;
import scanner.*;
import java.lang.IllegalArgumentException;
import java.util.Map;
import java.util.HashMap;
/**
 * A Parser uses the stream of tokens from a Scanner in order to 
 * execute simple commands and calculate simple expressions.
 * Currently, the Parser can:
 * 1. Read in input from standard input using the command READLN
 * 2. Calculate expressions that can include:
 *    a. Parentheses and negative signs
 *    b. Multiplication, division, and modulo using the symbols *, /, and %, respectively
 *    c. Addition and subtraction using the symbols + and -, respectively
 *    d. Variables named using a string identifier that have already been declared
 * 3. Write the value of a certain expression to standard output using the command WRITELN
 * 4. Declaring/Assigning a variable to a value using the symbol ":="
 * 5. Perform block expressions delineated by BEGIN and END
 * @author Rishab Parthasarathy
 * @version 3.13.2020
 */
public class Parser
{
    private Scanner sc;
    private java.util.Scanner scinput;
    private String curToken;
    private Map<String, Integer> variables;
    /**
     * A constructor for the Parser. Uses a Scanner that has been previously
     * written to tokenize the input into a stream of lexemes. This scanner
     * also assigns the current token being parsed to the next token in the
     * input stream provided by the scanner. In addition, the constructor
     * initializes the map that contains the variables and creates a scanner
     * for standard input in order to perform the READLN commands.
     * 
     * @param s The Scanner which the Parser uses to generate tokens from the input
     * @postcondition The Parser is currently observing the first token within the input
     *                document
     */
    public Parser(Scanner s)
    {
        sc = s;
        curToken = sc.nextToken();
        variables = new HashMap<String, Integer>();
        scinput = new java.util.Scanner(System.in);
    }
    /**
     * The eat method tests whether the string provided is equivalent to
     * the token in the lookahead. This allows the Parser to know whether the
     * input follows the rules provided in the grammar by checking whether the
     * next token to be parsed is actually what it is expected to be. If the tokens
     * are not equal, the eat method throws an IllegalArgumentException that informs the
     * user of what was expected and what was actually in the input file. This method
     * also advances the input stream by one token if not at end of file.
     * 
     * @param curT The expected value of the next token
     * @postcondition If the stream of tokens is not at the end of file, the eat method
     *                advances the stream of tokens by one if the tokens match up. This
     *                means that the input file is as expected. If the tokens do not match
     *                up, the eat method throws an IllegalArgumentException.
     */
    private void eat(String curT)
    {
        if (curT.equals(curToken))
        {
            curToken = sc.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected " + curT + " got " + curToken);
        }
    }
    /**
     * Method parseNumber takes the string version of the current token
     * and turns it into an integer, or number and also advances the input stream by one token. 
     * This method also throws a NumberFormatException if there is no number to parse.
     * 
     * @precondition The parser is currently observing a token that is an integer
     * @postcondition The integer token has been eaten and parsed
     * @return the value of the integer token
     */
    private int parseNumber()
    {
        int ret = Integer.parseInt(curToken);
        eat(curToken);
        return ret;
    }
    /**
     * Method parseStatement tries to parse 4 types of commands:
     *     1. WRITELN commands with the format WRITELN(expr); where
     *        expr denotes an expression containing numbers, variables, 
     *        parentheses, and arithmetic operations. The WRITELN command
     *        prints the value of the expression to the system output.
     *     2. READLN commands of the format READLN(var); where var
     *        is the name of a variable. The READLN command reads the next
     *        integer from standard input and assigns the value of the provided
     *        variable to that value.
     *     3. Block commands of the format:
     *        BEGIN
     *             statements
     *        END
     *        Within the BEGIN and END, there can be any of the 4 types of commands
     *        listed here, and the method uses a while loop to parse all of the 
     *        commands.
     *     4. Variable assignment/declaration commands of the form var := expr, where
     *        expr denotes an expression containing numbers, parentheses, 
     *        and arithmetic operations and var denotes a variable name. This method
     *        stores the variable name in a map and assigns the key to the value of the expression
     * 
     * @precondition The parser is currently observing a token that begins one of the 4
     *               command types described above
     * @postcondition The command has been properly executed and the parser has eaten/parsed
     *                the entirety of the command
     *                
     */
    public void parseStatement()
    {
        if (curToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            System.out.println(parseExpression());
            eat(")");
        }
        else if (curToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String id = curToken;
            int val = scinput.nextInt();
            variables.put(id, val);
            eat (id);
            eat(")");
        }
        else if (curToken.equals("BEGIN"))
        {
            eat("BEGIN");
            while (! curToken.equals("END"))
            {
                parseStatement();
            }
            eat("END");
        }
        else
        {
            String varname = curToken;
            eat(varname);
            eat(":=");
            int ret = parseExpression();
            variables.put(varname, ret);
        }
        eat(";");
    }
    /**
     * Method parseFactor parses four types of factors (simplest form of an expression):
     *     1. A factor of the form (expr), where expr denotes an expression containing 
     *        numbers, variables, parentheses, and arithmetic operations. This type of
     *        factor simply has the value of the expression.
     *     2. A factor of the form -fctr, where fctr is a factor defined by the rules
     *        described here. The value of this factor is -1*valfctr, if valfctr is the
     *        value of the aforementioned factor fctr.
     *     3. A factor of the form id, where id is the name of a previously defined
     *        variable. The value of this factor is just the value stored within the variable.
     *     4. A factor of the form num, where num is an integer. The value of this factor is
     *        just the number, which is computed using the method parseNumber().
     * 
     * @precondition The parser is currently observing a token that begins one of the 4
     *               factor types described above
     * @postcondition The factor has been properly computed and the parser has eaten/parsed
     *                the entirety of the factor
     * @return the value of the factor
     */
    private int parseFactor()
    {
        int ret;
        if (curToken.equals("("))
        {
            eat("(");
            ret = parseExpression();
            eat(")"); 
        }
        else if (curToken.equals("-"))
        {
            eat("-");
            ret = parseFactor();
            ret *= -1;
        }
        else if (variables.containsKey(curToken))
        {
            ret = variables.get(curToken);
            eat(curToken);
        }
        else
        {
            ret = parseNumber();
        }
        return ret;
    }
    /**
     * Method parseTerm parses four types of terms (medium complicated form of an expression):
     *     1. A term of the format trm*fctr, where trm is a term that follows the rules defined
     *        here and fctr is a factor that is defined by the rules in the method parseFactor().
     *        The value of this term is the value of trm multiplied by the value of fctr.
     *     2. A term of the format trm/fctr, where trm is a term that follows the rules defined
     *        here and fctr is a factor that is defined by the rules in the method parseFactor().
     *        The value of this term is the value of trm divided by the value of fctr.
     *     3. A term of the format trm%fctr, where trm is a term that follows the rules defined
     *        here and fctr is a factor that is defined by the rules in the method parseFactor().
     *        The value of this term is the value of trm modded by the value of fctr.
     *     4. A term of the format fctr where fctr is a factor that is defined by the rules in the
     *        method parseFactor().
     * The dilemma that arises with these definitions is that it is impossible to choose between
     * definitions 1-3 and 4, so instead of using a naive implementation, we notice that each term
     * consists of factors separated by the operators *, /, and %, and is evaluated from left to
     * right. Thus, we use a while loop to loop from the first factor until a separator is
     * observed between two factors that is not *, /, or %. While doing this, we keep a running
     * return value to return when the operations are done.
     * 
     * @precondition The parser is currently observing a token that begins one of the 4
     *               term types described above
     * @postcondition The term has been properly computed and the parser has eaten/parsed
     *                the entirety of the term
     * @return the value of the term
     */
    private int parseTerm()
    {
        int ret = parseFactor();
        while (curToken.equals("*") || curToken.equals("/") || curToken.equals("%"))
        {
            if (curToken.equals("*"))
            {
                eat("*");
                ret *= parseFactor();
            }
            else if (curToken.equals("%"))
            {
                eat("%");
                ret %= parseFactor();
            }
            else
            {
                eat("/");
                ret /= parseFactor();
            }
        }
        return ret;
    }
    /**
     * Method parseExpression parses three types of expressions:
     *     1. A term of the format expr+trm, where expr is a expression that follows the rules 
     *        defined here and trm is a term that is defined by the rules in the method 
     *        parseTerm(). The value of this expression is the value of expr added to 
     *        the value of trm.
     *     2. A term of the format expr-trm, where expr is a expression that follows the rules 
     *        defined here and trm is a term that is defined by the rules in the method 
     *        parseTerm(). The value of this expression is the value of expr subtracted by 
     *        the value of trm.
     *     3. A term of the format trm where trm is a term that is defined by the rules in the
     *        method parseTerm().
     * The dilemma that arises with these definitions is that it is impossible to choose between
     * definitions 1-2 and 3, so instead of using a naive implementation, we notice that each
     * expression consists of terms separated by the operators + and - and is evaluated from 
     * left to right. Thus, we use a while loop to loop from the first term until a separator is
     * observed between two terms that is not + or -. While doing this, we keep a running
     * return value to return when the operations are done.
     * 
     * @precondition The parser is currently observing a token that begins one of the 3
     *               expression types described above
     * @postcondition The expression has been properly computed and the parser has eaten/parsed
     *                the entirety of the expression
     * @return the value of the expression
     */
    private int parseExpression()
    {
        int ret = parseTerm();
        while (curToken.equals("+") || curToken.equals("-"))
        {
            if (curToken.equals("+"))
            {
                eat("+");
                ret += parseTerm();
            }
            else
            {
                eat("-");
                ret -= parseTerm();
            }
        }
        return ret;
    }
}