package Search;



import Classes.Query;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import PreProcessData.*;

/**
 * Read and parse TREC queries
 * -- INFSCI 2140: Information Storage and Retrieval Spring 2016
 */
public class ExtractQuery {


	public List<Query> GetQueries() throws Exception {
		//you should extract the 4 queries from the Path.TopicDir
		//NT: the query content of each topic should be 1) tokenized, 2) to lowercase, 3) remove stop words, 4) stemming
		//NT: third topic title is ----Star Trek "The Next Generation"-----, if your code can recognize the phrase marked by "", 
		//    and further process the phrase in search, you will get extra points.
		//NT: you can simply pick up title only for query, or you can also use title + description + narrative for the query content.
		
		List<Query> QueryList =  new ArrayList<Query>();
		//tokenize lowercase remove stop word stemming
		DocumentCollection corpus;
		corpus = new TrectextCollection();
		
		StopWordRemover stopwordRemover = new StopWordRemover();
		
		WordNormalizer normalizer = new WordNormalizer();


		// initiate a doc object, which can hold document number and document content of a document
		Map<String, Object> doc = null;

		// process the corpus, document by document, iteractively
		//int count=0;
		while ((doc = corpus.nextDocument()) != null) {
			// load document number of the document
			String docno = doc.keySet().iterator().next();

			// load document content
			char[] content = (char[]) doc.get(docno);
			
			// initiate the WordTokenizer class
			WordTokenizer tokenizer = new WordTokenizer(content);

			// initiate a word object, which can hold a word
			char[] word = null;

			String processedTitle ="";
			// process the document word by word iteratively
			while ((word = tokenizer.nextToken()) != null) {
				// each word is transformed into lowercase
				word = normalizer.lowercase(word);

				// filter out stopword, and only non-stopword will be written
				// into result file
				if (!stopwordRemover.isStopword(word)){
					//write the normalized word into QuryList
					//write the query number into the QueryList
					processedTitle= processedTitle + normalizer.stem(word)+" ";

				}
			}

			Query tempQ= new Query();
			tempQ.SetTopicId(docno);
			tempQ.SetQueryContent(processedTitle);
			QueryList.add(tempQ);
			}
		
		return QueryList;
	}
}
