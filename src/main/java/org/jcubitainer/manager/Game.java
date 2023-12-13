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

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.manager.process.PieceProcess;
import org.jcubitainer.manager.process.TexteProcess;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.tools.ProcessMg;
import org.jcubitainer.tools.Messages;

public class Game {

    static ProcessMg timer = null;

    static ProcessMg timer_new_piece = null;

    static ProcessMg timer_texte = null;

    private static Game _this = null;

    private static Bonus bonus = null;

    private MoveBoard db = null;

    private Level levelmanager = null;

    private MetaInfo dinfo = null;

    /**
     *  
     */
    public Game(MoveBoard pdb, MetaInfo di) {
        super();
        db = pdb;
        timer = new ProcessMg(new ChuteProcess(db));
        timer_new_piece = new ProcessMg(new PieceProcess(db));
        timer_texte = new ProcessMg(new TexteProcess(db.getMetabox()));
        _this = this;
        dinfo = di;
        bonus = new Bonus(di);
        levelmanager = new Level(db.getMetabox().getTexte(), di);
    }

    public static Game getGameService() {
        return _this;
    }

    public void start() {
        if (dinfo.isGame_over()) {
            bonus.clean();
            dinfo.clean();
            ((MoveBoard) db).getMetabox().createEmptyBoard();
            //dinfo.repaint();
        }
        ((ChuteProcess)timer.getProcess()).setNormal();
        dinfo.setPause(false);
        ((MoveBoard) db).setCheckMove(true);
        levelmanager.setLevel((ChuteProcess) timer.getProcess());
        timer.wakeUp();
        timer_new_piece.wakeUp();
    }

    public void pause() {
        timer.pause();
        dinfo.setPause(true);
        timer_new_piece.pause();
        ((MoveBoard) db).setCheckMove(false);
    }

    public boolean isPause() {
        return timer.isStop();
    }

    /**
     * @return
     */
    public Bonus getBonus() {
        return bonus;
    }

    public void endGame() {
        pause();
        db.getMetabox().fixAll();
        dinfo.setPause(false);
        if (!NetworkManager.isNetworkOn()) {
            dinfo.setGame_over(true);
            db.getMetabox().getTexte().setTexte(
                    Messages.getString("Game.game_over")); //$NON-NLS-1$
        }
        // Vérification du hit score :
        int score = dinfo.getScore();
        int hit = dinfo.getHit();
        if (score > hit)
            dinfo.setHit(score);
    }

    /**
     * @return
     */
    public ProcessMg getTimer() {
        return timer;
    }

    public ProcessMg getTextTimer() {
        return timer_texte;
    }

    /**
     * @return
     */
    public Level getLevelmanager() {
        return levelmanager;
    }
}