package org.inbio.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
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
  private HashMap<String, TaxaNode> nodeList;

  /* Constructor */
  public TaxaTree(){
    this.root = new TaxaNode("ROOT", "ROOT", TaxonRank.KINGDOM);
    this.nodeList = new HashMap<String, TaxaNode>();
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

      if(null != nodePointer ){
        this.find(nodePointer.getFathersName()).removeChild(nodePointer);
        newNode = nodePointer;
      }

      // is the son already there?
      if ( this.find(father, taxonName) == null){
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
          // TODO: This needs some thinking :D
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

    return flattenNode(this.root);
  }

  public List flattenNode(TaxaNode root){

    TaxaNode t = null;
    String[] current = null;
    ArrayList<String[]> list = new ArrayList<String[]>();

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.KINGDOM == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.PHYLUM == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.CLASS == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.ORDER == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }
    
    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.FAMILY == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.GENUS == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }

    for(String key: this.nodeList.keySet()){

        t = this.nodeList.get(key);
        if(TaxonRank.SPECIES == t.getTaxonRank()){

          current = new String[3];
          current[0] = t.getTaxonName(); // the name of the taxon
          current[1] = t.getFathersName(); // the name of the father
          current[2] = t.getTaxonRank().getName();

          list.add(current);
        }
    }
    return list;
  }
}
