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

package org.jcubitainer.display;

import java.awt.Graphics2D;
import java.awt.Image;

import org.jcubitainer.manager.MatiereFactory;
import org.jcubitainer.meta.MetaPiece;

public class DisplayPiece extends MetaPiece {

    public final static int LARGEUR_PIECE = 12;

    public final static int HAUTEUR_PIECE = 12;

    /**
     * @param pformat
     * @param pmatiere
     */
    public DisplayPiece(boolean[][] pformat, int pmatiere) {
        super(pformat, pmatiere);
    }

    public void drawPiece(boolean active, Graphics2D g2) {
        Image peinture = null;
        if (!active)
            peinture = MatiereFactory.getActiveColor(getMatiere());
        else
            peinture = MatiereFactory.getColorByMatiere(getMatiere(), false);

        int pos_y = DisplayBoard.deb_y + getPosition_y()
                * DisplayPiece.HAUTEUR_PIECE;

        boolean[][] format = getFormat();
        for (int x = 0; x < format.length; x++) {
            int pos_x = DisplayBoard.deb_x + getPosition_x()
                    * DisplayPiece.LARGEUR_PIECE;
            boolean[] ligne = format[x];
            for (int y = 0; y < ligne.length; y++) {
                if (ligne[y]) g2.drawImage(peinture, pos_x, pos_y, null);

                pos_x = pos_x + DisplayPiece.LARGEUR_PIECE;
            }
            pos_y = pos_y + DisplayPiece.HAUTEUR_PIECE;
        }
    }
}