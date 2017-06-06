
/**
 * Write a description of class ComparePorUserName here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Comparator;
import java.io.Serializable;

public class ComparePorUserName implements Comparator<String>, Serializable {
  
   public int compare(String e1, String e2) {
     return e1.compareTo(e2);
     
   }
    
}