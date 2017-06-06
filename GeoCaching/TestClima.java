
/**
 * Escreva a descrição da classe TestClima aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class TestClima
{
    public static void main(String[] args){
        Clima cl=new Clima();
        String a;
        
        cl.calculaTemperatura();
        cl.calculaEstacao();
        
        
        System.out.println(cl.toString());
    }
}
