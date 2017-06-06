
public class RegistoAmizade extends RegistoAtividade
{
    private Utilizador amigo;
    
    public RegistoAmizade(){
        super();
        this.amigo = new Utilizador();
    }
    
    public RegistoAmizade(Utilizador usr, Utilizador amg){
        super(usr);
        this.amigo = amg;
    }
    
    public RegistoAmizade(RegistoAmizade ra){
        super(ra);
        this.amigo = ra.getAmigoRA();
    }
    
    public Utilizador getAmigoRA(){
        return this.amigo.clone();
    }
    
    public void setAmigoRA(Utilizador amg){
        this.amigo = amg;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        RegistoAmizade ra = (RegistoAmizade)o;
        return (super.equals(ra) && this.amigo.equals(ra.getAmigoRA()));
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append(super.getUtilizadorRA().getNome()+" e "+this.amigo.getNome()+" sao agora amigos!\n");
        
        return s.toString();
    }
    
    public RegistoAmizade clone(){
        return new RegistoAmizade(this);
    }
}
