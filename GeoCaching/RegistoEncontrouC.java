
public class RegistoEncontrouC extends RegistoAtividade
{
    private CacheEncontrada ce;
    
    public RegistoEncontrouC(){
        super();
        this.ce = new CacheEncontrada();
    }
    
    public RegistoEncontrouC(Utilizador usr, CacheEncontrada c){
        super(usr);
        this.ce = c;
    }
    
    public RegistoEncontrouC(RegistoEncontrouC rec){
        super(rec);
        this.ce = rec.getCacheEncontradaREC();
    }
    
    public CacheEncontrada getCacheEncontradaREC(){
        return this.ce.clone();
    }
    
    public void setCacheEncontradaREC(CacheEncontrada c){
        this.ce = c;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        RegistoEncontrouC rec = (RegistoEncontrouC)o;
        return (super.equals(rec) && this.ce.equals(rec.getCacheEncontradaREC()));
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append(super.getUtilizadorRA().getNome()+" encontrou a cache:\n"+this.ce.toString());
        return s.toString();
    }
    
    public RegistoEncontrouC clone(){
        return new RegistoEncontrouC(this);
    }
}
