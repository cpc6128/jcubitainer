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

import java.io.PrintStream;

import javax.swing.JOptionPane;
import org.jcubitainer.tools.Messages;

public class ErrorPrintStream extends PrintStream {

    Object[] options = { Messages.getString("ErrorPrintStream.continuer"),
            Messages.getString("ErrorPrintStream.plus_prevenir"),
            Messages.getString("ErrorPrintStream.quitter") }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    // Pour avoir une seule fenêtre ouverte :
    boolean open = false;

    /**
     * @param out
     */
    public ErrorPrintStream() {
        super(new ErrorOutputStream());
    }

    public void print(Object obj) {
        super.print(obj);

        if (!open) {
            if (obj instanceof Exception) {
                Exception e = (Exception) obj;
                StackTraceElement[] stack = e.getStackTrace();
                for (int i = 0; i < stack.length; i++) {
                    StackTraceElement element = stack[i];
                    if (element.toString().indexOf("org.jcubitainer") == -1){
                        return;
                    }
                }
            }

            open = true;
            String message = obj.toString();
            int n = JOptionPane.showOptionDialog(null, Messages
                    .getString("ErrorPrintStream.erreur_survenue")
                    + message, Messages
                    .getString("ErrorPrintStream.titre_erreur"), //$NON-NLS-1$ //$NON-NLS-2$
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            open = n == 1;
            if (n == 2)
                System.exit(1);

        }
    }

}