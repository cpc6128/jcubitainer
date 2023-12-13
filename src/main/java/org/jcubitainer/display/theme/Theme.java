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

package org.jcubitainer.display.theme;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;

import org.jcubitainer.sound.Musique;
import org.jcubitainer.tools.ProcessMg;

public class Theme {

    // Description dans theme.properties :
    Properties description = null;

    // On stocke les images :
    Hashtable images_ressource = new Hashtable();

    // On stocke les musiques :
    List musique_ressource = new ArrayList();

    // Nom du thème :
    public String nom = null;

    public String auteur = null;

    public int MAX_MATIERE = 0;

    public Theme(InputStream is) throws ThemeError {
        load(is);
    }

    public void load(InputStream is) throws ThemeError {
        Hashtable dezippe = new Hashtable();
        // Chargement de tous les fichiers
        ZipInputStream zis = new ZipInputStream(is);

        try {
            ZipEntry ze = null;
            int size = 0;
            while ((ze = zis.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    size = (int) ze.getSize();
                    // Extraction du fichier :

                    byte[] data = new byte[(int) size];
                    int debut = 0;
                    int reste = 0;
                    while (((int) size - debut) > 0) {
                        reste = zis.read(data, debut, (int) size - debut);
                        if (reste == -1) {
                            break;
                        }
                        debut += reste;
                    }
                    dezippe.put(ze.getName(), data);
                }
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ThemeError("Impossible de lire le fichier zip :"
                    + e.toString());
        } finally {
            try {
                is.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        // Recherche du fichier theme.properties :
        byte[] ze1 = (byte[]) dezippe.get("theme.properties");
        if (ze1 != null) {
            description = new Properties();
            try {
                description.load(new ByteArrayInputStream(ze1));
                nom = description.getProperty("nom");
                auteur = description.getProperty("auteur");
                if (nom == null)
                        throw new ThemeError("Le thème n'a pas de nom.");
                System.out.print("Nom du thème : " + nom);
                if (auteur != null)
                        System.out.println(", auteur du thème : " + auteur);

                if (description.getProperty("nb_pieces") != null)
                    MAX_MATIERE = new Integer(description
                            .getProperty("nb_pieces")).intValue();
                else
                    throw new ThemeError(
                            "Il faut définir le nombre de couleur.");

            } catch (IOException e1) {
                throw new ThemeError("Impossible de lire theme.properties : "
                        + e1.toString());
            }
        } else
            throw new ThemeError("theme.properties n'existe pas !!");

        // On charge toutes les images :
        Enumeration e = description.keys();
        String n = null, fichier = null;
        while (e.hasMoreElements()) {
            n = (String) e.nextElement();
            fichier = description.getProperty(n);
            if (n.startsWith("i")) {
                // C'est une image :
                try {
                    Image image = new ImageIcon((byte[]) dezippe.get(fichier))
                            .getImage();
                    dezippe.put(fichier, image);
                } catch (Exception e1) {
                    throw new ThemeError("Impossible de lire l'image : " + n);
                }
            }
            if (n.startsWith("musique")) {
                // C'est une image :
                try {
                    InputStream ist = new ByteArrayInputStream((byte[]) dezippe
                            .get(fichier));
                    String title = description.getProperty("title_" + n);

                    if (title == null)
                            throw new ThemeError("La musique " + n
                                    + " n'a pas de titre.");
                    musique_ressource
                            .add(new ProcessMg(new Musique(ist, title)));
                } catch (ThemeError et) {
                    throw et;
                } catch (Exception e1) {
                    throw new ThemeError("Impossible de charger la musique : "
                            + n);
                }
            }

            if (n.startsWith("son")) {
                // C'est un son wave :
                try {
                } catch (Exception e1) {
                    throw new ThemeError("Impossible de charger le son : " + n);
                }
            }
        }
        // On transvase dans les images :
        Enumeration prendre = description.keys();
        String key = null;
        Object o = null;
        while (prendre.hasMoreElements()) {
            key = (String) prendre.nextElement();
            o = dezippe.get(description.get(key));
            if (o != null) {
                images_ressource.put(key, o);
            }
            //else
            //    throw new ThemeError("Description du thème non valide.");
        }
        try {
            is.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * @return Returns the nom.
     */
    public String getNom() {
        return nom;
    }

    public Image getImage(String nom) {
        return (Image) images_ressource.get(nom);
    }

    public byte[] getSon(String nom) {
        return (byte[]) images_ressource.get(nom);
    }

    public List getMusiques() {
        return musique_ressource;
    }

    /**
     * @return Returns the mAX_MATIERE.
     */
    public int getMAX_MATIERE() {
        return MAX_MATIERE;
    }
}