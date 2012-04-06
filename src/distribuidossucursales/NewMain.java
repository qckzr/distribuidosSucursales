/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidossucursales;

import Servidor.GUI_Servidor;
import Servidor.Servidor;

/**
 *
 * @author hector
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Servidor server = new Servidor(1337);
//        server.iniciar();
        GUI_Servidor gui = new GUI_Servidor();
        gui.setVisible(true);
    }
}
