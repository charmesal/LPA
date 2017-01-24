/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package first.console.socket.test;

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
        DataInputStream DIN = null;
        PrintStream PS = null;
        BufferedReader reader = null;
        String msgin = "";
        String msgout = "";
        int availableClients = 0;
        
        try
        {
            SS = new ServerSocket(8787);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        try
        {
            Client1 = SS.accept();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        
        try
        {
            DIN = new DataInputStream(Client1.getInputStream());
            PS = new PrintStream(Client1.getOutputStream());

        }
        catch(IOException e)
        {
            System.out.println(e);
        }

        //BufferedReader br = new BufferedReader(IR); 
        while(true)
        {
            msgin = DIN.readLine();

            //String msgout = "";
            if(msgin != null)
            {
                System.out.println(msgin);
                PS.println("got it");
            }
        }
        
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
            try
            {
                message = BR.readLine();
                //message = DIS.readUTF();
            }
            catch(IOException e)
            {
                System.out.println(e);
                break;
            }
            
            //if(reader.ready())
            //{
                outputMessage = reader.readLine();
            //}
            
            if(outputMessage.length() > 0 )
            {
                if(outputMessage.equals("quit"))
                {
                    break;
                }
                PS.println(outputMessage);
            }
            
            if(message != null)
            {
                System.out.println(message);
            }
            
            if (message.equals("🖕"))
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
