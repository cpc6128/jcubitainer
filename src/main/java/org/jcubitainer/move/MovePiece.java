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

package org.jcubitainer.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jcubitainer.key.MoveBoard;
import org.jcubitainer.manager.Game;
import org.jcubitainer.manager.PieceFactory;
import org.jcubitainer.manager.process.ChuteProcess;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaPiece;
import org.jcubitainer.tools.PieceComparator;

public class MovePiece {

    private final static int COLLISION_PIECE_FIXE = 2;

    private final static int COLLISION_PIECE_MOVE = 1;

    private final static int COLLISION_NO = 0;

    MoveBoard movebox = null;

    /**
     *  
     */
    public MovePiece(MoveBoard p) {
        super();
        movebox = p;
    }

    /**
     * @param mp
     */
    public void right(MetaPiece mp) {
        int position = mp.getPosition_x();
        if ((position + mp.getLargeur()) < movebox.getMetabox().getLargeur()) {
            //mp.clearPiece();
            mp.setPosition_x(mp.getPosition_x() + 1);
            if (checkCollision(mp) != COLLISION_NO)
                    mp.setPosition_x(mp.getPosition_x() - 1);
            //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
        }
    }

    /**
     * 2 Collision avec des pièces fixes 1 Collision avec une pièce mouvante 0
     * Rien
     * 
     * @param mp
     */
    private int checkCollision(MetaPiece mp) {
        MetaBoard mb = movebox.getMetabox();
        Iterator it = mb.getPieces_mouvantes().iterator();
        boolean[][] format_piece1 = mp.getFormat();

        for (; it.hasNext();) {
            MetaPiece temp_mp = (MetaPiece) it.next();
            if (temp_mp != mp) {
                // Vérification rapide des positions :
                // ( Si pas de superposition )

                if (// vérification de x
                ((mp.getPosition_x() + mp.getLargeur() > temp_mp
                        .getPosition_x()) && ((mp.getPosition_x() < temp_mp
                        .getPosition_x()
                        + temp_mp.getLargeur())))
                        && // vérification de y
                        ((mp.getPosition_y() + mp.getHauteur() > temp_mp
                                .getPosition_y()) && (mp.getPosition_y() < temp_mp
                                .getPosition_y()
                                + temp_mp.getHauteur()))) {
                    // *********************************
                    // * Faire une analyse plus fine : *
                    // *********************************
                    boolean[][] format_piece2 = temp_mp.getFormat();
                    // On boucle sur piece 1 :
                    for (int ref_piece_1_y = 0; ref_piece_1_y < format_piece1.length; ref_piece_1_y++) {

                        boolean[] ligne = format_piece1[ref_piece_1_y];

                        for (int ref_piece_1_x = 0; ref_piece_1_x < ligne.length; ref_piece_1_x++) {
                            // Position dans le référentiel piece 1 :
                            // Il y a une pièce :
                            if (ligne[ref_piece_1_x]) {

                                // On va dans le référentiel MetaBoard :
                                int ref_meta_x = mp.getPosition_x()
                                        + ref_piece_1_x;
                                int ref_meta_y = mp.getPosition_y()
                                        + ref_piece_1_y;
                                // On va dans le référentiel piece 2 :
                                int ref_piece_2_x = ref_meta_x
                                        - temp_mp.getPosition_x();
                                int ref_piece_2_y = ref_meta_y
                                        - temp_mp.getPosition_y();

                                if (ref_piece_2_y < 0
                                        || ref_piece_2_x < 0
                                        || ref_piece_2_x > (temp_mp
                                                .getLargeur() - 1)
                                        || ref_piece_2_y > (temp_mp
                                                .getHauteur() - 1)) {
                                    continue;

                                } else if (format_piece2[ref_piece_2_y][ref_piece_2_x])
                                // Collision !
                                { return COLLISION_PIECE_MOVE; }
                            }
                        }
                    }
                }
            }
        }

        // Vérification avec les pièces fixes :

        for (int ref_piece_1_y = 0; ref_piece_1_y < format_piece1.length; ref_piece_1_y++) {

            boolean[] ligne = format_piece1[ref_piece_1_y];

            for (int ref_piece_1_x = 0; ref_piece_1_x < ligne.length; ref_piece_1_x++) {
                // Position dans le référentiel piece 1 :
                // Il y a une pièce :
                if (ligne[ref_piece_1_x]) {

                    // On va dans le référentiel MetaBoard :
                    int ref_meta_x = mp.getPosition_x() + ref_piece_1_x;
                    int ref_meta_y = mp.getPosition_y() + ref_piece_1_y;

                    if (mb.getPieces_mortes()[ref_meta_y][ref_meta_x] > 0) { return COLLISION_PIECE_FIXE; }
                }
            }
        }

        return COLLISION_NO;
    }

    public void left(MetaPiece mp) {
        int position = mp.getPosition_x();
        if (position > 0) {
            //mp.clearPiece();
            mp.setPosition_x(mp.getPosition_x() - 1);
            if (checkCollision(mp) != COLLISION_NO)
                    mp.setPosition_x(mp.getPosition_x() + 1);
            //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
        }
    }

    /**
     * @param mp
     */
    public void up(MetaPiece mp) {
        int position = mp.getPosition_y();
        if (position > 5) {
            //mp.clearPiece();
            mp.setPosition_y(mp.getPosition_y() - 1);
            if (checkCollision(mp) != COLLISION_NO)
                    mp.setPosition_y(mp.getPosition_y() + 1);
            //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
        }

    }

    /**
     * Le boolean permet de savoir si la piece a touché le bas de la grille :
     * true : oui
     * 
     * @param mp
     */
    public boolean down(MetaPiece mp, boolean slow) {
        int position = mp.getPosition_y();
        if (position + mp.getHauteur() < movebox.getMetabox().getHauteur()) {
            mp.setPosition_y(mp.getPosition_y() + 1);
            int retour;
            if ((retour = checkCollision(mp)) != COLLISION_NO)
                mp.setPosition_y(mp.getPosition_y() - 1);
            else {
                if (!slow) mp.setPosition_y(mp.getPosition_y() - 1);
            }
            return retour == COLLISION_PIECE_FIXE;
        }
        return true;
    }

    public void rotation(MetaPiece mp) {
        // Ancienne version :
        boolean format[][] = mp.getFormat();
        //mp.clearPiece();
        mp.rotationLeft();
        int position = mp.getPosition_x();
        int position_y = mp.getPosition_y();

        if (((position + mp.getLargeur() - 1) < movebox.getMetabox()
                .getLargeur())
                && ((position_y + mp.getHauteur() - 1 < movebox.getMetabox()
                        .getHauteur()))) {
            if (checkCollision(mp) != COLLISION_NO) {
                mp.setFormat(format);
            }
        } else {
            mp.setFormat(format);
        }
        //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
    }

    public boolean forceAddPiece(MetaPiece mp) {
        movebox.getMetabox().addPiece(mp);
        int length = movebox.getMetabox().getLargeur() - mp.getLargeur() + 1;
        for (int i = 0; i < length; i++) {
            int pos = i;
            if (i % 2 != 0) pos = length - i;
            mp.setPosition_x(pos);
            if (checkCollision(mp) == COLLISION_NO) {
            //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
            return true; }
        }
        movebox.getMetabox().removePiece(mp);
        return false;
    }

    public boolean addPiece(MetaPiece mp) {
        movebox.getMetabox().addPiece(mp);
        if (checkCollision(mp) == COLLISION_NO) {
        //mp.drawPiece(mp != movebox.getMetabox().getCurrentPiece());
        return true; }
        movebox.getMetabox().removePiece(mp);
        return false;
    }

    /**
     * @param liste
     * @return
     */
    public int downPieces(List liste, boolean descendre) {
        Collections.sort(liste, new PieceComparator());
        ChuteProcess cs = (ChuteProcess) Game.getGameService().getTimer()
                .getProcess();
        int min_hauteur = 6;
        List list2 = new ArrayList();
        MetaBoard metabox = movebox.getMetabox();

        for (int pos = 0; pos < liste.size(); pos++) {
            MetaPiece mp = (MetaPiece) liste.get(pos);
            int y = mp.getPosition_y();
            if (((MoveBoard) movebox).getMovepiece().down(mp, descendre)) {
                // On fige la pièce :
                //System.out.println("*******");
                metabox.removePiece(mp);
                liste.remove(mp);
                pos--;
                metabox.fixPiece(mp);
                movebox.getMetaInfo().addScore(5);
                if (mp.getPosition_y() < 5) {
                    Game.getGameService().endGame();
                    return 0;
                }
            } else {
                if (min_hauteur > mp.getPosition_y())
                        min_hauteur = mp.getPosition_y();
                if (!list2.contains(mp) && y == mp.getPosition_y()) {
                    list2.add(mp);
                    liste.remove(mp);
                    liste.add(liste.size(), mp);
                    pos--;
                }
            }
        }

        //metabox.setFreeNewPiece(min_hauteur > 5);

        // Des lignes à supprimer ?
        int ligne;
        Game gs = Game.getGameService();

        if ((ligne = metabox.removeLines()) != 0)
        //movebox.repaint();
        /* else */{
            //System.out.println(ligne);
            movebox.getMetaInfo().addLines(ligne);
            movebox.getMetaInfo().addScore(50 * ligne);
            gs.getBonus().newBonusByLine(ligne);
            //movebox.repaint();
        }

        // Gestion des niveaux :
        gs.getLevelmanager().setLevel(cs);

        if (metabox.getPieces_mouvantes().size() == 0)
                addPiece(PieceFactory.getInstance().getDisplayPiece(
                        movebox.getMetaInfo().getNiveau()));
        //System.out.println(metabox.isFreeNewPiece());

        return min_hauteur;
    }

    public void forceDownPiece(MetaPiece mp) {
        List liste = new ArrayList();
        liste.add(mp);
        int hauteur = mp.getPosition_y();
        while (liste.size() > 0) {
            downPieces(liste, true);
            if (hauteur == mp.getPosition_y())
            // Une pièce sur le passage, on ne peut pas descendre
                    // plus bas !
                    break;
            hauteur = mp.getPosition_y();
        }
    }

}