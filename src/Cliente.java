import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

class Cliente
{
   public static void main(String args[]) throws Exception
   {
	   
	  String salida= "Disponible";
	  int PUERTO = 4999;
	  String servidor="localhost";  
	  int puerto_inicial=1;
      int puerto_final=2;
	  
	  try{
		 DatagramSocket socket_cliente = new DatagramSocket();
		  
		 byte salidablock[] = salida.getBytes();
		  
		 InetAddress address = InetAddress.getByName("255.255.255.255");
		  
		 DatagramPacket paquete_salida = new DatagramPacket(salidablock, salidablock.length,address, PUERTO);
		 //System.out.println("Client escuchando en el puerto"+PUERTO);
		 socket_cliente.send(paquete_salida);
		  
		  System.out.println("Cliente envió: "+salida);
		  
		  
		  //LO QUE RECIBE DEL SERVIDOR DESPUÉS DE LA ESPERA
		  byte bloque_entrada[]= new byte[256];
		  DatagramPacket paquete_entrada = new DatagramPacket(bloque_entrada, bloque_entrada.length);
		  
		  socket_cliente.receive(paquete_entrada);
		  
		  String cadena_entrada = new String(paquete_entrada.getData(),0, paquete_entrada.getLength());
		  System.out.println("Cliente recibió: "+cadena_entrada);
		  
		  StringTokenizer tokens = new StringTokenizer(cadena_entrada);
		  
		  int cont=1;
		  
		  while(tokens.hasMoreTokens()){
			  //System.out.println(tokens.nextToken());
			  if(cont == 1){
				  servidor = tokens.nextToken();
				  cont++;
			  }
			  if(cont == 2){
				  puerto_inicial = Integer.parseInt(tokens.nextToken());
				  cont++;
			  }
			  if(cont == 3){
				  puerto_final = Integer.parseInt(tokens.nextToken());
			  }			  
		  }
		  
		  String resultado= escanear(servidor,puerto_inicial, puerto_final);
		  
		  byte salidablock2[] = resultado.getBytes();
		  
		  //InetAddress address2 = InetAddress.getByName("255.255.255.255");			  
		  DatagramPacket paquete_salida2 = new DatagramPacket(salidablock2, salidablock2.length, address, PUERTO);
		  socket_cliente.send(paquete_salida2);
		  
		  socket_cliente.close();
		  System.out.println("Se cerro el socket");
		  
		  
	  }catch(SocketException e){
		  System.out.println(e);
	  }catch(UnknownHostException e){
		  System.out.println(e);
	  }catch(IOException e){
		  System.out.println(e);
	  }
   }
   
   //FUNCIÓN DE ESCANEAR
   public static String escanear(String servidor, int puerto, int puerto_final)
   {
	   Boolean b_encontro = false;
	   String resultado="";
	   
	   System.out.println("Contribuyendo en el escaner de puertos... Espere un momento...");
	   
	   for(int port =puerto;port<=puerto_final;port++){
		      try{    	  
				 Socket socket= new Socket (servidor,port);
				 resultado = resultado + "Puerto "+ port + " abierto.\n";
				 System.out.println("Puerto "+port+" Abierto");
				 socket.close();
				 b_encontro = true;
		      } catch (IOException e)
		      {
		       		//System.out.println("Error en conexión al intentar con el puerto " + port  + "!!!");
		      }      
		}
			
		if (b_encontro == false){
			resultado = "No se encontraron puertos abiertos";
		}
		return resultado;
	   
   }
}