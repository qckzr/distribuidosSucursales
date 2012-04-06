/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sucursales;


import Servidor.Paquete;
import java.io.IOException;
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
public class ArchivoSucursal {
    
    private String rutaArchivo;

    public ArchivoSucursal() {
        rutaArchivo = "archivos/archivoSucursal.xml";
    }
    
    public Sucursal cargarSucursal(){
        Sucursal sucursal = new Sucursal();
        SAXBuilder builder = new SAXBuilder();
        Document doc = new Document();
        try {
            doc = builder.build(rutaArchivo);
        } catch (JDOMException ex) {
            Logger.getLogger(ArchivoSucursal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoSucursal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (doc != null){
       
            Element elemento = (Element) doc.getRootElement();
            
            sucursal.setIdSucursal(Integer.parseInt(elemento.getChild("id_sucursal").getText()));
      
            sucursal.setIpSucursal(elemento.getChild("ip_sucursal").getText());
      
            sucursal.setIpServidor(elemento.getChild("ip_servidor").getText());
      
            sucursal.setPuertoServidor(Integer.parseInt(elemento.getChild("puerto_servidor").getText()));
      
            sucursal.setIpAnterior(elemento.getChild("ip_anterior").getText());
      
            sucursal.setPuertoAnterior(Integer.parseInt(elemento.getChild("puerto_anterior").getText()));
      
            sucursal.setIpSiguiente(elemento.getChild("ip_siguiente").getText());
      
            sucursal.setPuertoSiguiente(Integer.parseInt(elemento.getChild("puerto_siguiente").getText()));
      
            sucursal.setCantidadTotalPaquetes(Integer.parseInt(elemento.getChild("cantidad_total_paquetes").getText()));
      
            List<Element> paquetes = elemento.getChild("paquetes_enviar").getChildren();
      
            for (Element element : paquetes) {
                
                Paquete nuevoPaquete = new Paquete(sucursal.getIpSucursal(),element.getChild("ip_destino").getText(), element.getChild("mensaje").getText(), true);
                sucursal.getPaquetesPorEnviar().add(nuevoPaquete);
            }
        }
        else {
            System.out.println("Problemas cargando el xml de sucursales");
        }
        return sucursal;
    }
    
    
    
    
    
}
