import java.util.*;
import java.io.*;

public class Clima implements Serializable
{
    private final String ceuLimpo = "Ceu limpo";
    private final String nublado = "Nublado";
    private final String chuva ="Chuva";
    private final String tempestade = "Tempestade";
    private final String inverno = "Inverno";
    private final String outono = "Outono";
    private final String primavera = "Primavera";
    private final String verao = "Verao";
    private String clima;
    private int temperatura;
    private final ArrayList<String> escolheClima = new ArrayList<String>(Arrays.asList(ceuLimpo,
            ceuLimpo, nublado, ceuLimpo, nublado, chuva, ceuLimpo, nublado, chuva, tempestade,
            nublado, chuva, tempestade, chuva, tempestade, tempestade));
    private String estacao;
    
    public Clima(){
        this.clima = "N/A";
        this.estacao = "N/A";
        this.temperatura = 0;
    }
    
    public Clima(Clima c){
        this.estacao = c.getEstacao();
        this.temperatura = c.getTemperatura();
        this.clima = c.getClima();
    }
    
    public Clima(String e, int t){
        this.estacao = e;
        this.temperatura = t;
    }
    
    public ArrayList<String> getEscolheClima(){
        return this.escolheClima;
    }
    
    public String getClima(){
        return this.clima;
    }
    
    public String getEstacao(){
        return this.estacao;
    }
    
    public int getTemperatura(){
        return this.temperatura;
    }
    
    public void setClima(String c){
        this.clima = c;
    }
    
    public void setEstacao(String e){
        this.estacao = e;
    }
    
    public void setTemperatura(int t){
        this.temperatura = t;
    }
    
    public void calculaClima(){
        Random r = new Random();
        int i = 0;
        if(this.estacao.equals(verao))
            i = r.nextInt(10);
        if(this.estacao.equals(inverno))
            i = r.nextInt(10)+6;
        if(this.estacao.equals(primavera))
            i = r.nextInt(10)+2;
        if(this.estacao.equals(outono))
            i = r.nextInt(10)+4;
        this.clima = this.escolheClima.get(i);
    }
    
    public void calculaEstacao(){
        GregorianCalendar agora = new GregorianCalendar();
        int dia = agora.get(Calendar.DAY_OF_MONTH);
        int mes = agora.get(Calendar.MONTH);
        if(mes < 2)
            this.estacao = this.inverno;
        if(mes > 2 && mes < 5)
            this.estacao = this.primavera;
        if(mes > 5 && mes < 8)
            this.estacao = this.verao;
        if(mes > 8 && mes < 11)
            this.estacao = this.outono;
        if(mes == 2)
            if(dia >= 20)
                this.estacao = this.primavera;
            else
                this.estacao = this.inverno;
        if(mes == 5)
            if(dia >= 21)
                this.estacao = this.verao;
            else
                this.estacao = this.primavera;
        if(mes == 8)
            if(dia >= 23)
                this.estacao = this.outono;
            else
                this.estacao = this.verao;
        if(mes == 11)
            if(dia >= 22)
                this.estacao = this.inverno;
            else
                this.estacao = this.outono;
    }
    
    public void calculaTemperatura(){
        Random r = new Random();
        if(this.estacao == this.inverno)
            this.temperatura = r.nextInt(15)-5;
        if(this.estacao == this.verao)
            this.temperatura = r.nextInt(15)+20;
        if(this.estacao == this.primavera)
            this.temperatura = r.nextInt(12)+10;
        if(this.estacao == this.outono)
            this.temperatura = r.nextInt(12)+5;
    }
    
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append("Ambiente: "+ this.clima + "\n");
        s.append("A temperatura é de: "+ this.temperatura + "ºC \n");
        return s.toString();
    }
    
    public Clima clone(){
        return new Clima(this);
    }
    
    public boolean equals(Object o){
    if (this==o) return true;
    if ((o==null)  || (o.getClass()!=this.getClass())) return true;
    Clima a = (Clima) o;
    return (this.clima.equals(a.getClima()) && this.estacao.equals(a.getEstacao()) && 
           this.temperatura==(a.getTemperatura()));
    }
}
