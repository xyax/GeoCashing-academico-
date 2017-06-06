import java.io.*;
import java.util.*;
import java.lang.*;
import static java.lang.System.*;
import static java.lang.Math.*;

public class Main implements Serializable
{
    public static GeoCaching gc;
    
    public Main(){
        gc = new GeoCaching();
        gc.calculaClimaDia();
        
    }
    
    public Main(GeoCaching g){
        gc = g;
    }
    
    public static void main(String args[]){
        try{
            gc=new GeoCaching();
            gc = gc.leObject("Geo.obj");
            new Main(gc);
        }catch(IOException e){new Main();}
        catch(NullPointerException e){new Main();}
        
        menuPrincipal();
        
        try{
            gc.gravaObject("Geo.obj");
        }catch(IOException e){out.println(e.getMessage());}
        
    }
    
    public static void cls(){
        for(int i=0; i<50; i++)
            out.println();
    }
    
    public static void menuPrincipal(){
        Ler l = new Ler();
        int opcao;
        Administrador a = new Administrador("MASTER", "*geomaster", "impossivel", true);
        if(!gc.getAdministradores().containsKey("*geomaster"))
            gc.addAdministrador(a);
        String confirmaSair = new String();
        boolean sair = false;
        gc.calculaClimaDia();
        Clima clm = gc.getClimaDia();
        gc.setUltimoLog();
        do{
            out.println(clm.toString());
            out.println();
            out.println("/======================\\");
            out.println("||-----Geocaching-----||");
            out.println("/======================\\");
            out.println("/| 1-> Entrar         |\\");
            out.println("\\| 2-> Registar       |/");
            out.println("/|                    |\\");
            out.println("\\| 0-> Sair           |/");
            out.println("/|                    |\\");
            out.println("\\======================/");
            out.println("\n\n--Estao registados "+gc.getUtilizadores().size()+" utilizadores.--");
            out.println("--Encontram-se "+gc.getCaches().size()+" caches ativas.--\n\n");
            out.print(" @@@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: out.print("Tem a certeza que deseja sair? (s/n): ");
                        confirmaSair = l.simNao();
                        if(confirmaSair.equals("s") || confirmaSair.equals("S"))
                            sair = true;
                        cls();
                    break;
                case 1: cls();
                        entrar();
                    break;
                case 2: cls();
                        registar();
                    break;
                default:cls();
                        out.println("!Opcao invalida!\n\n");
                    break;
            }
        }while(!sair);
    }
    
    public static void entrar(){
        Ler l = new Ler();
        String un;
        String pw;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u;
        GregorianCalendar agora = new GregorianCalendar();
        GregorianCalendar uDataNasc;
        out.print("UserName: ");
        un = l.texto();
        out.print("PassWord: ");
        pw = l.texto();
        if(usrs.containsKey(un)){
            u = usrs.get(un);
            if(u.comparaPassword(pw)){
                cls();
                GregorianCalendar ulu = u.getUltimoLogUtilizador();
                StringBuffer smin = new StringBuffer();
                int imin = ulu.get(Calendar.MINUTE);
                if(imin<10)
                    smin.append("0");
                smin.append(imin);
                out.println("Bem Vindo "+u.getNome()+"!");
                out.println("A ultima vez que entrou foi dia "+ulu.get(Calendar.YEAR)+"/"+
                            ulu.get(Calendar.MONTH)+"/"+ulu.get(Calendar.DAY_OF_MONTH)+" as "+
                            ulu.get(Calendar.HOUR_OF_DAY)+":"+smin.toString());
                u.setUltimoLogUtilizador();
                u.calculaIdade();
                uDataNasc = u.getDataNasc();
                if(uDataNasc.get(Calendar.DAY_OF_MONTH) == agora.get(Calendar.DAY_OF_MONTH) &&
                uDataNasc.get(Calendar.MONTH) == agora.get(Calendar.MONTH)){
                    u.setParabens(true);
                    out.print("\nParabéns!!! Como hoje faz "+u.getIdade()+
                    "anos, todas as caches que encontrar ate ao fim do dia valem pontos a"); 
                    out.println("dobrar ;)\nCaches felizes!");
                }else
                    u.setParabens(false);
                if(ulu.get(Calendar.YEAR) < agora.get(Calendar.YEAR)){
                    u.setPontosAno(0);
                    u.setPontosMes(0);
                }else if(ulu.get(Calendar.MONTH) < agora.get(Calendar.MONTH))
                        u.setPontosMes(0);
                gc.addUtilizador(u);
                menuUtilizador(un);
            } else{
                cls();
                out.println("!Nome de utilizador ou password errado!\n\n");
            }
        } else{
            if(gc.getAdministradores().containsKey(un)){
                cls();
                Administrador a = gc.getAdministradores().get(un);
                if(a.comparaPassword(pw))
                    menuAdministrador(un);
                else
                    out.println("!Nome de utilizador ou password errado!\n\n");
            }else{
                cls();
                out.println("!Nome de utilizador ou password errado!\n\n");
            }
        }
    }
    
    public static void registar(){
        Ler l = new Ler();
        boolean check = true;
        String nome, userName, password, passwordConfirma;
        int dia, mes, ano, diaAgora, mesAgora, anoAgora;
        double latitude, longitude;
        GregorianCalendar agora = new GregorianCalendar();
        anoAgora = agora.get(Calendar.YEAR);
        mesAgora = agora.get(Calendar.MONTH)+1;
        diaAgora = agora.get(Calendar.DAY_OF_MONTH);
        Utilizador u = new Utilizador();
        
        do{
            out.print("Nome: ");
            nome = l.texto();
            if(nome.length() < 2){
                out.println("O nome tem que ter no minimo 2 caracteres.");
                check = false;
            } else{
                check = true;
            }
        }while(!check);
        
        out.println("-Data de nascimento-");
        do{
            out.print("Ano: ");
            ano = l.inteiro();
            if(ano < anoAgora-125){
                out.println("Nao acredito que tenha mais de 124 anos...");
                check = false;
            } else{
                if(ano > anoAgora){
                    out.println("Nao e permito que pessoas do futuro se registem...");
                    check = false;
                } else{
                    check = true;
                }
            }
        }while(!check);
        do{
            out.print("Mes: ");
            mes = l.inteiro();
            if(mes < 1 || mes > 12){
                out.println("Mes invalido!");
                check = false;
            } else{
                if(ano == anoAgora && mes > mesAgora){
                    out.println("Nao e permito que pessoas do futuro se registem...");
                    check = false;
                } else{
                    check = true;
                }
            }
        }while(!check);
        do{
            out.print("Dia: ");
            dia = l.inteiro();
            if((dia<1) || (mes==2 && ((dia>28 && !agora.isLeapYear(ano)) || dia>29)) || 
            (dia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) || (dia>31)){
                out.println("Dia invalido!");
                check = false;
            } else{
                if(ano == anoAgora && mes == mesAgora && dia > diaAgora){
                    out.println("Nao e permito que pessoas do futuro se registem...");
                    check = false;
                } else{
                    check = true;
                }
            }
        }while(!check);
        
        do{
            out.print("UserName: ");
            userName = l.texto();
            if(userName.length() < 4){
                out.println("O nome de utilizador tem que ter no minimo 4 caracteres.");
                check = false;
            } else{
                if(gc.getUtilizadores().containsKey(userName) || userName.contains("*")){
                    out.println("Nome de utilizador ja existe ou invalido. Tente outro.");
                    check = false;
                } else{
                    check = true;
                }
            }
        }while(!check);
        
        do{
            do{
                out.print("PassWord: ");
                password = l.texto();
                if(password.length() < 4){
                    out.println("A password tem que ter no minimo 4 caracteres.");
                    check = false;
                } else{
                    check = true;
                }
            }while(!check);
            out.print("Confirme a password: ");
            passwordConfirma = l.texto();
            if(!password.equals(passwordConfirma)){
                out.println("Password nao confirmada com sucesso...");
                check = false;
            } else{
                out.println("Passord confirmada com sucesso!");
                check = true;
            }
        }while(!check);
        
        out.println("-Coordenadas-");
        out.print("Latitude: ");
        latitude = l.latitude();
        out.print("Longitude: ");
        longitude = l.longitude();
        
        u.setNome(nome);
        GregorianCalendar dataNasc = new GregorianCalendar(ano, mes-1, dia);
        u.setDataNasc(dataNasc);
        u.setUserName(userName);
        u.setPassword(password);
        GPS coords = new GPS(latitude, longitude);
        u.setCoordenadasUtilizador(coords);
        gc.addUtilizador(u);
        cls();
        out.println("!Utilizador "+userName+" registado com sucesso!\n\n");
    }
    
    public static void menuUtilizador(String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        do{
            out.println("---Menu do utilizador---");
            out.println("\n 1-> Procurar Caches");
            out.println(" 2-> Caches ativas");
            out.println(" 3-> Ranking");
            out.println(" 4-> Criar uma Cache");
            out.println(" 5-> Eventos!");
            out.println(" 6-> Opcoes");
            out.println(" 7-> Mais atividades dos amigos");
            out.println("\n 0-> LogOut");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                        //grava ficheiro aqui
                    break;
                case 1: cls();
                        procurarCaches(u.getCoordenadasUtilizador(), un);
                    break;
                case 2: cls();
                        cachesAtivas(un);
                    break;
                case 3: cls();
                        ranking("Mensal", un);
                    break;
                case 4: cls();
                        criarCache(un);
                    break;
                case 5: cls();
                        //eventos(un);
                    break;
                case 6: cls();
                        opcoes(un);
                    break;
                case 7: cls();
                        registoAtividades(un);
                    break;
                default:cls();
                        out.println("!Opcao invalida!\n");
                    break;
            }
        }while(!atras);
    }
    
    public static void menuAdministrador(String adm){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        HashMap<String, Administrador> admins = gc.getAdministradores();
        Administrador a = admins.get(adm);
       do{
            out.println("---Menu Administrador---");
            out.println("\n 1-> Utilizadores");
            out.println(" 2-> Caches");
            out.println(" 3-> Report abuse");
            //out.println(" 4-> Criar evento");
            out.println(" 4-> Novo Administrador");
            out.println(" 5-> Lista Administradores");
            out.println("\n 0-> LogOut");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        mAUtilizador();
                    break;
                case 2: cls();
                        mACaches();
                    break;
                case 3: cls();
                        mAReportAb();
                    break;
                case 4: cls();
                        if(a.getMaster()==true)
                            addAdmin();
                        else
                            out.println("Não tem permissões suficientes!\n\n");
                     break;
                case 5: cls();
                        if(a.getMaster()==true)
                            ListaAdmin();
                        else
                            out.println("Não tem permissões suficientes!\n\n");
                    break;
                default:cls();
                        out.println("!Opcao invalida!\n");
                    break;
            }
        }while(!atras);
    }

    public static void mAUtilizador(){
        Ler l = new Ler();
        int opcao;
        int pagina=0;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        ArrayList<String> mapKeys=new ArrayList<String>(usrs.size());
        
        for (String key : usrs.keySet()) {
            mapKeys.add(key);
        }
        
        Collections.sort(mapKeys, String.CASE_INSENSITIVE_ORDER);
        int tamanho=mapKeys.size();
        
        do{
            out.println("\nUtilizadores registados: - (pagina " + (pagina+1) + ")\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanho;i++){
                out.println(" "+(i+1)+"-> " + mapKeys.get(5*pagina+i));
            }
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(tamanho>(pagina*5)+5)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar ao Menu Administrador");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if((5*pagina)<tamanho){
                            interagirUtilizador(mapKeys.get(5*pagina));
                            atras=true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        if((5*pagina+1)<tamanho){
                            interagirUtilizador(mapKeys.get(5*pagina+1));
                            atras=true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        if((5*pagina+2)<tamanho){
                            interagirUtilizador(mapKeys.get(5*pagina+2));
                            atras=true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        if((5*pagina+3)<tamanho){
                            interagirUtilizador(mapKeys.get(5*pagina+3));
                            atras=true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        if((5*pagina+4)<tamanho){
                            interagirUtilizador(mapKeys.get(5*pagina+4));
                            atras=true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina>0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if(tamanho>(pagina*5)+5)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    

    public static void interagirUtilizador(String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            cls();
            out.println("Utilizador: " + un);
            out.println("\n 1-> Remover utilizador");
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras=true;
                     break;
                case 1: cls();
                        out.println("Tem a certeza que deseja remover o utilizador: " + un + "? (s/n)");
                        String r = l.texto();
                        if(r.equals("s")||r.equals("S")){
                            gc.remUtilizador(un);
                            cls();
                            out.println("Utilizador removido.\n\n");
                            atras=true;
                        }
                     break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
   
    
    public static void mAReportAb(){ 
        Ler l = new Ler();
        int opcao;
        int pagina=0;
        boolean atras = false;
        TreeMap<GregorianCalendar, String> rAb=gc.getReportAbuse();
        ArrayList<GregorianCalendar> calend=new ArrayList<GregorianCalendar>(rAb.size());
        
        for (GregorianCalendar key : rAb.keySet()) {
            calend.add(key);
        }
        
        Collections.sort(calend);
        int tamanho=calend.size();
        int j=0;
        GregorianCalendar aux;
        do{
            out.println("\n Reports: - (pagina " + (pagina+1) + ")\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanho;i++){
                j = 5*pagina+i;
                aux = calend.get(j);
                int dia = aux.get(Calendar.DAY_OF_MONTH);
                int mes = aux.get(Calendar.MONTH);
                int ano = aux.get(Calendar.YEAR);
                out.println(" "+(i+1)+"-> "+ano+"/"+mes+"/"+dia);
            }
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(tamanho>(pagina*5)+5)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar ao Menu Administrador");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if((5*pagina)<tamanho){
                            interagirRA(calend.get(5*pagina));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        if((5*pagina+1)<tamanho){
                            interagirRA(calend.get(5*pagina+1));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        if((5*pagina+2)<tamanho){
                            interagirRA(calend.get(5*pagina+2));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        if((5*pagina+3)<tamanho){
                            interagirRA(calend.get(5*pagina+3));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        if((5*pagina+4)<tamanho){
                            interagirRA(calend.get(5*pagina+4));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina>0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if(tamanho>(pagina*5)+5)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
  
    public static void interagirRA(GregorianCalendar gckey){
        TreeMap<GregorianCalendar, String> rAb=gc.getReportAbuse();
        HashMap<String, Utilizador> utils=gc.getUtilizadores();
        TreeMap<GPS, Cache> caches=gc.getCaches();
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        String report=rAb.get(gckey);
        do{
            cls();
            out.println("Report: " + gc);
            out.println("\n 1-> Remover cache"); 
            out.println("\n 2-> Remover utilizador"); 
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras=true;
                     break;
                case 1: cls();
                        out.println("Report: " + report);
                        out.println("Insira as coordenadas da Cache");
                        out.println("Latitude: ");
                        double lat = l.latitude();
                        out.println("Longitude: ");
                        double longi = l.longitude();
                        GPS coord=new GPS(lat,longi);
                        if(caches.containsKey(coord)){
                            gc.remCache(coord);
                            cls();
                            out.println("Cache removida.\n\n");
                            atras=true;
                        }
                        else{
                            out.println("Cache não existe.");
                            cls();
                            atras=true;
                        }
                     break;
                case 2: cls();
                        out.println("Report: " + report);
                        out.println("Insira o userName do utilizador");
                        String n = l.texto();
                        if(utils.containsKey(n)){
                            gc.remUtilizador(n);
                            cls();
                            out.println("Utilizador removido.\n\n");
                            atras=true;
                        }
                        else{
                            out.println("Utilizador não existe.");
                            cls();
                            atras=true;
                        }
                     break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void addAdmin(){
        Ler l = new Ler();
        boolean master=false;
        String nome,userName,password,passwordConfirma;
        boolean check = false;
        Administrador a=new Administrador();
        
        do{
            out.print("Nome: ");
            nome = l.texto();
            if(nome.length() < 2){
                out.println("O nome tem que ter no minimo 2 caracteres.");
                check = false;
            }
            else check = true;
        }while(!check);
        
        do{
            out.print("UserName: ");
            userName = l.texto();
            if(userName.length() < 4){
                out.println("O nome de utilizador tem que ter no minimo 4 caracteres.");
                check = false;
            } else{
                if(gc.getAdministradores().containsKey(userName) || !userName.contains("*")){
                    out.println("Nome de administrador ja existe ou invalido. Tente outro.");
                    check = false;
                } else{
                    check = true;
                }
            }
        }while(!check);
        
        do{
            do{
                out.print("PassWord: ");
                password = l.texto();
                if(password.length() < 4){
                    out.println("A password tem que ter no minimo 4 caracteres.");
                    check = false;
                } else{
                    check = true;
                }
            }while(!check);
            out.print("Confirme a password: ");
            passwordConfirma = l.texto();
            if(!password.equals(passwordConfirma)){
                out.println("Password nao confirmada com sucesso...");
                check = false;
            } else{
                out.println("Passord confirmada com sucesso!");
                check = true;
            }
        }while(!check);
        
        do{
            out.print("É master? (s/n) ");
            String r = l.texto();
            if(r.equals("s")||r.equals("S")){
                master=true;
                check = true;
            }
            
            if(r.equals("n")||r.equals("N")){
                master=false;
                check = true;
            }
        }while(!check);
        
        a.setNome(nome);
        a.setUserName(userName);
        a.setPassword(password);
        a.setMaster(master);
        gc.addAdministrador(a);
        cls();
        out.println("!Administrador "+userName+" registado com sucesso!\n\n");
    }
    
    public static void ListaAdmin(){
        Ler l = new Ler();
        int opcao;
        int pagina=0;
        boolean atras = false;
        HashMap<String, Administrador> admins = gc.getAdministradores();
        ArrayList<String> mapKeys=new ArrayList<String>(admins.size());
        
        for (String key : admins.keySet()) {
            mapKeys.add(key);
        }
        
        Collections.sort(mapKeys, String.CASE_INSENSITIVE_ORDER);
        int tamanho=mapKeys.size();
        
        do{
            out.println("\nAdministradores registados: - (pagina " + (pagina+1) + ")\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanho;i++){
                out.println(" "+(i+1)+"-> " + mapKeys.get(5*pagina+i));
            }
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(tamanho>(pagina*5)+5)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar ao Menu Anterior");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if((5*pagina)<tamanho){
                            interagirAdministrador(mapKeys.get(5*pagina));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        if((5*pagina+1)<tamanho){
                            interagirAdministrador(mapKeys.get(5*pagina+1));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        if((5*pagina+2)<tamanho){
                            interagirAdministrador(mapKeys.get(5*pagina+2));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        if((5*pagina+3)<tamanho){
                            interagirAdministrador(mapKeys.get(5*pagina+3));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        if((5*pagina+4)<tamanho){
                            interagirAdministrador(mapKeys.get(5*pagina+4));
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina>0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if(tamanho>(pagina*5)+5)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
        
    public static void interagirAdministrador(String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            cls();
            out.println("Administrador: " + un);
            out.println("\n 1-> Remover Administrador");
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras=true;
                     break;
                case 1: cls(); 
                        out.println("Tem a certeza que deseja remover o Administrador: " + un + "? (s/n)");
                        String r = l.texto();
                        if(r.equals("s")||r.equals("S")){
                            gc.remAdministrador(un);
                            cls();
                            out.println("Administrador removido.\n\n");
                            atras=true;
                        }
                     break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void mACaches(){
        Ler l = new Ler();
        int opcao;
        int pagina=0;
        boolean atras = false;
        TreeMap<GPS, Cache> caches = gc.getCaches();
        ArrayList<Cache> listaCaches = new ArrayList<Cache>(); //decidir como ordenar
        
        for(Cache c:caches.values())
            listaCaches.add(c.clone());
        
        int tamanho=listaCaches.size();
        
        do{
            out.println("\nCaches: - (pagina " + (pagina+1) + ")\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanho;i++){
                out.println(" "+(i+1)+"-> " + listaCaches.get(5*pagina+i).getCoordenadas());
            }
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(tamanho>(pagina*5)+5)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar ao Menu Administrador");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if((5*pagina)<tamanho){
                            interagirMACache(listaCaches.get(5*pagina).getCoordenadas());
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        if((5*pagina+1)<tamanho){
                            interagirMACache(listaCaches.get(5*pagina+1).getCoordenadas());
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        if((5*pagina+2)<tamanho){
                            interagirMACache(listaCaches.get(5*pagina+2).getCoordenadas());
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        if((5*pagina+3)<tamanho){
                            interagirMACache(listaCaches.get(5*pagina+3).getCoordenadas());
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        if((5*pagina+4)<tamanho){
                            interagirMACache(listaCaches.get(5*pagina+4).getCoordenadas());
                            atras = true;
                        }
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina>0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if(tamanho>(pagina*5)+5)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void interagirMACache(GPS coord){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            cls();
            out.println("Coordenada: " + coord);
            out.println("\n 1-> Remover Cache");
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras=true;
                     break;
                case 1: cls();
                        out.println("Tem a certeza que deseja remover a cache: " + coord + "? (s/n)");
                        String r = l.texto();
                        if(r.equals("s")||r.equals("S")){
                            gc.remCache(coord);
                            cls();
                            out.println("Cache removida.\n\n");
                            atras=true;
                        }
                     break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void procurarCaches(GPS posicaoUtilizador, String un){
        boolean atras = false;
        Ler l = new Ler();
        int opcao;
        int pagina = 0;
        TreeMap<GPS, Cache> caches = gc.getCaches();
        ArrayList<Cache> cachesProximas = new ArrayList<Cache>();
        int numeroCaches = gc.getCaches().size();
        double normaMin;
        double normaAux, lat, lon;
        if(!caches.isEmpty()){
            Cache cacheAux = caches.get(caches.lastKey());
        
            while(cachesProximas.size() < numeroCaches){
                normaMin = Double.MAX_VALUE;
                for(GPS c: caches.keySet()){
                    if(!cachesProximas.contains(caches.get(c))){
                        normaAux = normaGPS(posicaoUtilizador, c);
                        if(normaMin > normaAux){
                            normaMin = normaAux;
                            cacheAux = caches.get(c);
                        }
                    }
                }
                cachesProximas.add(cacheAux);
            }
        }
        int tamanhoCP = cachesProximas.size();
                        
        do{
            out.println("\n-Caches mais proximas das coordenadas: "
                        +posicaoUtilizador.getLat()+", "
                        +posicaoUtilizador.getLongi()+"- (pagina "+(pagina+1)+")\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanhoCP; i++)
                out.println(" "+(i+1)+"-> "+cachesProximas.get(5*pagina+i).getNome());
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1)) < tamanhoCP)
                out.println(" 7-> Proxima pagina");
            out.println(" 8-> Procurar cache por nome");
            out.println(" 9-> Procurar cache por coordenadas");
            out.println("\n\n 0-> Voltar ao menu de utilizador");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if((5*pagina)<tamanhoCP)
                            interagirCache(cachesProximas.get(5*pagina), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        if((5*pagina+1)<tamanhoCP)
                            interagirCache(cachesProximas.get(5*pagina+1), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        if((5*pagina+2)<tamanhoCP)
                            interagirCache(cachesProximas.get(5*pagina+2), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        if((5*pagina+3)<tamanhoCP)
                            interagirCache(cachesProximas.get(5*pagina+3), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        if((5*pagina+4)<tamanhoCP)
                            interagirCache(cachesProximas.get(5*pagina+4), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina>0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoCP)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 8: cls();
                        procurarCacheNome(un);
                    break;
                case 9: cls();
                        out.println("Insira as coordenadas desejadas.");
                        out.print("Latitude: ");
                        lat = l.latitude();
                        out.print("Longitude: ");
                        lon = l.longitude();
                        GPS centro = new GPS(lat, lon);
                        cls();
                        procurarCaches(centro, un);
                        atras = true;
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void interagirCache(Cache c, String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            Utilizador u = gc.getUtilizadores().get(un);
            cls();
            out.println(c.toString());
            out.println(" 1-> Ativar cache");
            out.println(" 2-> Reportar anomalia cache");
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            GregorianCalendar agora = new GregorianCalendar();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        GPS coords = c.getCoordenadas();
                        Clima clm = gc.getClimaDia();
                        CacheAtiva ca = new CacheAtiva(coords, agora, clm);
                        CacheEncontrada ce = new CacheEncontrada();
                        ce.setCoordenadasCE(coords);
                        if(u.getCachesAtivas().contains(ca) || u.getCachesEncontradas().contains(ce)){
                            out.println("A cache selecionada ja se encontra ativa ou encontrada!");
                            atras = true;
                        } else{
                            u.addCacheAtiva(ca);
                            out.println("Cache "+c.getNome()+" ativada.");
                            out.println("Comece a procurar em: "+coords.toString());
                            RegistoProcuraC rpc = new RegistoProcuraC(u, ca);
                            u.addAtividade(rpc);
                            gc.addUtilizador(u);
                            atras = true;
                        }
                    break;
                case 2: cls();
                        out.println("Explique sucintamente qual a anomalia com a cache "+c.getNome());
                        String report = new String();
                        report = l.texto();
                        cls();
                        gc.addReportAbuse(agora, un+"___"+report+"___"+c.getCoordenadas().toString());
                        atras = true;
                        out.println("Anomalia reportada!");
                        out.println("Os administradores irao rever a situacao o mais cedo possivel. Obrigado pela colaboracao!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static double normaGPS(GPS c1,GPS c2){
        GPS vetor = new GPS(c2.getLat()-c1.getLat(), c2.getLongi()-c1.getLongi());
        return sqrt(pow(vetor.getLat(),2) + pow(vetor.getLongi(),2));
    }
    
    public static void procurarCacheNome(String un){
        Ler l = new Ler();
        boolean atras = false;
        ArrayList<Cache> caches = new ArrayList<Cache>();
        ArrayList<Cache> match = new ArrayList<Cache>();
        int tamanhoM = 0;
        int pagina = 0;
        int opcao = -1;
        int j = 0;
        String nome;
        
        caches.addAll(gc.getCaches().values());
        out.print("Introduza o nome da cahce: ");
        nome = l.texto();
        for(Cache c: caches)
            if(c.getNome().toUpperCase().contains(nome.toUpperCase()) ||
            nome.toUpperCase().contains(c.getNome().toUpperCase()))
                match.add(c);
        tamanhoM = match.size();
        
        cls();
        if(tamanhoM==0)
            out.println("!Nenhuma cache encontrada com esse nome!");
        else{
            do{
                out.println("\n-Resultados de pesquisa por cache de nome: \""+nome+
                            "\"- (pagina"+(pagina+1)+")\n");
                for(int i=0; i<tamanhoM && i<5; i++)
                    out.println(" "+(i+1)+"-> "+match.get(5*pagina+i).getNome());
                out.println("\n================================");
                if(pagina > 0)
                    out.println(" 6-> Pagina anterior");
                if((5*(pagina+1)) < tamanhoM)
                    out.println(" 7-> Proxima pagina");
                out.println("\n 0-> Voltar atras");
                out.print("\n\n@@ Introduza a sua opcao: ");
                opcao = l.inteiro();
                switch(opcao){
                    case 0: cls();
                            atras = true;
                        break;
                    case 1: cls();
                            j= 5*pagina;
                            if(j<tamanhoM)
                                interagirCache(match.get(j), un);
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 2: cls();
                            j= 5*pagina+1;
                            if(j<tamanhoM)
                                interagirCache(match.get(j), un);
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 3: cls();
                            j= 5*pagina+2;
                            if(j<tamanhoM)
                                interagirCache(match.get(j), un);
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 4: cls();
                            j= 5*pagina+3;
                            if(j<tamanhoM)
                                interagirCache(match.get(j), un);
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 5: cls();
                            j= 5*pagina+4;
                            if(j<tamanhoM)
                                interagirCache(match.get(j), un);
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 6: cls();
                            if(pagina > 0)
                                pagina--;
                            else
                                out.println("!Opcao invalida!");
                        break;
                    case 7: cls();
                            if((5*(pagina+1)) < tamanhoM)
                                pagina++;
                            else
                                out.println("!Opcao invalida!");
                        break;
                    default:cls();
                            out.println("!Opcao invalida!");
                        break;
                }
            }while(!atras);
        }
    }
    
    public static void cachesAtivas(String un){
        Ler l = new Ler();
        int opcao = -1;
        
        TreeMap<GPS, Cache> caches = gc.getCaches();
        int pagina = 0;
        int j = -1;
        boolean atras = false;
        do{
            Utilizador u = gc.getUtilizadores().get(un);
            ArrayList<CacheAtiva> ca = u.getCachesAtivas();
            int tamanhoCA = ca.size();
            out.println("\n-Caches Ativas!- (pagina"+(pagina+1)+")\n");
            for(int i=0; i<5 && 5*pagina+i<tamanhoCA; i++)
                out.println(" "+(i+1)+"-> "+caches.get(ca.get(5*pagina+i).getCoordenadas()).getNome());
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoCA)
                out.println(" 7-> Proxima pagina");
            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoCA)
                            interagirCacheAtiva(ca.get(j), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoCA)
                            interagirCacheAtiva(ca.get(j), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoCA)
                            interagirCacheAtiva(ca.get(j), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoCA)
                            interagirCacheAtiva(ca.get(j), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoCA)
                            interagirCacheAtiva(ca.get(j), un);
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoCA)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void interagirCacheAtiva(CacheAtiva ca, String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            cls();
            out.println(ca.toString());
            out.println("\n 1-> Encontrei esta cache!!!");
            out.println(" 2-> Reportar anomalia cache");
            out.println(" 0-> Voltar atras");
            out.print("\n @@ Introduza a sua opcao: ");
            opcao = l.inteiro();
            GregorianCalendar agora = new GregorianCalendar();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        encontrouCache(ca, un);
                        atras = true;
                    break;
                case 2: cls();
                        out.println("Explique sucintamente qual a anomalia com a cache "+
                                    gc.getCaches().get(ca.getCoordenadas()).getNome());
                        String report = new String();
                        report = l.texto();
                        gc.addReportAbuse(agora, un+"___"+report+"___cache___"+ca.getCoordenadas().toString());
                        out.println("Anomalia reportada!");
                        out.println("Os administradores irao rever a situacao o mais cedo possivel. Obrigado pela colaboracao!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void encontrouCache(CacheAtiva ca, String un){
        CacheEncontrada ce = new CacheEncontrada();
        GregorianCalendar agora = new GregorianCalendar();
        GPS coords = ca.getCoordenadas();
        Cache c = gc.getCaches().get(coords);
        Utilizador u = gc.getUtilizadores().get(un);
        double pts = 0;
        
        ce.setClimaCE(ca.getClimaCA());
        ce.setCoordenadasCE(coords);
        ce.calculaTempoDemorado(ca.getDataInicio(), agora);
        ce.calculaPontos(c);
        u.addCacheEncontrada(ce);
        u.remCacheAtiva(ca);
        pts = ce.getPontos();
        if(u.getParabens())
            pts *= 2;
        u.addPontosG(pts);
        RegistoEncontrouC rec = new RegistoEncontrouC(u, ce);
        u.addAtividade(rec);
        gc.addUtilizador(u);
        
        out.println("Parabens "+u.getNome()+" acabou de encontrar a cache \""+c.getNome()+"\"");
        out.println("Com o feito acabou de adicionar "+pts+ "a sua conta pessoal!\n");
    }
    
    public static void ranking(String periodo, String un){
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        int tamanhoUO;
        int pagina = 0;
        double pts = 0;
        int j = 0;
        do{
            ArrayList<Utilizador> uOrd = new ArrayList<Utilizador>();
            if(periodo.equals("Mensal"))
                uOrd = gc.ordenaPontosMes();
            if(periodo.equals("Anual"))
                uOrd = gc.ordenaPontosAno();
            if(periodo.equals("Todos os Tempos"))
                uOrd = gc.ordenaPontosSempre();
            for(Utilizador o: uOrd)
                out.println(o.getNome());
            tamanhoUO = uOrd.size();
            out.println("\n-Ranking "+periodo+"-");
            for(int i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
                j = 5*pagina+i;
                Utilizador uo = uOrd.get(j);
                if(periodo.equals("Mensal")) pts = uo.getPontosMes();
                if(periodo.equals("Anual")) pts = uo.getPontosAno();
                if(periodo.equals("Todos os Tempos")) pts = uo.getPontosSempre();
                out.println(" "+(i+1)+"-> "+uo.getNome()+" ("+String.format("%.2f",pts)+" pontos)");
            }
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");
            if(periodo.equals("Mensal")){
                out.println(" 8-> Ranking Anual");
                out.println(" 9-> Ranking de Todos os Tempos");
            }
            if(periodo.equals("Anual")){
                out.println(" 8-> Mensal");
                out.println(" 9-> Ranking de Todos os Tempos");
            }
            if(periodo.equals("Todos os Tempos")){
                out.println(" 8-> Mensal");
                out.println(" 9-> Ranking Anual");
            }
            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.simNao();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.addAmigo(un);
                                    uo.remAmigoOut(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    GregorianCalendar agora = new GregorianCalendar();
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uo.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                    interagirNaoAmigo(un, unuo);
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.simNao();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.addAmigo(un);
                                    uo.remAmigoOut(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    GregorianCalendar agora = new GregorianCalendar();
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uo.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                    interagirNaoAmigo(un, unuo);
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.simNao();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.addAmigo(un);
                                    uo.remAmigoOut(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    GregorianCalendar agora = new GregorianCalendar();
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uo.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                    interagirNaoAmigo(un, unuo);
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.simNao();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.addAmigo(un);
                                    uo.remAmigoOut(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    GregorianCalendar agora = new GregorianCalendar();
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uo.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                    interagirNaoAmigo(un, unuo);
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.simNao();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.addAmigo(un);
                                    uo.remAmigoOut(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    GregorianCalendar agora = new GregorianCalendar();
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uo.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                    interagirNaoAmigo(un, unuo);
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 8: cls();
                        if(periodo.equals("Mensal"))
                            ranking("Anual", un);
                        else
                            ranking("Mensal", un);
                        atras = true;
                    break;
                case 9: cls();
                        if(periodo.equals("Todos os Tempos"))
                            ranking("Anual", un);
                        else
                            ranking("Todos os Tempos", un);
                        atras = true;
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void interagirAmigo(String un, String una){
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        Utilizador ua = usrs.get(una);
        do{
            out.println(ua.toString());
            out.println("\n 1-> Atividades de "+ua.getNome());
            out.println(" 2-> Remover "+ua.getNome()+" da sua lista de cachers amigos");
            out.println(" 3-> Reportar comportamento improprio ou abusivo de "+ua.getNome());
            out.println("\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        registoAtividadesAmg(una);
                    break;
                case 2: cls();
                        u.remAmigo(una);
                        ua.remAmigo(un);
                        gc.addUtilizador(u);
                        gc.addUtilizador(ua);
                        out.println(ua.getNome()+" removido com sucesso da sua lista de cachers amigos!");
                    break;
                case 3: cls();
                        out.println("Explique sucintamente qual foi o comportamento improprio ou abusivo do utilizador "+
                                ua.getNome());
                        String report = new String();
                        GregorianCalendar agora = new GregorianCalendar();
                        report = l.texto();
                        gc.addReportAbuse(agora, un+"___"+report+"___user___"+una);
                        out.println("Anomalia reportada!");
                        out.println("Os administradores irao rever a situacao o mais cedo possivel. Obrigado pela colaboracao!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void registoAtividades(String un){
        Ler l = new Ler();
        boolean atras = false;
        Utilizador u = gc.getUtilizadores().get(un);
        int pagina = 0;
        int opcao = -1;
        String sn = new String();
        do{
            TreeMap<GregorianCalendar, RegistoAtividade> ra = u.getRegistoAtividades();
            Set<GregorianCalendar> keys = ra.keySet();
            ArrayList<GregorianCalendar> chaves = new ArrayList<GregorianCalendar>();
            chaves.addAll(keys);
            int tamanhoRA = ra.size();
            out.println("-Registo de atividades de "+u.getNome()+"-\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanhoRA; i++){
                GregorianCalendar k = chaves.get(5*pagina+i);
                int dia = k.get(Calendar.DAY_OF_MONTH);
                int mes = k.get(Calendar.MONTH);
                int ano = k.get(Calendar.YEAR);
                int hora = k.get(Calendar.HOUR_OF_DAY);
                int min = k.get(Calendar.MINUTE);
                int seg = k.get(Calendar.SECOND);
                out.println((i+1)+"-> ("+ano+"/"+mes+"/"+dia+" "+hora+"h"+min+"m"+seg+"s): "
                        +ra.get(k).toString());
            }
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(5*(pagina+1)<tamanhoRA)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: out.println("Tem a certeza que deseja apagar este registo? (s/n)");
                        sn = l.simNao();
                        cls();
                        if(sn.equals("S") || sn.equals("s")){
                            u.remAtividade(chaves.get(5*pagina));
                            out.println("Atividade removida com sucesso!");
                            gc.addUtilizador(u);
                        }
                    break;
                case 2: if(5*pagina+1<tamanhoRA){
                            out.println("Tem a certeza que deseja apagar este registo? (s/n)");
                            sn = l.simNao();
                            cls();
                            if(sn.equals("S") || sn.equals("s")){
                                u.remAtividade(chaves.get(5*pagina+1));
                                out.println("Atividade removida com sucesso!");
                                gc.addUtilizador(u);
                            }
                        }else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 3: if(5*pagina+2<tamanhoRA){
                            out.println("Tem a certeza que deseja apagar este registo? (s/n)");
                            sn = l.simNao();
                            cls();
                            if(sn.equals("S") || sn.equals("s")){
                                u.remAtividade(chaves.get(5*pagina+2));
                                out.println("Atividade removida com sucesso!");
                                gc.addUtilizador(u);
                            }
                        }else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 4: if(5*pagina+3<tamanhoRA){
                            out.println("Tem a certeza que deseja apagar este registo? (s/n)");
                            sn = l.simNao();
                            cls();
                            if(sn.equals("S") || sn.equals("s")){
                                u.remAtividade(chaves.get(5*pagina+3));
                                out.println("Atividade removida com sucesso!");
                                gc.addUtilizador(u);
                            }
                        }else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 5: if(5*pagina+4<tamanhoRA){
                            out.println("Tem a certeza que deseja apagar este registo? (s/n)");
                            sn = l.simNao();
                            cls();
                            if(sn.equals("S") || sn.equals("s")){
                                u.remAtividade(chaves.get(5*pagina+4));
                                out.println("Atividade removida com sucesso!");
                                gc.addUtilizador(u);
                            }
                        }else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 6: if(pagina>0)
                            pagina--;
                        else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 7: if(5*(pagina+1)<tamanhoRA)
                            pagina++;
                        else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void registoAtividadesAmg(String una){
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        int pagina = 0;
        int j = 0;
        String raClass = new String();
        Utilizador ua = gc.getUtilizadores().get(una);
        do{
            TreeMap<GregorianCalendar, RegistoAtividade> ra = ua.getRegistoAtividades();
            Set<GregorianCalendar> keys = ra.keySet();
            ArrayList<GregorianCalendar> chaves = new ArrayList<GregorianCalendar>();
            chaves.addAll(keys);
            int tamanhoRA = ra.size();
            out.println("-Registo de atividades de "+ua.getNome()+"-\n");
            for(int i=0; i<5 && (5*pagina+i)<tamanhoRA; i++){
                GregorianCalendar k = chaves.get(5*pagina+i);
                int dia = k.get(Calendar.DAY_OF_MONTH);
                int mes = k.get(Calendar.MONTH);
                int ano = k.get(Calendar.YEAR);
                int hora = k.get(Calendar.HOUR_OF_DAY);
                int min = k.get(Calendar.MINUTE);
                int seg = k.get(Calendar.SECOND);
                out.println((i+1)+"-> ("+ano+"/"+mes+"/"+dia+" "+hora+"h"+min+"m"+seg+"s): "
                        +ra.get(k).toString());
            }
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if(5*(pagina+1)<tamanhoRA)
                out.println(" 7-> Proxima pagina");
            out.println("\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: j = 5*pagina;
                        raClass = ra.get(chaves.get(j)).getClass().getSimpleName();
                        if(raClass.equals("RegistoAmizade")){
                            RegistoAmizade ram = (RegistoAmizade)ra.get(chaves.get(j));
                            out.println("O utilizador:\n"+ram.getUtilizadorRA().toString());
                            out.println("\nE o utilizador:\n"+ram.getAmigoRA().toString()+"\nSao amigos.");
                        }
                        if(raClass.equals("RegistoEncontrouC")){
                            RegistoEncontrouC rec = (RegistoEncontrouC)ra.get(chaves.get(j));
                            out.println("O utilizador:\n"+rec.getUtilizadorRA().toString());
                            out.println("Encontrou a cache:\n"+rec.getCacheEncontradaREC().toString());
                        }
                        if(raClass.equals("RegistoProcuraC")){
                            RegistoProcuraC rpc = (RegistoProcuraC)ra.get(chaves.get(j));
                            out.println("O utilizador:\n"+rpc.getUtilizadorRA().toString());
                            out.println("Comecou a procurar a cache:\n"+rpc.getCacheProcuradaRPC().toString());
                        }
                    break;
                case 2: j = 5*pagina+1;
                        cls();
                        if(j < tamanhoRA){
                            raClass = ra.get(chaves.get(j)).getClass().getSimpleName();
                            if(raClass.equals("RegistoAmizade")){
                                RegistoAmizade ram = (RegistoAmizade)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+ram.getUtilizadorRA().toString());
                                out.println("\nE o utilizador:\n"+ram.getAmigoRA().toString()+"\nSao amigos.");
                            }
                            if(raClass.equals("RegistoEncontrouC")){
                                RegistoEncontrouC rec = (RegistoEncontrouC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rec.getUtilizadorRA().toString());
                                out.println("Encontrou a cache:\n"+rec.getCacheEncontradaREC().toString());
                            }
                            if(raClass.equals("RegistoProcuraC")){
                                RegistoProcuraC rpc = (RegistoProcuraC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rpc.getUtilizadorRA().toString());
                                out.println("Comecou a procurar a cache:\n"+rpc.getCacheProcuradaRPC().toString());
                            }
                            out.println("Enter para voltar atras");
                            l.texto();
                        } else
                            out.println("!Opcao invalida!");
                    break;
                case 3: j = 5*pagina+2;
                        cls();
                        if(j < tamanhoRA){
                            raClass = ra.get(chaves.get(j)).getClass().getSimpleName();
                            if(raClass.equals("RegistoAmizade")){
                                RegistoAmizade ram = (RegistoAmizade)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+ram.getUtilizadorRA().toString());
                                out.println("\nE o utilizador:\n"+ram.getAmigoRA().toString()+"\nSao amigos.");
                            }
                            if(raClass.equals("RegistoEncontrouC")){
                                RegistoEncontrouC rec = (RegistoEncontrouC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rec.getUtilizadorRA().toString());
                                out.println("Encontrou a cache:\n"+rec.getCacheEncontradaREC().toString());
                            }
                            if(raClass.equals("RegistoProcuraC")){
                                RegistoProcuraC rpc = (RegistoProcuraC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rpc.getUtilizadorRA().toString());
                                out.println("Comecou a procurar a cache:\n"+rpc.getCacheProcuradaRPC().toString());
                            }
                            out.println("Enter para voltar atras");
                            l.texto();
                        } else
                            out.println("!Opcao invalida!");
                    break;
                case 4: j = 5*pagina+3;
                        cls();
                        if(j < tamanhoRA){
                            raClass = ra.get(chaves.get(j)).getClass().getSimpleName();
                            if(raClass.equals("RegistoAmizade")){
                                RegistoAmizade ram = (RegistoAmizade)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+ram.getUtilizadorRA().toString());
                                out.println("\nE o utilizador:\n"+ram.getAmigoRA().toString()+"\nSao amigos.");
                            }
                            if(raClass.equals("RegistoEncontrouC")){
                                RegistoEncontrouC rec = (RegistoEncontrouC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rec.getUtilizadorRA().toString());
                                out.println("Encontrou a cache:\n"+rec.getCacheEncontradaREC().toString());
                            }
                            if(raClass.equals("RegistoProcuraC")){
                                RegistoProcuraC rpc = (RegistoProcuraC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rpc.getUtilizadorRA().toString());
                                out.println("Comecou a procurar a cache:\n"+rpc.getCacheProcuradaRPC().toString());
                            }
                            out.println("Enter para voltar atras");
                            l.texto();
                        } else
                            out.println("!Opcao invalida!");
                    break;
                case 5: j = 5*pagina+4;
                        cls();
                        if(j < tamanhoRA){
                            raClass = ra.get(chaves.get(j)).getClass().getSimpleName();
                            if(raClass.equals("RegistoAmizade")){
                                RegistoAmizade ram = (RegistoAmizade)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+ram.getUtilizadorRA().toString());
                                out.println("\nE o utilizador:\n"+ram.getAmigoRA().toString()+"\nSao amigos.");
                            }
                            if(raClass.equals("RegistoEncontrouC")){
                                RegistoEncontrouC rec = (RegistoEncontrouC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rec.getUtilizadorRA().toString());
                                out.println("Encontrou a cache:\n"+rec.getCacheEncontradaREC().toString());
                            }
                            if(raClass.equals("RegistoProcuraC")){
                                RegistoProcuraC rpc = (RegistoProcuraC)ra.get(chaves.get(j));
                                out.println("O utilizador:\n"+rpc.getUtilizadorRA().toString());
                                out.println("Comecou a procurar a cache:\n"+rpc.getCacheProcuradaRPC().toString());
                            }
                            out.println("Enter para voltar atras");
                            l.texto();
                        } else
                            out.println("!Opcao invalida!");
                    break;
                case 6: if(pagina>0)
                            pagina--;
                        else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                case 7: if(5*(pagina+1)<tamanhoRA)
                            pagina++;
                        else{
                            cls();
                            out.println("!Opcao invalida!");
                        }
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
                
                            
    
    public static void interagirNaoAmigo(String un, String una){
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        Utilizador u = gc.getUtilizadores().get(un);
        Utilizador ua = gc.getUtilizadores().get(una);
        do{
            out.println("\n-Interacao com "+ua.getNome()+"-\n");
            out.println("\n 1-> Convidar "+ua.getNome()+" para ser um cacher seu amigo!");
            out.println(" 2-> Reportar comportamento improprio ou abusivo de "+ua.getNome());
            out.println("\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        if(!(u.getAmigosOut().contains(una))){
                        u.addAmigoOut(una);
                        ua.addAmigoIn(un);
                        gc.addUtilizador(u);
                        gc.addUtilizador(ua);
                        out.println("Convite enviado com sucesso!");
                       }else{out.println("Ja convidaste "+una +" para ser seu amigo tens de esperar !!");}
                    break;
                case 2: cls();
                        out.println("Explique sucintamente qual foi o comportamento improprio ou abusivo do utilizador "+
                                ua.getNome());
                        String report = new String();
                        GregorianCalendar agora = new GregorianCalendar();
                        report = l.texto();
                        gc.addReportAbuse(agora, un+"___"+report+"___user___"+una);
                        out.println("Anomalia reportada!");
                        out.println("Os administradores irao rever a situacao o mais cedo possivel. Obrigado pela colaboracao!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void criarCache(String un){
        Ler l = new Ler();
        int opcao = -1;
        boolean check = false;
        Utilizador u = gc.getUtilizadores().get(un);
        String nome = new String();
        String dono = un;
        String local = new String();
        double lat = 0.0;
        double lon = 0.0;
        String descricao = new String();
        String terreno = new String();
        String acessibilidade = new String();
        String sn = new String();
        String perigo = new String();
        String aptidoes = new String();
        String pistas = new String();
        GregorianCalendar data = new GregorianCalendar();
        int tmpMedio = 0;
        GPS coords;
        
        out.println(" -Criar cache-\n");
        
        out.print("Insira o nome da cache: ");
        nome = l.texto();
        
        out.print("Insira o local: ");
        local = l.texto();
        
        do{
            out.println("Coordenadas:");
            out.print("Latitude: ");
            lat = l.latitude();
            out.print("Longitude: ");
            lon = l.longitude();
            coords = new GPS(lat, lon);
            if(gc.getCaches().containsKey(coords))
                out.println("!Ja existe outra cache nestas coordenadas! Tente novamente.");
            else
                check = true;
        }while(!check);
        check = false;
        
        out.print("Insira a descricao da cache: ");
        descricao = l.texto();
        
        do{
            out.println("Selecione o tipo de terreno: ");
            out.println(" 1-> Cidade");
            out.println(" 2-> Campo");
            out.println(" 3-> Montanha");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 1: terreno = "cidade";
                        check = true;
                    break;
                case 2: terreno = "campo";
                        check = true;
                    break;
                case 3: terreno = "montanha";
                        check = true;
                    break;
                default:out.println("!Opcao invalida!");
                    break;
            }
        }while(!check);
        check = false;
        
        out.println("A cache tem acessibilidade facil a cadeiras de rodas? (s/n)");
        sn = l.simNao();
        if(sn.equals("s") || sn.equals("S"))
            acessibilidade = "sim";
        else
            acessibilidade = "nao";
        
        do{
            out.println("Escolha o nivel de perigo associado a cache");
            out.println(" 1-> Baixo");
            out.println(" 2-> Medio");
            out.println(" 3-> Alto");
            out.println(" 4-> Extremo");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 1: perigo = "baixa";
                        check = true;
                    break;
                case 2: perigo = "media";
                        check = true;
                    break;
                case 3: perigo = "Alta";
                        check = true;
                    break;
                case 4: perigo = "Extremo";
                        check = true;
                    break;
                default:out.println("!Opcao invalida!");
                    break;
            }
        }while(!check);
        check = false;
        
        do{
            out.println("Escolha as aptidoes necessarias para encontrar esta cache:");
            out.println(" 1-> Nadar");
            out.println(" 2-> Escalar");
            out.println(" 3-> Nadar e escalar");
            out.println(" 4-> Nunhuma");
            opcao = l.inteiro();
            switch(opcao){
                case 1: aptidoes = "nadar";
                        check = true;
                    break;
                case 2: aptidoes = "escalar";
                        check = true;
                    break;
                case 3: aptidoes = "ambos";
                        check = true;
                    break;
                case 4: aptidoes = "nenhum";
                        check = true;
                    break;
                default:out.println("!Opcao invalida!");
                    break;
            }
        }while(!check);
        check = false;
        
        do{
            out.print("Introduza o tempo medio em minutos que domora a encontrar a cache: ");
            tmpMedio = l.inteiro();
            if(tmpMedio <=0)
                out.println("!Tempo medio inserido invalido!");
        }while(tmpMedio<=0);
        
        do{
            out.println("\n Escolha o tipo da cache:");
            out.println(" 1-> Micro-cache");
            out.println(" 2-> Multi-cache");
            out.println(" 3-> Cache misterio");
            out.println(" 4-> Cache evento");
            out.println(" 5-> Cache Virtual");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 1: out.print("Insira o tamanho em milimetros: ");
                        double mm = l.real();
                        Micro_Cache mic = new Micro_Cache(nome, dono, local, coords, descricao, 
                                    terreno, acessibilidade, perigo, aptidoes, pistas, data, 
                                    tmpMedio, mm);
                        gc.addCache(mic);
                        check = true;
                    break;
                case 2: out.print("Insira o numero de checkpoints: ");
                        int cp = l.inteiro();
                        Multi_Cache muc = new Multi_Cache(nome, dono, local, coords, descricao, 
                                    terreno, acessibilidade, perigo, aptidoes, pistas, data, 
                                    tmpMedio, cp);
                        gc.addCache(muc);
                        check = true;
                    break;
                case 3: out.print("Insira o codigo misterio: ");
                        String m = l.texto();
                        Cache_Misterio cm = new Cache_Misterio(nome, dono, local, coords, 
                                    descricao, terreno, acessibilidade, perigo, aptidoes, 
                                    pistas, data, tmpMedio, m);
                        gc.addCache(cm);
                        check = true;
                    break;
                case 4: Cache_Evento ce = new Cache_Evento(nome, dono, local, coords, descricao,
                                    terreno, acessibilidade, perigo, aptidoes, pistas, data, 
                                    tmpMedio);
                        gc.addCache(ce);
                        out.print("Nao insira nada......");
                        check = true;
                    break;
                case 5: out.print("Insira a prova necessaria para comprovar a cache: ");
                        String p = l.texto();
                        Cache_Virtual cv = new Cache_Virtual(nome, dono, local, coords, 
                                    descricao, terreno, acessibilidade, perigo, aptidoes, 
                                    pistas, data, tmpMedio, p);
                        gc.addCache(cv);
                        check = true;
                    break;
                default:out.println("!Opcao invalida!");
                    break;
            }
        }while(!check);
        
        u.addCacheCriada(coords);
        gc.addUtilizador(u);
        cls();
        out.println("Cache "+nome+" inserida com sucesso!");
    }
    public static void opcoes(String un){
        Ler l = new Ler();
        int opcao;
        boolean atras = false;
        do{
            out.println("\n 1-> Perfil ");
            out.println("\n 2-> Amigos");
            out.println("\n 3-> Procurar Geocacher ");
            out.println("\n 4-> Ver cache encontradas ");
            out.println("\n 0-> Voltar ao menu de utilizador");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                        
                    break;
                case 1: cls();
                         perfil(un); 
                    break;
                case 2: cls();
                        amigos(un);
                    break;
                case 3: cls();
                        procurarPessoal(un);
                    break;    
                case 4: cls();
                        verCacheEncontrada(un);
                    break; 
                default:cls();
                        out.println("!Opcao invalida!\n");
                    break;
            }
        }while(!atras);
    }
    
    public static void perfil(String un){
        Ler l = new Ler();
        String nome, password, passwordConfirma, passwordAntiga;
        int opcao, ano,mes,dia, anoAgora, mesAgora, diaAgora;
        double latitude, longitude;
        boolean atras = false;
        boolean check = false;
        GregorianCalendar agora = new GregorianCalendar();
        anoAgora = agora.get(Calendar.YEAR);
        mesAgora = agora.get(Calendar.MONTH)+1;
        diaAgora = agora.get(Calendar.DAY_OF_MONTH);
        Utilizador u = gc.getUtilizadores().get(un);
        do{
            out.println("---Perfil---");
            out.println("Carregue no numero para alterar o campo");
            out.println("\n 1-> nome: "+ u.getNome());
            out.println("\n 2-> Password:******");
            out.println("\n 3-> Data de Nascimento: "+u.getDataNasc().get(Calendar.DAY_OF_MONTH)+ " - "+(u.getDataNasc().get(Calendar.MONTH)+1)+" - "
                         +u.getDataNasc().get(Calendar.YEAR)+ " ( "+u.getIdade()+" )");
            out.println("\n 4->Latitude: "+ u.getCoordenadasUtilizador().getLat() + "  Longitude: "+u.getCoordenadasUtilizador().getLongi());
            out.println("\n 0-> Voltar ao menu de opcoes");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                        
                    break;
                case 1: cls();
                    do{
                       out.println("\n Insira o novo nome"); 
                       nome = l.texto();
                       if(nome.length() < 2){
                           out.println("O nome tem que ter no minimo 2 caracteres.");
                           check = false;
                           }else{
                           check = true;
                           }
                    }while(!check);
                u.setNome(nome);
                gc.addUtilizador(u); 
                    break;
                case 2: cls();
                out.print("Digite PassWord antiga: ");
                passwordAntiga=l.texto();
                 if(u.comparaPassword(passwordAntiga)){
                   do{
                         do{
                            out.print("Nova PassWord: ");
                            password = l.texto();
                            if(password.length() < 4){
                                out.println("A password tem que ter no minimo 4 caracteres.");
                                check = false;
                            } else{
                                check = true;
                                }
                         }while(!check);
                         out.print("Confirme a password: ");
                         passwordConfirma = l.texto();
                         if(!password.equals(passwordConfirma)){
                              out.println("Password nao confirmada com sucesso...");
                              check = false;
                         } else{
                             out.println("Passord confirmada com sucesso!");
                             check = true;
                          }
                   }while(!check);
                   u.setPassword(password);
                   gc.addUtilizador(u);
                 }else{
                   cls();
                   out.print("password errado!\n\n "); 
                 }
                 cls();
                   break;  
                case 3: cls();
                   out.println("-Data de nascimento-");
                   do{
                       out.print("Ano: ");
                       ano = l.inteiro();
                       if(ano < anoAgora-125){
                           out.println("Nao acredito que tenha mais de 124 anos...");
                           check = false;
                           }else{
                              if(ano > anoAgora){
                                   out.println("Nao e permito que pessoas do futuro se registem...");
                                   check = false;
                              } else{
                                   check = true;
                                   
                              }
                       }
                   }while(!check);
                   
                   do{
                      out.print("Mes: ");
                      mes = l.inteiro();
                      if(mes < 1 || mes > 12){
                          out.println("Mes invalido!");
                          check = false;
                          }else{
                              if(ano == anoAgora && mes > mesAgora){
                                  out.println("Nao e permito que pessoas do futuro se registem...");
                                  check = false;
                            } else{
                                   check = true;
                              }
                          }
                   }while(!check);
                   
                   do{
                       out.print("Dia: ");
                       dia = l.inteiro();
                       if((dia<1) || (mes==2 && ((dia>28 && !agora.isLeapYear(ano)) || dia>29)) || 
                       (dia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) || (dia>31)){
                           out.println("Dia invalido!");
                           check = false;
                      }else{
                          if(ano == anoAgora && mes == mesAgora && dia > diaAgora){
                                out.println("Nao e permito que pessoas do futuro se registem...");
                                check = false;
                          } else{
                                check = true;
                          }
                      }
                    }while(!check);
                GregorianCalendar nova = new GregorianCalendar(ano, mes-1, dia);
                u.setDataNasc(nova);
                u.calculaIdade();
                cls();
                    break;
                case 4: cls();
                       out.println("-Coordenadas-");
                       out.print("Latitude: ");
                       latitude = l.latitude();
                       out.print("Longitude: ");
                       longitude = l.longitude();
                GPS coords = new GPS(latitude, longitude);
                u.setCoordenadasUtilizador(coords);
                cls();
                    break;
                default:cls();
                        out.println("!Opcao invalida!\n");
                    break;
            }
        }while(!atras);
    }
        
        public static void amigos(String un){
         Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        ArrayList<String> uOrd = u.ordenaPorUserName();
        int tamanhoUO = uOrd.size();
        int pagina = 0;
        int j = 0;
        int i=0;
        do{
            out.println("\n Amigos ");
            for(i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
             
                 j = 5*pagina+i;   
                 out.println(" "+(i+1)+" Nome-> "+uOrd.get(j));
                }
           
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");
            out.println("\n\n 8-> Pedidos Pendentes");
            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                      
                        if(j<tamanhoUO){
                              String unuo =uOrd.get(j);
                              interagirAmigo(un, unuo);
                              atras=true;
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                       
                        if(j<tamanhoUO){
                              String unuo =uOrd.get(j);
                              interagirAmigo(un, unuo);
                              atras=true;
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        j = 5*pagina+2;

                        if(j<tamanhoUO){
                              String unuo =uOrd.get(j);
                              interagirAmigo(un, unuo);
                              atras=true;
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                              String unuo =uOrd.get(j);
                              interagirAmigo(un, unuo);
                              atras=true;
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;

                        if(j<tamanhoUO){
                                    String unuo =uOrd.get(j);
                                    interagirAmigo(un, unuo);
                                    atras=true;
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 8: cls();
                          listarAmigosIn(un);
                          atras=true;
                   break;
                default:cls();
                        out.println("!Opcao invalida!");
                   break;
            }
        }while(!atras);
       
    }
    
    public static void procurarPessoal(String un){
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        ArrayList<Utilizador> uOrd = gc.ordenaPorUtilizadoresUserName();
        int tamanhoUO = uOrd.size();
        int pagina = 0;
        int j = 0;
        do{
            out.println("\n Geocachers ");
            for(int i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
             
                 
                 j = 5*pagina+i;
                 Utilizador uo = uOrd.get(j);
                 out.println(" "+(i+1)+" Nome-> "+uo.getNome()+"  UserName: "+uo.getUserName());

                }
           
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");
            out.println("\n\n 8-> Convites que fez para amizade");
            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.texto();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.remAmigoOut(un);
                                    uo.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                    atras=true;
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                        if(u.getUserName().equals(unuo)){atras=true;}
                                        else{ interagirNaoAmigo(un, unuo);}
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.texto();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.remAmigoOut(un);
                                    uo.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                    atras=true;
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                     if(u.getUserName().equals(unuo)){atras=true;}
                                     else{ interagirNaoAmigo(un, unuo);}
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.texto();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.remAmigoOut(un);
                                    uo.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                    atras=true;
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                     if(u.getUserName().equals(unuo)){atras=true;}
                                     else{ interagirNaoAmigo(un, unuo);}
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.texto();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.remAmigoOut(un);
                                    uo.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                    atras=true;
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                     if(u.getUserName().equals(unuo)){atras=true;}
                                     else{ interagirNaoAmigo(un, unuo);}
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoUO){
                            Utilizador uo = uOrd.get(j);
                            String unuo = uo.getUserName();
                            if(u.getAmigosIn().contains(unuo)){
                                out.println(uo.getNome()+
                                    " ja o convidou para ser ser amigo no GeoCaching!");
                                out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                                String r = l.texto();
                                if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uo.remAmigoOut(un);
                                    uo.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uo);
                                    cls();
                                    out.println(uo.getNome()+" adicionado a sua lista de amigos!");
                                    atras=true;
                                }
                            }else 
                                if(u.getAmigos().contains(unuo)){
                                    interagirAmigo(un, unuo);
                                }else{
                                     if(u.getUserName().equals(unuo)){atras=true;}
                                     else{ interagirNaoAmigo(un, unuo);}
                                }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 8: cls();
                        listarAmigosOut(un);
                        atras=true;
                    break;
                   
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void listarAmigosIn(String un){
    
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        ArrayList<String> uOrd = u.getAmigosIn();
        int tamanhoUO = uOrd.size();
        int pagina = 0;
        int j = 0;
        do{
            out.println("\n Geocachers ");
            for(int i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
             
                 
                 j = 5*pagina+i;   
                 out.println(" "+(i+1)+"-> "+usrs.get(uOrd.get(j)).getNome());
                }
           
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");

            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println(unuo+" convidou-o para ser ser amigo no GeoCaching!");
                            out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uti.remAmigoOut(un);
                                    uti.addAmigo(un);
                                    out.println(unuo+" adicionado a sua lista de amigos!");
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uti.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    atras=true;
                            } 
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println(unuo+" convidou-o para ser ser amigo no GeoCaching!");
                            out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uti.remAmigoOut(un);
                                    uti.addAmigo(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uti.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(unuo+" adicionado a sua lista de amigos!");
                                    atras=true;
                            }
                        }else
                            out.println("!Opcao invalida!");
                     break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println(unuo+" convidou-o para ser ser amigo no GeoCaching!");
                            out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uti.remAmigoOut(un);
                                    uti.addAmigo(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uti.addAtividade(raa);
                                    cls();
                                    out.println(unuo+" adicionado a sua lista de amigos!");
                                    atras=true;
                            }    
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println(unuo+" convidou-o para ser ser amigo no GeoCaching!");
                            out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uti.remAmigoOut(un);
                                    uti.addAmigo(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uti.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(unuo+" adicionado a sua lista de amigos!");
                                    atras=true;
                            }   
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println(unuo+" convidou-o para ser ser amigo no GeoCaching!");
                            out.println("Deseja aceitar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoIn(unuo);
                                    u.addAmigo(unuo);
                                    uti.remAmigoOut(un);
                                    uti.addAmigo(un);
                                    RegistoAmizade ra = new RegistoAmizade(usrs.get(un), usrs.get(unuo));
                                    u.addAtividade(ra);
                                    RegistoAmizade raa = new RegistoAmizade(usrs.get(unuo), usrs.get(un));
                                    uti.addAtividade(raa);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(unuo+" adicionado a sua lista de amigos!");
                                    atras=true;
                            }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                    break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    
     public static void listarAmigosOut(String un){
    
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        ArrayList<String> uOrd = u.getAmigosOut();
        int tamanhoUO = uOrd.size();
        int pagina = 0;
        int j = 0;
        do{
            out.println("\n Geocachers ");
            for(int i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
             
                 
                 j = 5*pagina+i;
                 
                 out.println(" "+(i+1)+"-> "+usrs.get(uOrd.get(j)).getNome());
                }
           
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");

            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println("Convidas-te o "+unuo+ " para ser ser amigo no GeoCaching!");
                            out.println("Deseja retirar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoOut(unuo);
                                    uti.remAmigoIn(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(" Removeste o pedido de amizade de "+unuo);
                                    atras=true;
                                }  
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println("Convidas-te o "+unuo+ " para ser ser amigo no GeoCaching!");
                            out.println("Deseja retirar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoOut(unuo);
                                    uti.remAmigoIn(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(" Removeste o pedido de amizade de "+unuo);
                                    atras=true;
                                }  
                        }else
                            out.println("!Opcao invalida!");
                     break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println("Convidas-te o "+unuo+ " para ser ser amigo no GeoCaching!");
                            out.println("Deseja retirar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoOut(unuo);
                                    uti.remAmigoIn(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(" Removeste o pedido de amizade de "+unuo);
                                    atras=true;
                                }  
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println("convidas-te o "+unuo+ " para ser ser amigo no GeoCaching!");
                            out.println("Deseja retirar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoOut(unuo);
                                    uti.remAmigoIn(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(" Removeste o pedido de amizade de "+unuo);
                                    atras=true;
                                }  
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoUO){
                            String unuo = uOrd.get(j);
                            Utilizador uti = usrs.get(unuo);
                            out.println("Convidas-te o "+unuo+ " para ser ser amigo no GeoCaching!");
                            out.println("Deseja retirar o seu pedido de amizade? (s/n)");
                            String r = l.texto();
                            if(r.equals("s")||r.equals("S")){
                                    u.remAmigoOut(unuo);
                                    uti.remAmigoIn(un);
                                    gc.addUtilizador(u);
                                    gc.addUtilizador(uti);
                                    cls();
                                    out.println(" Removeste o pedido de amizade de "+unuo);
                                    atras=true;
                                }  
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
               
                   
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
    
    public static void verCacheEncontrada(String un){ 
        
        Ler l = new Ler();
        int opcao = -1;
        boolean atras = false;
        HashMap<String, Utilizador> usrs = gc.getUtilizadores();
        Utilizador u = usrs.get(un);
        TreeMap<GPS, Cache> ca=gc.getCaches();
        ArrayList<CacheEncontrada> uOrd = u.getCachesEncontradas();
        int tamanhoUO = uOrd.size();
        int pagina = 0;
        int j = 0;
        do{
            out.println("\n Caches ");
            for(int i=0; i<5 && 5*pagina+i<tamanhoUO; i++){
             
                 
                 j = 5*pagina+i;
                 CacheEncontrada x = uOrd.get(j);
                 Cache z= ca.get(x.getCoordenadasCE());
                 out.println(" "+(i+1)+" Nome-> "+z.getNome());
                }
           
            out.println("\n================================");
            if(pagina>0)
                out.println(" 6-> Pagina anterior");
            if((5*(pagina+1))<tamanhoUO)
                out.println(" 7-> Proxima pagina");

            out.println("\n\n 0-> Voltar atras");
            out.print("\n @@Introduza a sua opcao: ");
            opcao = l.inteiro();
            switch(opcao){
                case 0: cls();
                        atras = true;
                    break;
                case 1: cls();
                        j = 5*pagina;
                        if(j<tamanhoUO){
                           CacheEncontrada x = uOrd.get(j);
                           out.println(x.toString());
                           out.println(" 0-> Voltar atras");
                           out.println(" 1-> Ver cache");
                           out.print("\n @@ Introduza a sua opcao: ");
                           opcao = l.inteiro();
                           switch(opcao){ 
                                       case 0: cls();
                                               atras = true;
                                               break;
                                       case 1: cls();
                                              Cache z= ca.get(x.getCoordenadasCE());
                                              out.println(z.toString());
                                              out.println(" 0-> Voltar atras");
                                              opcao = l.inteiro();
                                              cls();
                                              break;
                                       default:cls();
                                               out.println("!Opcao invalida!");
                                               break;
                                       }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 2: cls();
                        j = 5*pagina+1;
                        if(j<tamanhoUO){
                            CacheEncontrada x = uOrd.get(j);
                            out.println(x.toString());
                            out.println(" 0-> Voltar atras");
                            out.println(" 1-> Ver cache");
                            out.print("\n @@ Introduza a sua opcao: ");
                            opcao = l.inteiro();
                            switch(opcao){ 
                                       case 0: cls();
                                               atras = true;
                                               break;
                                       case 1: cls();
                                              Cache z= ca.get(x.getCoordenadasCE());
                                              out.println(z.toString());
                                              out.println(" 0-> Voltar atras");
                                              opcao = l.inteiro();
                                              cls();
                                              break;
                                       default:cls();
                                               out.println("!Opcao invalida!");
                                               break;
                                       }
                        }else
                            out.println("!Opcao invalida!");
                     break;
                case 3: cls();
                        j = 5*pagina+2;
                        if(j<tamanhoUO){
                                CacheEncontrada x = uOrd.get(j);
                                out.println(x.toString());
                                out.println(" 0-> Voltar atras");
                                out.println(" 1-> Ver cache");
                                out.print("\n @@ Introduza a sua opcao: ");
                                opcao = l.inteiro();
                                switch(opcao){ 
                                       case 0: cls();
                                               atras = true;
                                               break;
                                       case 1: cls();
                                              Cache z= ca.get(x.getCoordenadasCE());
                                              out.println(z.toString());
                                              out.println(" 0-> Voltar atras");
                                              opcao = l.inteiro();
                                              cls();
                                              break;
                                       default:cls();
                                               out.println("!Opcao invalida!");
                                               break;
                                       }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 4: cls();
                        j = 5*pagina+3;
                        if(j<tamanhoUO){
                             CacheEncontrada x = uOrd.get(j);
                             out.println(x.toString());
                             out.println(" 0-> Voltar atras");
                             out.println(" 1-> ver cache");
                             out.print("\n @@ Introduza a sua opcao: ");
                             opcao = l.inteiro();
                             switch(opcao){ 
                                       case 0: cls();
                                               atras = true;
                                               break;
                                       case 1: cls();
                                              Cache z= ca.get(x.getCoordenadasCE());
                                              out.println(z.toString());
                                              out.println(" 0-> Voltar atras");
                                              opcao = l.inteiro();
                                              cls();
                                              break;
                                       default:cls();
                                               out.println("!Opcao invalida!");
                                               break;
                                       }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 5: cls();
                        j = 5*pagina+4;
                        if(j<tamanhoUO){
                             CacheEncontrada x = uOrd.get(j);
                             out.println(x.toString());
                             out.println(" 0-> Voltar atras");
                             out.println(" 1-> Ver cache");
                             out.print("\n @@ Introduza a sua opcao: ");
                             opcao = l.inteiro();
                             switch(opcao){ 
                                       case 0: cls();
                                               atras = true;
                                               break;
                                       case 1: cls();
                                              Cache z= ca.get(x.getCoordenadasCE());
                                              out.println(z.toString());
                                              out.println(" 0-> Voltar atras");
                                              opcao = l.inteiro();
                                              cls();
                                              break;
                                       default:cls();
                                               out.println("!Opcao invalida!");
                                               break;
                                       }
                        }else
                            out.println("!Opcao invalida!");
                    break;
                case 6: cls();
                        if(pagina > 0)
                            pagina--;
                        else
                            out.println("!Opcao invalida!");
                        break;
                case 7: cls();
                        if((5*(pagina+1)) < tamanhoUO)
                            pagina++;
                        else
                            out.println("!Opcao invalida!");
                    break;
               
                   
                default:cls();
                        out.println("!Opcao invalida!");
                    break;
            }
        }while(!atras);
    }
}
