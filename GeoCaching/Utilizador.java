import java.util.*;
import java.io.*;

public class Utilizador implements Serializable
{
    //variaveis de instancia
    private String nome, userName, password;
    private GregorianCalendar dataNasc;
    private int idade;
    private boolean parabens;
    private ArrayList<String> amigos, amigosIn, amigosOut;
    private ArrayList<GPS> cachesCriadas;
    private ArrayList<CacheAtiva> cachesAtivas;
    private ArrayList<CacheEncontrada> cachesEncontradas;
    private GPS coordenadasUtilizador;
    private double pontosAno, pontosMes, pontosSempre;
    private TreeMap<GregorianCalendar, RegistoAtividade> regAtividades;
    private GregorianCalendar ultimoLogUtilizador;
    
    //construtores
    public Utilizador(){
        this.nome = "N/A";
        this.userName = "N/A";
        this.password = "N/A";
        this.dataNasc = new GregorianCalendar();
        this.idade = 0;
        this.parabens = false;
        this.amigos = new ArrayList<String>();
        this.amigosIn = new ArrayList<String>();
        this.amigosOut = new ArrayList<String>();
        this.coordenadasUtilizador = new GPS();
        this.pontosAno = 0.0;
        this.pontosMes = 0.0;
        this.pontosSempre = 0.0;
        this.cachesEncontradas = new ArrayList<CacheEncontrada>();
        this.cachesCriadas = new ArrayList<GPS>();
        this.cachesAtivas = new ArrayList<CacheAtiva>();
        this.regAtividades = new TreeMap<GregorianCalendar, RegistoAtividade>(new CompareData());
        this.ultimoLogUtilizador = new GregorianCalendar();
    }
    
    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.userName = u.getUserName();
        this.password = u.getPassword();
        this.dataNasc = u.getDataNasc();
        this.idade = u.getIdade();
        this.parabens = u.getParabens();
        this.amigos = u.getAmigos();
        this.amigosIn = u.getAmigosIn();
        this.amigosOut = u.getAmigosOut();
        this.coordenadasUtilizador = u.getCoordenadasUtilizador();
        this.pontosAno = u.getPontosAno();
        this.pontosMes = u.getPontosMes();
        this.pontosSempre = u.getPontosSempre();
        this.cachesEncontradas = u.getCachesEncontradas();
        this.cachesCriadas = u.getCachesCriadas();
        this.cachesAtivas = u.getCachesAtivas();
        this.regAtividades = u.getRegistoAtividades();
        this.ultimoLogUtilizador = u.getUltimoLogUtilizador();
    }
    
    public Utilizador(String nom, String un, String pw,GregorianCalendar dn, 
    ArrayList<String> amgs, ArrayList<String> amgsIn, ArrayList<String> amgsOut, GPS coordU, 
    double ptsAno, double ptsMes, double ptsSempre, ArrayList<CacheEncontrada> cachesE, ArrayList<GPS> cachesC, 
    ArrayList<CacheAtiva> cachesA, TreeMap<GregorianCalendar, RegistoAtividade> regAtiv, GregorianCalendar ulu){
        this.nome = nom;
        this.userName = un;
        this.password = pw;
        this.dataNasc = dn;
        this.calculaIdade();
        this.parabens = false;
        this.amigos = amgs;
        this.amigosIn = amgsIn;
        this.amigosOut = amgsOut;
        this.coordenadasUtilizador = coordU;
        this.pontosAno = ptsAno;
        this.pontosMes = ptsMes;
        this.pontosSempre = ptsSempre;
        this.cachesEncontradas = cachesE;
        this.cachesCriadas = cachesC;
        this.cachesAtivas = cachesA;
        this.regAtividades = regAtiv;
        this.ultimoLogUtilizador = ulu;
    }
    
    //gets
        public String getNome(){
        return this.nome;
    }
    
   
    
    public String getUserName(){
        return this.userName;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public GregorianCalendar getDataNasc(){
        return (GregorianCalendar) this.dataNasc.clone();
    }
    
    public int getIdade(){
        return this.idade;
    }
    
    public boolean getParabens(){
        return this.parabens;
    }
    
    
    public boolean comparaPassword(String pw){
        return this.getPassword().equals(pw);
    }
    
    public ArrayList<String> getAmigos(){
        ArrayList<String> am = new ArrayList<String>();
        
        for(String e: amigos)
            am.add(e);
        return am;
    }
    
    public ArrayList<String> getAmigosIn(){
        ArrayList<String> aux = new ArrayList<String>();
        
        for(String e: amigosIn)
            aux.add(e);
        return aux;
    }
    
    public ArrayList<String> getAmigosOut(){
        ArrayList<String> q = new ArrayList<String>();
        
        for(String e: amigosOut)
            q.add(e);
        return q;
    }
    
    public GPS getCoordenadasUtilizador(){
        return this.coordenadasUtilizador;
    }
    
    public double getPontosAno(){
        return this.pontosAno;
    }
    
    public double getPontosMes(){
        return this.pontosMes;
    }
    
    public double getPontosSempre(){
        return this.pontosSempre;
    }
    
    public ArrayList<CacheEncontrada> getCachesEncontradas(){
        ArrayList<CacheEncontrada> enc = new ArrayList<CacheEncontrada>();
        
        for(CacheEncontrada e: this.cachesEncontradas)
            enc.add(e.clone());
        return enc;
    }
    
    public ArrayList<GPS> getCachesCriadas(){
        ArrayList<GPS> cria = new ArrayList<GPS>();
        
        for(GPS e: this.cachesCriadas)
            cria.add(e.clone());
        return cria;
    }
    
    public ArrayList<CacheAtiva> getCachesAtivas(){
        ArrayList<CacheAtiva> ativ = new ArrayList<CacheAtiva>();
        
        for(CacheAtiva e: this.cachesAtivas)
            ativ.add(e.clone());
        return ativ;
    }
    
    public TreeMap<GregorianCalendar, RegistoAtividade> getRegistoAtividades(){
        return this.regAtividades;
    }
    
    public GregorianCalendar getUltimoLogUtilizador(){
        if(this.ultimoLogUtilizador == null)
            this.ultimoLogUtilizador = new GregorianCalendar();
        return this.ultimoLogUtilizador;
    }
    
    //sets
     public void setNome(String nom){
        this.nome = nom;
    }
    
    public void setUserName(String un){
        this.userName = un;
    }
    
    public void setPassword(String pw){
        this.password = pw;
    }
    
    public void setDataNasc(GregorianCalendar dn){
        this.dataNasc = dn;
    }
    
    public void setParabens(boolean p){
        this.parabens = p;
    }
    

    
    public void setAmigos(ArrayList<String> amgs){
        this.amigos = amgs;
    }
    
    public void setAmigosIn(ArrayList<String> amgsIn){
        this.amigosIn = amgsIn;
    }
    
    public void setAmigosOut(ArrayList<String> amgsOut){
        this.amigosOut = amgsOut;
    }
    
    public void setCoordenadasUtilizador(GPS coordsU){
        this.coordenadasUtilizador = coordsU;
    }
    
    public void setPontosAno(double ptsAno){
        this.pontosAno = ptsAno;
    }
    
    public void setPontosMes(double ptsMes){
        this.pontosMes = ptsMes;
    }
    
    public void setPontosSempre(double ptsSempre){
        this.pontosSempre = ptsSempre;
    }
    
    public void addPontosG(double pts){
        this.pontosMes += pts;
        this.pontosAno += pts;
        this.pontosSempre += pts;
    }
    
    public void resetPontosMes(){
        this.pontosMes = 0;
    }
    
    public void resetPontosAno(){
        this.pontosMes = 0;
        this.pontosAno = 0;
    }
    
    public void resetPontosG(){
        this.pontosMes = 0;
        this.pontosAno = 0;
        this.pontosSempre = 0;
    }
    
    public void setCachesEncontradas(ArrayList<CacheEncontrada> cachesE){
        this.cachesEncontradas = cachesE;
    }
    
    public void setCachesCriadas(ArrayList<GPS> cachesC){
        this.cachesCriadas = cachesC;
    }
    
    public void setCachesAtivas(ArrayList<CacheAtiva> cachesA){
        this.cachesAtivas = cachesA;
    }
    
    public void setRegistoAtividades(TreeMap<GregorianCalendar, RegistoAtividade> regAtiv){
        this.regAtividades = regAtiv;
    }
    
    public void setUltimoLogUtilizador(){
        this.ultimoLogUtilizador = new GregorianCalendar();
    }
    
    //adds
    public void addCacheEncontrada(CacheEncontrada cacheE){
        this.cachesEncontradas.add(cacheE);
    }
    
    public void addCacheCriada(GPS cacheCoords){
        this.cachesCriadas.add(cacheCoords);
    }
    
    public void addCacheAtiva(CacheAtiva cacheA){
        this.cachesAtivas.add(cacheA);
    }
    
    public void addAtividade(RegistoAtividade ativ){
        GregorianCalendar agora = new GregorianCalendar();
        this.regAtividades.put(agora, ativ);
    }
    
    public void addAmigo(String unAmg){
        this.amigos.add(unAmg);
    }
    
    public void addAmigoIn(String unAI){
        this.amigosIn.add(unAI);
    }
    
    public void addAmigoOut(String unAO){
        this.amigosOut.add(unAO);
    }
    
    public void remAmigo(String unAmg){
        if(this.amigos.contains(unAmg))
            this.amigos.remove(unAmg);
    }
   
    public void remAmigoIn(String unAI){
        if(this.amigosIn.contains(unAI))
            this.amigosIn.remove(unAI);
    }
    
    public void remAmigoOut(String unAO){
        if(this.amigosOut.contains(unAO))
            this.amigosOut.remove(unAO);
    }
    
    public void remCacheAtiva(CacheAtiva cacheA){
        if(this.cachesAtivas.contains(cacheA))
            this.cachesAtivas.remove(cacheA);
    }
    
    public void remAtividade(GregorianCalendar d){
        if(this.regAtividades.containsValue(d))
            this.regAtividades.remove(d);
    }
    
    public  ArrayList<String> ordenaPorUserName(){
        TreeSet <String> r = new TreeSet<String>(new ComparePorUserName());
           for(String s : this.amigos){
           r.add(s);
       }
    
       Iterator<String> it = r.iterator();
       ArrayList<String> x = new ArrayList<String>();
        while (it.hasNext()) {
            x.add(it.next());
       
       }
       return x;
    }
    
    //calcula
    public void calculaIdade(){
        GregorianCalendar agora = new GregorianCalendar();
        int anoAgora=agora.get(GregorianCalendar.YEAR);
        int mesAgora=agora.get(GregorianCalendar.MONTH);
        int diaAgora=agora.get(GregorianCalendar.DAY_OF_MONTH);
        int diferenca = 0;
        if((mesAgora<dataNasc.get(Calendar.MONTH)) || (mesAgora==dataNasc.get(Calendar.MONTH) && diaAgora<dataNasc.get(Calendar.DAY_OF_MONTH)))
            diferenca=(anoAgora-this.dataNasc.get(Calendar.YEAR))-1;
        else 
            diferenca=(anoAgora-this.dataNasc.get(Calendar.YEAR));
        
        this.idade=diferenca;
        
    }
    
    //equals
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        return this.userName.equals(((Utilizador)o).getUserName());
    }
    
    //clone
    public Utilizador clone(){
        return new Utilizador(this);
    }
    
    //toString
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append("Nome: "+this.nome);
        s.append("\nNome de utilizador: "+this.getUserName());
        s.append("\nData de Nascimento: "+this.dataNasc.get(Calendar.DAY_OF_MONTH)+"/"+
            (this.dataNasc.get(Calendar.MONTH)+1)+"/"+this.dataNasc.get(Calendar.YEAR)+
            " ("+this.idade+" anos)");
        s.append("\nCoordenadas GPS: "+this.coordenadasUtilizador.toString());
        s.append("\nNumero de amigos: "+this.amigos.size());
        s.append("\nNumero de caches encontradas: "+this.cachesEncontradas.size());
        s.append("\nNumero de caches criadas: "+this.cachesCriadas.size());
        s.append("\nNumero de caches ativas: "+this.cachesAtivas.size());
        s.append("\nPontos acumulados este ano: "+this.pontosAno);
        s.append("\nPontos acumulados este mes: "+this.pontosMes);
        s.append("\nPontos acumulados desde sempre: "+this.pontosSempre+"\n\n");
        return s.toString();
    }
}
