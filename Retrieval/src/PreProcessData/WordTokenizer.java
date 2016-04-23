package PreProcessData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 * 
 * TextTokenizer can split a sequence of text into individual word tokens, the delimiters can be any common punctuation marks(space, period etc.).
 */
public class WordTokenizer {
	//you can add any essential private method or variable
	private
	List<String> content = new ArrayList<String>();
	String[] substrs;
	int count_pointer;
	int i;
	// YOU MUST IMPLEMENT THIS METHOD
	
	public WordTokenizer( char[] texts ) {
		// this constructor will tokenize the input texts (usually it is the char array for a whole document)
		i=0;
		String str = new String(texts);
		str.replaceAll( "\\s+ ", "| ");
		
		//the space is splited but may it should be deleted
		String regEx="[-=/<>:;,.?!\"\'|\\s*|\t|\r|\n]";  
        Pattern p =Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  

     
        substrs = p.split(str);
        content = new ArrayList<String>(Arrays.asList(substrs));

	}
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] nextToken() {
		// read and return the next word of the document
		// or return null if it reaches the end of the document
		 		i++;
		 		if (i>= content.size())
		 			return null;
	        	String subword =content.get(i);

	            char[] token_char = subword.toCharArray();
	            
	            return token_char;

	        
	}
	
}
