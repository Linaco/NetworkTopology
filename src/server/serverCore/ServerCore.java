package server.serverCore;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.Server;

//Main server which will communicate with the user
public class ServerCore implements Runnable {
	private DatagramSocket socket = null;
	private DatagramPacket cerere, raspuns = null;
	private Server server;
	private int name;
	
	public ServerCore(Server server){
		this.server = server;
		this.name = server.serverName;
	}

	@Override
	public void run() {
		
		
		
		try {
			
			socket = new DatagramSocket();
			System.out.println(name + " : " + socket.getLocalSocketAddress());
			server.info.port = socket.getLocalPort();
			server.info.IP = socket.getLocalAddress().toString();
			
			while (true) {
				byte[] buf = null;
				//Crearea pachetului in care va fi receptionata cererea
				buf = new byte[2048];
				cerere = new DatagramPacket(buf, buf.length);
				//Preluarea pachetului cu cererea
				socket.receive(cerere);
				//Aflarea adresei si a portului de la care vine cererea
				InetAddress adresa = cerere.getAddress();
				int port = cerere.getPort();
				
				
				//Construirea raspunsului
				String s = new String(cerere.getData(), cerere.getOffset(), cerere.getLength());
				System.out.println("Message received! : " + s);
				
				String toDisplay = server.getSnapshot(s);
				
				System.out.println(toDisplay);
				
				buf = toDisplay.getBytes();
				
				//Trimite un pachet cu raspunsul catre client
				raspuns = new DatagramPacket(buf, buf.length, adresa, port);
				socket.send(raspuns);
				System.out.println("Message sent");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	
			socket.close();
		}
		
	}

}