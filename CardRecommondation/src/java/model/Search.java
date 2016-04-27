/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Zhirun Tian
 */

import java.io.File;  
  
import org.apache.lucene.analysis.Analyzer;  
import org.apache.lucene.analysis.standard.StandardAnalyzer;  
import org.apache.lucene.document.Document;  
import org.apache.lucene.index.DirectoryReader;  
import org.apache.lucene.queryparser.classic.QueryParser;  
import org.apache.lucene.search.IndexSearcher;  
import org.apache.lucene.search.Query;  
import org.apache.lucene.search.TopDocs;  
import org.apache.lucene.store.Directory;  
import org.apache.lucene.store.FSDirectory;  
import org.apache.lucene.util.Version;  
    
public class Search {  
  
    public static void main(String[] args) {  
        Directory directory = null;  
        try {  
            //path of indexing  
            directory = FSDirectory.open(new File(Path.IndexDir));  
            //read index  
            DirectoryReader dReader = DirectoryReader.open(directory);  
            //create indexing search object  
            IndexSearcher searcher = new IndexSearcher(dReader);  
            //the model should be same between indexing and searching
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);  
            //set a query  
            QueryParser parse = new QueryParser(Version.LUCENE_43, "content", analyzer);  
            Query query = parse.parse("chase");  
            //searching the index and get top 10 answer
            TopDocs topDocs = searcher.search(query, 10);  
            if (topDocs != null) {  
                System.out.println("the total result is  " +  topDocs.totalHits );  
                //print out the result  
                for (int i = 0; i < topDocs.scoreDocs.length; i++) {  
                    Document doc = searcher.doc(topDocs.scoreDocs[i].doc);  
                    System.out.println("the No.   " + (i + 1) + "has content--\tname:\t" + doc.get("link") + "\tcontent:\t" + doc.get("content"));  
                }  
            }  
            //close the resouruce  
            dReader.close();  
            directory.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  