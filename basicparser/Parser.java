package basicparser;
import basicscanner.*;
import basicast.Number;
import basicast.BinOp;
import basicast.Assignment;
import basicast.Variable;
import basicast.Writeln;
import basicast.Expression;
import basicast.Statement;
import basicast.Condition;
import basicast.If;
import basicast.Program;
import basicast.Readln;
import basicenv.Environment;
import basicast.GoTo;
import basicast.End;
import basicast.Rem;
/**
 * A Parser uses the stream of tokens from a Scanner in order to 
 * execute simple commands and calculate simple expressions.
 * Currently, the Parser can:
 * 1. Read in input from standard input using the command INPUT
 * 2. Calculate expressions that can include:
 *    a. Parentheses and negative signs
 *    b. Multiplication, division, and modulo using the symbols *, /, and %, respectively
 *    c. Addition and subtraction using the symbols + and -, respectively
 *    d. Variables named using a string identifier that have already been declared
 * 3. Write the value of a certain expression to standard output using the command PRINT
 * 4. Declaring/Assigning a variable to a value using the symbol "=" and the command LET
 * 5. Perform IF statements
 * 6. Perform GOTO statements
 * 7. Perform REM (comment) and END statements
 * 8. List all stored lines using LIST
 * 9. Run all stored lines using RUN
 * 10. Clear all stored data using CLEAR
 * 11. Print out a link to documentation using HELP
 * 12. Quit the program using QUIT
 * 13. Storing lines with line numbers and immediately executing lines without line numbers
 * @author Rishab Parthasarathy
 * @version 05.27.2020
 */
public class Parser
{
    private Scanner sc;
    private String curToken;
    private Environment env;
    /**
     * A constructor for the Parser. Uses a Scanner that has been previously
     * written to tokenize the input into a stream of lexemes. This scanner
     * also assigns the current token being parsed to the next token in the
     * input stream provided by the scanner. In addition, the constructor
     * initializes the environment in which the program will be executed.
     * 
     * @param s The Scanner which the Parser uses to generate tokens from the input
     * @postcondition The Parser is currently observing the first token within the input
     *                document
     */
    public Parser(Scanner s, Environment e)
    {
        sc = s;
        curToken = sc.nextToken();
        env = e;
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
     * and turns it into an Number, or an integer in the AST and advances the 
     * input stream by one token. This method also throws a NumberFormatException 
     * if there is no number to parse.
     * 
     * @precondition The parser is currently observing a token that is an integer
     * @postcondition The integer token has been eaten and parsed
     * @return an AST Number containing the value
     */
    private Number parseNumber()
    {
        int ret = Integer.parseInt(curToken);
        eat(curToken);
        return new Number(ret);
    }
    /**
     * Method parseNumber takes the string version of the current token
     * and turns it into an integer and advances the input stream by one token. 
     * This method also throws a NumberFormatException if there is no number to parse.
     * 
     * @precondition The parser is currently observing a token that is an integer
     * @postcondition The integer token has been eaten and parsed
     * @return the integer value of the token
     */
    private int parseInt()
    {
        int ret = Integer.parseInt(curToken);
        eat(curToken);
        return ret;
    }
    /**
     * Method parseStatement tries to parse 7 types of commands that can be used with line #s:
     *     1. PRINT commands with the format:
     *          PRINT expr 
     *        where expr denotes an expression containing numbers, variables, 
     *        parentheses, and arithmetic operations. The PRINT command
     *        prints the value of the expression to the system output.
     * 
     *     2. INPUT commands of the format:
     *           INPUT var
     *        where var is the name of a variable. The INPUT command reads the next
     *        integer from standard input and assigns the value of the provided
     *        variable to that value.
     * 
     *     3. END commands of the format:
     *           END 
     *        that instantly end code execution when the program is run.
     * 
     *     4. IF statements of the format:
     *          IF cond THEN ln
     *        cond represents a boolean condition and ln represents a line number. If the condition
     *        is true, the program jumps to the line number within ln.
     *
     *     5. GOTO statements of the format:
     *          GOTO ln 
     *        where ln represents a line number. The program instantly jumps to line ln.
     * 
     *     6. Variable assignment/declaration commands of the form:
     *          LET var = expr 
     *        where expr denotes an expression containing numbers, parentheses, 
     *        and arithmetic operations and var denotes a variable name. This method
     *        stores the variable name in a map and assigns the key to the value of the expression.
     * 
     *     7. REM statements of the form:
     *          REM cmt
     *        where cmt is some comment. In the program, REM serves just for comments.
     * 
     * This method stores all of these 7 functionalities within nodes of the Abstract
     * Syntax Tree or AST. This allows the AST to simplify and evaluate everything
     * more efficiently after all of the parsing is done.
     * 
     * @precondition The parser is currently observing a token that begins one of the 7
     *               command types described above
     * @postcondition The command has been transformed to part of an AST and the parser has
     *                eaten/parsed the entirety of the command
     * @return the AST portion referring to the statement that has been parsed
     */
    private Statement parseStatement()
    {
        Statement ret = null;
        if (curToken.equals("PRINT"))
        {
            eat("PRINT");
            ret = new Writeln(parseExpression());
        }
        else if (curToken.equals("INPUT"))
        {
            eat("INPUT");
            String id = curToken;
            ret = new Readln(sc, id);
            eat(id);
        }
        else if (curToken.equals("END"))
        {
            eat("END");
            ret = new End();
        }
        else if (curToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            int n = parseInt();
            ret = new If(cond, n);
        }
        else if (curToken.equals("GOTO"))
        {
            eat("GOTO");
            ret = new GoTo(parseInt());
        }
        else if (curToken.equals("LET"))
        {
            eat("LET");
            String varname = curToken;
            eat(varname);
            eat("=");
            ret = new Assignment(varname, parseExpression());
        }
        else if (curToken.equals("REM"))
        {
            eat("REM");
            String s = "";
            while (! curToken.equals("\n"))
            {
                s += curToken + " ";
                eat(curToken);
            }
            ret = new Rem(s);
        }
        return ret;
    }
    /**
     * Method parseStatement tries to parse 3 types of commands that can be used with line #s:
     *     1. PRINT commands with the format:
     *          PRINT expr 
     *        where expr denotes an expression containing numbers, variables, 
     *        parentheses, and arithmetic operations. The PRINT command
     *        prints the value of the expression to the system output.
     * 
     *     2. INPUT commands of the format:
     *           INPUT var
     *        where var is the name of a variable. The INPUT command reads the next
     *        integer from standard input and assigns the value of the provided
     *        variable to that value.
     * 
     *     3. Variable assignment/declaration commands of the form:
     *          LET var = expr 
     *        where expr denotes an expression containing numbers, parentheses, 
     *        and arithmetic operations and var denotes a variable name. This method
     *        stores the variable name in a map and assigns the key to the value of the expression.
     * 
     * This method stores all of these 3 functionalities within nodes of the Abstract
     * Syntax Tree or AST. This allows the AST to simplify and evaluate everything
     * more efficiently after all of the parsing is done.
     * 
     * @precondition The parser is currently observing a token that begins one of the 3
     *               command types described above
     * @postcondition The command has been transformed to part of an AST and the parser has
     *                eaten/parsed the entirety of the command
     * @return the AST portion referring to the statement that has been parsed
     */
    private Statement parseNoLineNum()
    {
        Statement ret = null;
        if (curToken.equals("PRINT"))
        {
            eat("PRINT");
            ret = new Writeln(parseExpression());
        }
        else if (curToken.equals("INPUT"))
        {
            eat("INPUT");
            String id = curToken;
            ret = new Readln(sc, id);
            eat(id);
        }
        else if (curToken.equals("LET"))
        {
            eat("LET");
            String varname = curToken;
            eat(varname);
            eat("=");
            ret = new Assignment(varname, parseExpression());
        }
        return ret;
    }
    /**
     * Method parseFactor parses five types of factors (simplest form of an expression):
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
     *     5. Procedure calls of the form id(params), where id is the name of the procedure
     *        and params is a comma separated list of expressions of nonnegative length that
     *        represents the parameters. With the given parameters, the statement within the
     *        procedure is executed and the value of this factor is the return value of the
     *        procedure.
     * 
     * @precondition The parser is currently observing a token that begins one of the 5
     *               factor types described above
     * @postcondition The factor has been properly transformed to an AST component and the parser 
     *                has eaten/parsed the entirety of the factor
     * @return the factor as an AST Expression
     */
    private Expression parseFactor()
    {
        Expression ret;
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
            ret = new BinOp("-", new Number(0), ret);
        }
        else
        {
            try
            {
                ret = parseNumber();
            }
            catch (NumberFormatException n)
            {
                String s = curToken;
                eat(curToken);
                ret = new Variable(s);
            }
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
     * observed between two factors that is not *, /, or %. While doing this, we recursively create
     * the AST component by recursively creating descent while making sure that the rightmost
     * operator is at the top in order to maintain proper order of operations.
     * 
     * @precondition The parser is currently observing a token that begins one of the 4
     *               term types described above
     * @postcondition The term has been properly turned into an AST Expression and the parser 
     *                has eaten/parsed the entirety of the term
     * @return the term as an AST Expression
     */
    private Expression parseTerm()
    {
        Expression ret = parseFactor();
        while (curToken.equals("*") || curToken.equals("/") || curToken.equals("%"))
        {
            String cur = curToken;
            eat(curToken);
            ret = new BinOp(cur, ret, parseFactor());
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
     * observed between two terms that is not + or -. While doing this, we recursively create
     * the AST component by recursively creating descent while making sure that the rightmost
     * operator is at the top in order to maintain proper order of operations.
     * 
     * @precondition The parser is currently observing a token that begins one of the 3
     *               expression types described above
     * @postcondition The expression has been properly turned into an AST Expression and the 
     *                parser has eaten/parsed the entirety of the expression
     * @return the expression as an AST Expression
     */
    private Expression parseExpression()
    {
        Expression ret = parseTerm();
        while (curToken.equals("+") || curToken.equals("-"))
        {
            String cur = curToken;
            eat(curToken);
            ret = new BinOp(cur, ret, parseTerm());
        }
        return ret;
    }
    /**
     * Method parseCondition parses a boolean condition of one format:
     * Expr RelOp Expr
     * where the two Exprs are two expressions, and the RelOp is an operator that is one of the
     * following six: =, <>, <, >, <=, >=. These operators are equality, inequality, less than,
     * greater than, less than or equal to, and greater than or equal to, respectively. These
     * conditions have a value that is dependent on the association between the values of the
     * two expressions created by the relacional operator. This method stores these conditions
     * within an AST Expression so that the AST can easily evaluate these boolean values.
     * 
     * @precondition The parser is on the beginning of a condition of the format described above,
     *               with two expressions separated by a relacional operator
     * @postcondition The entire condition has been eaten/parsed, and the condition has also been
     *                transformed into an AST Expression
     * @return the AST Expression representing the Condition
     */
    private Condition parseCondition()
    {
        Expression exp1 = parseExpression();
        String operator = "";
        if (curToken.equals("=") || curToken.equals("<>") || curToken.equals("<") || 
            curToken.equals(">") || curToken.equals(">=") || curToken.equals("<="))
        {
            operator = curToken;
            eat(curToken);
        }
        Expression exp2 = parseExpression();
        return new Condition(operator, exp1, exp2);
    }
    /**
     * Method parseProgram parses the whole program from top to bottom. Each line can follow
     * 7 types of statements:
     * 
     *     1. The command RUN, which signifies to run the program of statements stored with
     *        line numbers from top to bottom.
     * 
     *     2. The command LIST, which signifies to print all the statements stored with line
     *        numbers from top to bottom
     * 
     *     3. The command CLEAR, which signifies to clear all statements stored in the program
     *        and variables stored in the environment.
     * 
     *     4. The command HELP, which signifies to print out a link to documentation found here:
     *          https://schoology.harker.org/course/2162686103/materials/gp/2534696109
     *     
     *     5. The command QUIT, which signifies to exit from interpreting.
     * 
     *     6. A command of the form:
     *          stmt
     *        where stmt is either INPUT, LET, or PRINT. This type of statement is immediately
     *        executed.
     * 
     *     7. A command of the form:
     *          lnum stmt
     *        where lnum is a line number and stmt is either EXIT, GOTO, IF, INPUT, LET, PRINT,
     *        or REM. This type of statement is then stored within the program for execution
     *        with a call of RUN.
     * 
     * @precondition the program follows the format of each line being one of the aforementioned
     *               7 statements
     * @postcondition the program is properly parsed fully and properly executed
     */
    public void parseProgram()
    {
        Program p = new Program();
        while (true)
        {
            if (curToken.equals("RUN"))
            {
                eat("RUN");
                p.exec(env);
                eat("\n");
            }
            else if (curToken.equals("LIST"))
            {
                eat("LIST");
                p.list();
                eat("\n");
            }
            else if (curToken.equals("CLEAR"))
            {
                eat("CLEAR");
                p.clear();
                env.clear();
                eat("\n");
            }
            else if (curToken.equals("HELP"))
            {
                eat("HELP");
                System.out.println("Use the documentation found here:" +
                        "https://schoology.harker.org/course/2162686103/materials/gp/2534696109");
                eat("\n");
            }
            else if (curToken.equals("QUIT"))
            {
                System.exit(0);
            }
            else
            {
                try
                {
                    int i = parseInt();
                    Statement st = parseStatement();
                    if (st != null)
                    {
                        p.add(i, st);
                    }
                }
                catch (NumberFormatException n)
                {
                    Statement st = parseNoLineNum();
                    if (st != null)
                    {
                        st.exec(env);
                    }
                }
                eat("\n");
            }
        }
    }
}