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
    public static void main(String[] args) throws IOException, InterruptedException
    {
        int Quit = 0;
        if(args.length < 1)
        {
            System.out.println("not engough arguments");
            System.exit(0);
        }
        if(args[0].equals("--server") || args[0].equals("-s"))
        {
            Server server = new Server();
            while(Quit >= 0)
            {
                Quit = server.runServer();
            }
            System.out.println("Terminating program");
            System.exit(0);
        }
        else if(args[0].equals("--client") || args[0].equals("-c"))
        {
            Server client = new Server();
            client.runClient();
        }
        else if(args[0].equals("--help") || args[0].equals("-h"))
        {
            System.out.println("use \"--server\" or \"-s\" to start the server\n"
                    + "use \"--client\" or \"-c\" to start a client\n"
                    + "use \"--help\" or \"-h\" to get to this screen");
        }
        else
        {
            System.out.println("dont know \"" + args[0] + "\". Try \"--help\" or \"-h\" for options");
            System.exit(0);
        }
        

    }

    private int runServer() throws IOException, InterruptedException
    {
        System.out.println("server starting");
        int returnval = 0;
        ServerSocket SS = null;
        Socket Client1 = null;
        DataInputStream DataStreamClient1 = null;
            DataOutputStream DataOutStreamClient1 = null;
//PrintStream PrintStreamClient1 = null;
        String InputStringClient1 = "";
//        String OutputStringClient1 = "";
        
        Socket Client2 = null;
        DataInputStream DataStreamClient2 = null;
            DataOutputStream DataOutStreamClient2 = null;
//PrintStream PrintStreamClient2 = null;
        String InputStringClient2 = "";
//        String OutputStringClient2 = "";   
        
        
        int availableClients = 0;
        BufferedReader ConsoleReader = null;
        String outputMessage = "";
        
        try
        {
            SS = new ServerSocket(8787);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        System.out.println("server started");

        while(availableClients < 2)
        {
                if(availableClients == 0)
                {
                    Client1 = SS.accept();
                }
                
                if(Client1 != null && Client1.isConnected() && availableClients == 0)
                {
                    try
                    {
                        DataStreamClient1 = new DataInputStream(Client1.getInputStream());
                        //PrintStreamClient1 = new PrintStream(Client1.getOutputStream());
                        DataOutStreamClient1 = new DataOutputStream(Client1.getOutputStream());
                        Thread.sleep(500);
                        if(DataStreamClient1.available() > 0)
                        {
                            if(DataStreamClient1.readUTF().equals("⛄hi server⛄"))
                            {
                                DataOutStreamClient1.writeUTF("connected");
                                //PrintStreamClient1.println("connected");
                                availableClients++;
                                System.out.println("Client 1 connected");
                            }
                        }
                        if(availableClients != 1)
                        {
                            DataOutStreamClient1.close();
                            DataStreamClient1.close();
                            Client1.close();
                        }
                    }
                    catch(IOException e)
                    {
                        System.out.println(e);
                    }
                }
                
                if(availableClients == 1)
                {
                    Client2 = SS.accept();
                }
                

                if(Client2 != null && Client2.isConnected() && availableClients == 1)
                {
                    try
                    {
                        DataStreamClient2 = new DataInputStream(Client2.getInputStream());
                        DataOutStreamClient2 = new DataOutputStream(Client2.getOutputStream());
                        
                        Thread.sleep(500);
                        if(DataStreamClient2.available() > 0)
                        {
                            if(DataStreamClient2.readUTF().equals("⛄hi server⛄"))
                            {
                                DataOutStreamClient2.writeUTF("connected");
                                availableClients++;
                                System.out.println("Client 2 connected");
                            }
                        }
                        if(availableClients != 2)
                        {
                            DataOutStreamClient2.close();
                            DataStreamClient2.close();
                            Client2.close();
                        }
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
            outputMessage = ConsoleReader.readLine();
            }

            
            if(outputMessage.length() > 0 )
            {
                if(outputMessage.equals("restart"))
                {
                    //ConsoleReader.close();
                    break;
                }
                if(outputMessage.equals("quit"))
                {            
                    DataOutStreamClient1.writeUTF("Server shutting down");
                    DataOutStreamClient2.writeUTF("Server shutting down");
                    
                    returnval = -1;
                    break;
                }
                sendStringToClient(DataOutStreamClient1, "server: " + outputMessage);
                sendStringToClient(DataOutStreamClient2, "server: " + outputMessage);
                outputMessage = "";
            }
            
            if(DataStreamClient1.available() > 0)
            {
                InputStringClient1 = DataStreamClient1.readUTF();

                sendStringToClient(DataOutStreamClient2, "partnet: " + InputStringClient1);
                InputStringClient1 = "";
            }
            
            if(DataStreamClient2.available() > 0)
            {
                InputStringClient2 = DataStreamClient2.readUTF();

                sendStringToClient(DataOutStreamClient1, "partnet: " + InputStringClient2);
                InputStringClient2 = "";
            }

            try
            {
                DataOutStreamClient1.writeUTF("⛄keepalive⛄");
                DataOutStreamClient2.writeUTF("⛄keepalive⛄");
            }
            catch(IOException e)
            {
                System.out.println("one or more clients have disconnected. Resetting the server");
                break;                
            }
        }
        
        try
        {
            DataOutStreamClient1.writeUTF("Connection lost. Quiting program");
            DataOutStreamClient1.writeUTF("⛄quit⛄");
        }
        catch(Exception e)
        {
        }
        
        try
        {
            DataOutStreamClient2.writeUTF("Connection lost. Quiting program");
            DataOutStreamClient2.writeUTF("⛄quit⛄");
        }
        catch(Exception e)
        {
        }
        
        Client1.close();
        DataStreamClient1.close();
        DataOutStreamClient1.close();
        
        Client2.close();
        DataStreamClient2.close();
        DataOutStreamClient2.close();
        
        //ConsoleReader.close();
        
        SS.close();
        
        return returnval;

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    private void runClient() throws IOException
    {
        Socket S = null;
        //PrintStream PS = null;
        DataOutputStream DataOutStream = null;
        InputStreamReader IR =null;
        DataInputStream DIS = null;
        BufferedReader BR = null;
        BufferedReader reader = null;
        
        String message = "";
        String outputMessage = "";
        try
        {
            S = new Socket("127.0.0.1", 8787);
            DataOutStream = new DataOutputStream(S.getOutputStream());  
            IR = new InputStreamReader(S.getInputStream());
            DIS = new DataInputStream(S.getInputStream());
            BR = new BufferedReader(IR);
            reader = new BufferedReader(new InputStreamReader(System.in));
        }
        catch(UnknownHostException e)
        {
            System.out.println("unknow host");
            System.exit(0);
        }
        catch(IOException e)
        {
            System.out.println("io:" + e);
            System.exit(0);
        }

        DataOutStream.writeUTF("⛄hi server⛄");
            

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
                DataOutStream.writeUTF(outputMessage);
                outputMessage = "";
            }
            
            if(DIS.available() > 0)
            {
                message = DIS.readUTF();
            }
            //message = DIS.readUTF();
            
            if(message != "")
            {
                if (message.equals("⛄quit⛄"))
                {
                    break;
                }
                if (!message.equals("⛄keepalive⛄"))
                {
                    System.out.println(message);
                }
                message = "";
            }
        }
        
        reader.close();
        BR.close();
        DIS.close();
        DataOutStream.close();
        IR.close();
        S.close();
        System.exit(0);
    }

    private void sendStringToClient(DataOutputStream printStream, String messageToSend) throws IOException
    {
            if(messageToSend != "" && printStream != null)
            {
                //System.out.println(messageToSend);
                printStream.writeUTF(messageToSend);
            }
    }
}
