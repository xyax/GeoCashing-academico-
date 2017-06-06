import java.util.*;
import java.io.*;

public class Administrador implements Serializable
{
    private String nome, userName, password;
    private boolean master;
    //construtores
    public Administrador(){
        this.nome ="N/A" ;
        this.userName = "N/A";
        this.password = "N/A";
        this.master=false;
    }
    
    public Administrador(Administrador u){
        this.nome = u.getNome();
        this.userName = u.getUserName();
        this.password = u.getPassword();
        this.master=u.getMaster();
    }
    
    public Administrador(String nom, String un, String pw,boolean mast ){
        this.nome = nom;
        this.userName = un;
        this.password = pw;
        this.master=mast;
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
    
    public boolean getMaster(){
        return this.master;
    }
    
    
    // sets
     public void setNome(String nom){
        this.nome = nom;
    }
    
    public void setUserName(String un){
        this.userName = un;
    }
    
    public void setPassword(String pw){
        this.password = pw;
    }
    
    public void setMaster(boolean i){
        this.master=i;
    }
    
    
    public boolean comparaPassword(String pw){
        return this.getPassword().equals(pw);
    }
    
    
    public Administrador clone(){
        return new Administrador(this);
    }
    
      public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Administrador a = (Administrador) o;
        return((this.userName.equals(((Administrador)o).getUserName())) && this.master==a.getMaster());
    }
    
     public String toString(){
        StringBuffer s = new StringBuffer();
        s.append("Nome: "+this.nome);
        s.append("\nNome de utilizador: "+this.getUserName());
        s.append("\nMaster: "+this.getMaster());

        return s.toString();
    }
}