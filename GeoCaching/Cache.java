import java.util.*;
import java.io.*;

public abstract class Cache implements Serializable
{
   
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    private double pontuacao, dificuldade;
    private GPS coordenadas;
    private String  nome, pistas, descricao , dono , local, terreno, perigo,acessibilidade,aptidoes;
    private GregorianCalendar dataCriacao;
    private int tempoMedio;

    // construtores
    public Cache (){
        this.nome = "N/A";
        this.dono = "N/A";
        this.local = "N/A";
        this.coordenadas = new GPS();
        this.descricao = "N/A";
        this.terreno="N/A";
        this.acessibilidade="N/A";
        this.perigo="N/A";
        this.aptidoes="N/A";
        this.pistas ="N/A";
        this.dataCriacao = new GregorianCalendar();
        this.tempoMedio = 0;
    }
    
    public Cache (String n, String dono, String local, GPS x , String desc, String t,
    String ace,String p,String ap,String pistas,GregorianCalendar g, int tm){
        this.nome = n;
        this.dono = dono;
        this.local = local;
        this.coordenadas = x;
        this.descricao = desc;
        this.terreno=t;
        this.acessibilidade=ace;
        this.perigo=p;
        this.aptidoes=ap;
        this.pistas = pistas;
        this.dataCriacao = g;
        this.tempoMedio = tm;
    }
    
    public Cache(Cache a){
        this.nome = a.getNome();
        this.dono = a.getDono();
        this.local = a.getLocal();
        this.coordenadas = a.getCoordenadas();
        this.descricao = a.getDescricao();
        this.terreno=a.getTerreno();
        this.acessibilidade=a.getAcess();
        this.perigo=a.getPerigo();
        this.aptidoes=a.getAptidoes();
        this.pistas = a.getPistas();
        this.dataCriacao = a.getDataCriacao();
        this.tempoMedio = a.getTempoMedio();
    }
    
    //gets
    public String getNome(){
        return this.nome;
    }
    
    
    public String getDono(){
        return this.dono;
    }
    public String getLocal(){
        return this.local;
    }
    
    public GPS getCoordenadas(){
        return this.coordenadas;
    }
    
    
    public String getDescricao(){
        return this.descricao;
    }
    
    public String getTerreno(){
        return this.terreno;
    }
    
    public String getAcess(){
        return this.acessibilidade;
    }
    
    public String getPerigo(){
        return this.perigo;
    }
    
    public String getAptidoes(){
        return this.aptidoes;
    }
    
    public String getPistas(){
        return this.pistas;
    }
    
    public double getPontuacao(){
        calculaPontuacao();
        return this.pontuacao;
    }
    
    public double getDificuldade(){
        calculaDificuldade();
        return this.dificuldade;
    }
    
    public GregorianCalendar getDataCriacao(){
        return this.dataCriacao;
    }
    
    public int getTempoMedio(){
        return this.tempoMedio;
    }
    
    //sets
    public void setNome(String n){
        this.nome = n;
    }
    
    
    public void setDono(String d){
        this.dono = d;
    }
    
    public void setLocal(String loc){
        this.local = loc;
    }
    
    public void setCoordenadas(GPS coord){
        this.coordenadas = coord;
    }
    
    
    public void setDescricao(String desc){
        this.descricao = desc;
    }
    
    public void setTerreno(String t){
        this.terreno = t;
    }
    
    public void setAcess(String a){
        this.acessibilidade = a;
    }
    
    public void setPerigo(String p){
        this.perigo = p;
    }
    
    public void setAptidoes(String ap){
        this.aptidoes = ap;
    }
    
    public void setPistas(String pis){
        this.pistas = pis;
    }
    
    public void  setPontuacao(double i){
        this.pontuacao=i;
    }
    
    public void setDificuldade(double j){
        this.dificuldade=j;
    }
    
    public void setDataCriacao(){
        this.dataCriacao = new GregorianCalendar();
    }
    
    public void setTempoMedio(int tm){
        this.tempoMedio = tm;
    }
    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || (o.getClass()!=this.getClass())) return false;
        Cache a = (Cache) o;
        return this.coordenadas.equals(a.getCoordenadas());
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        
        s.append("      ->"+this.nome+"<-");
        s.append("\nDono: "+this.dono);
        s.append("\nLocal: "+this.local);
        s.append("\n"+this.coordenadas.toString());
        s.append("\nDescrição: "+this.descricao);
        s.append("\nTerreno: "+this.terreno);
        s.append("\nAcessibilidade: "+this.acessibilidade);
        s.append("\nPerigos na zona: "+this.perigo);
        s.append("\nAptidões extras: "+this.aptidoes);
        s.append("\nPontuação base: "+String.format("%.2f",this.getPontuacao()));
        s.append("\nDificuldade base: "+this.getDificuldade());
        s.append("\nPistas: "+this.pistas);
        s.append("\nData: "+this.dataCriacao.get(Calendar.DAY_OF_MONTH)+"/"+
                (this.dataCriacao.get(Calendar.MONTH)+1)+"/"+
                this.dataCriacao.get(Calendar.YEAR));
        s.append("\nTempo medio de execucao: "+this.tempoMedio+"\n");
        
        return s.toString();
    }
    
    
    
    public abstract Cache clone();
    
    
    public abstract void calculaPontuacao();
    
    public abstract void calculaDificuldade();
    
}
