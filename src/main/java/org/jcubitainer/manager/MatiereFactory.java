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

package org.jcubitainer.manager;

import java.awt.Image;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.meta.MetaPiece;


public class MatiereFactory {

    public static Image getColorByMatiere(int pmatiere, boolean fix) {
        if (pmatiere > MetaPiece.MATIERE_MAX) pmatiere = MetaPiece.MATIERE_MAX;
        // En fonction du thème en cours :
        pmatiere = pmatiere % ThemeManager.getCurrent().getMAX_MATIERE();
        if (fix)
            return ThemeManager.getCurrent().getImage("if" + (pmatiere));
        else
            return ThemeManager.getCurrent().getImage("i" + (pmatiere));
    }

    public static Image getActiveColor(int pmatiere) {
        if (pmatiere > MetaPiece.MATIERE_MAX) pmatiere = MetaPiece.MATIERE_MAX;
        // En fonction du thème en cours :
        pmatiere = pmatiere % ThemeManager.getCurrent().getMAX_MATIERE();
        return ThemeManager.getCurrent().getImage("ia" + (pmatiere));
    }

    public static Image getOmbre() {
        return ThemeManager.getCurrent().getImage("iombre");
    }

}