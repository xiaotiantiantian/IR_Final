package PreProcessData;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import Classes.Path;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * StopWordRemover is a class takes charge of judging whether a given word
 * is a stopword by calling the method <i>isStopword(word)</i>.
 */
public class StopWordRemover {
	//you can add essential private methods or variables
	FileInputStream fis1 = null;
    BufferedReader reader = null;
	List<String> StopList = new ArrayList<String>();

    
	public StopWordRemover( ) throws IOException {
		// load and store the stop words from the fileinputstream with appropriate data structure
		// that you believe is suitable for matching stop words.
		// address of stopword.txt should be Path.StopwordDir
		fis1 = new FileInputStream(Path.StopwordDir);
	    reader = new BufferedReader(new InputStreamReader(fis1));
		String line = reader.readLine();
		while(line!=null){
			
			StopList.add(line);
			line = reader.readLine();
		}
	}
		
	
	
	// YOU MUST IMPLEMENT THIS METHOD
	public boolean isStopword( char[] word ) {
		// return true if the input word is a stopword, or false if not
		String str = new String(word);
		Boolean StopWord=StopList.contains(str);
		//return StopList.contains(str);
		return StopWord;
		

	}
}
