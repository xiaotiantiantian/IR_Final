package PreProcessData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import Classes.Path;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * Implementation of DocumentCollection
 */
public class TrecwebCollection implements DocumentCollection {
	//you can add essential private methods or variables
	FileInputStream fis = null;
    BufferedReader reader = null;
    String line;
    String subs;
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrecwebCollection() throws IOException {
		// This constructor should open the file in Path.DataWebDir
		// and also should make preparation for function nextDocument()
		// Do not load the whole corpus into memory!!!
		fis = new FileInputStream(Path.DataWebDir);
	    reader = new BufferedReader(new InputStreamReader(fis));
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NT: the returned content of the document should be cleaned, all html tags should be removed.
		// NTT: remember to close the file that you opened, when you do not use it any more
		line = reader.readLine();
		while(line != null){
			
			if(line.equals("<DOC>") )
			{
				line = reader.readLine();
				//get the doc number
				subs = line.substring(7,(line.length()-8));

			}
			else{
			//if(line.equals("<p>") ){
				String result = "";
		        StringBuilder stringBuilder = new StringBuilder();
		        //String temp = reader.readLine();
		        //System.out.println("+++++++++"+ temp);
		        int count = 0;
		        
		        String tempString = reader.readLine();
		        if(tempString.equals(null))
		        	continue;
		        
				while(!(line.contains("</DOC>")))
		        {		     
					stringBuilder.append(line);
					
					line = reader.readLine();
					if(line ==null)
						break;
					count ++;
			        //System.out.println("count "+ count+"+++++++++"+ temp);
				}
				//combine the lines to a string
				result = stringBuilder.toString();
				//return the map
				Map<String, Object> rstPair = new HashMap<String, Object>();
				rstPair.put(subs, result.toCharArray());
				return rstPair;
				
			}

			if(line==null)
				break;
			line = reader.readLine();
	 	}
	
		reader.close();
		fis.close();

		return null;
	}
	
}
