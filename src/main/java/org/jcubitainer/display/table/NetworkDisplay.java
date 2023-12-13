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

package org.jcubitainer.display.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class NetworkDisplay extends JPanel {

    static NetworkDisplayTable table = new NetworkDisplayTable(150, 200);

    /**
     * 
     */
    public NetworkDisplay() {
        super();

        JPanel panel = new JPanel(new GridLayout(1, 0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        panel.add(table, BorderLayout.EAST);

        panel.setBackground(Color.black);
        panel.setForeground(Color.black);

        setBackground(Color.black);
        setForeground(Color.black);

        add(panel, BorderLayout.NORTH);
    }

    public static NetworkDisplayTable getTable() {
        return table;
    }

}