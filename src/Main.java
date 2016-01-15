import server.Server;

public class Main {
	public static void main(String[] args) {
		
		/*try {
			for ( Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();)
				System.out.println(e.nextElement());
			//System.out.println(NetworkInterface.getNetworkInterfaces());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		System.out.println("---------------Main-------------");
		System.out.println();

		new Server(2);
        new Server(1);
		new Server(4);
        new Server(3);
        
        
        new Server(5);
        new Server(6);
        new Server(7);
        //new Server(3);
        
    }

}
