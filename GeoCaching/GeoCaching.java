import java.io.IOException;
import java.util.*;
import java.io.*;

public class GeoCaching implements Serializable
{
    private HashMap<String, Utilizador> utilizadores;
    private HashMap<String, Administrador> administradores;
    private TreeMap<GPS, Cache> caches;
    private Clima clima;
    private GregorianCalendar ultimoLog;
    private TreeMap<GregorianCalendar, String> reportAbuse;
    
    public GeoCaching(){
        this.utilizadores = new HashMap<String, Utilizador>();
        this.administradores=new HashMap<String, Administrador>();
        this.caches = new TreeMap<GPS, Cache>(new CompareCachesCoords());
        this.clima = new Clima();
        this.reportAbuse = new TreeMap<GregorianCalendar, String>();
    }
    
    public GeoCaching(HashMap<String, Utilizador> util,HashMap<String, Administrador> adm, TreeMap<GPS, Cache> c   ,Clima a, 
            GregorianCalendar g){
        this.utilizadores.putAll(util);
        
        for(Utilizador e: util.values())
            this.utilizadores.put(e.getUserName(), e.clone());

        for(Administrador e: adm.values())
            this.administradores.put(e.getUserName(), e.clone());
            
        this.caches= new TreeMap<GPS, Cache>(new CompareCachesCoords());
        for(Cache e: c.values())
           this.caches.put(e.getCoordenadas(), e.clone());
        
        this.clima=a;
        this.ultimoLog=g;
    }
    
    public GeoCaching(GeoCaching gc){
        this.utilizadores = gc.getUtilizadores();
        this.utilizadores = gc.getUtilizadores();
        this.caches = gc.getCaches();
        this.ultimoLog = gc.getUltimoLog();
        this.reportAbuse = gc.getReportAbuse();
    }
    
    
    public HashMap<String,Administrador> getAdministradores(){
        HashMap <String,Administrador> admin = new HashMap <String,Administrador>();
        
        for(Administrador a: this.administradores.values())
            admin.put(a.getUserName(),a.clone());
            
        return admin;
    }
    
    public HashMap<String, Utilizador> getUtilizadores(){
       HashMap<String, Utilizador> utiliza  = new HashMap<String, Utilizador>();
               

       for(Utilizador e: this.utilizadores.values())
           utiliza.put(e.getUserName(), e.clone());
       
       return utiliza;
    }
    
    public TreeMap<GPS, Cache> getCaches(){
        TreeMap<GPS,Cache> aux = new TreeMap<GPS,Cache>(new CompareCachesCoords());

        for(Cache e: this.caches.values())
            aux.put(e.getCoordenadas(), e.clone());

        return aux;
    }
    
    public Clima getClimaDia(){
        return this.clima;
    }
    
    public GregorianCalendar getUltimoLog(){
        return this.ultimoLog;
    }
    
    public TreeMap<GregorianCalendar, String> getReportAbuse(){
        return this.reportAbuse;
    }
    
    public void addUtilizador(Utilizador u){
        this.utilizadores.put(u.getUserName(), u.clone());
    }
    
    public void remUtilizador(String un){
        this.utilizadores.remove(un);
    }
    
    public void remAdministrador(String un){
        this.administradores.remove(un);
    }
    
     public void remCache(GPS coord){
        this.caches.remove(coord);
    }
    
    public void addAdministrador(Administrador a){
        this.administradores.put(a.getUserName(), a.clone());
    }
    
    public void addCache(Cache c){
        this.caches.put(c.getCoordenadas(), c.clone());
    }
    
    public void addReportAbuse(GregorianCalendar d, String r){
        this.reportAbuse.put(d,r);
    }
    
    public void setUltimoLog(){
        this.ultimoLog = new GregorianCalendar();
    }
    
    public void calculaClimaDia(){
        if(!foiHoje()){
            this.clima.calculaEstacao();
            this.clima.calculaClima();
            this.clima.calculaTemperatura();
        }
    }
    
    public GeoCaching clone(){
        return new GeoCaching(this);
    }
    

    public boolean equals(Object o){
        if(this==o)return true;
        if((o==null)||(o.getClass()!=this.getClass()))return false;
        else{
            GeoCaching a = (GeoCaching) o;
        return (this.utilizadores.equals(a.getUtilizadores())&&this.caches.equals(a.getCaches()));
        }
    }
    
    public boolean foiHoje(){
       if(this.ultimoLog == null)
            return false;
        
        GregorianCalendar hoje = new GregorianCalendar();
        int diaH = hoje.get(Calendar.DAY_OF_MONTH);
        int mesH = hoje.get(Calendar.MONTH);
        int anoH = hoje.get(Calendar.YEAR);
        int diaU = this.ultimoLog.get(Calendar.DAY_OF_MONTH);
        int mesU = this.ultimoLog.get(Calendar.MONTH);
        int anoU = this.ultimoLog.get(Calendar.YEAR);
        
        return (diaH == diaU && mesH == mesU && anoH == anoU);
    }
    
    
    public  ArrayList<Utilizador> ordenaPontosMes(){
       TreeSet <Utilizador> qq = new TreeSet<Utilizador>(new ComparePontosMes());
       for(Utilizador s: this.utilizadores.values())
            qq.add(s.clone());
       ArrayList<Utilizador> x = new ArrayList<Utilizador>();
       x.addAll(qq);
       return x;
    }
    
     public  ArrayList<Utilizador> ordenaPontosAno(){
       TreeSet <Utilizador> a = new TreeSet<Utilizador>(new ComparePontosAno());
       for(Utilizador s : this.utilizadores.values()){
           a.add(s.clone());
       }
    
       ArrayList<Utilizador> x = new ArrayList<Utilizador>();
       x.addAll(a);
       return x;
    }
    
     public  ArrayList<Utilizador> ordenaPontosSempre(){
        TreeSet <Utilizador> r = new TreeSet<Utilizador>(new ComparePontosSempre());
           for(Utilizador s : this.utilizadores.values()){
           r.add(s.clone());
       }
       ArrayList<Utilizador> x = new ArrayList<Utilizador>();
       x.addAll(r);
       return x;
    }
    
    public  ArrayList<Utilizador> ordenaPorUtilizadoresUserName(){
        TreeSet <Utilizador> r = new TreeSet<Utilizador>(new ComparePorUtilizadoresUserName());
           for(Utilizador s : this.utilizadores.values()){
           r.add(s.clone());
       }
    
       Iterator<Utilizador> it = r.iterator();
       ArrayList<Utilizador> x = new ArrayList<Utilizador>();
        while (it.hasNext()) {
            x.add(it.next().clone());
       
       }
       return x;
    }
    
    public void gravaObject(String fich) throws IOException{
        try{
            ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream(fich));
            a.writeObject(this);
            a.close();
        }catch(IOException e){}
    }
    
    public GeoCaching leObject(String fich)throws IOException{
        GeoCaching g = new GeoCaching();
        try{
            ObjectInputStream a = new ObjectInputStream(new FileInputStream(fich));
            g = (GeoCaching)a.readObject();
            a.close();
        }catch(IOException e){}
        catch(ClassNotFoundException ec){}
        return g;
    }
}