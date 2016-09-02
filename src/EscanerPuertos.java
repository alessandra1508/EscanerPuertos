import java.net.*;
import java.util.ArrayList;
import java.io.*;

// Itzel Alessandra Reyes Flores

public class EscanerPuertos {
	
	public static void main(String args[]){
	
	    String servidor = args[0];
		int puerto_inicial = Integer.parseInt(args[1]);
		int puerto_final = Integer.parseInt(args[2]);
		
		System.out.println("IP: " + servidor);
		System.out.println("Puerto inicial: " + args[1]);
		System.out.println("Puerto final: " + args[2]);
		
		
		DatagramSocket socket = null; //UDP
		
		int PUERTO = 4999;		
		int contador = 0;
		
		ArrayList<Client> clientesConec = new ArrayList<Client>();
		int numClientes;
		Boolean b=false;		
		String msg_salida;
		
		try{ //CREAR SOCKET UDP
			
			socket = new DatagramSocket(PUERTO);
			socket.setBroadcast(true);
			socket.setSoTimeout(15000);
			
			System.out.println("Servidor escuchado en puerto "+ PUERTO);
			
		}catch(IOException e){
			System.out.println(e);
		}
		
		while(b == false){ //MIENTRAS NO SE ACABE EL TIEMPO
			
			try  //INTENTA TENER ALGUNA CONEXIÓN
			{ 
				
				byte block[] = new byte[256];
				
				DatagramPacket paquete_entrada = new DatagramPacket(block, block.length);	
				socket.receive(paquete_entrada);
				
				int length = paquete_entrada.getLength();
				
				byte bloque_entrada[] = paquete_entrada.getData();				
				String msg_entrada = new String(bloque_entrada,0,length);
				contador++;
				System.out.println("Servidor obtuvo: "+ msg_entrada +" de Cliente"+ contador);			
				
										
				
				if(msg_entrada.equals("Disponible")){				
					
					InetAddress returnAddress = paquete_entrada.getAddress();
					int returnport = paquete_entrada.getPort();
					
					Client cliente = new Client(returnAddress,returnport);
					clientesConec.add(cliente);
					//System.out.println(clientesConec.size());
					
				}else 
					if(msg_entrada.equals("stop")){
					socket.close();
					return;
				}else
				{
					msg_salida = "No se puede ejecutar esa acción";
				}
				
			}catch(SocketTimeoutException e) {
				   System.out.println("Agotado el tiempo de espera...");
				   b = true;
			       continue;
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Tengo "+ clientesConec.size()+ " clientes conectados.");
		
		if(clientesConec.size()>0){			// SI ENCUENTRO AYUDA DE CLIENTES
			
			try 
			{
				socket.setSoTimeout(0);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			int rango = puerto_final - puerto_inicial;
			numClientes = clientesConec.size();
			int puertos_x_cliente = (rango/numClientes)+1;	
			System.out.println("Puertos por cliente:"+puertos_x_cliente);
			
			for(int i = 0;i<numClientes;i++)
			{
				clientesConec.get(i).setServidor(servidor);
				clientesConec.get(i).setPuerto_inicial(puerto_inicial);
				clientesConec.get(i).setPuerto_final(puerto_inicial+ puertos_x_cliente-1);
				puerto_inicial = puerto_inicial+ puertos_x_cliente; 
			}	
			
			for(int i = 0;i<numClientes;i++)
			{
				msg_salida = ""+clientesConec.get(i).getServidor()+" "+clientesConec.get(i).getPuerto_inicial()+" "+clientesConec.get(i).getPuerto_final();
	        	byte outblock[] = msg_salida.getBytes();
	        	DatagramPacket outpacket = new DatagramPacket(outblock, outblock.length, clientesConec.get(i).getReturnAddress(), clientesConec.get(i).getReturnport());
				try {
					socket.send(outpacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			while(true) //AHORA ESCUCHO
			{
				byte block2[] = new byte[256];
				
				DatagramPacket paquete_entrada2 = new DatagramPacket(block2, block2.length);	
				try {
					socket.receive(paquete_entrada2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int length = paquete_entrada2.getLength();
				//System.out.println("Data length: "+ length);
				
				byte bloque_entrada[] = paquete_entrada2.getData();				
				String msg_entrada = new String(bloque_entrada,0,length);
				System.out.println("Servidor obtuvo: "+ msg_entrada);		
			}
			}else{
			 String resultado= escanear(servidor,puerto_inicial, puerto_final);
			 System.out.println(resultado);	
			}
		//socket.close();	
		
	}
	
	//FUNCIÓN PARA ESCANEAR PUERTOS
	 public static String escanear(String servidor, int puerto, int puerto_final)
	 {
		   Boolean b_encontro = false;
		   String resultado="";
		   
		   System.out.println("Escaneando puertos... Espere un momento...");
		   
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
