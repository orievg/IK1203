import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HTTPAsk {
    public static String[] parseQuery(String q){
        String[] r = new String[3];

        Scanner s = new Scanner(q);
        s.useDelimiter("&");
        String host = null;
        String port = null;
        String msg = null;
        try {
            host = s.next();
        }
        catch (NoSuchElementException e){
            //System.out.println("No host specified");
            return null;
        }
        try {
            port = s.next();
        }
        catch (NoSuchElementException e){
            //System.out.println("No port specified");
            return null;
        }
        try {
            msg = s.next();
        }
        catch (NoSuchElementException e){
            //System.out.println("No message specified");
        }
        //if all 3 fields are specified
        if (host.substring(0,8).equals("hostname") && port.substring(0,4).equals("port") && msg !=null  && msg.substring(0,6).equals("string")){
            r[0] = host.substring(9);
            r[1] = port.substring(5);
            r[2] = msg.substring(7);
            r[2] = r[2] + "\r\n";
        }
        //if only 2 fields specified
        else if (host.substring(0,8).equals("hostname") && port.substring(0,4).equals("port") && msg == null){
            r[0] = host.substring(9);
            r[1] = port.substring(5);
            r[2] = null;

        }
        else{
            return null;
        }

        return r;
    }
    public static void main( String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        while(true){
            byte[] userQuery = new byte[1024];
            Socket client = serverSocket.accept();
            String responseHEAD = null;
            String responseOK =
                    "HTTP/1.1 200 OK\n" +
                            "Connection: close\n" +
                            "Content-Type: text/plain; charset=utf-8\n\n";
            String responseBR =
                    "HTTP/1.1 400 Bad Request\n" +
                            "Connection: close\n" +
                            "Content-Type: text/plain; charset=utf-8\n\n";
            String responseNF =
                    "HTTP/1.1 404 Not Found\n" +
                            "Connection: close\n" +
                            "Content-Type: text/plain; charset=utf-8\n\n";


            String requestMethod = null;
            String requestPath = null;
            String requestHost = null;



            int userQueryL = client.getInputStream().read(userQuery);
            //read the request
            String userQueryString = new String(userQuery, StandardCharsets.UTF_8);
            //get the method path and host
            Scanner sc = new Scanner(userQueryString);
            try {
                requestMethod = sc.next();
                requestPath = sc.next();
                //skip to host
                sc.next();
                sc.next();
                requestHost = sc.next();
            }
            catch(NoSuchElementException e) {
                responseHEAD = responseNF;
                responseHEAD = responseHEAD + "The page you are looking for is not found";
                client.getOutputStream().write(responseHEAD.getBytes(StandardCharsets.UTF_8));
                client.close();
                break;
            }
            String requestURL = "http://" + requestHost + requestPath;

            URL url = new URL(requestURL);
            String urlPath = url.getPath();
            String urlQuery = url.getQuery();

            StringBuilder responseBODY = new StringBuilder();
            //TODO: path check here
            if (urlPath.equals("/ask")){
                responseHEAD = responseOK;
                //query the desired server
                String[] toServer = parseQuery(urlQuery);
                String fromServer;
                if (toServer==null){
                    responseHEAD=responseBR;
                    //if the query did not meet the requirements
                    responseBODY.append("You have sent a bad query\n");
                    String response = responseHEAD + responseBODY.toString();
                    client.getOutputStream().write(response.getBytes(StandardCharsets.UTF_8));
                    client.close();
                    continue;
                }
                else if (toServer[2] == null){
                    fromServer = TCPClient.askServer(toServer[0],Integer.parseInt(toServer[1]));
                }
                else {
                    fromServer = TCPClient.askServer(toServer[0],Integer.parseInt(toServer[1]), toServer[2]);
                }
                //generate HTML response BODY
                //parse the response from the requested sever and add it to the our response
                Scanner scanner = new Scanner(fromServer);
                scanner.useDelimiter("");
                char prev = '\0';
                while(scanner.hasNext()){

                    char c = scanner.next().charAt(0);
                    //find EOF
                    if(c == '\0' && prev == '\n'){
                        break;
                    }
                    else{
                        responseBODY.append(c);
                        prev = c;
                    }
                }
            }
            else {
                responseHEAD = responseNF;
            }
            //send response
            responseHEAD =  responseHEAD + responseBODY.toString();
            client.getOutputStream().write(responseHEAD.getBytes(StandardCharsets.UTF_8));
            client.close();
        }
    }
}

