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
 ******** May 12, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.tools;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.jcubitainer.manager.Configuration;

public class Messages {

    private static ResourceBundle RESOURCE_BUNDLE = null;

    private static final String[] langues = { "fr", "en"};

    private static final Locale[] langues_objet = { Locale.FRANCE,
            Locale.ENGLISH};

    private static final String[] langues_title = { "français", "english"};

    static {

        String langue = Configuration.getProperties("langue");
        langue = langue == null ? Locale.getDefault().getLanguage() : langue;

        int pos = 0;
        // Recherche de la langue
        if (langue != null) {
            for (; pos < langues.length; pos++)
                if (langue.equals(langues[pos])) break;
            if (pos == langues.length) pos = 0;
        }

        Configuration.setPropertie("langue", langues[pos]);

        RESOURCE_BUNDLE = ResourceBundle.getBundle("ressources.textes",
                langues_objet[pos]);
    }

    private Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
        return "<" + key + ">";
    }

    public static void switchLangue() {
        String langue = Configuration.getProperties("langue");
        int pos = 0;
        // Recherche de la langue
        if (langue != null) {
            for (; pos < langues.length; pos++)
                if (langue.equals(langues[pos])) break;
            pos++;
            if (pos == langues.length) pos = 0;
        }
        Configuration.setPropertie("langue", langues[pos]);
    }
}