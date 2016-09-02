import java.net.InetAddress;

public class Client {
	
	InetAddress returnAddress;
	int returnport;
	String servidor;
	int puerto_inicial;
	int puerto_final;
	
	public Client(InetAddress p1, int p2){
		returnAddress = p1;
		returnport = p2;
	}
	
	public InetAddress getReturnAddress() {
		return returnAddress;
	}
	public void setReturnAddress(InetAddress returnAddress) {
		this.returnAddress = returnAddress;
	}
	public int getReturnport() {
		return returnport;
	}
	public void setReturnport(int returnport) {
		this.returnport = returnport;
	}
	
	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public int getPuerto_inicial() {
		return puerto_inicial;
	}

	public void setPuerto_inicial(int puerto_inicial) {
		this.puerto_inicial = puerto_inicial;
	}

	public int getPuerto_final() {
		return puerto_final;
	}

	public void setPuerto_final(int puerto_final) {
		this.puerto_final = puerto_final;
	}
	
	

}
