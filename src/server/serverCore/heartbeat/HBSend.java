package server.serverCore.heartbeat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.Server;
import server.serverCore.Info;


public class HBSend implements Heartbeat {
	
	Server server;
	
	public HBSend(Server server){
		this.server = server;
	}

	@Override
	public void run(){
		
		try {
			send();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void send() throws IOException, InterruptedException {
		InetAddress group = InetAddress.getByName(IP);
		
		//byte[] buf = null;
		
		
		//se creeaza un socket pe un port oarecare
		DatagramSocket socket = new DatagramSocket();
		
		try {
			//trimite un pachet catre toti clientii din grup
			//String s = setMessage();
			//buf = s.getBytes();
            
           
			while(true){
				Thread.sleep(WAIT);
				
				Info info = server.info;
				
	            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	            ObjectOutputStream os = new ObjectOutputStream(outputStream);
				
				//System.out.println("HBSend self: " + info.selfPerf);
	            os.writeObject(info);
	            byte[] buf = outputStream.toByteArray();
				
	            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);
				socket.send(packet);
			}
		
		} finally {
			socket.close();
		
		}	
	}

}