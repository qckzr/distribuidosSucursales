/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.net.Socket;

/**
 *
 * @author hector
 */
public class HiloServidor extends Thread{
    
    Socket socket;
    Servidor server;

    public HiloServidor(Servidor server) {
        this.server = server;
    }
    
    @Override
    public void run(){
        
    }
    
}
