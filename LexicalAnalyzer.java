import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// 9-8-16 9:27pm

/**
 * Lexical Analyzer, part one:
 * Tokenize strings from file!
 * @author mc1838
 */
public class LexicalAnalyzer {
	
	/**
	 * Class vars for general tokenization defs
	 */
	static ArrayList<String> digit = new ArrayList<String>(
			Arrays.asList("0","1","2","3","4","5","6","7","8","9"));
	static ArrayList<String> letter = new ArrayList<String>(
			Arrays.asList("a","b","c","d","e","f","g","h","i","j",
					"k", "l", "m", "n", "o", "p", "q", "r", "s",
					"t", "u", "v", "w", "x", "y", "z"));
	static ArrayList<String> operator = new ArrayList<String>(
			Arrays.asList("+","-","*","/"));
	//NOTE: assignment is simply '=', no need for list
	
	private static Recognizer recog = new Recognizer(); //instance of recognizer function class

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//Initialize file and vars:
		File f = new File("src/a2_examples.txt");
		ArrayList<String> strList = new ArrayList<String>();
		String line = "";
		String tokens;
		String validity_stmt = "";
		
		//Store file contents in list:
		try
		{
			Scanner in = new Scanner(f);
			
			while (in.hasNextLine())
			{
				line = in.nextLine();
				strList.add(line);
			}
			
			in.close();
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
		for (int i=0; i<strList.size(); i++)
		{
			//Tokenize each line of string from file:
			tokens = tokenize(strList.get(i));
			if (!tokens.equals("")) //prevent printing null lines
			{
				System.out.println(strList.get(i) + " tokenizes as" + tokens);
			}
			
			//Recognize each line of tokens and print valid/invalid OR nothing if error:
			validity_stmt = recog.recognize(tokens);
			if (!validity_stmt.equals("")) //prevent printing null lines
			{
				System.out.println("\t" + strList.get(i) + " " + validity_stmt);
			}
			
			else
			{
				System.out.println("");
			}
		}
		
	}
	
	/**
	 * Tokenize each whole line and return the output,
	 *  which will consist of identifiers, numbers, operators,
	 *  assignments, or errors.
	 * @param s The input line, which will be broken up into tokens
	 * @return output The whole line as tokens.
	 */
	public static String tokenize(String s)
	{
		String output="";
		String c = null;
		
		Scanner analyzer = new Scanner(s); //analyze each char in each line
		analyzer.useDelimiter(""); //force scanner to read in spaces too!
		
		/**
		 * Init state counter: 
		 * 0=start, 1=identifier, 2=number, 3=operator
		 * 4=assignment, 5=error (halt and return)
		 */
		int state = 0;
		boolean done = false; //keep parsing until end flag
		
		while (!done)
		{
			if (analyzer.hasNext())
			{
				c = analyzer.next();
			}
			
			else
			{
				done=true; //end of line
				break; //no need to continue
			}
			
			
			
			if (state == 0) //start
			{
				
				if (letter.contains(c))
				{
					state=1;
					output += " identifier";
				}
				
				else if (digit.contains(c))
				{
					state=2;
					output += " number";
				}
				
				else if (operator.contains(c))
				{
					state=3;
					output += " operator";
				}
				
				else if (c.equals("="))
				{
					state=4;
					output += " assignment_operator";
				}
				
				//CHECK FOR SPACE
				else if (c.equals(" "))
				{
					state=0; //loop back to start
				}
				
				else //ERROR
				{
					state=5;
					output += " error";
				}
					
			}
			
			else if (state == 1) //ident
			{
				if (letter.contains(c) || digit.contains(c))
				{
					state=1; //loopback
				}
				
				else if (operator.contains(c))
				{
					state=3;
					output += " operator";
				}
				
				else if (c.equals("="))
				{
					state=4;
					output += " assignment_operator";
				}
				
				//CHECK FOR SPACE
				else if (c.equals(" "))
				{
					state=0; //loop back to start
				}
				
				else
				{
					state=5;
					output += " error";
				}
				
			}
			
			else if (state == 2) //number
			{
				if (letter.contains(c))
				{
					state=5; //error, no letters after a number!
					output += " error";
				}
				
				else if (digit.contains(c))
				{
					state=2; //loopback
				}
				
				else if (operator.contains(c))
				{
					state=3;
					output += " operator";
				}
				
				else if (c.equals("="))
				{
					state=4;
					output += " assignment_operator";
				}
				
				//CHECK FOR SPACE
				else if (c.equals(" "))
				{
					state=0; //loop back to start
				}
				
				else //ERROR
				{
					state=5;
					output += " error";
				}
				
			}
			
			else if (state == 3) //op
			{
				if (letter.contains(c))
				{
					state=1;
					output += " identifier";
				}
				
				else if (digit.contains(c))
				{
					state=2;
					output += " number";
				}
				
				else if (operator.contains(c))
				{
					state=5; //error, no support for complex operators!
					output += " error";
				}
				
				else if (c.equals("="))
				{
					state=4;
					output += " assignment_operator";
				}
				
				//CHECK FOR SPACE
				else if (c.equals(" "))
				{
					state=0; //loop back to start
				}
				
				else //ERROR
				{
					state=5;
					output += " error";
				}
				
			}
			
			else if (state == 4) //assign
			{
				
				if (letter.contains(c))
				{
					state=1;
					output += " identifier";
				}
				
				else if (digit.contains(c))
				{
					state=2;
					output += " number";
				}
				
				else if (operator.contains(c))
				{
					state=3;
					output += " operator";
				}
				
				else if (c.equals("="))
				{
					state=5; //error, no support for complex/double assignment signs!
					output += " error";
				}
				
				//CHECK FOR SPACE
				else if (c.equals(" "))
				{
					state=0; //loop back to start
				}
				
				else //ERROR
				{
					state=5;
					output += " error";
				}
				
			}
			
			else if (state == 5) //error
			{
				done=true;
			}
		} //end loop
		analyzer.close();
		
		return output;
	}

}
