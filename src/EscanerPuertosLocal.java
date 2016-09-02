import java.io.*;
import java.net.*;

public class EscanerPuertosLocal{
	
    public static void main(String[] args) {
    	
		System.out.println("IP: " + args[0]);
		System.out.println("Puerto inicial: " + args[1]);
		System.out.println("Puerto final: " + args[2]);
		System.out.println("Descubriendo puertos, espere unos segundos...");
	
	    String servidor = args[0];
	      
	
		int puerto_inicial = Integer.parseInt(args[1]);
		int puerto = puerto_inicial;
		int puerto_final = Integer.parseInt(args[2]);
		Boolean b_encontro = false;
		
		for(int port =puerto;port<=puerto_final;port++){
	      try{    	  
			 Socket socket= new Socket (servidor,port);
			 System.out.println("Puerto "+port+" Abierto");
			 socket.close();
			 b_encontro = true;
	      } catch (IOException e)
	      {
	       		//System.out.println("Error en conexión al intentar con el puerto " + port  + "!!!");
	      }      
		}
		
		if (b_encontro == false){
			System.out.println("No se encontraron puertos abiertos");
		}
      
    }
}

