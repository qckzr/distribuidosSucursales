/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sucursales;

import Servidor.Paquete;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

/**
 *
 * @author hector
 */


public class Sucursal implements Serializable{
    
    private  Socket conexion = null;

    private  String ipSucursal = "localhost";
    
    private  int puertoAnterior;
    
    private  int puertoSiguiente;
    
    private  String ipSiguiente;
    
    private  String ipAnterior;
    
    private List<Paquete> paquetesRecibidos;
    
    private List<Paquete> paquetesPorEnviar;
    
    private String ipServidor;
    
    private int puertoServidor;
    
    private int cantidadTotalPaquetes;
    
    private JLabel labelTransporteEnviar;
    
    private JLabel labelTransporteRecepcion;
    
    private int idSucursal;
    
    
    public Sucursal(){
        paquetesPorEnviar = new ArrayList<Paquete>();
        paquetesRecibidos = new ArrayList<Paquete>();
    }
    
    public Sucursal(int puertoEntradaAnterior, int puertoEntradaSiguiente, JLabel labelTransporteEnviar,JLabel labelTransporteRecepcion){
        this.puertoAnterior = puertoEntradaAnterior;
        this.puertoSiguiente = puertoEntradaSiguiente;
        paquetesPorEnviar = new ArrayList<Paquete>();
        paquetesRecibidos = new ArrayList<Paquete>();
        this.labelTransporteEnviar = labelTransporteEnviar;
        this.labelTransporteRecepcion = labelTransporteRecepcion;
    }
    

    public  Socket getConexion() {
        return conexion;
    }
    public  void setConexion(Socket conexion) {
        this.conexion = conexion;
    }

    public  String getIpSucursal() {
        return ipSucursal;
    }

    public  void setIpSucursal(String ipSucursal) {
        this.ipSucursal = ipSucursal;
    }

 


    public  String getIpAnterior() {
        return this.ipAnterior;
    }

    public  void setIpAnterior(String ipAnterior) {
        this.ipAnterior = ipAnterior;
    }

    public  String getIpSiguiente() {
        return ipSiguiente;
    }

    public  void setIpSiguiente(String ipSiguiente) {
        this.ipSiguiente = ipSiguiente;
    }

    public int getPuertoAnterior() {
        return puertoAnterior;
    }

    public void setPuertoAnterior(int puertoAnterior) {
        this.puertoAnterior = puertoAnterior;
    }

    public int getPuertoSiguiente() {
        return puertoSiguiente;
    }

    public void setPuertoSiguiente(int puertoSiguiente) {
        this.puertoSiguiente = puertoSiguiente;
    }

    public List<Paquete> getPaquetesPorEnviar() {
        return paquetesPorEnviar;
    }

    public void setPaquetesPorEnviar(List<Paquete> paquetesPorEnviar) {
        this.paquetesPorEnviar = paquetesPorEnviar;
    }

    public List<Paquete> getPaquetesRecibidos() {
        return paquetesRecibidos;
    }

    public void setPaquetesRecibidos(List<Paquete> paquetesRecibidos) {
        this.paquetesRecibidos = paquetesRecibidos;
    }

    public int getCantidadTotalPaquetes() {
        return cantidadTotalPaquetes;
    }

    public void setCantidadTotalPaquetes(int cantidadTotalPaquetes) {
        this.cantidadTotalPaquetes = cantidadTotalPaquetes;
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public int getPuertoServidor() {
        return puertoServidor;
    }

    public void setPuertoServidor(int puertoServidor) {
        this.puertoServidor = puertoServidor;
    }

    public JLabel getLabelTransporteEnviar() {
        return labelTransporteEnviar;
    }

    public void setLabelTransporteEnviar(JLabel labelTransporteEnviar) {
        this.labelTransporteEnviar = labelTransporteEnviar;
    }

    public JLabel getLabelTransporteRecepcion() {
        return labelTransporteRecepcion;
    }

    public void setLabelTransporteRecepcion(JLabel labelTransporteRecepcion) {
        this.labelTransporteRecepcion = labelTransporteRecepcion;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

   
    


 
    



//    public  boolean conectar(Socket conexion){
//
//        try {
//           Socket a = new Socket(ip, port);
//           setConexion(a);
//            return true;
//        } catch (Exception e) {
//            System.out.println("no se logro crear la conexion:\n" + e.getMessage());
//            return false;
//        }
//
//    }
    


   

    public  void desconectar(Socket conexion){

        if (conexion!=null) {
            try {
                 conexion.close();
            } catch (Exception e) {
                System.out.println("no se logro cerrar la conexion:\n" + e.getMessage());
            }
        }
    }

    public  OutputStream getOutput(Socket conexion){

        if (conexion!=null) {
            try {
                return conexion.getOutputStream();
            } catch (IOException ex) {
                System.out.println("error obteniendo input stream:\n" + ex.getMessage());
                return null;
            }
        }else{
            return null;
        }
    }

    public  void enviar(Socket conexion,Object object){
        try {
                ObjectOutputStream output =
                        new ObjectOutputStream(this.getOutput(conexion));
                output.flush();
                output.writeObject(object);
                output.close();
                this.desconectar(conexion);
                System.out.println("se enviaron los datos?");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    



    
    public void iniciarEspera(){
        HiloSucursalEspera clienteEspera1 = new HiloSucursalEspera(this,puertoAnterior,labelTransporteEnviar);
        HiloSucursalEspera clienteEspera2 = new HiloSucursalEspera(this,puertoSiguiente,labelTransporteRecepcion);
        clienteEspera1.start();
        clienteEspera2.start();
    }
    
    
        
}
