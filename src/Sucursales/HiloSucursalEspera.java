/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sucursales;

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
public class HiloSucursalEspera extends Thread{
    
    private Sucursal sucursal;
    private boolean control = true;
    private  ServerSocket serverSocket;
    private ObjectInputStream input = null;
    private int puerto;
    
    
    public HiloSucursalEspera(Sucursal sucursal,int puertoEspera){
        
        this.sucursal = sucursal;
        this.puerto = puertoEspera;
        iniciar(puertoEspera);
    }
    
    public  void iniciar(int puerto){
        try {
            serverSocket = new ServerSocket(puerto);
        } catch (IOException ex) {
            Logger.getLogger(HiloSucursalEspera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run(){
        System.out.println("Empezando hilo nodo");
        while (control){
            try {
                recibir(serverSocket.accept());
            } catch (IOException ex) {
                Logger.getLogger(HiloSucursalEspera.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
    }

    public void recibir (Socket socket){
     
        try {
             input = new ObjectInputStream(socket.getInputStream());
            Object datoRecibido = input.readObject();
           System.out.println("lo que recibi..." + datoRecibido.toString()+" desde el puerto: "+this.puerto);
            if (datoRecibido instanceof String) {
                String mensaje = (String) datoRecibido;
                if (mensaje.contains("hola")){
                    if (socket.getInetAddress().toString().contentEquals(sucursal.getIpAnterior())){
                        System.out.println("se envia a la siguiente sucursal");
                        sucursal.enviar(new Socket(sucursal.getIpSiguiente(), sucursal.getPuertoSiguiente()), datoRecibido);
                    }
                    else {
                        System.out.println("se envia a la sucursal anterior");
                        sucursal.enviar(new Socket(sucursal.getIpAnterior(), sucursal.getPuertoAnterior()), datoRecibido);
                    }
                    
                    System.out.println("enviando...");
//                    enviar("hola",new Socket("192.168.1.101", 2000));
                    System.out.println("puerto de recibir: "+socket.getPort());
                    sucursal.enviar(new Socket("localhost",3000),"hola");
                }
                    
            } 
            else {
                System.out.println("Se recibio un objeto que no corresponde" + "a la clase Persona");
            }
        }
        catch(Exception e){
            System.out.println("explota en el try de recibir? "+e.getMessage());
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
}
