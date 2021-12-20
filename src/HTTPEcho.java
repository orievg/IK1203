import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HTTPEcho {
    public static void main( String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("The server is up and running on localhost:" + port);

        while(true){
            byte[] userQuery = new byte[16834];
            Socket client = serverSocket.accept();

            //System.out.println("New client has connected! \n");

            client.getInputStream().read(userQuery);
            //read the request
            String userQueryString = new String(userQuery, StandardCharsets.UTF_8);
            Scanner sc = new Scanner(userQueryString);
            //generate HTML response HEAD
            StringBuffer responseSB = new StringBuffer("HTTP/1.1 200 OK\n" +
                    "Connection: close\n" +
                    "Content-Type: text/plain; charset=utf-8\n\n");

            Scanner scanner = new Scanner(userQueryString);
            scanner.useDelimiter("");

            char prev = '\0';
            while(scanner.hasNext()){

                char c = scanner.next().charAt(0);
                //find EOF
                if(c == '\0' && prev == '\n'){
                    break;
                }
                else{
                    responseSB.append(c);
                    prev = c;
                }

            }
            //send response
            //System.out.println(responseSB.toString());
            client.getOutputStream().write(responseSB.toString().getBytes(StandardCharsets.UTF_8));
            client.close();
        }
    }
}

