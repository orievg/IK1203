# IK1203
 Assignments for the KTH IK1203 course.
# Task 1: TCPAsk
Open a TCP connection to a server at a given host address and port number.
Take any data that the server sends, and and print the data.
TCPAsk takes an optional string as parameter. This string is sent as data to the server when the TCP connection is opened, followed by a newline character (linefeed '\n').
Example usage: `java TCPAsk time.nist.gov 13`
# Task 2: HTTPEcho server.
In this part, you will implement a web server. It is a web server that does not do very much, but you will probably find it useful for the rest of this assignment. The server accepts an incoming TCP connection, reads data from it until the client closes the connection, and returns ("echoes") an HTTP response back with the data in it.
Example usage: `java HTTPEcho 8080`
# Task 3: HTTPAsk Server
In this part, you will implement another web server – HTTPAsk. This is a web server that uses the TCPAsk client. When you send an HTTP request to HTTPAsk, you provide a hostname, a port number, and optionally a string as parameters for the request.

When HTTPAsk receives the HTTP request, it will call the method TCPClient.askServer, and return the output as an HTTP response.

The goal is to:

Read and analyse an HTTP GET request, and extract a query string from it.
Launch an application from your server, where the application provides the data that the server returns in response to the HTTP get.
Example usage: `java HTTPAsk 8080` and open `http://localhost:8080/ask?hostname=time.nist.gov&port=13` in the web browser.
# Task 4: Concurrent HTTPAsk Server
The goal was to do the same as above, but with the use of Java threading to implement a concurrent server that can handle many clients at the same time.
Example usage: same as above.
