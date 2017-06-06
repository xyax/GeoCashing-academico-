
/**
 * Escreva a descrição da classe Cache_Evento aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.*;
public class Cache_Evento extends Cache
{
        

    private int hora,min;
    private double pont=0.0;
    // construtores
    public Cache_Evento(){
        super();
        this.hora= 0;
        this.min= 0;
    }
    
    public Cache_Evento(String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p, String ap, String pistas, GregorianCalendar g,int tm){
        super(n,dono, local, x, desc,t ,ace, p, ap, pistas, g,tm);
        this.hora = g.get(Calendar.HOUR_OF_DAY);
        this.min = g.get(Calendar.MINUTE);
    }
    
    public Cache_Evento(Cache_Evento a){
        super(a);
        this.hora=a.getDataCriacao().get(Calendar.HOUR_OF_DAY);
        this.min=a.getDataCriacao().get(Calendar.MINUTE);
    }
    

    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || (o.getClass()!=this.getClass())) return false;
        Cache_Evento a = (Cache_Evento) o;
        return super.equals(a);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("Hora: "+this.hora+"\n");
        return s.toString();
    }
    

    public Cache_Evento clone(){
        return new Cache_Evento(this);
    }

    
    public  void calculaPontuacao(){
        int pontua=0;
        if(this.getTerreno().equals("cidade")){pontua+=3;}
        if(this.getTerreno().equals("campo")){pontua+=5;}
        if(this.getTerreno().equals("montanha")){pontua+=8;}
        if(this.getAcess().equals("sim")){pontua+=5;}
        if(this.getAcess().equals("nao")){pontua+=7;}
        if(this.getPerigo().equals("baixa")){pontua+=3;}
        if(this.getPerigo().equals("media")){pontua+=5;}
        if(this.getPerigo().equals("Alta")){pontua+=7;}
        if(this.getPerigo().equals("Extremo")){pontua+=10;}
        if(this.getAptidoes().equals("nadar")){pontua+=7;}
        if(this.getAptidoes().equals("escalar")){pontua+=7;}
        if(this.getAptidoes().equals("ambos")){pontua+=15;}
        if(this.getAptidoes().equals("nenhum")){pontua+=3;}
        
        pont=pontua;
        this.setPontuacao(pontua *4 +20);
       
    }
    
        public  void calculaDificuldade(){
        this.setDificuldade(pont/10);
    
    }
}

    
