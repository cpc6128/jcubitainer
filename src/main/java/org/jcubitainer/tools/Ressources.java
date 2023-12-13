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

package org.jcubitainer.tools;

import java.awt.Image;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

import org.jcubitainer.display.JCubitainerFrame;

public class Ressources {

    public static Image getImage(String nom_image) {
        ImageIcon ii = Ressources.getImageIcon(nom_image);
        return ii == null ? null : ii.getImage();
    }

    public static ImageIcon getImageIcon(String nom_image) {
        java.net.URL imgURL = JCubitainerFrame.class.getResource(nom_image);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }

    public static InputStream getConfigInputStream(String fichier) {
        return JCubitainerFrame.class.getResourceAsStream("/config/" + fichier);
    }

    public static URL getURL(String fichier) {
        return JCubitainerFrame.class.getResource(fichier);
    }

    public static InputStream getInputStream(String fichier) {
        return JCubitainerFrame.class.getResourceAsStream(fichier);
    }

}