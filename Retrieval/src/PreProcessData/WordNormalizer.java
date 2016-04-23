package PreProcessData;
import Classes.Stemmer;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * This class is used for extract the stem of certain word by calling stemmer
 */
public class WordNormalizer {
	//you can add essential private methods or variables
	
	// YOU MUST IMPLEMENT THIS METHOD
	public char[] lowercase( char[] chars ) {
		//transform the uppercase characters in the word to lowercase
		//change it to string then work with it and change ti back to char*
		String str = new String(chars);
		String strLower= str.toLowerCase();
		chars = strLower.toCharArray();
		
		return chars;
	}
	
	public String stem(char[] chars)
	{
		//use the stemmer in Classes package to do the stemming on input word, and return the stemmed word
		//the class stemmer is not encapsulated
		String str="";
		Stemmer s = new Stemmer();	
		s.add(chars, chars.length);
		s.stem();
		str=  s.toString();
		return str;
	}
	
}
