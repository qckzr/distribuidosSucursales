/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sucursales;

import Servidor.Transporte;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

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
    private JLabel labelTransporte;
    
    
    public HiloSucursalEspera(Sucursal sucursal,int puertoEspera,JLabel labelTransporte){
        
        this.sucursal = sucursal;
        this.puerto = puertoEspera;
        this.labelTransporte = labelTransporte;
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
   
           this.labelTransporte.setText("EN SUCURSAL");
            if (datoRecibido instanceof Transporte) {
                Transporte transporte = (Transporte) datoRecibido;
                System.out.println("recibiendo un objeto transporte");
                System.out.println("IP DE DONDE LO RECIBO: "+socket.getInetAddress().toString().substring(1));
               
               System.out.println("durmiendo por 1 segundo");
                tiempoTransporte(0);
                
                    
                    buscarPaquetes(transporte);
                    enviarPaquetes(transporte);
                    if (transporte.getEsParaEnviar()){
                        System.out.println("Se duerme el hilo");
                        System.out.println("transporte paquete 1:"+transporte.getPaquete1().getMensaje());
                        System.out.println("transporte paquete 2:"+transporte.getPaquete2().getMensaje());
                           tiempoTransporte(1);

                        sucursal.enviar(new Socket(sucursal.getIpSiguiente(), sucursal.getPuertoSiguiente()), transporte);
                        this.labelTransporte.setText("-");
                    }
                    else {
                        tiempoTransporte(2);
                        System.out.println("se envia a la sucursal anterior");
                        sucursal.enviar(new Socket(sucursal.getIpAnterior(), sucursal.getPuertoAnterior()), datoRecibido);
                        this.labelTransporte.setText("-");
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
    

    
    
    public void buscarPaquetes(Transporte transporte){
        
        if(transporte.getPaquete1()!=null){
            if (transporte.getPaquete1().getIpDestino().contentEquals(sucursal.getIpSucursal())){
                sucursal.getPaquetesRecibidos().add(transporte.getPaquete1());
                transporte.setPaquete1(null);
            }
        }
        if (transporte.getPaquete2()!=null){
            if (transporte.getPaquete2().getIpDestino().contentEquals(sucursal.getIpSucursal())){
                sucursal.getPaquetesRecibidos().add(transporte.getPaquete2());
                transporte.setPaquete2(null);
            }
        }
        
    }
        
    public void enviarPaquetes(Transporte transporte){
        
        if(sucursal.getPaquetesPorEnviar().size()>0){
          
                
                if (transporte.getPaquete1()==null){
                    transporte.setPaquete1(sucursal.getPaquetesPorEnviar().get(0));
                    sucursal.getPaquetesPorEnviar().remove(0);
                }
                if (transporte.getPaquete2()==null){
                    transporte.setPaquete2(sucursal.getPaquetesPorEnviar().get(0));
                    sucursal.getPaquetesPorEnviar().remove(0);
                
                }
            
        }
                
    }
    
    
    public void tiempoTransporte(int opcion){
        try {
            
            Thread a = new Thread();
            a.start();
            
            if (opcion==0)
                a.sleep(1000);
            else if (opcion==1)
                a.sleep(5000);
            else a.sleep(10000);
        } 
        catch (InterruptedException ex) {
            Logger.getLogger(HiloSucursalEspera.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
