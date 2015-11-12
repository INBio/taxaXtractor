package org.inbio.core.xls;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import org.inbio.core.tree.TaxaNode;
import org.inbio.core.tree.TaxonRank;

/**
 * Implements the tree for the in-memory taxonomy 
 *
 * To speed up the process, the tree keeps always a flat list of nodes
 * in a hashtable with the format [name, node];
 *
 */


public class CSVWriter{

  public void printFullTaxonmyPerLine(List<TaxaNode[]> l, String filename){
      for(TaxaNode[] hierarchy : l){
          
      }
    OutputStream fis = null;
    BufferedWriter br = null;
    try {
      fis = new FileOutputStream(filename);
      br = new BufferedWriter(new OutputStreamWriter(fis, Charset.forName("UTF-8")));


      for(TaxaNode[] hierarchy : l){
        br.write(hierarchy[0].getTaxonName());
        br.write(",");
        br.write(hierarchy[1].getTaxonName());
        br.write(",");
        br.write(hierarchy[2].getTaxonName());
        br.write(",");
        br.write(hierarchy[3].getTaxonName());
        br.write(",");
        br.write(hierarchy[4].getTaxonName());
        br.write(",");
        br.write(hierarchy[5].getTaxonName());
        br.write(",");
        br.write(hierarchy[6].getTaxonName());
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
  
  public void printBreadthFirst(List<Object[]> list, String filename) {
    OutputStream fis = null;
    BufferedWriter br = null;
    try {
      fis = new FileOutputStream(filename);
      br = new BufferedWriter(new OutputStreamWriter(fis, Charset.forName("UTF-8")));
      
      for(Object[] node: list) {
        String path = (String)node[0];
        TaxaNode taxon = (TaxaNode)node[1];
        String line = "";
        // nodeId
        line += taxon.getNodeId().toString();
        line += ",";
        // fatherId
        line += taxon.getFatherId() == null? "" : taxon.getFatherId().toString();
        line += ",";
        // path
        if (path != "") {
          line += path;
          line += ",";
        }
        // taxonName
        if (taxon.getTaxonRank() == TaxonRank.SPECIES ||
            taxon.getTaxonRank() == TaxonRank.SUBSPECIES) {
            line += "\"" + taxon.getTaxonName() + "\"";
        }
        else {
            line += taxon.getTaxonName();
        }
        // fix number of ',' to make a proper csv
        int count = line.length() - line.replace(",", "").length();
        for (int i = count; i < 8; i++){
          line += ",";
        }
        if (!taxon.getTaxonRank().getName().equals("species") && 
            !taxon.getTaxonRank().getName().equals( "subspecies")) {
          // scientificName = lower name 
            System.out.println(taxon.getTaxonRank().getName());
            line += taxon.getTaxonName();
        }
        // taxonRank
        line += ",";
        line += taxon.getTaxonRank().getName();
        br.write(line);
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
