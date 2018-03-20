import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//9-8-16 9:26pm

/**
 * Lexical Analyzer, part two:
 * Recognize token list and validate statements.
 * @author mc1838
 */
public class Recognizer {

	/**
	 * Class vars for productions
	 */
	static ArrayList<String> LHS = new ArrayList<String>(
			Arrays.asList("statement", "assign_stmt", "expression")); //represents a valid stmt in language!!
	static ArrayList<String> statement = new ArrayList<String>(
			Arrays.asList("assign_stmt", "expression")); 
	static ArrayList<String> assign_stmt = new ArrayList<String>(
			Arrays.asList("identifier", "assignment_operator", "expression"));
	static ArrayList<String> expression = new ArrayList<String>(
			Arrays.asList("identifier", "number", "operator", 
					"expression", "identifier", "number"));
	
	/**
	 * Recognize each tokenized line and return the output,
	 *  which will verifying the validity of statement, or 
	 *  return nothing if error occurred in orig. statement.
	 * @param s The input line of tokens, which will be scanned.
	 * @return output The validity result of the tokens.
	 */
	public String recognize(String s)
	{
		String output = "";
		boolean done = false;
		boolean stillValid = false;
		String origTokens = s.trim(); //grab original tokens, strip whitespace!
		String workingTokens = origTokens.toString(); //copy of token so we can remove/replace terms
		String[] tempTokens = origTokens.split(" "); //will replace terms
		
		//Pre-check for error token:
		if (workingTokens.contains("error"))
		{
			done = true;
			return output; //error token, done.
		}
		
		//Loop through set of tokens and compare to RHS lists:
		int removed_words = 0; //keep track of words removed for iteration
		while (!done)
		{
			//Check if all tokens are in LHS;
			// if not, continue to parse				
			if (workingTokens.equals("statement")) //break down all words to statement eventually
			{
				stillValid = true;
				done = true;
				break;
			}
			
			else
			{
				stillValid = false;
			}
			
			//--
			// Attempt to match production blocks to whole temp string:
			// Expression Block:
			// Case 1 (identifier operator expression)
			if (workingTokens.equals("identifier operator expression"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 3;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "expression";
				continue;
			}
			
			// Case 2 (number operator expression)
			else if (workingTokens.equals("number operator expression"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 3;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "expression";
				continue;
			}
			// Case 3 (identifier)
			else if (workingTokens.equals("identifier"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 1;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "expression";
				continue;
			}
			
			// Case 4 (number)
			else if (workingTokens.equals("number"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 1;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "expression";
				continue;
			}
			
			// Assign_stmt Block:
			// Case 1 (identifier assignment_operator expression)
			if (workingTokens.equals("identifier assignment_operator expression"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 3;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words) //was -1
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "assign_stmt";
				continue;
			}
			
			// Statement Block:
			// Case 1 (assign_stmt)
			if (workingTokens.equals("assign_stmt"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 1;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "statement";
				continue;
			}
			
			// Case 2 (expression)
			else if (workingTokens.equals("expression"))
			{
				//Re-grab original tokens except for last word
				workingTokens = ""; //clear out
				tempTokens = origTokens.split(" ");
				removed_words += 1;
				for (int i=0; i<tempTokens.length; i++)
				{
					if (i<tempTokens.length-removed_words)
					{
						workingTokens += tempTokens[i] + " ";
					}
				}
				
				//Replace last word with "matched" word
				workingTokens += "statement";
				continue;
			}
			
			//Eliminate a token if no blocks match:
			else
			{
				if (workingTokens.length() > 1)
				{
					String arrtemp[] = workingTokens.split(" ");
					workingTokens = ""; //clear out

					for (int i=0; i<arrtemp.length; i++)
					{
						if (i!=0) //SKIP first token; same as removing first word
						{
							if (i == arrtemp.length-1)
							{
								workingTokens += arrtemp[i];
							}
							
							else
							{
								workingTokens += arrtemp[i] + " "; //need spacing for mid terms
							}
						}
					}
				}
				
				else if (workingTokens.length() == 1)
				{
					if (workingTokens.equals("statement"))
					{
						stillValid = true;
						done = true;
					}
					
					else
					{
						stillValid = false;
						done = true;
					}
				}
				
				else //nothing else left, whole string eliminated
				{
					stillValid = false;
					done = true;
				}
				
			} //end no match else block
			
		} //end main loop
		
		//4) Check for validity:
		if (stillValid)
		{
			output += " is a Valid Statement";
		}
		
		else
		{
			output += " is an Invalid Statement";
		}
		
		return output;
	}

}
