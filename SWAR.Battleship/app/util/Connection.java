package util;

import java.io.IOException;
import java.net.Socket;

public class Connection {
    /**
     * Gets the IP Adress of the local host regarding the active Internet adapter
     * @return The IP of the local host
     */
    public static String getActiveNetConnectionLocalHostIP() {
    	Socket s;
    	String result;
		try {
			s = new Socket("google.com", 80);
	    	result = s.getLocalAddress().getHostAddress();
	    	s.close();
		} catch (IOException e) {
			result = "localhost";
		}
		return result;
    }
}
