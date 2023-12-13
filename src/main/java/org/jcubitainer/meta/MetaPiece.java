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

package org.jcubitainer.meta;

import org.jcubitainer.tools.TableauTools;

public class MetaPiece {

    private int position_x;

    private int position_y;

    private boolean[][] format = null;

    private int matiere = 0;

    private int largeur = 0;

    private int hauteur = 0;

    public static final int MATIERE_MAX = 100;

    /**
     *  
     */
    public MetaPiece(boolean[][] pformat, int pmatiere) {
        super();
        setFormat(pformat);
        matiere = pmatiere;

    }

    /**
     * @return
     */
    public int getPosition_x() {
        return position_x;
    }

    /**
     * @return
     */
    public int getPosition_y() {
        return position_y;
    }

    /**
     * @param i
     */
    public void setPosition_x(int i) {
        position_x = i;
    }

    /**
     * @param i
     */
    public void setPosition_y(int i) {
        position_y = i;
    }

    /**
     * @return
     */
    public boolean[][] getFormat() {
        return format;
    }

    /**
     * @return
     */
    public int getMatiere() {
        return matiere;
    }

    /**
     * @return
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * @return
     */
    public int getLargeur() {
        return largeur;
    }

    public void rotationLeft() {
        setFormat(TableauTools.rotationLeft(format));
    }

    /**
     * @param bs
     */
    public void setFormat(boolean[][] bs) {
        format = bs;
        largeur = format[0].length;
        hauteur = format.length;
    }

}