import java.util.*;
import java.io.*;

public class CacheEncontrada implements Serializable
{
    private GPS coordenadas;
    private double tempoDemorado;
    private Clima clima;
    private double pontos;
    
    public CacheEncontrada(){
        this.coordenadas = new GPS();
        this.tempoDemorado = 0.0;
        this.clima = new Clima();
        this.pontos = 0.0;
    }
    
    public CacheEncontrada(CacheEncontrada ce){
        this.coordenadas = ce.getCoordenadasCE();
        this.tempoDemorado = ce.getTempoDemorado();
        this.clima = ce.getClimaCE();
        this.pontos = ce.getPontos();
    }
    
    public CacheEncontrada(GPS coords, double td, Clima c, double pts){
        this.coordenadas = coords;
        this.tempoDemorado = td;
        this.clima = c;
        this.pontos = pts;
    }
    
    public GPS getCoordenadasCE(){
        return this.coordenadas.clone();
    }
    
    public double getTempoDemorado(){
        return this.tempoDemorado;
    }
    
    public Clima getClimaCE(){
        return this.clima.clone();
    }
    
    public double getPontos(){
        return this.pontos;
    }
    
    public void setCoordenadasCE(GPS coords){
        this.coordenadas = coords;
    }
    
    public void setTempoDemorado(double td){
        this.tempoDemorado = td;
    }
    
    public void setClimaCE(Clima c){
        this.clima = c;
    }
    
    public void calculaTempoDemorado(GregorianCalendar inicio, GregorianCalendar fim){
        int dHor = fim.get(Calendar.HOUR_OF_DAY) - inicio.get(Calendar.HOUR_OF_DAY);
        int dMin = fim.get(Calendar.MINUTE) - inicio.get(Calendar.MINUTE);
        int dSec = fim.get(Calendar.SECOND) - inicio.get(Calendar.SECOND);
        if(fim.get(Calendar.DAY_OF_MONTH) > inicio.get(Calendar.DAY_OF_MONTH) ||
            fim.get(Calendar.MONTH) > inicio.get(Calendar.MONTH) ||
            fim.get(Calendar.YEAR) > inicio.get(Calendar.YEAR))
                this.tempoDemorado = 24*60;
        else
            this.tempoDemorado = dHor*60 + dMin + dSec/60;
    }
    
    public void calculaPontos(Cache c){
        double ptsBase = 1;
        double fatorTempo = 1;
        double fatorTemperatura = 1;
        double fatorAmbiente = 1;
        double fatorClima = 1;
        double tMedio = c.getTempoMedio();
        //vou somar 6 a temperatura, visto que pode ir ate -5 e quero que nao seja 0
        double temperatura = this.clima.getTemperatura()+6;
        String ambiente = this.clima.getClima();
        
        if(c.getClass().getSimpleName().equals("Micro_Cache"))
            ptsBase = ((Micro_Cache)c).getPontuacao();
        if(c.getClass().getSimpleName().equals("Multi_Cache"))
            ptsBase = ((Multi_Cache)c).getPontuacao();
        if(c.getClass().getSimpleName().equals("Cache_Misterio"))
            ptsBase = ((Cache_Misterio)c).getPontuacao();
        if(c.getClass().getSimpleName().equals("Cache_Evento"))
            ptsBase = ((Cache_Evento)c).getPontuacao();
        if(c.getClass().getSimpleName().equals("Cache_Virtual"))
            ptsBase = ((Cache_Virtual)c).getPontuacao();
        
        if(tMedio!=0.0)
            fatorTempo = this.tempoDemorado/tMedio;
        if(fatorTempo<0.5)
            fatorTempo = 0.5;
        if(fatorTempo>1.5)
            fatorTempo = 1.5;
        if(temperatura < 6 || temperatura > 36)
            fatorTemperatura = 1.5;
        else 
            if(temperatura < 11 || temperatura > 31)
                fatorTemperatura = 1.25;
            else
                if(temperatura < 16 || temperatura > 26)
                    fatorTemperatura = 1.1;
        
        if(ambiente.equals("Tempestade"))
            fatorAmbiente = 1.5;
        if(ambiente.equals("Chuva"))
            fatorAmbiente = 1.25;
        if(ambiente.equals("Nublado"))
            fatorAmbiente = 1.1;
        
        fatorClima = (fatorTemperatura + fatorAmbiente)/2;
        this.pontos = ptsBase*((fatorTempo + fatorClima)/2);
    }
        
    
    public String toString(){
        StringBuffer s =new StringBuffer();
        s.append("As coordenadas da cache: "+this.coordenadas+"\n");
        s.append("Demorou: "+ this.tempoDemorado+"\n");
        s.append("O clima que estava:\n"+this.clima.toString() +"\n");
        s.append("Ganhou: "+String.format("%.2f",this.pontos)+" pontos com esta cache!\n\n");
        return s.toString();
    }
    
    public CacheEncontrada clone(){
        return new CacheEncontrada(this);
    }
    
    public boolean equals(Object o){
        if(this==o) return true;
        if((o==null) || ((o.getClass())!=o.getClass())) return false;
        CacheEncontrada a =(CacheEncontrada) o;
        return this.coordenadas.equals(a.getCoordenadasCE());
    }
}
