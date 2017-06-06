
public class RegistoProcuraC extends RegistoAtividade
{
    private CacheAtiva ca;
    
    public RegistoProcuraC(){
        super();
        this.ca = new CacheAtiva();
    }
    
    public RegistoProcuraC(Utilizador usr, CacheAtiva c){
        super(usr);
        this.ca = c;
    }
    
    public RegistoProcuraC(RegistoProcuraC rpc){
        super(rpc);
        this.ca = rpc.getCacheProcuradaRPC();
    }
    
    public CacheAtiva getCacheProcuradaRPC(){
        return this.ca.clone();
    }
    
    public void setCacheProcuradaRPC(CacheAtiva c){
        this.ca = c;
    }
    
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(o == null || this.getClass() != o.getClass())
            return false;
        RegistoProcuraC rpc = (RegistoProcuraC)o;
        return (super.equals(rpc) && this.ca.equals(rpc.getCacheProcuradaRPC()));
    }
    
    public String toString(){
        StringBuffer s = new StringBuffer();
        s.append(super.toString());
        s.append(super.getUtilizadorRA().getNome()+" comecou a procurar a cache:\n"+this.ca.toString());
        return s.toString();
    }
    
    public RegistoProcuraC clone(){
        return new RegistoProcuraC(this);
    }
}
