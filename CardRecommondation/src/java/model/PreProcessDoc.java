/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Zhirun Tian
 */
public class PreProcessDoc {

    FileInputStream fis = null;
    BufferedReader reader = null;
    String line;
    String subs;

    public PreProcessDoc() throws FileNotFoundException {
        fis = new FileInputStream(Path.DataDir);
        reader = new BufferedReader(new InputStreamReader(fis));
    }

    public WebDocument nextDocument() throws IOException {
        line = reader.readLine();
        while (line != null) {

            if (line.equals("<DOC>")) {
                line = reader.readLine();
                //get the doc number
                subs = line.substring(7, (line.length() - 8));

            } else {
                //if(line.equals("<p>") ){
                String result = "";
                StringBuilder stringBuilder = new StringBuilder();
                //String temp = reader.readLine();
                //System.out.println("+++++++++"+ temp);
                int count = 0;

                String tempString = reader.readLine();
                if (tempString.equals(null)) {
                    continue;
                }

                while (!(line.contains("</DOC>"))) {
                    stringBuilder.append(line);

                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    count++;
                    //System.out.println("count "+ count+"+++++++++"+ temp);
                }
                //combine the lines to a string
                result = stringBuilder.toString();
                //return the map
//                Map<String, Object> rstPair = new HashMap<String, Object>();
//                rstPair.put(subs, result.toCharArray());
                WebDocument doc = new WebDocument(subs, result);
                return doc;

            }

            if (line == null) {
                break;
            }
            line = reader.readLine();
        }

        reader.close();
        fis.close();

        return null;
    }

}
