
/**
 * Write a description of class ComparePorNome here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Comparator;
import java.io.Serializable;

public class ComparePorUtilizadoresUserName implements Comparator<Utilizador>, Serializable {
  
   public int compare(Utilizador e1, Utilizador e2) {
     return e1.getUserName().compareTo(e2.getUserName());
     
   }
    
}