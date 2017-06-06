import java.util.*;
import java.io.*;
/**
 * Escreva a descrição da classe GeoTest aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class TestCache implements Serializable
{
    public static void main (String [] args){
    GPS gp=new GPS (10,5);
    Micro_Cache mc1,mc2,mc3;
    Multi_Cache multic1,multic2,multic3;
    Cache_Misterio mistc1,mistc2,mistc3;
    Cache_Evento event1,event2,event3;
    Cache_Virtual virt1,virt2,virt3;
    
    
    mc1=new Micro_Cache("MicCache1","luis","Braga",gp,"asd","cidade","sim","baixa","nadar","afasd",new GregorianCalendar(),10,40);
    mc2=new Micro_Cache("MicCache2","jose","Amares",gp,"asd","campo","sim","Alta","escalar","adas",new GregorianCalendar(),9,95);
    mc3=new Micro_Cache("MicCache3","Qas","Amares",gp,"asd","montanha","nao","media","ambos","adas",new GregorianCalendar(),9,2);
    
  
    System.out.println(mc1.toString());
    mc1.setTerreno("campo");
    System.out.println(mc1.toString());
    /*
    mc2.pontuacao();
    mc2.dificuldade();
    System.out.println(mc2.toString());
    
    mc3.pontuacao();
    mc3.dificuldade();
    System.out.println(mc3.toString());
    */
    
    
    multic1=new Multi_Cache("MultiCache1","qwe","Braga",gp,"asd","cidade","sim","baixa","nadar","afasd",new GregorianCalendar(),10,2);
    multic2=new Multi_Cache("MultiCache2","ewq","Braga",gp,"asd","montanha","nao","Alta","nenhum","afasd",new GregorianCalendar(),10,3);
    multic3=new Multi_Cache("MultiCache3","ret","Braga",gp,"asd","campo","sim","baixa","nadar","afasd",new GregorianCalendar(),10,11);
    
    /*multic1.pontuacao();
    multic1.dificuldade();
    System.out.println(multic1.toString());
    
    multic2.pontuacao();
    multic2.dificuldade();
    System.out.println(multic2.toString());
    
    multic3.pontuacao();
    multic3.dificuldade();
    System.out.println(multic3.toString());
    */
   /*
   
    mistc1=new Cache_Misterio("misterioCache1","nel","Braga",gp,"asd","campo","sim","Extremo","nenhum","afasd",new GregorianCalendar(),10,1);
    mistc2=new Cache_Misterio("misterioCache2","ze","Braga",gp,"asd","cidade","nao","Alta","nenhum","afasd",new GregorianCalendar(),10,2);
    mistc3=new Cache_Misterio("misterioCache3","jp","Braga",gp,"asd","montanha","sim","baixa","ambos","afasd",new GregorianCalendar(),10,4);
    */
    
    /*mistc1.pontuacao();
    mistc1.dificuldade();
    System.out.println(mistc1.toString());
    
    mistc2.pontuacao();
    mistc2.dificuldade();
    System.out.println(mistc2.toString());
    
    mistc3.pontuacao();
    mistc3.dificuldade();
    System.out.println(mistc3.toString());
    */
   
   
    event1=new Cache_Evento("EventoCache1","joaquim","Braga",gp,"asd","cidade","nao","Alta","nadar","afasd",new GregorianCalendar(),10);
    event2=new Cache_Evento("EventoCache2","ab","Braga",gp,"asd","campo","sim","baixa","nadar","afasd",new GregorianCalendar(),10);
    event3=new Cache_Evento("EventoCache3","cd","Braga",gp,"asd","montanha","sim","media","escalar","afasd",new GregorianCalendar(),10);
    /*
    event1.pontuacao();
    event1.dificuldade();
    System.out.println(event1.toString());
    
    event2.pontuacao();
    event2.dificuldade();
    System.out.println(event2.toString());
    
    event3.pontuacao();
    event3.dificuldade();
    System.out.println(event3.toString());
    */
    
    virt1=new Cache_Virtual("virtualCache1","andre","porto",gp,"asd","montanha","sim","Alta","nenhum","afasd",new GregorianCalendar(),10,"prova");
    virt2=new Cache_Virtual("virtualCache2","andre","porto",gp,"asd","cidade","sim","Extremo","ambos","afasd",new GregorianCalendar(),10,"prova");
    virt3=new Cache_Virtual("virtualCache3","andre","porto",gp,"asd","campo","nao","media","nenhum","afasd",new GregorianCalendar(),10,"prova");
    /*
    virt1.pontuacao();
    virt1.dificuldade();
    System.out.println(virt1.toString());
    
    virt2.pontuacao();
    virt2.dificuldade();
    System.out.println(virt2.toString());
    
    
    virt3.pontuacao();
    virt3.dificuldade();
    System.out.println(virt3.toString());
    */
    }
}
