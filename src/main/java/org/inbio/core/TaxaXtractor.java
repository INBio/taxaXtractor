package org.inbio.core;

import java.util.List;
import org.inbio.core.csv.OccurrenceParser;
import org.inbio.core.tree.TaxaTree;
import org.inbio.core.xls.CSVWriter;

/**
 * Main of the program
 *
 * Parse the options and run the program.
 */
public class TaxaXtractor{
/*
 * Print the tree in the format
 *   <Taxon,Parent,Rank>
  public static void main(String[] args){

    List l = null;
    TaxaTree t = null;

    OccurrenceParser o = new OccurrenceParser();
    CSVWriter x = new CSVWriter();
    
    t = o.parse("/home/asanabria/occurrence.txt");
    l = t.flattenTree();

    x.printFlattenTree(l, "/home/asanabria/taxonomy.csv");
  }
  */
  public static void main(String[] args){

    List l = null;
    TaxaTree t = null;
    List<Object[]> breadthFirst = null;

    OccurrenceParser o = new OccurrenceParser();
    CSVWriter x = new CSVWriter();
    
    t = o.parse(args[0]);
    l = t.completeHierarchyPerTaxon();
    
    breadthFirst = t.breadthFirstList();
   // x.printBreadthFirst(breadthFirst, args[1]);
    x.printFullTaxonmyPerLine(l, args[1]);
  }
}
