package Search;

import Classes.Document;
import Classes.Query;
import IndexingLucene.MyIndexReader;
import Classes.DocDetail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A language retrieval model for ranking documents
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class QueryRetrievalModel {
	
	protected MyIndexReader indexReader;
	private Entry<Integer, DocDetail> entry;
	
	public QueryRetrievalModel(MyIndexReader ixreader) {
		indexReader = ixreader;
	}
	
	/**
	 * Search for the topic information. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * TopN specifies the maximum number of results to be returned.
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @return
	 */
	
	public List<Document> retrieveQuery( Query aQuery, int TopN ) throws IOException {
		// NT: you will find our IndexingLucene.Myindexreader provides method: docLength()
		// implement your retrieval model here, and for each input query, return the topN retrieved documents
		// sort the docs based on their relevance score, from high to low
		//set the doclist needed to return first
		List<Document> retrievedQuery = new ArrayList<Document>();
		
		//key is integer doc id
		HashMap<Integer, DocDetail> DocList = new HashMap<Integer, DocDetail>();
		//<token,p(token|REF)>
		HashMap<String, Double> tokenRefProb = new HashMap<String, Double>();
		
		//calculate how many tokens there.
		//search each term to get the docs
		MyIndexReader reader = new MyIndexReader("trecweb");
		//the token should be only one
		String[] tokenlist = aQuery.GetQueryContent().split(" ");
		
		//we should calculate the number of tokens in the colection. 
		//for some tokens may accour in a document but not other document
		//we calculate it together.

		//then we calculate other things
		
		//number of all the tokens in the collection
		int CountCollection = 1000;
		for(int i=0;i<tokenlist.length;i++){
			int[][] posting = reader.getPostingList(tokenlist[i]);
			if(posting == null)
			{
				continue;
			}
			//number of the searching token in the collection
			int CountToken= 0;
			
			for(int ix=0;ix<posting.length;ix++){
				int docid = posting[ix][0];				
				int freq = posting[ix][1];	
				int docSize=reader.docLength(docid);
				DocDetail temp= new DocDetail();
				HashMap<Integer, Integer> temptdFreq = new HashMap<Integer, Integer>();
				temptdFreq.put(i, freq);
				
				temp.setTdFreq(temptdFreq);
				temp.setDocSize(docSize);
				DocList.put(docid, temp);
				
				//counnt the number of token accours in the collection
				CountToken +=freq;

			}
		
		
		}
		
		//get docid from docList, then get the collection size 
	   // for (Entry<Integer, DocDetail> entry : DocList.entrySet()) {  
	    	//CountCollection+=entry.getValue().getDocSize();
	    //}  
		//put tokenRefProb
	    for(int i=0;i<tokenlist.length;i++){
			int[][] posting = reader.getPostingList(tokenlist[i]);
			if(posting == null)
			{
				continue;
			}
			//number of the searching token in the collection
			int CountToken= 0;
			//this freq is total count 
			int freq = 0;
			for(int ix=0;ix<posting.length;ix++){
				int docid = posting[ix][0];				
				freq += posting[ix][1];
		}
		tokenRefProb.put(tokenlist[i], (double) (freq/CountCollection));
		
		}

	    //rank all the doc with score
    	HashMap<Double, Document> rankDoc=new HashMap<Double, Document>();
	    //circle all recieved doc and get the score
	    
	    for (Entry<Integer, DocDetail> entry : DocList.entrySet()) {  
	    	//CountCollection+=entry.getValue().getDocSize();
	    	int docid = entry.getKey();
	    	String docno = reader.getDocno(docid);
	    	
	    	//calculate the final equation
	    	HashMap<Integer, Integer> tempTdFreq = entry.getValue().getTdFreq();
	    	double FinalScore=1;
	    	//set miu as the doc amount in the collection
	    	//change to miu as 2000 as is given in ppt
	    	//int miu = CountCollection;
	    	int miu = 2000;
	    	for (Entry<Integer, Integer> entryTdFreq : tempTdFreq.entrySet()) {
	    		double tempScore =( entryTdFreq.getValue()+miu*tokenRefProb.get(tokenlist[entryTdFreq.getKey()]))/(entry.getValue().getDocSize()+miu);
	    	
	    		FinalScore*=tempScore;
	    	}
	    	
	    	Document tempDoc=new Document(Integer.toString(docid), docno, FinalScore);
	    	//rank all the doc with score

	    	rankDoc.put(FinalScore, tempDoc);
	    	
	    	//retrievedQuery.add(tempDoc);
	    	
	    	
	    }
	    //rank the doc in rankDoc by score
	    Object[] key =  rankDoc.keySet().toArray();    
	    Arrays.sort(key,Collections.reverseOrder());    
	      
	    for(int i = 0; i<key.length; i++)  
	    {    
	         //System.out.println(map.get(key[i]));    
	    	retrievedQuery.add(rankDoc.get(key[i]));
	    }  
		
		
		return retrievedQuery;
	}
	
}