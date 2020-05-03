//Search Result class to return easy to access data to user about the search
//contains whether or not the node was found
//what the shortest path length is
//and the number of nodes processed to finish the search
  public class SearchResult {
    boolean status;
    int pathLength;
    int nodesExamined;
    
    public SearchResult (){
      this(false, 0, 0);
    }
    
    public SearchResult(boolean s, int p, int n){
      status = s;
      pathLength = p;
      nodesExamined = n;
    }
  }