package org.inbio.core.xls;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements the tree for the in-memory taxonomy 
 *
 * To speed up the process, the tree keeps always a flat list of nodes
 * in a hashtable with the format [name, node];
 *
 */


public class CSVWriter{
  public void printFlattenTree(List<String[]> l, String filename){
    OutputStream fis = null;
    BufferedWriter br = null;
    try {
      fis = new FileOutputStream(filename);
      br = new BufferedWriter(new OutputStreamWriter(fis, Charset.forName("UTF-8")));

      for(String[] taxon: l){
        br.write(taxon[0]);
        br.write(",");
        br.write(taxon[1]);
        br.write(",");
        br.write(taxon[2]);
        br.write("\n");
      }

    } catch (FileNotFoundException ex) {
      System.out.println(ex.getMessage());
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }finally{
      try {
        br.close();
      } catch (IOException ex) {
        System.out.println(ex.getMessage());
      }

    }
  }
}
