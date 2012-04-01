/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hector
 */
public class Servidor extends Thread{
    
    private int puerto;
    
    private ServerSocket serverSocket;
    

    private ObjectInputStream input;
    
    private int numeroServidor;
    
    public Servidor(int puerto,int numeroServidor)
    {
        try {
            this.puerto = puerto;
            serverSocket = new ServerSocket(this.puerto);
            this.numeroServidor = numeroServidor;

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @Override
    public void run()
    {
        System.out.println("Iniciando servidor "+this.numeroServidor);
        while(true)
        {
            try {
                recibir(serverSocket.accept());
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }
        
    }
    

   public void enviar(Object objeto,Socket socket){
        try {
  
          ObjectOutputStream output =
                        new ObjectOutputStream(socket.getOutputStream());
  
            output.writeObject(objeto);
          output.flush();
                System.out.println("se enviaron los datos?");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

     }

    public void recibir (Socket s1){
     
        try {
             input = new ObjectInputStream(s1.getInputStream());
            Object datoRecibido = input.readObject();
           System.out.println("lo que recibi..." + datoRecibido.toString());
            if (datoRecibido instanceof String) {
                String mensaje = (String) datoRecibido;
                if (mensaje.contains("hola"))
          //          enviar("CHAO", s1);
                    System.out.println("Y AHORA QUE?");
            } 
            else {
                System.out.println("Se recibio un objeto que no corresponde" + "a la clase Persona");
            }
        }
        catch(Exception e){
            System.out.println("explota en el try de recibir? "+e.getMessage());
        }
    }



}

    
    

