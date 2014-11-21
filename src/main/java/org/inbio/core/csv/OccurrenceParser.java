package org.inbio.core.csv;

import java.io.*;
import java.nio.charset.Charset;
import org.inbio.core.tree.TaxaTree;
import org.inbio.core.tree.TaxonRank;
import org.inbio.core.csv.Dwca;

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

        t.addChild("ROOT",       fields[Dwca.KINGDOM.getColumn()],   TaxonRank.KINGDOM);
        t.addChild(fields[Dwca.KINGDOM.getColumn()],    fields[Dwca.PHYLUM.getColumn()],    TaxonRank.PHYLUM);
        t.addChild(fields[Dwca.PHYLUM.getColumn()],     fields[Dwca.CLASS.getColumn()],     TaxonRank.CLASS);
        t.addChild(fields[Dwca.CLASS.getColumn()],      fields[Dwca.ORDER.getColumn()],     TaxonRank.ORDER);
        t.addChild(fields[Dwca.ORDER.getColumn()],      fields[Dwca.FAMILY.getColumn()],    TaxonRank.FAMILY);
        t.addChild(fields[Dwca.FAMILY.getColumn()],     fields[Dwca.GENUS.getColumn()],     TaxonRank.GENUS);
        t.addChild(fields[Dwca.GENUS.getColumn()], "\""+fields[Dwca.SPECIES.getColumn()]+"\"", TaxonRank.SPECIES);
//      System.out.println(lineNumber+":\t"+Dwca.);
     
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
