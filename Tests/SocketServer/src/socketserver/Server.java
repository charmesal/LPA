/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
       try
        {
            ServerSocket ss = new ServerSocket(8787);
            Socket s = ss.accept();

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));        
            String msgin = "";
            String msgout = "";

            while(!msgin.equals("end"))
            {
                msgin = din.readUTF();
                System.out.println(msgin);

                msgout = br.readLine();
                dout.writeUTF(msgout);

                dout.writeUTF(msgout);
                dout.flush();
            }

            s.close();
        }
        catch (IOException ex)
        {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
