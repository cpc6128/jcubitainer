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

package org.jcubitainer.display;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import org.jcubitainer.key.MoveBoard;

public class JCubitainerFrame extends JFrame {

    MoveBoard mb = null;

    final static Color bg = Color.white;

    final static Color fg = Color.black;

    private static JCubitainerFrame this_ = null;

    /**
     * @param string
     * @param db
     */
    public JCubitainerFrame(String string, MoveBoard p) {
        super(string);
        mb = p;
        init();
        this_ = this;
    }

    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
    }

    /* (non-Javadoc)
     * @see java.awt.Container#paint(java.awt.Graphics)
     */
    public void paint(Graphics arg0) {
        mb.paint(arg0);
        super.paint(arg0);
    }

    public static JCubitainerFrame getFrame() {
        return this_;
    }
}