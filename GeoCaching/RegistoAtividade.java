import java.io.Serializable;

public abstract class RegistoAtividade implements Serializable
{
    private Utilizador u;
    
    public RegistoAtividade(){
        this.u = new Utilizador();
    }
    
    public RegistoAtividade(Utilizador usr){
        this.u = usr;
    }
    
    public RegistoAtividade(RegistoAtividade ra){
        this.u = ra.getUtilizadorRA();
    }
    
    public Utilizador getUtilizadorRA(){
        return this.u.clone();
    }
    
    public void setUtilizadorRA(Utilizador usr){
        this.u = usr;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(this.getClass()!=o.getClass() || o == null)
            return false;
        RegistoAtividade ra = (RegistoAtividade) o;
        return this.u.equals(ra.getUtilizadorRA());
    }
    
    public String toString(){
        return "Atividade de "+this.u.getNome()+": ";
    }
    
    public abstract RegistoAtividade clone();
}
