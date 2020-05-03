import java.util.Scanner;

public class Driver {
  public static void main(String args[]){
    //testing constants
    int NODES = 100000;
    int CONNECTIONS = 5;
    int RAND_RANGE = 300000;
    
    //scanner init
    Scanner scan = new Scanner(System.in);
    
    Graph graph = new Graph();
    
    //add nodes
    for (int i = 0; i < NODES; i++){
      graph.add(new Listing((int) (Math.random() * RAND_RANGE)));
    }
    
    //add edges
    for (int i = 0; i < NODES; i++){
      int edgesToAdd = (int) (Math.random() * CONNECTIONS) + 1;
      for (int j = 0; j < edgesToAdd; j++){
        //prevent connections that loop back to the same node
        while(graph.addEdge(i, (int) (Math.random() * NODES)) == false){};
      }
    }
    
    //graph.show();
    
    System.out.println("Please note the Dijkstra search takes a long time to run with 100,000 nodes each with 5 connections but it does work (about 1 minute for my computer).");
    int input = 0;
    while (input != -1) {
      System.out.println("Please enter the number you wish to search for (-1 to exit program)");
      input = scan.nextInt();
      if (input != -1) {
        //DFT
        SearchResult result = graph.searchDFT(input);
        System.out.print("(Depth First Search) ");
        if (result.status){
          System.out.print("the number was: found");
        } else {
          System.out.print("the number was: not found");
        }
        System.out.println(", the shortest path was: " + result.pathLength + ", the number of examined nodes was: " + result.nodesExamined);
        
        //BFT
        result = graph.searchBFT(input);
        System.out.print("(Breadth First Search) ");
        if (result.status){
          System.out.print("the number was: found");
        } else {
          System.out.print("the number was: not found");
        }
        System.out.println(", the shortest path was: " + result.pathLength + ", the number of examined nodes was: " + result.nodesExamined);
        
        //Dijkstra
        result = graph.searchDijkstra(input);
        System.out.print("(Dijkstra Search) ");
        if (result.status){
          System.out.print("the number was: found");
        } else {
          System.out.print("the number was: not found");
        }
        System.out.println(", the shortest path was: " + result.pathLength + ", the number of examined nodes was: " + result.nodesExamined);
      }
    }
  }
}