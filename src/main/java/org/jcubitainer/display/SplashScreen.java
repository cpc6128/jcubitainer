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

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

import org.jcubitainer.display.theme.ThemeManager;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        super();
        ImageIcon icon = new ImageIcon(ThemeManager.getCurrent().getImage(
                "isplash"));
        if (icon != null) {
            getContentPane().add(new JLabel(icon, JLabel.CENTER));
            Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
            setSize(icon.getIconWidth(), icon.getIconHeight());
            setLocation((screensize.width - icon.getIconWidth()) / 2,
                    (screensize.height - icon.getIconHeight()) / 2);
        }
    }

    public void hide(int after) {
        try {
            Thread.sleep(after);
        } catch (InterruptedException e) {
        }

        setVisible(false);
    }

}