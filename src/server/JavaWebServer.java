package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
 
public class JavaWebServer
{
    private static final int port = 82;
    
    private static final int fNumberOfThreads = 100;
    private static final Executor fThreadPool = Executors.newFixedThreadPool(fNumberOfThreads);
 
    public static void main(String[] args) throws IOException
    {
        ServerSocket socket = new ServerSocket(port);
        while (true)
        {
            final Socket connection = socket.accept();
            Runnable task = new Runnable()
            {
                @Override
                public void run()
                {
                    HandleRequest(connection);
                }
            };
            fThreadPool.execute(task);
        }
    }
 
    private static void HandleRequest(Socket socket)
    {
        BufferedReader in;
        PrintWriter out;
        String request;
 
        try
        {
            String webServerAddress = socket.getInetAddress().toString();
            int webPort = socket.getPort();
            
            System.out.println("=========== Connection server:" + webServerAddress);
            System.out.println("- port:" + webPort);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            request = in.readLine();
            System.out.println("--- Client request: " + request);
 
            String response = "{"
                    + "temperature:20"
                    + "}";
            
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("HTTP/1.0 200");
            out.println("Content-type: application/json");
            out.println("Server-name: smarthome");
            out.println("Content-length: " + response.length());
            out.println("");
            out.println(response);
            out.flush();
            out.close();
            
           /*Date today = new Date();
           String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today; 
           socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));*/

           socket.close();
        }
        catch (IOException e)
        {
            System.out.println("Failed respond to client request: " + e.getMessage());
        }
        finally
        {
            if (socket != null)
            {
                try
                {
                    socket.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return;
    }
 
}