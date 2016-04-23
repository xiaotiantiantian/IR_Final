package PreProcessData;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classes.Path;

/**
 * This file is for the assignment of INFSCI 2140 in 2016 Spring
 *
 * Implementation of DocumentCollection
 *
 */
public class TrectextCollection implements DocumentCollection {
	//you can add essential private methods or variables
	FileInputStream fis = null;
    BufferedReader reader = null;
    String line;
    String subs;
    String subs_title;
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public TrectextCollection() throws IOException {
		// This constructor should open the file in Path.DataTextDir
		// and also should make preparation for function nextDocument()
        // Do not load the whole corpus into memory!!!
	    //read sample.txt
	    //fis = new FileInputStream("./src/data/docset.trectext");
		//fis = new FileInputStream("./data/docset.trectext");
		fis = new FileInputStream(Path.TopicDir);
	    reader = new BufferedReader(new InputStreamReader(fis));
	    //Reading File line by line using BufferedReader
	    //You can get next line using reader.readLine() method.
		
		
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD
	public Map<String, Object> nextDocument() throws IOException {
		// this method should load one document from the corpus, and return this document's number and content.
		// the returned document should never be returned again.
		// when no document left, return null
		// NTT: remember to close the file that you opened, when you do not use it any more
		//Map<String, Object> doc = null;
		//the key is the <DOCNO>, the vaule is the <TEXT> 
		//every time only use 1 doc?
		line = reader.readLine();
		while(line != null){
			
			if(line.equals("<top>") )
			{
				line = reader.readLine();
				//get the num of query.
				subs = line.substring(13,line.length());
				line = reader.readLine();
				//get the title of query.
				subs_title=line.substring(7,line.length());
				
				Map<String, Object> rstPair = new HashMap<String, Object>();
				rstPair.put(subs, subs_title.toCharArray());
				return rstPair;
				
			}
			line = reader.readLine();
	 	}
		reader.close();
		fis.close();
		return null;
	}
	
}
