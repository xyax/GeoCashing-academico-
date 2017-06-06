import java.util.*;

public class Cache_Virtual extends Cache
{
    
    private String  prova;
    private double pont=0.0;
    // construtores
    public Cache_Virtual(){
        super();
        this.prova= "N/A";
    }
    
    public Cache_Virtual(String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p, String ap, String pistas, GregorianCalendar g, int tm,String prova){
        super(n,dono, local, x, desc,t ,ace, p, ap, pistas, g,tm);
        this.prova = prova;
    }
    
    public Cache_Virtual(Cache_Virtual a){
        super(a);
        this.prova = a.getProva();
    }
    
    //gets
    public String getProva(){
        return this.prova;
    }

    
    //sets
    public void setProva(String tam){
        this.prova = tam;
    }
 
    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || (o.getClass()!=this.getClass())) return false;
        Cache_Virtual a = (Cache_Virtual) o;
        return super.equals(a);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("Prova: "+this.prova+"\n");
        return s.toString();
    }
    

    public Cache_Virtual clone(){
        return new Cache_Virtual(this);
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
        this.setPontuacao(pontua*4+30);
       
    }
    
        public  void calculaDificuldade(){
        this.setDificuldade(pont/10);
    
    }
}
