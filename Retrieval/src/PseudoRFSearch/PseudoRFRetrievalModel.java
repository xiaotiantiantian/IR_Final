package PseudoRFSearch;

import Classes.DocDetail;
import Classes.Document;
import Classes.DocumentWithRFScore;
import Classes.Query;
import IndexingLucene.MyIndexReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Class of Assignment 4.
 * Implement your pseudo feedback retrieval model here
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class PseudoRFRetrievalModel {

	MyIndexReader ixreader;
	
	protected MyIndexReader indexReader;
	private Entry<Integer, DocDetail> entry;
	
	//the score calculated by RetriveQuery first, then the GetToken RF socre function use it.
	protected HashMap<String,Double> TokenRFScore;
	
	
	
	protected List<DocumentWithRFScore> resultsWithRF;
	
	public PseudoRFRetrievalModel(MyIndexReader ixreader)
	{
		this.ixreader=ixreader;
	}
	
	/**
	 * Search for the topic with pseudo relevance feedback. 
	 * The returned results (retrieved documents) should be ranked by the score (from the most relevant to the least).
	 * 
	 * @param aQuery The query to be searched for.
	 * @param TopN The maximum number of returned document
	 * @param TopK The count of feedback documents
	 * @param alpha parameter of relevance feedback model
	 * @return TopN most relevant document, in List structure
	 */
	public List<Document> RetrieveQuery( Query aQuery, int TopN, int TopK, double alpha) throws Exception {	
		// this method will return the retrieval result of the given Query, and this result is enhanced with pseudo relevance feedback
		// (1) you should first use the original retrieval model to get TopK documents, which will be regarded as feedback documents
		// (2) implement GetTokenRFScore to get each query token's P(token|feedback model) in feedback documents
		// (3) implement the relevance feedback model for each token: combine the each query token's original retrieval score P(token|document) with its score in feedback documents P(token|feedback model)
		// (4) for each document, use the query likelihood language model to get the whole query's new score, P(Q|document)=P(token_1|document')*P(token_2|document')*...*P(token_n|document')
List<Document> retrievedQuery = new ArrayList<Document>();
		
		//key is integer doc id
		HashMap<Integer, DocDetail> DocList = new HashMap<Integer, DocDetail>();
		//<token,p(token|REF)>
		HashMap<String, Double> tokenRefProb = new HashMap<String, Double>();
		
		//calculate how many tokens there.
		//search each term to get the docs
		MyIndexReader reader = new MyIndexReader("trectext");
		//the token should be only one
		String[] tokenlist = aQuery.GetQueryContent().split(" ");
		
		//we should calculate the number of tokens in the colection. 
		//for some tokens may accour in a document but not other document
		//we calculate it together.

		//then we calculate other things
		
		//number of all the tokens in the collection
		int CountCollection = 0;
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
	    for (Entry<Integer, DocDetail> entry : DocList.entrySet()) {  
	    	CountCollection+=entry.getValue().getDocSize();
	    }  
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
    	HashMap<Double, DocumentWithRFScore> rankDoc=new HashMap<Double, DocumentWithRFScore>();
	    //circle all recieved doc and get the score
	    
	    for (Entry<Integer, DocDetail> entry : DocList.entrySet()) {  
	    	//CountCollection+=entry.getValue().getDocSize();
	    	int docid = entry.getKey();
	    	String docno = reader.getDocno(docid);
	    	
	    	//calculate the final equation
	    	HashMap<Integer, Integer> tempTdFreq = entry.getValue().getTdFreq();
	    	double FinalScore=1;
	    	//set miu as the ppt 2000
	    	int miu = 2000;
	    	
	    	//set a list for the term count in a document
	    	List<Integer> termCount = new ArrayList<Integer>();
	    	//set a list for the term prob in each document
	    	List<Double> termProb = new ArrayList<Double>();
	    	//add a integer for doc length
	    	//int docLength
	    	for (Entry<Integer, Integer> entryTdFreq : tempTdFreq.entrySet()) {
	    		double tempScore =( entryTdFreq.getValue()+miu*tokenRefProb.get(tokenlist[entryTdFreq.getKey()]))/(entry.getValue().getDocSize()+miu);
	    	
	    		FinalScore*=tempScore;
	    		
	    		//add  count of every term in the doc
	    		termCount.add(entryTdFreq.getValue());
	    		
	    		
	    		//add probability of every term in the doc
	    		termProb.add(tempScore);
	    	}
	    	
	    	
	    	//Document tempDoc=new Document(Integer.toString(docid), docno, FinalScore);
	    	DocumentWithRFScore tempDoc = new DocumentWithRFScore(Integer.toString(docid), docno, FinalScore, termCount, entry.getValue().getDocSize(),termProb);
	    	//rank all the doc with score

	    	rankDoc.put(FinalScore, tempDoc);
	    	
	    	//retrievedQuery.add(tempDoc);
	    	
	    	
	    }
	    //rank the doc in rankDoc by score
	    Object[] key =  rankDoc.keySet().toArray();    
	    Arrays.sort(key,Collections.reverseOrder());    
	      
	    
	    //construct the list of top k doc with doc detail
	    List<DocumentWithRFScore> resultsWithRF1 = new ArrayList<DocumentWithRFScore>();
	    for(int i = 0; i<TopK; i++)  
	    {    
	         //System.out.println(map.get(key[i]));    
	    	resultsWithRF1.add(rankDoc.get(key[i]));
	    }  
	    resultsWithRF = resultsWithRF1;

		



		
		
		
		//get P(token|feedback documents)
		HashMap<String,Double> TokenRFScore=GetTokenRFScore(aQuery,TopK);
		
		//prevent exploit
		int queryLength = 0;    
	    if(resultsWithRF.size()>0)
	    {
	    	queryLength = resultsWithRF.get(0).getWordFreqList().size();
	    }
		queryLength= (tokenlist.length>queryLength)?queryLength:tokenlist.length;
		
		/////
		HashMap<Double, Document> rankDocNew=new HashMap<Double, Document>();
		//calculate each doc's new score
		//for each document
		double newScore = 1;
		double tempScore = 0;
		for(int i=0; i< TopK; i++)
		{
			//for each query term
			for(int j=1; j<queryLength; j++)
			{
				tempScore = alpha * resultsWithRF1.get(i).getWordProb().get(j) + (1-alpha)*TokenRFScore.get(tokenlist[j]);
				newScore *= tempScore;
			}
			Document tempDoc=new Document(resultsWithRF1.get(i).docid(),resultsWithRF1.get(i).docno(),newScore);
					//(Integer.toString(docid), docno, FinalScore);
	    	//rank all the doc with score

	    	rankDocNew.put(newScore, tempDoc);
		}
		
		//rank the doc in rankDoc by score
	    Object[] keyNew =  rankDoc.keySet().toArray();    
	    Arrays.sort(keyNew,Collections.reverseOrder());    
	     
		// sort all retrieved documents from most relevant to least, and return TopN
		List<Document> results = new ArrayList<Document>();
		
	    
	    for(int i = 0; i<TopN; i++)  
	    {    
	         //System.out.println(map.get(key[i]));    
	    	results.add(rankDoc.get(key[i]));
	    }  
		
		
	
		return results;
	}
	
	public HashMap<String,Double> GetTokenRFScore(Query aQuery,  int TopK) throws Exception
	{
		// for each token in the query, you should calculate token's score in feedback documents: P(token|feedback documents)
		// use Dirichlet smoothing
		// save <token, score> in HashMap TokenRFScore, and return it
		HashMap<String,Double> TokenRFScore=new HashMap<String,Double>();
		
		String[] tokenlist = aQuery.GetQueryContent().split(" ");
		
	    double probInFeedback =0;
	    
	    int countInFeedback = 0;
	    //FeedbackLength is the length of all the returnd top K document
	    int FeedbackLength = 0;
	    
	    int queryLength = 0;
	    
	    if(resultsWithRF.size()>0)
	    {
	    	queryLength = resultsWithRF.get(0).getWordFreqList().size();
	    }
	    
	    //temporary store the data of count of each term in query
	    List<Integer> countTermFeedback = new ArrayList<Integer>();
	    //the list's first element could not be 0
    	for (int j=1; j< queryLength; j++    )
    	{
    		//initial it to 0
    		countTermFeedback.set(j, 0);
    	}
	    
	    for (int i=0; i< resultsWithRF.size(); i++)
	    {
	    	FeedbackLength += resultsWithRF.get(i).getDocLength();

	    }
	    for (int i=0; i< resultsWithRF.size(); i++)
	    {
	    	for (int j=1; j< queryLength; j++)
	    	{
	    		int count =0;
	    		count = (int) resultsWithRF.get(i).getWordFreqList().get(j);
	    		countTermFeedback.set(j, countTermFeedback.get(j) + count);
	    	}
	    }
	    
	    //add a fault-tolerance code
	    queryLength= (tokenlist.length>queryLength)?queryLength:tokenlist.length;
	    //then we calculate each p(qi|feedback)=qi/length of all doc
	    //for each query
	    
	    for (int j=1; j< queryLength; j++)
	    {
	    	probInFeedback= countTermFeedback.get(j)/queryLength;
	    	TokenRFScore.put(tokenlist[j], probInFeedback);
	    }
		
		
		
		return TokenRFScore;
	}
	
	
}