import static java.lang.System.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Ler implements Serializable
{
    public static String texto(){
        boolean falha = true;
        String s = new String();
        Scanner leitor = new Scanner(in);
        do{
            try{
                s = leitor.nextLine();
                falha = false;
            }
            catch(InputMismatchException e){
                out.println("\nFormato de texto invalido!");
                out.print("Introduza texto simples: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return s;
    }
    
    public static int inteiro(){
        boolean falha = true;
        int n = 0;
        Scanner leitor = new Scanner(in);
        do{
            try{
                n = leitor.nextInt();
                falha = false;
            }
            catch(InputMismatchException e){
                out.println("\nFormato de inteiro invalido!");
                out.print("Introduza um numero inteiro: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return n;
    }
    
    public static double real(){
        boolean falha = true;
        double x = 0.0;
        Scanner leitor = new Scanner(in);
        do{
            try{
                x = leitor.nextDouble();
                falha = false;
            }
            catch(InputMismatchException e){
                out.println("\nFormato de real invalido!");
                out.print("Introduza um numero real: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return x;
    }
    
    public static double latitude(){
        boolean falha = true;
        double lat = 0.0;
        Scanner leitor = new Scanner(in);
        do{
            try{
                lat = leitor.nextDouble();
                if(lat <= 90.0 && lat >= -90.0)
                    falha = false;
                else{ 
                    out.println("Valor de latitude invalido!");
                    out.println("A latitude tera que estar compreendida entre -90 e 90 graus.");
                    out.print("Tente novamente. Introduza a latitude: ");
                }
            }
            catch(InputMismatchException e){
                out.println("Formato de latitude invalido!");
                out.print("A latitude apresenta-se num numero real. Introduza a latitude: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return lat;
    }
    
    public static double longitude(){
        boolean falha = true;
        double lon = 0.0;
        Scanner leitor = new Scanner(in);
        do{
            try{
                lon = leitor.nextDouble();
                if(lon <= 180.0 && lon >= -180.0)
                    falha = false;
                else{ 
                    out.println("Valor de longitude invalido!");
                    out.println("A longitude tera que estar compreendida entre -180 e 180 graus.");
                    out.print("Tente novamente. Introduza a longitude: ");
                }
            }
            catch(InputMismatchException e){
                out.println("Formato de longitude invalido!");
                out.print("A longitude apresenta-se num numero real. Introduza a longitude: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return lon;
    }
    
    public static String simNao(){
        boolean falha = true;
        Scanner leitor = new Scanner(in);
        String sn = new String();
        do{
            try{
                sn = leitor.next();
                if(sn.equals("S") || sn.equals("s") || sn.equals("N") || sn.equals("n"))
                    falha = false;
                else{
                    out.println("Formato de resposta fechada invalido!");
                    out.print("Responda a questao. (s/n)");
                }
            }catch(InputMismatchException e){
                out.println("\nFormato de caracter invalido!");
                out.print("Introduza um caracter simples: ");
                leitor.nextLine();
            }
        }while(falha);
        leitor.close();
        return sn;
    }
}
