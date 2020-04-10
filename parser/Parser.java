package parser;
import scanner.*;
import java.util.List;
import java.util.ArrayList;
import ast.Number;
import ast.BinOp;
import ast.Block;
import ast.Assignment;
import ast.Variable;
import ast.Writeln;
import ast.Expression;
import ast.Statement;
import ast.Condition;
import ast.If;
import ast.While;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.ProcedureCallStmt;
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
 * 6. Perform IF statements
 * 7. Perform WHILE loops
 * @author Rishab Parthasarathy
 * @version 3.25.2020
 */
public class Parser
{
    private Scanner sc;
    private java.util.Scanner scinput;
    private String curToken;
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
     *     5. IF statements of the format
     *        IF cond THEN stmt (ELSE stmt) where the else is optional. cond represents a boolean
     *        condition and the stmts refer to statements. If the condition is true, the statement
     *        within the then is executed, but if the condition is false, the else statement is   
     *        executed whenever it exists.
     *     6. WHILE loops of the format
     *        WHILE cond DO stmt. cond represents a boolean condition and stmt refers to a
     *        statement. While the condition is true, the statement is executed.
     * 
     * This method stores all of these 6 functionalities within nodes of the Abstract
     * Syntax Tree or AST. This allows the AST to simplify and evaluate everything
     * more efficiently after all of the parsing is done.
     * 
     * @precondition The parser is currently observing a token that begins one of the 6
     *               command types described above
     * @postcondition The command has been transformed to part of an AST andthe parser has 
     *                eaten/parsed the entirety of the command
     * @return the AST portion referring to the statement that has been parsed
     */
    public Statement parseStatement()
    {
        Statement ret;
        if (curToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            ret = new Writeln(parseExpression());
            eat(")");
            eat(";");
        }
        else if (curToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String id = curToken;
            int val = scinput.nextInt();
            ret = new Assignment(id, new Number(val));
            eat (id);
            eat(")");
            eat(";");
        }
        else if (curToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> statements = new ArrayList<Statement>();
            while (! curToken.equals("END"))
            {
                statements.add(parseStatement());
            }
            eat("END");
            eat(";");
            ret = new Block(statements);
        }
        else if (curToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement st1 = parseStatement();
            Statement st2 = null;
            if (curToken.equals("ELSE"))
            {
                eat("ELSE");
                st2 = parseStatement();
            }
            ret = new If(cond, st1, st2);
        }
        else if (curToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement st = parseStatement();
            ret = new While(cond, st);
        }
        else
        {
            String varname = curToken;
            eat(varname);
            if (curToken.equals(":="))
            {
                eat(":=");
                ret = new Assignment(varname, parseExpression());
            }
            else
            {
                eat("(");
                List<Expression> variables = new ArrayList<>();
                if (!curToken.equals(")"))
                {
                    variables.add(parseExpression());
                }
                while (! curToken.equals(")"))
                {
                    eat(",");
                    variables.add(parseExpression());
                }
                eat(")");
                ret = new ProcedureCallStmt(varname, variables);

            }
            eat(";");
        }
        return ret;
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
                if (curToken.equals("("))
                {
                    eat("(");
                    List<Expression> variables = new ArrayList<>();
                    if (!curToken.equals(")"))
                    {
                        variables.add(parseExpression());
                    }
                    while (! curToken.equals(")"))
                    {
                        eat(",");
                        variables.add(parseExpression());
                    }
                    eat(")");
                    ret = new ProcedureCall(s, variables);
                }
                else
                {
                    ret = new Variable(s);
                }
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
            ret = new BinOp(cur, ret, parseTerm());
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
    public Program parseProgram()
    {
        List<ProcedureDeclaration> ls = new ArrayList<>();
        while (curToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String s = curToken;
            eat(curToken);
            eat("(");
            List<String> params = new ArrayList<>();
            if (!curToken.equals(")"))
            {
                params.add(curToken);
                eat(curToken);
            }
            while (! curToken.equals(")"))
            {
                eat(",");
                params.add(curToken);
                eat(curToken);
            }
            eat(")");
            eat(";");
            Statement st = parseStatement();
            ls.add(new ProcedureDeclaration(s, st, params));
        }
        Statement st = parseStatement();
        return new Program(ls, st);
    }
}