import javax.swing.JOptionPane; ;
import java.io.*;

public class GPS implements Serializable
{
    private double lat, longi;
    
   //construtor
   public GPS(double la, double lo) { 
       this.lat=la;
       this.longi = lo;
   }
   
   public GPS(){
       this(0, 0); 
   }
   
   public GPS(GPS c){
       lat = c.getLat(); 
       longi = c.getLongi(); 
   }

   // get
   public double getLat(){ 
       return lat; 
   }
   public double getLongi(){ 
       return longi; 
   }
   
   //set
   public void setLat(double la){
       this.lat=la;
   }
   
   public void setLongi(double lo){
       this.longi=lo;
   }
   
   //
   public GPS clone(){ 
       return new GPS(this);
   }
   
   public String toString(){
         StringBuilder s = new StringBuilder();
         s.append("Latitude: " + this.lat + "\n");
         s.append("Longitude: " + this.longi);
         
         return s.toString();
   }
   
   public boolean equals (Object o){
       if (this==o) return true;
       if(o == null || o.getClass() != this.getClass() ) return false;
       
       GPS c = (GPS) o;
       return(this.lat==c.getLat() && this.longi==c.getLongi());
   }
}