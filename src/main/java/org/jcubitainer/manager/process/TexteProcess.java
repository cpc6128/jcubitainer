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

import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaTexte;
import org.jcubitainer.tools.Process;

public class TexteProcess extends Process {

    private int boucle = 0;

    MetaTexte mt = null;

    public static final float MAX_BOUCLE = 40f;

    public TexteProcess(MetaBoard mb) {
        super(100);
        mt = mb.getTexte();
    }

    /* (non-Javadoc)
     * @see org.jcubitainer.tools.Process#action()
     */
    public void action() throws InterruptedException {
        if (boucle > 0) {
            boucle--;
            float alpha = ((float) boucle) / MAX_BOUCLE;
            if (alpha > 0.5) alpha = 0.5f;
            mt.setAlpha(alpha);
            mt.setDisplay(true);
        } else {
            mt.setDisplay(false);
            pause();
        }
    }

    /**
     * @param i
     */
    public void setBoucle(int i) {
        boucle = i;
    }

}