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

package org.jcubitainer.sound;

import java.util.List;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Configuration;
import org.jcubitainer.tools.Process;
import org.jcubitainer.tools.ProcessMg;

public class InterfaceMusique {

    private ProcessMg play = null;

    private static ProcessMg plg = null;

    private static InterfaceMusique this_ = null;

    private static boolean playMusic = true;

    static {
        playMusic = "true".equals(Configuration.getProperties("musique"));
        this_ = new InterfaceMusique();
    }

    class PlaySound extends Process {

        protected PlaySound() {
            super(2000);
            plg = new ProcessMg(this);
        }

        public void action() throws InterruptedException {
            try {
                List playlist = ThemeManager.getCurrent().getMusiques();
                if (playlist.size() > 0) {
                    int pos = 0;
                    if (play != null) {
                        pos = playlist.indexOf(play) + 1;
                        if (pos > 0 && pos < playlist.size()) {
                        } else
                            pos = 0;
                    }
                    play = (ProcessMg) playlist.get(pos);
                    play.wakeUp();
                    synchronized (((Musique) play.getProcess())) {
                        while (true)
                            if (!((Musique) play.getProcess()).isPause())
                                ((Musique) play.getProcess()).wait(500);
                            else
                                break;
                    }
                }
            } catch (InterruptedException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        } /*
         * (non-Javadoc)
         * 
         * @see org.jcubitainer.tools.Process#pause()
         */

        public void pause() {
            super.pause();
            if (play != null) {
                Musique m = (Musique) play.getProcess();
                m.pause();
            }
            //            plg.getProcess().interrupted();
        }

    }

    public static void START_musique() {
        synchronized (this_.getPlg()) {
            if (InterfaceMusique.playMusic) this_.getPlg().wakeUp();
        }
    }

    public static boolean STOP_musique() {
        synchronized (this_.getPlg()) {
            if (!this_.getPlg().isStop()) {
                this_.getPlg().pause();
                return true;
            } else
                return false;
        }
    }

    /**
     * @return
     */
    private synchronized ProcessMg getPlg() {
        if (plg == null) new PlaySound();
        return plg;
    }

    /**
     * @param playMusic
     *            The playMusic to set.
     */
    public static boolean switchPlayMusic() {
        synchronized (this_) {
            playMusic = !playMusic;
            // on veut éteindre et la musique tourne :
            if (!playMusic) STOP_musique();
            // on veut la musique et c'était arrêté :
            if (playMusic) START_musique();
        }
        Configuration.setPropertie("musique", String.valueOf(playMusic));
        return playMusic;
    }
}