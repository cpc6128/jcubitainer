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

package org.jcubitainer.manager;

import org.jcubitainer.display.DisplayPiece;
import org.jcubitainer.meta.MetaBoard;
import org.jcubitainer.meta.MetaPiece;

public class PieceFactory {

    public static final boolean[][] format = { { true}, { true}, { true},
            { true}};

    // #
    // #
    // #
    // #

    public static final boolean[][] format_ = { { false, true}, { true, true}};

    //  #
    // ##

    public static final boolean[][] format2 = { { true, true, true},
            { false, true, false}};

    // ###
    //  #

    public static final boolean[][] format3 = { { true, true}, { false, true},
            { false, true}};

    // ##
    //  #
    //  #

    public static final boolean[][] format3_ = { { true, true}, { true, false},
            { true, false}};

    // ##
    // # 
    // #

    public static final boolean[][] format4 = { { true, false}, { true, true}};

    // # 
    // ##

    public static final boolean[][] format5 = { { true, true, false},
            { false, true, true}};

    // ##
    //  ##

    public static final boolean[][] format5_ = { { false, true, true},
            { true, true, false}};

    //  ##
    // ## 

    public static final boolean[][] format6 = { { true, true}, { true, true}};

    // ##
    // ##

    public static final boolean[][] format7 = { { false, true, false},
            { true, true, true}, { false, true, false}};

    //  #
    // ###
    //  #

    public static final boolean[][] format8 = { { true, false}, { false, true}};

    // #
    //  #

    public static final boolean[][] format8_ = { { true, false, false},
            { false, true, false}, { false, false, true}};

    // #  
    //  #
    //   #

    public static final boolean[][] format9 = { { true, true}};

    // ##

    public static final boolean[][] format10 = { { true}};

    // #

    public static final boolean[][] format11 = { { true, true}, { true, true},
            { true, true}};

    // ##
    // ##
    // ##

    private static boolean[][][] formats = { format, format_, format2,
    /* format2_, */
    format3, format3_, format4, format5, format5_, format6,
    // Level 3 :
            format7, format8, format8_, format9, format10, format11};

    private static PieceFactory _this = null;

    private static MetaBoard mb = null;

    int position_depart;

    /**
     *  
     */
    public PieceFactory(MetaBoard pmb) {
        super();
        _this = this;
        mb = pmb;
        position_depart = mb.getLargeur() / 2;
    }

    public DisplayPiece getDisplayPiece(int level) {
        int max_matiere = MetaPiece.MATIERE_MAX;
        int max = 8 + level;
        if (max > formats.length) max = formats.length;
        int piece = (int) (Math.random() * (double) max);
        int couleur = (int) (Math.random() * (double) max_matiere) + 1;
        DisplayPiece mp1 = new DisplayPiece(formats[piece], couleur);
        mp1.setPosition_x(position_depart - mp1.getLargeur() / 2);
        mp1.setPosition_y(0);
        return mp1;
    }

    public static PieceFactory getInstance() {
        if (_this == null) throw new Error("Pas d'instance !");
        return _this;
    }

}