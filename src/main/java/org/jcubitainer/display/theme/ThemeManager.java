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
 ******** May 6, 2004 **************************************************
 *   - Théme ajouté par drag and drop                                  *
 ***********************************************************************/

package org.jcubitainer.display.theme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.jcubitainer.manager.Configuration;

public class ThemeManager {

    public static final String DIR2 = "themes";

    private static List theme = new ArrayList();

    private static ThemeLoader current = null;

    private static Theme currentTheme = null;

    private static Hashtable cache = new Hashtable();

    // Va permettre de savoir si ce thème est déjà présent :
    private static HashSet noms = new HashSet();

    /**
     *  
     */
    public ThemeManager() throws ThemeError {
        super();
        //        theme.add(new ThemeLoaderFromJar("/ressources/themes/theme_basic.zip"));
        //        theme.add(new ThemeLoaderFromJar("/ressources/themes/theme_gnome.zip"));
        //        theme.add(new ThemeLoaderFromJar("/ressources/themes/theme_xp.zip"));
        //        theme.add(new ThemeLoaderFromJar("/ressources/themes/mandrake.zip"));
        theme.add(new ThemeLoaderFromJar("/ressources/themes/default.zip"));
        theme.addAll(getFilesFromDir());
        reload(null);
    }

    public synchronized static boolean swithTheme() throws ThemeError {
        return reload(null);
    }

    public synchronized static boolean swithTheme(ThemeLoader toSwith)
            throws ThemeError {
        return reload(toSwith);
    }

    public static boolean reload(ThemeLoader toSwith) throws ThemeError {
        ThemeLoader tl = null;
        if (toSwith == null) {
            if (current == null) {
                // Recherche du thème dans le fichier de configuration :
                String key = Configuration.getProperties("theme");
                // Si on ne trouve pas le thème :
                tl = (ThemeLoader) theme.get(0);
                Iterator i = theme.iterator();
                while (i.hasNext()) {
                    ThemeLoader temp = (ThemeLoader) i.next();
                    if (temp.getID().equals(key)) tl = temp;
                }
            } else {
                int pos = theme.indexOf(current) + 1;
                if (pos >= theme.size()) pos = 0;
                tl = (ThemeLoader) theme.get(pos);
            }
        } else
            tl = toSwith;
        if (tl != null) {
            // Chargement :
            Theme temp = (Theme) cache.get(tl);
            if (temp == null) {
                try {
                    temp = new Theme(tl.getInputStream());
                } catch (Throwable e) {
                    e.printStackTrace();
                    theme.remove(tl);
                    System.out.println("thème non valide : " + tl.getID());
                    return false;
                }
                cache.put(tl, temp);
                noms.add(tl.getID());
            }
            current = tl;
            currentTheme = temp;
            Configuration.setPropertie("theme", current.getID());
            return true;
        }
        return false;
    }

    /**
     * @return Returns the current.
     */
    public static Theme getCurrent() {
        return currentTheme;
    }

    public static List getFilesFromDir() {
        new File(System.getProperty("user.home") + File.separator
                + Configuration.DIR).mkdirs();
        File dir = new File(System.getProperty("user.home") + File.separator
                + Configuration.DIR + File.separator + DIR2);
        dir.mkdirs();
        Iterator temp = Arrays.asList(dir.listFiles()).iterator();
        List retour = new ArrayList();

        while (temp.hasNext()) {
            File f = (File) temp.next();
            if (f.isFile()) retour.add(new ThemeLoaderFromFile(f));
        }

        return retour;
    }

    public static void addThemeFromDragAndDropAndSaveToDisk(File f) {
        // On l'ajoute en mémoire :
        ThemeLoader temp = new ThemeLoaderFromFile(f);
        if (!noms.contains(temp.getID())) {
            if (f.isFile()) {
                theme.add(temp);
                try {
                    if (swithTheme(temp)) {

                        // Entrée :
                        FileInputStream fis = null;
                        // Sortie :
                        FileOutputStream fos = null;

                        try {
                            // La thème est validé, on le sauvegarde sur disque :
                            File newTheme = new File(System
                                    .getProperty("user.home")
                                    + File.separator
                                    + Configuration.DIR
                                    + File.separator
                                    + DIR2
                                    + File.separator
                                    + temp.getID());

                            fis = new FileInputStream(f);
                            fos = new FileOutputStream(newTheme);

                            byte buffer[] = new byte[512 * 1024];
                            int nbLecture;

                            while ((nbLecture = fis.read(buffer)) != -1) {
                                fos.write(buffer, 0, nbLecture);
                            }
                        } catch (java.io.FileNotFoundException fnfe) {

                        } catch (java.io.IOException e) {

                        } finally {
                            try {
                                if (fis != null) fis.close();
                            } catch (Exception e) {
                            }
                            try {
                                if (fos != null) fos.close();
                            } catch (Exception e) {
                            }
                        }
                    }
                } catch (ThemeError e) {
                    e.printStackTrace();
                }
            }
        } else
            System.out.println("Thème déjà présent : " + temp.getID());
    }
}