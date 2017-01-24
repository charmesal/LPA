/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author kevin
 */
public class MessageListener extends Thread{
    
    ServerSocket server;
    int port = 8877;
    WriteableGUI gui;
    public MessageListener(WriteableGUI gui, int port){
        this.port = port;
        this.gui = gui;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     public MessageListener(){
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        Socket Clientsocket;
        try {
            while((Clientsocket = server.accept()) != null){
                InputStream is = Clientsocket.getInputStream(); //get Stream from client
                BufferedReader br = new BufferedReader(new InputStreamReader(is)); // change stream to buffer and read buffer
                String line = br.readLine();
                if(line != null){
                    gui.write(line);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
     
}
