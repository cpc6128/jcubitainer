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

package org.jcubitainer.manager.process;

import java.util.List;

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.tools.Process;

public class ChuteProcess extends Process {

    MetaBoard metabox = null;

    MoveBoard dbox = null;

    public final static int SPEED_SLOW = 0;

    public final static int SPEED_NORMAL = 1;

    public final static int SPEED_FAST = 2;

    private static int speed = SPEED_NORMAL;

    int boucle = 0;

    /**
     *  
     */
    public ChuteProcess(MoveBoard pb) {
        super(900);//1800
        metabox = pb.getMetabox();
        dbox = pb;
        setPriority(MAX_PRIORITY);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jcubitainer.manager.tools.Process#action()
     */
    public void action() throws InterruptedException {

        boolean descendre = true;

        switch (speed) {
        case SPEED_SLOW:
            // Descendre 1 temps sur 4
            boucle--;
            if (boucle < 0) {
                speed = SPEED_NORMAL;
                Game.getGameService().getBonus().stopSlow();
                boucle = 0;
            } else
                descendre = boucle % 4 == 0;
            break;
        case SPEED_FAST:
            // Descendre à chaque fois
            boucle--;
            if (boucle < 0) {
                speed = SPEED_NORMAL;
                Game.getGameService().getBonus().stopSlow();
                boucle = 0;
            }
            break;
        default:
            // Descendre une fois sur deux
            descendre = boucle % 2 == 0;
            boucle--;
            if (boucle < 0)
                boucle = 1;
            break;
        }

        dbox.getMetaInfo().addScore(1);

        synchronized (metabox.getPieces_mouvantes()) {
            List liste = metabox.getPieces_mouvantes();
            ((MoveBoard) dbox).getMovepiece().downPieces(liste, descendre);
        }
        //dbox.repaint();
    }

    public void setSlow() {
        speed = SPEED_SLOW;
        boucle = boucle + 120;
    }

    public void setFast() {
        speed = SPEED_FAST;
        boucle = boucle + 60;
    }

    public void setNormal() {
        speed = SPEED_NORMAL;
        boucle = 0;
    }

}