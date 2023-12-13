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

public class MetaOmbre {

    private int[] format = null;

    private MetaBoard mb = null;

    /**
     * 
     */
    public MetaOmbre(MetaBoard pmb) {
        super();
        mb = pmb;
        format = new int[mb.getLargeur()];
    }

    public int[] getFormat() {
        return format;
    }

    public int[] setFormat() {
        // Supprimer l'ombre :
        for (int i = 0; i < format.length; i++)
            format[i] = 0;
        MetaPiece mp = mb.getCurrentPiece();
        if (mp == null) return null;
        // On recherche les positions de l'ombre :
        int[][] tab = mb.getPieces_mortes();

        int deb = mp.getPosition_x();
        int fin = mp.getPosition_x() + mp.getLargeur();
        if (fin > tab[0].length) fin = tab[0].length;
        //tab[x].length

        for (int x = mp.getPosition_y() + 1; x < tab.length; x++) {
            for (int y = deb; y < fin; y++)
                if (tab[x][y] != 0 && format[y] == 0)
                    format[y] = x - 1;
                else if (x == (tab.length - 1) && format[y] == 0)
                        format[y] = tab.length - 1;
        }
        return format;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(format.length);
        for (int i = 0; i < format.length; i++)
            sb.append(format[i]);
        return sb.toString();
    }
}