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
 ******** May 6, 2004 **************************************************
 *   - Ajout du drage'n'drop pour les thèmes                           *
 ******** May 12, 2004 **************************************************
 *   - Amélioration de la gestion des touches                          *
 ***********************************************************************/

package org.jcubitainer.key;

import java.awt.dnd.DropTarget;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.display.theme.ThemeError;
import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Bonus;
import org.jcubitainer.manager.Configuration;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.meta.MetaPiece;
import org.jcubitainer.move.MovePiece;
import org.jcubitainer.sound.InterfaceMusique;
import org.jcubitainer.tools.Messages;

public class MoveBoard extends DisplayBoard implements MouseListener,
        KeyListener, Runnable {

    // http://forum.java.sun.com/thread.jsp?forum=406&thread=478606

    private final long KEY_DELAY = 70;

    private MovePiece movepiece = null;

    private boolean checkMove = false;

    Thread thread = null;

    int keys[] = new int[256];

    private final int TOUCHE_NON_ACTIVE = 0;

    private final int TOUCHE_NON_ACTIVE_ET_NON_HONOREE = 1;

    private final int TOUCHE_ACTIVE_ET_HONOREE = 2;

    private final int TOUCHE_ACTIVE_ET_NON_HONOREE = 3;

    /**
     * @param pmetabox
     */
    public MoveBoard(MetaBoard pmetabox, MetaInfo pmi) {
        super(pmetabox, pmi);
        addKeyListener(this);
        movepiece = new MovePiece(this);
        DropTarget dropTarget = new DropTarget(this,
                new DragAndDropBoardListener());
        thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent arg0) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent arg0) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent arg0) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent arg0) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent arg0) {
    }

    /*
     * (non-Javadoc)
     *  
     */
    public void run() {

        while (true) {

            if (isDown(KeyEvent.VK_T)) {
                up(KeyEvent.VK_T);
                synchronized (getMetabox().getPieces_mouvantes()) {
                    try {
                        ThemeManager.swithTheme();
                    } catch (ThemeError e) {
                        e.printStackTrace();
                    }
                }
            }

            if (isDown(KeyEvent.VK_L)) {
                up(KeyEvent.VK_L);
                Messages.switchLangue();
            }

            if (isDown(KeyEvent.VK_P) && !getMetaInfo().isGame_over()) {
                up(KeyEvent.VK_P);
                synchronized (getMetabox().getPieces_mouvantes()) {
                    Game gs = Game.getGameService();
                    if (gs.isPause())
                        gs.start();
                    else {
                        gs.pause();
                        getMetabox().getTexte().setTexte(
                                Messages.getString("MoveBoard.pause")); //$NON-NLS-1$
                    }
                }
            }

            if (checkMove) {

                if (isDown(KeyEvent.VK_M) && !getMetaInfo().isGame_over()) {
                    up(KeyEvent.VK_M);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        InterfaceMusique.switchPlayMusic();
                    }
                }

                if (isDown(KeyEvent.VK_R)) {
                    up(KeyEvent.VK_R);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        Game gs = Game.getGameService();
                        Bonus bonus = gs.getBonus();
                        MetaBoard mb = getMetabox();
                        if (bonus.canDeleteLine()) {
                            bonus.deleteLine();
                            mb.removeLastLines();
                        }
                    }
                }

                if (isDown(KeyEvent.VK_S)) {
                    up(KeyEvent.VK_S);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        Game gs = Game.getGameService();
                        Bonus bonus = gs.getBonus();
                        MetaBoard mb = getMetabox();
                        if (bonus.canSlow()) {
                            bonus.slow();
                            ((ChuteProcess) gs.getTimer().getProcess())
                                    .setSlow();
                        }
                    }
                }

                if (isDown(KeyEvent.VK_D)) {
                    up(KeyEvent.VK_D);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        Game gs = Game.getGameService();
                        Bonus bonus = gs.getBonus();
                        MetaBoard mb = getMetabox();
                        if (bonus.canDeletePiece()) {
                            MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                            if (mp != null) {
                                bonus.deletePiece();
                                mb.removePiece(mp);
                            }
                        }
                    }
                }

                if (isDown(KeyEvent.VK_J)) {
                    up(KeyEvent.VK_J);
                    getMetabox().getTexte().setTexte(
                            "J3itainer" + Configuration.VERSION); //$NON-NLS-1$
                }

                if (isDown(KeyEvent.VK_RIGHT)) {
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) movepiece.right(mp);
                    }
                }

                if (isDown(KeyEvent.VK_LEFT)) {
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) movepiece.left(mp);
                    }
                }

                if (isDown(KeyEvent.VK_UP)) {
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) movepiece.up(mp);
                    }
                }

                if (isDown(KeyEvent.VK_DOWN)) {
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) {
                            List liste = new ArrayList();
                            liste.add(mp);
                            movepiece.downPieces(liste, true);
                        }
                    }
                }

                if (isDown(KeyEvent.VK_ENTER)) {
                    up(KeyEvent.VK_ENTER);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) movepiece.forceDownPiece(mp);

                    }
                }

                if (isDown(KeyEvent.VK_SPACE)) {
                    up(KeyEvent.VK_SPACE);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaBoard mb = getMetabox();
                        MetaPiece mp = (MetaPiece) mb.getCurrentPiece();
                        if (mp != null) movepiece.rotation(mp);
                    }
                }

                if (isDown(KeyEvent.VK_C)) {
                    up(KeyEvent.VK_C);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaPiece old_dp = (MetaPiece) getMetabox()
                                .getCurrentPiece();
                        if (old_dp != null) {
                            MetaPiece new_dp = (MetaPiece) getMetabox()
                                    .changeCurrentPiece(true);
                        }
                    }
                }

                if (isDown(KeyEvent.VK_V)) {
                    up(KeyEvent.VK_V);
                    synchronized (getMetabox().getPieces_mouvantes()) {
                        MetaPiece old_dp = (MetaPiece) getMetabox()
                                .getCurrentPiece();
                        if (old_dp != null) {
                            MetaPiece new_dp = (MetaPiece) getMetabox()
                                    .changeCurrentPiece(false);
                        }
                    }
                }

            }
            try {
                Thread.sleep(KEY_DELAY);
            } catch (InterruptedException e) {
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode() & 0xff] = TOUCHE_ACTIVE_ET_NON_HONOREE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        if (keys[e.getKeyCode() & 0xff] == TOUCHE_ACTIVE_ET_NON_HONOREE)
            keys[e.getKeyCode() & 0xff] = TOUCHE_NON_ACTIVE_ET_NON_HONOREE;
        else if (keys[e.getKeyCode() & 0xff] != TOUCHE_NON_ACTIVE_ET_NON_HONOREE)
                keys[e.getKeyCode() & 0xff] = TOUCHE_NON_ACTIVE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent arg0) {

    }

    /**
     * @return
     */
    public MovePiece getMovepiece() {
        return movepiece;
    }

    /**
     * @param b
     */
    public void setCheckMove(boolean b) {
        checkMove = b;
        if (b) {
            // Pour être sûr :
            for (int i = 0; i < keys.length; keys[i++] = TOUCHE_NON_ACTIVE) {
            }
            this.requestFocus();
        }
    }

    protected boolean isDown(int key) {
        int valeur = keys[key & 0xff];
        boolean retour = valeur != TOUCHE_NON_ACTIVE;
        if (valeur == TOUCHE_ACTIVE_ET_NON_HONOREE)
                keys[key & 0xff] = TOUCHE_ACTIVE_ET_HONOREE;
        if (valeur == TOUCHE_NON_ACTIVE_ET_NON_HONOREE)
                keys[key & 0xff] = TOUCHE_NON_ACTIVE;
        return retour;
    }

    protected void up(int key) {
        keys[key & 0xff] = TOUCHE_NON_ACTIVE;
    }

    public void newPiece() {
        synchronized (getMetabox().getPieces_mouvantes()) {
            PieceFactory dps = PieceFactory.getInstance();
            if (!movepiece.forceAddPiece(dps
                    .getDisplayPiece(getMetaInfo().getNiveau()))) {
            }
        }
    }
    
    public void newLine() {
        synchronized (getMetabox().getPieces_mouvantes()) {
            // On mettre 1 car on ne veut pas que les pièces descendent.
            movepiece.downPieces(getMetabox().getPieces_mouvantes(), false);
            getMetabox().upLines();
        }
    }
    
}