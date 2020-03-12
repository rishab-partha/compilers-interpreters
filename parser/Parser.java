package parser;
import scanner.*;
import java.lang.IllegalArgumentException;
import java.util.Map;
import java.util.HashMap;
public class Parser
{
    private Scanner sc;
    private java.util.Scanner scinput;
    private String curToken;
    private Map<String, Integer> variables;
    public Parser(Scanner s)
    {
        sc = s;
        curToken = sc.nextToken();
        variables = new HashMap<String, Integer>();
        scinput = new java.util.Scanner(System.in);
    }
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
    private int parseNumber()
    {
        int ret = Integer.parseInt(curToken);
        eat(curToken);
        return ret;
    }
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