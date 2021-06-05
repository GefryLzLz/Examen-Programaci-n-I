
package Principal;

import Gui.JFPrincipal;


public class Main {

    // El Mian nos sirve para mostrar lo que tenemos en nuestro JFPrincipal
    public static void main(String[] args) {
        // TODO code application logic here
        JFPrincipal frmprincipal= new JFPrincipal();
        frmprincipal.setExtendedState(frmprincipal.MAXIMIZED_BOTH);
        frmprincipal.show();
    }
    
}
