//Graph class by Erik Nilsson

import java.util.ArrayList;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Graph {
  //linked list to allow for expandability of graph
  //one contains the nodes and the other, the connections
  private ArrayList<Node> nodes;
  private ArrayList<ArrayList<Edge>> adj;
  
  public Graph(){
    nodes = new ArrayList<Node>();
    adj = new ArrayList<ArrayList<Edge>>();
  }
  
  //add a node to the datastructure
  public boolean add(Listing newNode){
    if (nodes.add(new Node(newNode)) == false || adj.add(new ArrayList<Edge>()) == false){
      return false;
    }
    
    return true;
  }
  
  //add a connection to the data structure
  public boolean addEdge(int fromNode, int toNode){
    //System.out.println(fromNode + " " + toNode);
    if (fromNode == toNode || adj.get(fromNode).add(new Edge(toNode)) == false){
      return false;
    }
    
    return true;
  }
  
  public boolean insert(int location, Listing newNode){
    if (location >= nodes.size()){
      return false;
    }
    
    nodes.set(location, new Node(newNode));
    return true;
  }
  
  //search traversal methods
  //Depth First Traversal Search
  public SearchResult searchDFT(int key){
    Stack<Integer> stack = new Stack<Integer>();
    
    //house keeping initialization
    for (Node node: nodes){
      node.pushed = false;
    }
    
    //monitor variables
    int examinedNodes = 0;
    
    //algorithm
    stack.push(0);
    nodes.get(0).pushed = true;
    
    while (!stack.empty()){
      int v = stack.pop();
      
      //process node
      examinedNodes += 1;
      //test if it is the node I am looking for
      if (nodes.get(v).listing.compareTo(key)){
        return new SearchResult(true, examinedNodes, examinedNodes);
      }
      //add all the children of the node to the stack to be processed next
      for (Edge edge: adj.get(v)){
        //if the child node being examined is not already being examined then add it to the list
        if (!nodes.get(edge.toNode).pushed){
          stack.push(edge.toNode);
          nodes.get(edge.toNode).pushed = true;
        }
      }
    }
    
    //if the node was not found
    return new SearchResult(false, examinedNodes, examinedNodes);
  }
  
  //Breadth First Traversal Search
  public SearchResult searchBFT(int key){
    Queue<Integer> queue = new LinkedList<Integer>();
    
    //house keeping initialization
    for (Node node: nodes){
      node.pushed = false;
    }
    
    //monitor variables
    int examinedNodes = 0;
    
    //algorithm
    queue.add(0);
    nodes.get(0).pushed = true;
    
    while (!queue.isEmpty()){
      int v = queue.poll();
      
      //process node
      examinedNodes += 1;
      //test if it is the node I am looking for
      if (nodes.get(v).listing.compareTo(key)){
        return new SearchResult(true, examinedNodes, examinedNodes);
      }
      //add all the children of the node to the queue to be processed next
      for (Edge edge: adj.get(v)){
        //if the child node being examined is not already being examined then add it to the list
        if (!nodes.get(edge.toNode).pushed){
          queue.add(edge.toNode);
          nodes.get(edge.toNode).pushed = true;
        }
      }
    }
    
    return new SearchResult(false, examinedNodes, examinedNodes);
  }
  
  //Note: it runs until it searches all nodes everytime to ensure that the shortest path was found.
  public SearchResult searchDijkstra(int key){
    //a variable to store the index of the node if it matches the one the user specified with the key
    int found = -1;
    
    //housekeeping init for program
    for (Node node: nodes){
      node.pushed = false;
      node.visited = false;
    }
    
    //init all the distances for the algorithm to start
    int[] distance = new int[nodes.size()];
    distance[0] = 0;
    for (int i = 1; i < distance.length; i++){
      distance[i] = Integer.MAX_VALUE;
    }
    
    //current is the current node that is having its children processed
    int current;
    //the flag to end the search once all nodes have been examined
    boolean searching = true;
    while (searching) {
      //sets current to the least distance node to be examined
      //if the node has not been examined yet and it is either the first one encountered or if not, if it's distance is less than the current smallest distance
      current = -1;
      for (int i = 0; i < distance.length; i++){
        if (nodes.get(i).visited == false && (current == -1 || distance[i] < distance[current])){
          current = i;
        }
      }
      nodes.get(current).visited = true;
      
      //test if this is the node we are looking for
      if (nodes.get(current).listing.compareTo(key)){
        found = current;
      }
      
      //proccess neighbors in the algorithm to find the shortest path from node 0
      //adjust the distances for all the children
      ArrayList<Edge> children = adj.get(current);
      for (int i = 0; i < children.size(); i++){
        if (distance[current] + children.get(i).weight < distance[children.get(i).toNode]){
          distance[children.get(i).toNode] = distance[current] + children.get(i).weight;
        }
      }
      
      //break the loop if all nodes have been visited.
      searching = false;
      for (Node n: nodes){
        if (!n.visited){
          searching = true;
          break;
        }
      }
    }
    
    //if the node was not found
    //the distance in the second slot should be null but int's do not allow for it since it is a class datatype
    if (found == -1){
      return new SearchResult(false, nodes.size(), nodes.size());
    }
    //if the node was found
    return new SearchResult(true, distance[found], nodes.size());
  }
  
  //test methods
  public void show(){
    for (int i = 0; i < nodes.size(); i++){
      System.out.println("Node " + i + " is: " + nodes.get(i).listing.toString());
    }
  }
  
  //utility classes
  //wrapper for the listing
  private class Node {
    Listing listing;
    boolean pushed;
    boolean visited;
    
    Node (){
      this(null);
    }
    
    Node(Listing l){
      listing = l;
    }
  }
  //edge to store the toNode number and weight
  private class Edge {
    int toNode;
    int weight;
    
    Edge(){
      
    }
    
    Edge(int n){
      this(n, 1);
    }
    
    Edge(int n, int w){
      toNode = n;
      weight = w;
    }
  }
}