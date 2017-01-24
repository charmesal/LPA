/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketservertest;

//import is like the using fro c#
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author kevin
 */
public class SocketServerTest {

    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;
    static int port = 8877;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
             
        
        
        
            String msgin = "";

            try
            {
                ss = new ServerSocket(port);
                s = ss.accept();
                
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(!msgin.equalsIgnoreCase("exit"))
                {
                    msgin = din.readUTF();
                    System.out.println(msgin);
                }

            }
            catch(Exception e)
                    {
                        System.out.println(e.toString());
                    }
        
    }
    
   
    
    
}
