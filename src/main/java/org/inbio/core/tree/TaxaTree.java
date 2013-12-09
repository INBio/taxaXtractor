package org.inbio.core.tree;
/**
 * Implements the tree for the in-memory taxonomy 
 *
 * To speed up the process, the tree keeps always a flat list of nodes
 * in a hashtable with the format [name, node];
 *
 */


public class TaxaTree{

  private TaxaNode root;
  private HashTable nodeList;

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
    return this.nodeList[taxonName];
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

    ArrayList<TaxonNode> children = root.getChildren();
    TaxonNode node = null;

    for(TaxonNode child: children){
      if (child.getName().equals(taxonName))
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
    TaxaNode father = null;
      
    // the new node of the tree
    newNode = new TaxaNode(taxonName, taxonRank);

    // is the father already there?
    father = this.find(rootTaxonName);

    if (null != father){
      // is the son already there?
      if ( this.find(father, taxonName) == null){ 
        father.addChild(newNode);
      }
      // if there is no father in the tree. add the node to the root.
    }else{ 
      this.root.addChild(newNode);
    }

    this.nodeList[newNode.getName()] = newNode;
  }

  /**
   * The method get the children of the root, select all the nodes whit 
   * taxonRank != TaxonRank.KINGDOM and tries to add it again somewhere else.
   */
  public void prune(){

  }

  /**
   * Make the tree flat.
   *
   * @return List
   *    A list with the flat form of the tree
   */
  public List flattenTree(){ }

}

class TaxaNode{
  // name and rank for the current taxon.
  private TaxonNode father;
  private String taxonName;
  private TaxonRank taxonRank;

  // for the children of every node
  private ArrayList<TaxaNode> children;


  /** Costructor */
  public TaxaNode(TaxonNode father, String name, TaxonRank taxonRank){
    this.father = father;
    this.taxonName = name;
    this.taxonRank = taxonRank;
    this.children = new ArrayList<TaxaNode>();
  }

  /** Costructor */
  public TaxaNode(String name, TaxonRank taxonRank){
    this.father = null;
    this.taxonName = name;
    this.taxonRank = taxonRank;
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
   * Return a list of the children of the current taxon
   */
  public void getChildren(){
    return this.children;
  }

  public void setTaxonName(String name){
    this.taxonName = name;
  }
  public void setTaxonRank(TaxonRank rank){
    this.taxonRank = rank;
  }
  public void setFather(TaxonNode father){
    this.father = father;
  }

  public String getTaxonName(){
    return this.taxonName;
  }

  public TaxonRank getTaxonRank(){
    return this.taxonRank;
  }

  public TaxonNode getFather(){
    return this.father;
  }
}
