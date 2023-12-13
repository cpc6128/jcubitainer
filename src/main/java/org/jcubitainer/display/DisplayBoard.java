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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.MatiereFactory;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.meta.MetaTexte;

public class DisplayBoard extends JPanel {

    final static Color bg = Color.white;

    final static Color fg = Color.black;

    final static int deb_x = 5;

    final static int deb_y = 0;

    MetaBoard metabox = null;

    MetaInfo mi = null;

    static private DisplayBoard this_ = null;

    // Buffer pour dessiner :
    private Graphics2D imageGraphics = null;

    private BufferedImage image = null;

    // Font :
    private String family = "Serif";

    private String family_small = "Dialog";

    private int style = Font.BOLD;

    private int style_small = Font.PLAIN;

    private int size = 22;

    private int size_small = 15;

    private Font FONT = new Font(family, style, size);

    private Font FONT_SMALL = new Font(family_small, style_small, size_small);

    public DisplayBoard(MetaBoard pmetabox, MetaInfo pmi) {
        super();
        metabox = pmetabox;
        mi = pmi;
        init();
        this_ = this;
    }

    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);
    }

    protected void createImage() {

        image = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        imageGraphics = image.createGraphics();

        imageGraphics.setColor(Color.yellow);
        imageGraphics.setFont(FONT);

        clearBackground();
    }

    protected void clearBackground() {

        if (imageGraphics != null)
                imageGraphics.drawImage(ThemeManager.getCurrent().getImage(
                        "ifond"), 0, 0, null);
    }

    public final void paint(Graphics g) {

        if (image != null) {
            clearBackground();
            toPaint(imageGraphics);
            g.drawImage(image, 0, 0, null);
        } else
            createImage();

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Container#paint(java.awt.Graphics)
     */
    public void toPaint(Graphics2D g2) {

        //        if (getMetaInfo().isGame_over()) {

        // En pause :
        Composite c = g2.getComposite();
        if (Game.getGameService().isPause() && !getMetaInfo().isGame_over())
                g2.setComposite(AlphaComposite.getInstance(
                        AlphaComposite.SRC_OVER, .2f));

        // On dessine les pièces mouvantes :
        List liste = metabox.getPieces_mouvantes();
        synchronized (liste) {
            // On dessine l'ombre :
            paintOmbre(g2);

            // On dessine les pièces mouvantes :
            for (int i = 0; i < liste.size(); i++) {
                DisplayPiece mp = (DisplayPiece) liste.get(i);
                mp.drawPiece(mp != metabox.getCurrentPiece(), g2);
            }

            // Les pièces fixes :
            paintFix(g2);

            // Afficher le texte :
            paintText(g2);
        }

        g2.setComposite(c);
        //        }
    }

    private void paintText(Graphics2D g2) {

        if (getMetabox().getTexte().isDisplay()) {

            MetaTexte mt = getMetabox().getTexte();

            Composite c = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    mt.getAlpha()));

            String texte = mt.getTexte();

            // Calcule de la position :
            g2.setFont(FONT);
            int x = (getWidth() - g2.getFontMetrics().stringWidth(texte)) / 2;
            // Si le texte est trop grand, on réduit la police :
            int y = 230;
            if (x < 0) {
                g2.setFont(FONT_SMALL);
                // On recalcule la nouvelle position :
                x = (getWidth() - g2.getFontMetrics().stringWidth(texte)) / 2;
            }

            g2.drawString(texte, x, y);
            g2.setComposite(c);
        }
    }

    protected void paintOmbre(Graphics2D g2) {
        // On dessine les pièces fixes :
        int[] ombre = metabox.getOmbre().getFormat();

        if (ombre == null) return;
        //System.out.println(metabox.getOmbre());
        for (int x = 0; x < ombre.length; x++) {
            int ligne = ombre[x];
            if (ligne > 0) {
                //System.out.println(ligne);
                g2.drawImage(MatiereFactory.getOmbre(), deb_x
                        + ((x) * DisplayPiece.LARGEUR_PIECE), deb_y
                        + ((ligne) * DisplayPiece.HAUTEUR_PIECE), null);
            }
        }
    }

    protected void paintFix(Graphics2D g2) {

        // On dessine les pièces fixes :
        int[][] pieces = metabox.getPieces_mortes();
        for (int y = 0; y < pieces.length; y++) {
            int[] ligne = pieces[y];
            for (int x = 0; x < ligne.length; x++) {
                if (ligne[x] > 0) {
                    g2.drawImage(MatiereFactory.getColorByMatiere(ligne[x],
                            true), deb_x + ((x) * DisplayPiece.LARGEUR_PIECE),
                            deb_y + ((y) * DisplayPiece.HAUTEUR_PIECE), null);
                }
            }
        }

    }

    /**
     * @return
     */
    public MetaBoard getMetabox() {
        return metabox;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#repaint()
     */
    public void repaint() {
        //System.out.println("refresh");
        super.repaint();
    }

    /**
     * @return
     */
    public static DisplayBoard getThis() {
        return this_;
    }

    /**
     * @return
     */
    public MetaInfo getMetaInfo() {
        return mi;
    }

}