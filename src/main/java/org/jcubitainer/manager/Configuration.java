/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
 * Author : Moun�s Ronan metalm@users.berlios.de                       *
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

/*******************************************************************************
 * History & changes * ******* May 5, 2004
 * ************************************************** - First release *
 ******************************************************************************/

package org.jcubitainer.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class Configuration extends Properties {

    static File conf = null;

    static Configuration c = null;

    public static final String DIR = ".jcubitainer";

    public static final String VERSION = "0.3.1";

    public static final String NAME_VERSION = "VERSION";

    /**
     *  
     */
    public Configuration() {
        super();
        conf = new File(System.getProperty("user.home") + File.separator + DIR
                + File.separator + "conf.txt");
        loadConf();
        c = this;
    }

    private void loadConf() {
        if (conf.canRead()) {
            try {

                FileInputStream fileinputstream = new FileInputStream(conf);
                load(fileinputstream);
                fileinputstream.close();

                upgrade();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier de configuration n'existe pas !");
        }
    }

    /**
     * 
     */
    private void upgrade() {
        String prop_version = getProperty(NAME_VERSION);
        if (!VERSION.equals(prop_version)) {
            remove("theme");
            setProperty(NAME_VERSION, VERSION);
        }

    }

    public static void setPropertie(String key, String value) {
        c.setProperty(key, value);
    }

    public static String getProperties(String key) {
        return c.getProperty(key);
    }

    public static void save() {
        try {
            new File(System.getProperty("user.home") + File.separator + DIR)
                    .mkdirs();

            System.out.println("Sauvegarde du fichier de configuration !");
            FileOutputStream out = new FileOutputStream(conf);
            c.store(out, "Properties");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}