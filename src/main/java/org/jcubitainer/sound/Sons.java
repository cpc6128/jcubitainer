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

import org.jcubitainer.display.theme.ThemeManager;

import sun.applet.AppletAudioClip;

/**
 * 
 * http://forum.java.sun.com/thread.jsp?forum=31&thread=420688
 * 
 *  
 */
public class Sons {

    private static void jouerSon(byte[] s) {
        try {
            new AppletAudioClip(s).play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void son1() {
        jouerSon(ThemeManager.getCurrent().getSon("son1"));
    }

    public static void son2() {
        jouerSon(ThemeManager.getCurrent().getSon("son2"));
    }

}