/***********************************************************************
 * JXtainer                                                            *
 * Version release date : April 3, 2005                                *
 * Author : Mounes Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/jxtainer/                         *
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


package org.jxtainer.util;

public abstract class Process extends Thread {

    boolean pause = false;

    boolean start = false;

    long wait;

    public Process(long pwait) {
        super();
        wait = pwait;
    }

    public void start() {
        start = true;
        super.start();
    }

    public void setWait(long p) {
        wait = p;
    }

    public abstract void action() throws InterruptedException;

    public void run() {
        while (true) {
            try {
                if (!pause) action();
                sleep(wait);

            } catch (InterruptedException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        pause = true;
    }

    public void reStart() {
        pause = false;
    }

    public boolean isPause() {
        return pause;
    }

    protected boolean isStart() {
        return start;
    }

}