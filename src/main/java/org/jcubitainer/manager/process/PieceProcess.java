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

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.meta.MetaPiece;
import org.jcubitainer.tools.Process;

public class PieceProcess extends Process {

    MoveBoard dbox = null;

    PieceFactory dpf = null;

    private static PieceProcess this_ = null;

    /**
     *  
     */
    public PieceProcess(MoveBoard mb) {
        super(5000);
        dbox = mb;
        dpf = PieceFactory.getInstance();
        setPriority(MAX_PRIORITY);
        this_ = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jcubitainer.manager.tools.Process#action()
     */
    public void action() throws InterruptedException {
        synchronized (dbox.getMetabox().getPieces_mouvantes()) {
            MetaPiece mp = dpf.getDisplayPiece(dbox.getMetaInfo().getNiveau());
            ((MoveBoard) dbox).getMovepiece().addPiece(mp);
        }
    }

    public static PieceProcess getPieceService() {
        return this_;
    }

}