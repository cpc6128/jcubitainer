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

package org.jcubitainer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jcubitainer.display.DisplayPiece;
import org.jcubitainer.display.JCubitainerFrame;
import org.jcubitainer.display.SplashScreen;
import org.jcubitainer.display.infopanel.DisplayInfo;
import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.Configuration;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.tools.Messages;
import org.jcubitainer.tools.logs.Logs;

public class Start {

    static SplashScreen ss = null;

    private static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        int largeur = 15;
        int hauteur = 40;
        // Affichage de la boite :
        MetaBoard mb = new MetaBoard(largeur, hauteur);
        MetaInfo mi = new MetaInfo();
        MoveBoard db = new MoveBoard(mb, mi);
        db.setPreferredSize(new Dimension(largeur * DisplayPiece.LARGEUR_PIECE
                + 9, hauteur * DisplayPiece.HAUTEUR_PIECE));
        // Affichage des infos :
        DisplayInfo di = new DisplayInfo(mi);
        di.setPreferredSize(new Dimension(200, 50));
        new PieceFactory(mb);

        JCubitainerFrame frame = new JCubitainerFrame(Messages
                .getString("J3.title"), db);
        frame.setIconImage(ThemeManager.getCurrent().getImage("ilogo"));
        //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                Configuration.save();
                System.exit(0);
            }
        });

        frame.setResizable(false);
        //frame.setUndecorated(true);
        frame.getContentPane().add(db, BorderLayout.WEST);
        frame.getContentPane().add(di, BorderLayout.EAST);
        frame.addKeyListener(db);
        frame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int maxX = (screenSize.width - frame.getWidth()) / 2;
        int maxY = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(maxX, maxY);
        // Seulement pour le JDK 1.4 :
        //frame.setLocationRelativeTo(null);
        /*
         * GraphicsDevice device = GraphicsEnvironment
         * .getLocalGraphicsEnvironment() .getDefaultScreenDevice();
         */
        new Game(db, mi);
        ss.hide(1000);
        frame.setVisible(true);
        mb.getTexte().setTexte("http://jcubitainer.berlios.de");
    }

    public static void main(String[] args) {

        try {
            // Chargement du fichier de configuration s'il existe :
            new Configuration();

            new Logs();

            // Chargement du thème par défaut :
            new ThemeManager();

        } catch (Throwable e1) {
            e1.printStackTrace();
        }
        ss = new SplashScreen();
        ss.setVisible(true);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}