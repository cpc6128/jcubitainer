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

package org.jcubitainer.tools;

public class TableauTools {

    /**
     *  
     */
    public static boolean[][] rotationLeft(boolean[][] tab) {
        boolean[][] nouveau = new boolean[tab[0].length][tab.length];
        for (int x = 0; x < tab.length; x++) {
            boolean[] ligne = tab[x];
            for (int y = 0; y < ligne.length; y++) {
                nouveau[y][tab.length - x - 1] = ligne[y];
            }
        }
        return nouveau;
    }

    public static int[][] removeLine(int[][] tab, int line) {
        for (int x = line; x > 1; x--)
            for (int y = 0; y < tab[x].length; y++)
                tab[x][y] = tab[x - 1][y];
        return tab;
    }

    public static int[][] upAll(int[][] tab) {
        for (int x = 0; x < tab.length - 1; x++)
            for (int y = 0; y < tab[x].length; y++)
                tab[x][y] = tab[x + 1][y];
        // Il faut mettre des cases vides sur la dernière :
        for (int y = 0; y < tab[tab.length - 1].length; y++)
            tab[tab.length - 1][y] = 0;
        return tab;
    }

    public static int[][] swingLast(int[][] tab, int max) {
        int total = 0;
        for (int y = 0; y < tab[tab.length - 1].length; y++) {
            if (Math.random() > 0.2)
                tab[tab.length - 1][y] = (int) (Math.random() * (double) (max + 1));
            else
                tab[tab.length - 1][y] = 0;
            // On veut calculer le nombre de carré dans la ligne :
            total += (tab[tab.length - 1][y]) > 0 ? 1 : 0;
        }
        // Si le nombre de carré est trop important, on recommence :
        if (total > tab[0].length - 5) return swingLast(tab, max);
        return tab;
    }
}