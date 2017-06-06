import java.util.*;
import java.io.*;

public class CacheAtiva implements Serializable
{
    private GPS coordenadas;
    private GregorianCalendar dataInicio;
    private Clima clima;
    
    public CacheAtiva(){
        this.coordenadas = new GPS();
        this.dataInicio = new GregorianCalendar();
        this.clima = new Clima();
    }
    
    public CacheAtiva(CacheAtiva ca){
        this.coordenadas = ca.getCoordenadas();
        this.dataInicio = ca.getDataInicio();
        this.clima = ca.getClimaCA();
    }
    
    public CacheAtiva(GPS coords, GregorianCalendar dIni, Clima c){
        this.coordenadas = coords;
        this.dataInicio = dIni;
        this.clima = c;
    }
    
    public GPS getCoordenadas(){
        return this.coordenadas.clone();
    }
    
    public GregorianCalendar getDataInicio(){
        return (GregorianCalendar) this.dataInicio.clone();
    }
    
    public Clima getClimaCA(){
        return this.clima.clone();
    }
    
    public void setCoordenadas(GPS coords){
        this.coordenadas = coords;
    }
    
    public void setDataInicio(){
        this.dataInicio = new GregorianCalendar();
    }
    
    public void setClimaCA(Clima c){
        this.clima = c;
    }
    
    public CacheAtiva clone(){
        return new CacheAtiva(this);
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass()!=o.getClass())
            return false;
        CacheAtiva ca = (CacheAtiva) o;
        return ca.getCoordenadas().equals(this.coordenadas);
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        
        s.append("Coordenadas: "+this.coordenadas.toString());
        s.append("\nData de inicio: "+this.dataInicio.get(Calendar.YEAR)+"/"+
                                    this.dataInicio.get(Calendar.MONTH)+"/"+
                                    this.dataInicio.get(Calendar.DAY_OF_MONTH));
        s.append("\nClima registado: "+this.clima.toString());
        
        return s.toString();
    }
}
