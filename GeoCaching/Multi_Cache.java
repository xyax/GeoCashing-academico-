import java.util.*;

public class Multi_Cache extends Cache
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private int  checkPoints;
    private double pont=0.0;
    // construtores
    public Multi_Cache (){
        super();
        this.checkPoints = 0;
    }
    
    public Multi_Cache (String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p, String ap, String pistas, GregorianCalendar g,int tm ,int inter){
        super(n,dono, local, x, desc,t ,ace, p, ap, pistas, g,tm);
        this.checkPoints=inter;
    }
    
    public Multi_Cache(Multi_Cache a){
        super(a);
        this.checkPoints = a.getCheckPoints();
    }
    
    //gets
    public int getCheckPoints(){
        return this.checkPoints;
    }

    
    //sets
    public void setCheckPoints(int inter){
        this.checkPoints=inter;
    }
 
    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || (o.getClass()!=this.getClass()))return false;
        Multi_Cache a = (Multi_Cache) o;
        return super.equals(a);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append("Nº de pontos intermedios: "+this.checkPoints+"\n");
        return s.toString();
    }
    

    public Multi_Cache clone(){
        return new Multi_Cache(this);
    }

    
    public void calculaPontuacao(){
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
        if(this.checkPoints <=3 ){pontua+=3;}
        if((this.checkPoints>3) && (this.checkPoints<=6)){pontua+=5;}
        if((this.checkPoints>6) && (this.checkPoints<=10)){pontua+=7;}
        if(this.checkPoints>10){pontua+=10;}
        pont=pontua;
        this.setPontuacao(pontua *4+ this.checkPoints*20);
   
    }
    
        public  void calculaDificuldade(){
        this.setDificuldade(pont/10);
    
    }
}

