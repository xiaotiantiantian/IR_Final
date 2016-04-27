/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 *
 * @author Zhirun Tian
 */
public class Index {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        //set the split word tech  
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        //indexwriter config info  
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
        //open the index, if there is no index, build a new one
        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        Directory directory = null;
        IndexWriter indexWrite = null;
        try {
            //set path of the original data
            directory = FSDirectory.open(new File(Path.IndexDir));
            //if the directory is locked , unlock it
            if (IndexWriter.isLocked(directory)) {
                IndexWriter.unlock(directory);
            }
            //new a object indexWrite  
            indexWrite = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreProcessDoc getDoc = new PreProcessDoc();
        WebDocument tempDoc = null;
        while ((tempDoc = getDoc.nextDocument()) != null) {
            Document doc = new Document();
            doc.add(new TextField("link", tempDoc.getDocLink(), Store.YES));
            doc.add(new TextField("content", tempDoc.getDocContent(), Store.YES));
            try {
                //write doc into index  
                indexWrite.addDocument(doc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //commit the data, if not , it would not be saved
        try {
            indexWrite.commit();
            //close the resource
            indexWrite.close();
            directory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
