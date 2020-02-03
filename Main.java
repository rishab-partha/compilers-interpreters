import java.io.*;
public class Main
{
    public static void main(String[] args) throws IOException
        {
            BufferedReader in = new BufferedReader(new FileReader("ScannerTest.txt"));
            Scanner sc = new Scanner(in);
            while (sc.hasNext())
            {
                System.out.println(sc.nextToken());
            }
        }
}