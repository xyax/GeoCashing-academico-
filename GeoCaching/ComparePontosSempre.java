
/**
 * Write a description of class ComparePontosSempre here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Comparator;
import java.io.Serializable;
public class ComparePontosSempre implements Comparator<Utilizador>,Serializable
{
    public int compare(Utilizador e1, Utilizador e2) {
    
 
     if(e1.getPontosSempre() > e2.getPontosSempre())
       return 1; 
     if(e1.getPontosSempre() < e2.getPontosSempre())
       return -1;
     return 0;     
      
     
   }
}

    
  

