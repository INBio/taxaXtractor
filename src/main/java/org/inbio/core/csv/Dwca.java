package org.inbio.core.csv;
/**
 * Enum of the Darwin Core Archive fields 
 *
 */

public enum Dwca{

  KINGDOM(127),
  PHYLUM(164),
  CLASS(66),
  ORDER(157),
  FAMILY(92),
  GENUS(99),
  SPECIES(219),
  INFRASPECIFIC_EPITHET(122);

  private int column;

  Dwca(int column){
    this.column = column;
  }

  public int getColumn(){
      return this.column;
  }
}

