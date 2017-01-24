/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package first.console.socket.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 *
 * @author kevin
 */
public class Server
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        if(args.length < 1)
        {
            System.out.println("not engough arguments");
            System.exit(0);
        }
        if(args[0].equals("server"))
        {
            Server server = new Server();
            server.runServer();
        }
        else if(args[0].equals("client"))
        {
            Server client = new Server();
            client.runClient();
        }
        else
        {
            System.exit(0);
        }
        

    }

    private void runServer() throws IOException
    {
        ServerSocket SS = null;
        Socket Client1 = null;
        DataInputStream DataStreamClient1 = null;
        PrintStream PrintStreamClient1 = null;
        String InputStringClient1 = "";
        String OutputStringClient1 = "";
        
        Socket Client2 = null;
        DataInputStream DataStreamClient2 = null;
        PrintStream PrintStreamClient2 = null;
        String InputStringClient2 = "";
        String OutputStringClient2 = "";   
        
        
        int availableClients = 0;
        BufferedReader ConsoleReader = null;
        String OutPutMessage = "";
        
        try
        {
            SS = new ServerSocket(8787);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        while(availableClients < 2)
        {
                Client1 = SS.accept();
                
                if(Client1.isConnected() && availableClients == 0)
                {
                    try
                    {
                        DataStreamClient1 = new DataInputStream(Client1.getInputStream());
                        PrintStreamClient1 = new PrintStream(Client1.getOutputStream());
                        availableClients++;

                        PrintStreamClient1.println("connected");
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
                
                
                Client2 = SS.accept();
                

                if(Client2.isConnected() && availableClients == 1)
                {
                    try
                    {
                        DataStreamClient2 = new DataInputStream(Client2.getInputStream());
                        PrintStreamClient2 = new PrintStream(Client2.getOutputStream());
                        availableClients++;
                        PrintStreamClient2.println("connected");
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
        }
        
        
        ConsoleReader = new BufferedReader(new InputStreamReader(System.in));

        //BufferedReader br = new BufferedReader(IR); 
        while(true)
        {

            if(ConsoleReader.ready())
            {
            OutPutMessage = ConsoleReader.readLine();
            }

            
            if(OutPutMessage.length() > 0 )
            {
                if(OutPutMessage.equals("quit"))
                {
                    break;
                }
                //PS.println(OutPutMessage);
            }
            
                InputStringClient1 = DataStreamClient1.readLine();
                InputStringClient2 = DataStreamClient2.readLine();


            //String msgout = "";
            if(InputStringClient1 != null)
            {
                System.out.println(InputStringClient1);
                InputStringClient1 = null;
                //PrintStreamClient2.println(InputStringClient1);
            }
            
            if(InputStringClient2 != null)
            {
                System.out.println(InputStringClient2);
                InputStringClient2 = null;
                //PrintStreamClient1.println(InputStringClient2);
            }
            
            if(Client1.isClosed() || Client2.isClosed())
            {
                System.out.println("one of the clients got disconnected");
                break;
            }
        }
        SS.close();
        
        Client1.close();
        DataStreamClient1.close();
        PrintStreamClient1.close();
        
        Client2.close();
        DataStreamClient2.close();
        PrintStreamClient2.close();
        
        ConsoleReader.close();
    }

    private void runClient() throws IOException
    {
        Socket S = null;
        PrintStream PS = null;
        InputStreamReader IR =null;
        DataInputStream DIS = null;
        BufferedReader BR = null;
        BufferedReader reader = null;
        
        String message = "";
        String outputMessage = "";
        try
        {
            S = new Socket("127.0.0.1", 8787);
            PS = new PrintStream(S.getOutputStream());  
            IR = new InputStreamReader(S.getInputStream());
            DIS = new DataInputStream(S.getInputStream());
            BR = new BufferedReader(IR);
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch(UnknownHostException e)
        {
            System.out.println(e);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        PS.println("hi server");
            

        while(true)
        {
            if(reader.ready())
            {
                outputMessage = reader.readLine();
            }
            
            if(outputMessage.length() > 0 )
            {
                if(outputMessage.equals("quit"))
                {
                    break;
                }
                PS.println(outputMessage);
            }
            
            message = DIS.readLine();
            //message = DIS.readUTF();
            
            if(message != null)
            {
                System.out.println(message);
            }
            
            if (message.equals("quit"))
            {
                break;
            }   
        }
        
        reader.close();
        BR.close();
        DIS.close();
        PS.close();
        IR.close();
        S.close();
        System.exit(0);
    }
    
}
