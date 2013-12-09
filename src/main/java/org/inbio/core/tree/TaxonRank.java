package org.inbio.core.tree;
/**
 * Enum of the taxonomy
 *
 */

public enum TaxonRank{

  KINGDOM("kingdom"),
  PHYLUM("phylum"),
  CLASS("class"),
  ORDER("order"),
  FAMILY("family"),
  GENUS("genus"),
  SPECIES("species"),
  SUBSPECIES("subspecies"),
  VARIETY("variety");

  public String name;

  public TaxonRank(String n){
    this.name = n;
  }
}

