//the listing class specified in the project details
public class Listing {
  int value;
  
  Listing (){
    this(0);
  }
  
  Listing(int value) {
    this.value = value;
  }
  
  public boolean compareTo(int key){
    return (key == value);
  }
  
  public String toString(){
    return "" + value + "";
  }
}