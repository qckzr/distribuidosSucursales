/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Sucursales.ArchivoSucursal;
import Sucursales.Sucursal;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
    
    
    private List<Sucursal> listaSucursales;
    
    private Transporte transporteEnviar;
    
    private Transporte transporteRecepcion;

    public Servidor() {
        listaSucursales = new ArrayList<Sucursal>();
        transporteEnviar = new Transporte();
        transporteEnviar.setEsParaEnviar(Boolean.TRUE);
        transporteRecepcion = new Transporte();
        transporteRecepcion.setEsParaEnviar(Boolean.FALSE);
    }
    
    
    
    public Servidor(int puerto)
    {
        try {
            this.puerto = puerto;
            serverSocket = new ServerSocket(this.puerto);
            listaSucursales = new ArrayList<Sucursal>();
            transporteEnviar = new Transporte();
            transporteRecepcion = new Transporte();

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public List<Sucursal> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursal> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Transporte getTransporteEnviar() {
        return transporteEnviar;
    }

    public void setTransporteEnviar(Transporte transporteEnviar) {
        this.transporteEnviar = transporteEnviar;
    }

    public Transporte getTransporteRecepcion() {
        return transporteRecepcion;
    }

    public void setTransporteRecepcion(Transporte transporteRecepcion) {
        this.transporteRecepcion = transporteRecepcion;
    }
    
    
    
    

    @Override
    public void run()
    {
        System.out.println("Empezando servidor");
        while(true)
        {
            try {
       //         HiloServidor hilo = new HiloServidor(serverSocket.accept(),this);
         //       hilo.start();
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
      //     System.out.println("lo que recibi..." + datoRecibido.toString());
            if (datoRecibido instanceof String) {
                String mensaje = (String) datoRecibido;
                if (mensaje.contains("hola"))
          //          enviar("CHAO", s1);
                    System.out.println("Y AHORA QUE?");
            }
            else if (datoRecibido instanceof Sucursal){
                getListaSucursales().add((Sucursal)datoRecibido);
                System.out.println("Recibo una sucursal y la agrego a la lista");
            }
            else if (datoRecibido instanceof ArchivoSucursal){
                System.out.println("asdasd es un archivo");
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

    
    

