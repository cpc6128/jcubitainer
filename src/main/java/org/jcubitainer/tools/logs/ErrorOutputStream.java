/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
 * Author : Mounès Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/                                  *
 *                                                                     *
 * This code is released under the GNU GPL license, version 2 or       *
 * later, for educational and non-commercial purposes only.            *
 * If any part of the code is to be included in a commercial           *
 * software, please contact us first for a clearance at                *
 * metalm@users.berlios.de                                             *
 *                                                                     *
 *   This notice must remain intact in all copies of this code.        *
 *   This code is distributed WITHOUT ANY WARRANTY OF ANY KIND.        *
 *   The GNU GPL license can be found at :                             *
 *           http://www.gnu.org/copyleft/gpl.html                      *
 *                                                                     *
 ***********************************************************************/

/* History & changes **************************************************
 *                                                                     *
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.tools.logs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.jcubitainer.manager.Configuration;

public class ErrorOutputStream extends OutputStream {

    FileOutputStream file = null;

    boolean first = true;

    /**
     *  
     */
    public ErrorOutputStream() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.OutputStream#write(int)
     */
    public void write(int b) throws IOException {
        if (first) {
            first = false;
            System.out.println("** Création du fichier de logs !!");
            try {
                file = new FileOutputStream(new File(System
                        .getProperty("user.home")
                        + File.separator
                        + Configuration.DIR
                        + File.separator
                        + "erreurs.log"));

                // Ajout d'info en plus :
                String info = new Date().toString();
                // Version JDK :
                info += "\n" + "java.version: "
                        + System.getProperty("java.version");
                info += "\n" + "java.vm.version: "
                        + System.getProperty("java.vm.version");
                info += "\n" + "java.vm.vendor: "
                        + System.getProperty("java.vm.vendor");
                info += "\n" + "java.vm.name: "
                        + System.getProperty("java.vm.name") + "\n";
                write(info.getBytes());
            } catch (Exception e) {
                System.out
                        .println("** Impossible de créer le fichier de logs !!"
                                + e.getMessage());
            }
        }

        if (file != null) file.write(b);
    }
}