package org.inbio.core.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.csv.CSVParser;
import org.inbio.core.gbifapi.SpeciesGbifAPI;
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
    File file = null;
    String line = null;
    String[] fields = null;
    // avoid repeated species
    Set<String> uniqueSpecies = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    TaxaTree t = new TaxaTree();

    try{
//      file = new File(filename);
      fis = new FileInputStream(filename);
      br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
      Integer currentLine = new Integer(0);
      Integer errorCounter = new Integer(0);
      Integer lastColumnNumber = new Integer(0);
      
      while ((line = br.readLine()) != null) {
        currentLine++;
        CSVParser parser;
        String kingdom, phylum = null, class_ = null, order = null, family = null, genus = null, species = null;
//        CSVRecord record;
        fields = line.split("\t");
        
        if (lastColumnNumber > 0 && lastColumnNumber != fields.length) {
          System.out.println("Line " + currentLine + " different column number agains last line");
        }
        lastColumnNumber = fields.length;
        
        kingdom  = fields[Dwca.KINGDOM.getColumn()];
        species  = fields[Dwca.SPECIES.getColumn()];
        
        if (uniqueSpecies.contains(kingdom + species)) {
          // repeat key
          continue;
        }
        
        // insert key to map of unique species
        uniqueSpecies.add(kingdom + species);
        
        Map<String, String> taxonomy = new HashMap<>();
        if(!kingdom.isEmpty()) {
          taxonomy.put("kingdom", kingdom);
        }
        
        SpeciesGbifAPI speciesGbifApi = new SpeciesGbifAPI();
        HashMap<String, Object> gbifResult = speciesGbifApi.Match(null, species, false, true, taxonomy);


       // System.out.println("Search gbif " + species + "|" + taxonomy);

       /* if(gbifResult.containsKey("rank") && gbifResult.get("rank").toString().equals("SPECIES")) {
            System.out.println(gbifResult);
            System.out.println("");
        }*/
        
        try {
          kingdom = gbifResult.get("kingdom").toString();
          species = gbifResult.get("canonicalName").toString();
          if (gbifResult.containsKey("phylum"))
            phylum   = gbifResult.get("phylum").toString();
          else
            phylum  = fields[Dwca.PHYLUM.getColumn()];
          if (gbifResult.containsKey("class"))
            class_   = gbifResult.get("class").toString();
          else
            class_  = fields[Dwca.CLASS.getColumn()];
          if (gbifResult.containsKey("order"))
            order    = gbifResult.get("order").toString();
          else
            order   = fields[Dwca.ORDER.getColumn()];
          if (gbifResult.containsKey("family"))
            family   = gbifResult.get("family").toString();
          else
            family  = fields[Dwca.FAMILY.getColumn()];
          if (gbifResult.containsKey("genus"))
            genus    = gbifResult.get("genus").toString();
          else
            genus = fields[Dwca.GENUS.getColumn()];
        } catch (NullPointerException ex) {
          System.out.println ("hashMap " + gbifResult);
          System.out.println ("kingdom: " + kingdom + " species: " + species);
          errorCounter++;
          continue;
        }
        
        if (kingdom.isEmpty() ||
            phylum.isEmpty()  ||
            class_.isEmpty()  ||
            order.isEmpty()   ||
            family.isEmpty()  ||
            genus.isEmpty()   ||
            species.isEmpty()) {
//          System.out.print("Error! line [" + currentLine.toString() + "] species: " + species + "\n");
          errorCounter++;
          continue;
        }

        t.addChild("ROOT",  kingdom,   TaxonRank.KINGDOM);
        t.addChild(kingdom, phylum,    TaxonRank.PHYLUM);
        t.addChild(phylum,  class_,     TaxonRank.CLASS);
        t.addChild(class_,  order,     TaxonRank.ORDER);
        t.addChild(order,   family,    TaxonRank.FAMILY);
        t.addChild(family,  genus,     TaxonRank.GENUS);
        t.addChild(genus,   "\""+species+"\"", TaxonRank.SPECIES);
//      System.out.println(lineNumber+":\t"+Dwca.);
        if (currentLine % 10000 == 0)
          System.out.println(currentLine.toString() + " líneas procesadas");
      }
      
//      while ((line = br.readLine()) != null) {
//
//        fields = line.split("\t");
//        
//        String kingdom  = fields[Dwca.KINGDOM.getColumn()];
//        String phylum   = fields[Dwca.PHYLUM.getColumn()];
//        String class_   = fields[Dwca.CLASS.getColumn()];
//        String order    = fields[Dwca.ORDER.getColumn()];
//        String family   = fields[Dwca.FAMILY.getColumn()];
//        String genus    = fields[Dwca.GENUS.getColumn()];
//        String species  = fields[Dwca.SPECIES.getColumn()];
//        
//        if (kingdom.isEmpty() ||
//            phylum.isEmpty()  ||
//            class_.isEmpty()  ||
//            order.isEmpty()   ||
//            family.isEmpty()  ||
//            genus.isEmpty()   ||
//            species.isEmpty()) {
////          System.out.print("Error! line [" + currentLine.toString() + "] species: " + species + "\n");
//          errorCounter++;
//          continue;
//        }
//
//        t.addChild("ROOT",  kingdom,   TaxonRank.KINGDOM);
//        t.addChild(kingdom, phylum,    TaxonRank.PHYLUM);
//        t.addChild(phylum,  class_,     TaxonRank.CLASS);
//        t.addChild(class_,  order,     TaxonRank.ORDER);
//        t.addChild(order,   family,    TaxonRank.FAMILY);
//        t.addChild(family,  genus,     TaxonRank.GENUS);
//        t.addChild(genus,   "\""+species+"\"", TaxonRank.SPECIES);
////      System.out.println(lineNumber+":\t"+Dwca.);
//        currentLine++;
//     
//      }
      System.out.println("Total errors: " + errorCounter.toString() + " in " + currentLine.toString() + " lines");

    }catch(IOException ex){
      System.out.println(ex.getMessage());

    }finally{
      // Done with the file
      try {
        br.close();
        fis.close();
      } catch (IOException e) {
        System.out.println(e.getMessage());
      }
    }

    return t;
  }
}
