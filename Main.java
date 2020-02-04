import java.io.*;
import scanner.*;
/**
 * This Main class is responsible for testing the scanner by
 * choosing a source document and forcing the scanner to parse it.
 * 
 * @author Rishab Parthasarathy
 * @version 2.4.2020
 */
public class Main
{
    /**
     * Method main initializes a BufferedReader on a file and then feeds it to a Scanner.
     * In order to test the scanner, the method forces the Scanner to read the document
     * for as long as it can.
     * 
     * @param args a String array of command line arguments
     * @throws IOException if the File cannot be found and if input goes wrong
     * @throws ScanErrorException if there some internal error thrown by the Scanner such
     *                            as the eat not matching up or the number not starting
     *                            with a digit.
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        BufferedReader in = new BufferedReader(new FileReader("ScannerTestAdvanced.txt"));
        Scanner sc = new Scanner(in);
        while (sc.hasNext())
        {
            System.out.println(sc.nextToken());
        }
    }   
}