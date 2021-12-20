import java.io.IOException;
import java.net.ServerSocket;

public class ConcHTTPAsk {
    public static void main(String args[]) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        while(true){
            MyRunnable myRunnable = new MyRunnable(serverSocket.accept());
            new Thread(myRunnable).start();
        }
    }
}
