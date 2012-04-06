/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Servidor.Paquete;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author hector
 */
public class ArchivoServidor {
    
    private String rutaArchivo;

    public ArchivoServidor() {
        rutaArchivo = "archivos/archivoServidor.xml";
    }
    
    public Servidor cargarServidor(){
        
        Servidor servidor = new Servidor();
        SAXBuilder builder = new SAXBuilder();
        Document doc = new Document();
        try {
            doc = builder.build(rutaArchivo);
        } catch (JDOMException ex) {
            Logger.getLogger(ArchivoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (doc!=null){
            Element elemento = doc.getRootElement();
            servidor.setPuerto(Integer.parseInt(elemento.getChild("puerto_servidor").getText()));
           
            List<Element> paquetes_transporte_enviar = doc.getRootElement().getChild("transporte_enviar").getChildren();
            
            for (Element paquetes : paquetes_transporte_enviar) {
                Paquete paquete = new Paquete(paquetes.getChild("ip_origen").getText(),
                        paquetes.getChild("ip_destino").getText(),
                        paquetes.getChild("mensaje").getText(), true);
               
                if (servidor.getTransporteEnviar().getPaquete1()==null)
                    servidor.getTransporteEnviar().setPaquete1(paquete);
                else 
                    servidor.getTransporteEnviar().setPaquete2(paquete);
            }
            
            List<Element> paquetes_transporte_recepcion = doc.getRootElement().getChild("transporte_recepcion").getChildren();
            
            for (Element paquetes : paquetes_transporte_recepcion) {
                Paquete paquete = new Paquete(paquetes.getChild("ip_origen").getText(),
                        paquetes.getChild("ip_destino").getText(),
                        paquetes.getChild("mensaje").getText(), false);

                if (servidor.getTransporteRecepcion().getPaquete1()==null)
                    servidor.getTransporteRecepcion().setPaquete1(paquete);
                else 
                    servidor.getTransporteRecepcion().setPaquete2(paquete);
            }
            
        }
        try {
            servidor.setServerSocket(new ServerSocket(servidor.getPuerto()));
        } catch (IOException ex) {
            Logger.getLogger(ArchivoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  servidor;
    }
    
    
    
}
