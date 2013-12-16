package org.inbio.core.csv;

import java.io.*;
import java.nio.charset.Charset;
import org.inbio.core.tree.TaxaTree;
import org.inbio.core.tree.TaxonRank;

/**
 * Implement the csv parser to read the occurrence.txt files in the 
 * format that GBIF ships the occurrence.txt files whitin the DwC-A
 * files
 * */
public class OccurrenceParser{

  public TaxaTree parse(String filename){
    InputStream fis = null;
    BufferedReader br = null;
    String line = null;
    String[] fields = null;

    TaxaTree t = new TaxaTree();

    try{
      fis = new FileInputStream(filename);
      br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

      while ((line = br.readLine()) != null) {

        fields = line.split("\t");

        t.addChild("ROOT", fields[9], TaxonRank.KINGDOM);
        t.addChild(fields[9], fields[10], TaxonRank.PHYLUM);
        t.addChild(fields[10], fields[11], TaxonRank.CLASS);
        t.addChild(fields[11], fields[12], TaxonRank.ORDER);
        t.addChild(fields[12], fields[13], TaxonRank.FAMILY);
        t.addChild(fields[13], fields[14], TaxonRank.GENUS);
        t.addChild(fields[14], "\""+fields[6]+"\"", TaxonRank.SPECIES);
//      System.out.println(lineNumber+":\t"+fields[15]);
     
      }

    }catch(IOException ex){
      System.out.println(ex.getMessage());

    }finally{
      // Done with the file
      try{
        br.close();
      }catch(IOException ex){
        System.out.println(ex.getMessage());
      }
      br = null;
      fis = null;
    }

    return t;
  }
}
