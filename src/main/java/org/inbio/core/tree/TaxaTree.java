package org.inbio.core.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
  private Integer nextId; // return next id value to use has nodeId

  /* Constructor */
  public TaxaTree(){
    this.root = new TaxaNode("ROOT", "ROOT", TaxonRank.KINGDOM, null, null);
    this.nodeList = new HashMap<String, TaxaNode>();
    this.nodeList.put("ROOT", this.root);
    this.nextId = 1;
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
    if (null != this.nodeList.get(taxonName))
    	return;
//    nodePointer = this.nodeList.get(taxonName);

    // is the father already there?
    father = this.find(rootTaxonName);
    
    // the new node of the tree
    newNode = new TaxaNode(rootTaxonName, taxonName, taxonRank, this.nextId++, null);

    if (null != father){

      newNode.setFatherId(father.getNodeId());
      
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

  /**
   * Run the
   */
  public List<TaxaNode[]> completeHierarchyPerTaxon(){

    List<TaxaNode[]> completeHierarchy = new ArrayList<TaxaNode[]>();

    TaxaNode t = null;
    TaxaNode[] th = null;

    for(String key: this.nodeList.keySet()){

      t = this.nodeList.get(key);
      if(TaxonRank.SPECIES == t.getTaxonRank() || 
         TaxonRank.SUBSPECIES == t.getTaxonRank()){

        th = this.fullHierarchy(t);
        completeHierarchy.add(th);
      }
    }

    return completeHierarchy;
  }

  public TaxaNode[] fullHierarchy(TaxaNode taxon){
      TaxaNode[] hierarchy = new TaxaNode[7];


      hierarchy[6] = taxon;

      for (int i = 5; i >= 0; i--){
        taxon = this.nodeList.get(taxon.getFathersName());

        if(null == taxon){
            hierarchy[i] = new TaxaNode("ROOT", "Unknown", TaxonRank.KINGDOM, null, null);
        }else{
            hierarchy[i] = taxon;
        }
      }

      return hierarchy;
  }

  /**
   *  Traverse the tree breadth first = level by level left to right
   * @return  A list of arrays of the form { String, TaxaNode }
   *          where <code>String</code> contains the path to the node.
   */
  public List<Object[]> breadthFirstList() {
    List<Object[]> list = new ArrayList<Object[]>();
    Queue<Object[]> queue = new LinkedList<Object[]>();

    for (TaxaNode node : this.root.getChildren()) {
      queue.offer(new Object[]{"", node});
    }

    while (!queue.isEmpty()) {
      Object[] object = queue.poll();
      TaxaNode node 	= (TaxaNode)object[1];
      String path 	= (String)object[0];
      String newPath;
      // avoid include species (scientific name) in the path
      // this way subspecies works fine
      if(node.getTaxonRank() != TaxonRank.SPECIES)
          newPath 	= path == "" ? node.getTaxonName() : path + "," + node.getTaxonName();
      else
          newPath = path;
      list.add(new Object[]{path,node});
      for (TaxaNode taxaNode : node.getChildren()) {
        queue.offer(new Object[]{newPath, taxaNode});
      }
    }

    return list;
  }
  
}
