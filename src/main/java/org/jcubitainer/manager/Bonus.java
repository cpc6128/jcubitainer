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

import org.jcubitainer.meta.MetaInfo;

public class Bonus {

    MetaInfo mi = null;

    /**
     * 
     */
    public Bonus(MetaInfo pdi) {
        super();
        mi = pdi;
        clean();
    }

    /**
     * 
     */
    public void clean() {
        mi.setBonus_des(1);
        mi.setBonus_sup(1);
        mi.setBonus_slow(1);
    }

    public boolean canDeletePiece() {
        return mi.getBonus_des() > 0;
    }

    public void deletePiece() {
        mi.setBonus_des(mi.getBonus_des() - 1);
    }

    public void bonusDeletePiece() {
        mi.setBonus_des(mi.getBonus_des() + 1);
    }

    public boolean canDeleteLine() {
        return mi.getBonus_sup() > 0;
    }

    public void deleteLine() {
        mi.setBonus_sup(mi.getBonus_sup() - 1);
    }

    public void bonusDeleteLine() {
        mi.setBonus_sup(mi.getBonus_sup() + 1);
    }

    public boolean canSlow() {
        return mi.getBonus_slow() > 0;
    }

    public void slow() {
        mi.setBonus_slow(mi.getBonus_slow() - 1);
    }

    public void stopSlow() {
        //di.activeSlow(false);
    }

    public void bonusSlow() {
        mi.setBonus_slow(mi.getBonus_slow() + 1);
    }

    public void newBonusByLine(int line) {
        if (line == 2) bonusDeletePiece();
        if (line == 3) bonusDeleteLine();
        if (line == 4) bonusSlow();
        if (line == 5) {
            bonusDeletePiece();
            bonusSlow();
        }
        if (line == 6) {
            bonusDeleteLine();
            bonusSlow();
        }
        if (line > 6) {
            bonusDeletePiece();
            bonusDeleteLine();
            bonusSlow();
        }
    }
}