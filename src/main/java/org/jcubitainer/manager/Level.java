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

import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.manager.process.PieceProcess;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.meta.MetaTexte;
import org.jcubitainer.tools.Messages;

public class Level {

    MetaInfo mi = null;

    MetaTexte mt = null;

    /**
     * 
     */
    public Level(MetaTexte pmt, MetaInfo pdi) {
        super();
        mi = pdi;
        mt = pmt;
    }

    public void setLevel(ChuteProcess cs) {
        int score = mi.getScore();
        int level = mi.getNiveau();
        //System.out.println(score);
        PieceProcess ps = PieceProcess.getPieceService();
        if (score > mi.getNiveauSuivant()) {
            // Pas de changement de niveau la première fois :
            if (mi.getNiveauSuivant() != 0 && !NetworkManager.isNetworkOn())
                mi.setNiveau(mi.getNiveau() + 1);
            mi.setNiveauSuivant(mi.getNiveauSuivant() + 2000);
            mt.setTexte(Messages.getString("Level.niveau") + mi.getNiveau()
                    + " !"); //$NON-NLS-1$ //$NON-NLS-2$
            setSpeedByLevel(cs);
            return;
        }
    }

    public void setSpeedByLevel(ChuteProcess cs) {
        int level = mi.getNiveau();
        PieceProcess ps = PieceProcess.getPieceService();
        if (level == 1) {
            cs.setWait(900);
            ps.setWait(5000);
            return;
        }
        if (level == 2) {
            cs.setWait(750);
            ps.setWait(4500);
            return;
        }
        if (level == 3) {
            cs.setWait(550);
            ps.setWait(4000);
            return;
        }
        if (level == 4) {
            cs.setWait(400);
            ps.setWait(3500);
            return;
        }
        if (level == 5) {
            cs.setWait(350);
            ps.setWait(3000);
            return;
        }
        if (level == 6) {
            cs.setWait(300);
            ps.setWait(2500);
            return;
        }
        if (level == 7) {
            cs.setWait(275);
            ps.setWait(2000);
            return;
        }
        if (level == 8) {
            cs.setWait(250);
            ps.setWait(1500);
            return;
        }
        if (level == 9) {
            cs.setWait(200);
            ps.setWait(1500);
            return;
        } else {
            cs.setWait(150);
            ps.setWait(1000);
            return;
        }
    }
}