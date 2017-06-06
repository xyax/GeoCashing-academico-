import java.util.*;

public class Micro_Cache extends Cache
{

    private double  tamanho;
    private double pont=0.0;
    // construtores
    public Micro_Cache (){
        super();
        this.tamanho= 0.0;
    }
    
    public Micro_Cache (String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p, String ap, String pistas, GregorianCalendar g, int tm,double tam){
        super(n, dono, local, x, desc, t, ace, p, ap, pistas,g, tm);
        this.tamanho=tam;
    }
    
    public Micro_Cache(Micro_Cache a){
        super(a);
        this.tamanho=a.getTamanho();
    }
    
    //gets
    public double getTamanho(){
        return this.tamanho;
    }

    
    //sets
    public void setTamanho(double tam){
        this.tamanho=tam;
    }
 
    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || (o.getClass()!=this.getClass()))return false;
        Micro_Cache a = (Micro_Cache) o;
        return super.equals(a);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("Tamanho: "+this.tamanho+"\n");
        return s.toString();
    }
    

    public Micro_Cache clone(){
        return new Micro_Cache(this);
    }

    
    public  void calculaPontuacao(){
        double fator=0;
        double pontua=0;
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
        if(this.tamanho<=25){pontua+=10;fator=150;}
        if((this.tamanho>25) && (this.tamanho<=50)){pontua+=7;fator=100;}
        if((this.tamanho>50) && (this.tamanho<=80)){pontua+=5;fator=80;}
        if(this.tamanho>80){pontua+=3;fator=50;}
        
        pont=pontua;
        this.setPontuacao(pontua *4 +fator);
        
    }
    
        public  void calculaDificuldade(){
        this.setDificuldade(pont/10);
    
    }
}

