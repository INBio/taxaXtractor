package org.inbio.core.tree;

import java.util.ArrayList;

public class TaxaNode{
  private Integer nodeId;
  private Integer fatherId;
  // name and rank for the current taxon.
  private String fathersName;
  private String taxonName;
  private TaxonRank taxonRank;

  // for the children of every node
  private ArrayList<TaxaNode> children;


  /** Constructor 
   * */
  public TaxaNode(String fathersName, String name, TaxonRank taxonRank, Integer nodeId, Integer fatherId){
    this.fathersName = fathersName;
    this.taxonName = name;
    this.taxonRank = taxonRank;
    this.nodeId = nodeId;
    this.fatherId = fatherId;
    this.children = new ArrayList<TaxaNode>();
  }


  /**
   * Add a children to the current node
   *
   * @param t the child to be added
   */
  public void addChild(TaxaNode t){
    this.children.add(t);
  }

  /**
   * Remove a child from the current node
   *
   * @param t the child to be removed
   */
  public void removeChild(TaxaNode t){
    this.children.remove(t);
  }

  /**
   * Return a list of the children of the current taxon
   */
  public ArrayList<TaxaNode> getChildren(){
    return this.children;
  }

  /* getters and setters */
  public void setTaxonName(String name){
    this.taxonName = name;
  }
  public String getTaxonName(){
    return this.taxonName;
  }

  public void setFathersName(String name){
    this.fathersName = name;
  }
  public String getFathersName(){
    return this.fathersName;
  }

  public void setTaxonRank(TaxonRank rank){
    this.taxonRank = rank;
  }
  public TaxonRank getTaxonRank(){
    return this.taxonRank;
  }


  public Integer getNodeId() {
    return nodeId;
  }


  public void setNodeId(Integer nodeId) {
    this.nodeId = nodeId;
  }


  public Integer getFatherId() {
    return fatherId;
  }


  public void setFatherId(Integer fatherId) {
    this.fatherId = fatherId;
  }
  

}
