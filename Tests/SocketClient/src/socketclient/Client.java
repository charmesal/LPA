/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            Socket s = new Socket("127.0.0.1", 8787);
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String msgin = "";
            String msgout = "";
            
            while(!msgin.equals("end"))
                    {
                        msgout = br.readLine();
                        dout.writeUTF(msgout);
                        msgin = din.readUTF();
                        System.out.println(msgin);
                    }
        }
        
        catch (IOException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
