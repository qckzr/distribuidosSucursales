/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidossucursales;

import Sucursales.VentanaSucursal;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author hector
 */
public class DistribuidosSucursales {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        // TODO code application logic here
        
        new VentanaSucursal().setVisible(true);
       
        
//        Sucursal s = new Sucursal(2000, 2001);
//        s.setIpSucursal("192.168.1.104");
//        s.setIpAnterior("192.168.1.111");
//        s.setIpSiguiente("192.168.1.113");
//        for (int i = 0; i < 5; i++) {
//            Paquete p = new Paquete(s.getIpSusucursal(), s.getIpSiguiente(),"hola mundo"+i, true);
//            s.getPaquetesPorEnviar().add(p);
//        }
//        for (int i = 0; i < 2; i++) {
//            Paquete p = new Paquete(s.getIpAnterior(), s.getIpSusucursal(),"hola mundo"+i, false);
//            s.getPaquetesRecibidos().add(p);
//            
//        }
//        new VentanaSucursal(s).setVisible(true);
//        s.iniciarEspera();
//        Thread a = new Thread();
//        a.start();
//        a.sleep(5000);
//        
//        s.enviar(new Socket(s.getIpSiguiente(), s.getPuertoSiguiente()), "hola");
//        Thread b = new Thread();
//        b.start();
//        b.sleep(10000);
//        s.enviar(new Socket(s.getIpAnterior(), s.getPuertoAnterior()), "hola");
        
        
        
        
//        
//        Sucursal s = new Sucursal(2000,2001, null, null);
//        s.setIdSucursal(1);
//        s.setIpSucursal("localhost");
//        s.setIpAnterior("localhost");
//        Sucursal a = new Sucursal(3000, 3001, null, null);
//        ArchivoSucursal b = new ArchivoSucursal();
//        s.iniciarEspera();
//        s.enviar(new Socket("localhost",1337),s);
    }
}
