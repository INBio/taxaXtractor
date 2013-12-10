package org.inbio.core.tree;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Implements the tree for the in-memory taxonomy 
 *
 * To speed up the process, the tree keeps always a flat list of nodes
 * in a hashtable with the format [name, node];
 *
 */


public class TaxaTree{

  private TaxaNode root;
  private Hashtable<String, TaxaNode> nodeList;

  /* Constructor */
  public TaxaTree(){
    this.root = new TaxaNode("ROOT", "ROOT", TaxonRank.KINGDOM);
    this.nodeList = new Hashtable<String, TaxaNode>();
    this.nodeList.put("ROOT", this.root);
  }

  /**
   * Search the tree for a node with some specific name
   *
   * @param taxonName
   *    The name of the taxon to find.
   *
   *  @return TaxaNode
   *    The node found or null
   */
  public TaxaNode find(String taxonName){
    return this.nodeList.get(taxonName);
  }


  /**
   * Find an specific taxonName whitin the child of a given node.
   *
   * @param root 
   *    Root node to start searching.
   * @param taxonName
   *    The name of the taxon to search
   *
   *  @return TaxaNode
   *    The node found or null
   */
  public TaxaNode find(TaxaNode root, String taxonName){

    ArrayList<TaxaNode> children = root.getChildren();
    TaxaNode node = null;

    for(TaxaNode child: children)
      if (child.getTaxonName().equals(taxonName))
        node = child;

    return node;

  }

  /**
   * Add a children under the rootTaxonName
   *
   * @param rootTaxonName
   *    The name of the father for the node. If rootTaxonName is not in the tree the 
   *    new node is not added. period.
   * @param taxonName
   *    The name of the new node to be added.
   * @param taxonRank
   *    The rank of the taxon to be addes.
   *
   */
  public void addChild(String rootTaxonName, String taxonName, TaxonRank taxonRank){

    TaxaNode newNode = null;
    TaxaNode nodePointer = null;
    TaxaNode father = null;

    // check if the element is already in the tree
    nodePointer = this.nodeList.get(taxonName);

    // the new node of the tree
    newNode = new TaxaNode(rootTaxonName, taxonName, taxonRank);

    // is the father already there?
    father = this.find(rootTaxonName);

    if (null != father){
      // is the son already there?
      if ( this.find(father, taxonName) == null){

        if(null != nodePointer ){
          this.find(nodePointer.getFathersName()).removeChild(nodePointer);
          newNode = nodePointer; 
        }

        father.addChild(newNode);
      }
      // if there is no father in the tree. add the node to the root.
    }else{
      newNode.setFathersName("ROOT");
      this.root.addChild(newNode);
    }

    this.nodeList.put(newNode.getTaxonName(), newNode);
  }

  /**
   * The method get the children of the root, select all the nodes whit 
   * taxonRank != TaxonRank.KINGDOM and tries to add it again somewhere else.
   */
  public void prune(){
    ArrayList<TaxaNode> rootsChildren = null;

    rootsChildren = this.root.getChildren();

    for (TaxaNode child: rootsChildren){

      if (child.getTaxonRank() != TaxonRank.KINGDOM){
        // if the node is not inside the root, insert it;
        if(null == this.find(child.getTaxonName())){

        }
      }
    }
  }

  /**
   * Make the tree flat.
   *
   * @return List
   *    A list with the flat form of the tree
   */
  public List flattenTree(){
    return null;
  }

  public static void main(String[] args){

    TaxaTree t = new TaxaTree();

    t.addChild("2","21" , TaxonRank.PHYLUM);
    t.addChild("21","212" , TaxonRank.CLASS);
    t.addChild("","2" , TaxonRank.KINGDOM);
    t.addChild("2","21" , TaxonRank.PHYLUM);
    t.addChild("21","212" , TaxonRank.PHYLUM);

    t.prune();

  }
}
