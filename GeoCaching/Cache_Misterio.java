import java.util.*;

public class Cache_Misterio extends Cache
{
    

    private String  misterio;
    private double pont=0.0;
    // construtores
    public Cache_Misterio(){
        super();
        this.misterio = "N/A";
    }
    
    public Cache_Misterio(String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p, String ap, String pistas, GregorianCalendar g,int tm ,String m){
        super(n,dono, local, x, desc,t ,ace, p, ap, pistas, g,tm);
        this.misterio = m;
    }
    
    public Cache_Misterio(Cache_Misterio a){
        super(a);
        this.misterio = a.getMisterio();
    }
    
    //gets
    public String getMisterio(){
        return this.misterio;
    }

    
    //sets
    public void setMisterio(String m){
        this.misterio = m;
    }
 
    
    public boolean equals(Object o){
       if(this==o) return true;
       if((o==null) || (o.getClass()!=this.getClass()))return false;
       Cache_Misterio a = (Cache_Misterio) o;
       return super.equals(a);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("Misterio: "+this.misterio+"\n");
        return s.toString();
    }
    

    public Cache_Misterio clone(){
        return new Cache_Misterio(this);
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
        /*if(this.misterio==1){pontua+=1;}
        if(this.misterio==2){pontua+=3;}
        if(this.misterio==3){pontua+=5;}
        if(this.misterio==4){pontua+=7;}
        if(this.misterio==5){pontua+=10;}*/
        pont=pontua;
        this.setPontuacao(pontua *4);//+ this.misterio*20);
        
    }
    
        public  void calculaDificuldade(){
        this.setDificuldade(pont/10);
    
    }
}
