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

package org.jcubitainer.meta;

import org.jcubitainer.manager.Configuration;
import org.jcubitainer.manager.NetworkManager;

public class MetaInfo {

    private int score = 0;

    private int ligne = 0;

    private int niveau = 0;

    private int bonus_des = 0;

    private int bonus_sup = 0;

    private int bonus_slow = 0;

    private int niveauSuivant = 0;

    boolean game_over = true;

    boolean pause = false;
    
    private static final String HIT_STANDARD = "hit";

    private static final String HIT_NETWORK = "hit_network";

    /**
     * @return
     */
    public int getLigne() {
        return ligne;
    }

    /**
     * @return
     */
    public int getNiveau() {
        return niveau;
    }

    /**
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * @param i
     */
    public void setLigne(int i) {
        ligne = i;
    }

    /**
     * @param i
     */
    public void setNiveau(int i) {
        niveau = i;
    }

    /**
     * @param i
     */
    public void setScore(int i) {
        score = i;
    }

    /**
     * @param i
     */
    public void setHit(int i) {
        // On stocke directement dans le fichier de configuration :
        Configuration.setPropertie(getHitKey(), String.valueOf(i));
    }

    public int getHit() {
        try {            
            return Integer.parseInt(Configuration.getProperties(getHitKey()));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private String getHitKey() {
        return NetworkManager.isNetworkOn()?HIT_NETWORK:HIT_STANDARD;
    }

    /**
     * @return
     */
    public int getBonus_des() {
        return bonus_des;
    }

    /**
     * @return
     */
    public int getBonus_slow() {
        return bonus_slow;
    }

    /**
     * @return
     */
    public int getBonus_sup() {
        return bonus_sup;
    }

    /**
     * @return
     */
    public boolean isGame_over() {
        return game_over;
    }

    /**
     * @param i
     */
    public void setBonus_des(int i) {
        bonus_des = i;
    }

    /**
     * @param i
     */
    public void setBonus_slow(int i) {
        bonus_slow = i;
    }

    /**
     * @param i
     */
    public void setBonus_sup(int i) {
        bonus_sup = i;
    }

    /**
     * @param b
     */
    public void setGame_over(boolean b) {
        game_over = b;
    }

    public void addLines(int l) {
        setLigne(ligne + l);
    }

    public void addScore(int l) {
        setScore(score + l);
    }

    public void clean() {
        setLigne(0);
        setScore(0);
        setNiveauSuivant(0);
    }

    /**
     * @return
     */
    public boolean isPause() {
        return pause;
    }

    /**
     * @param b
     */
    public void setPause(boolean b) {
        pause = b;
    }

    /**
     * @return
     */
    public int getNiveauSuivant() {
        return niveauSuivant;
    }

    /**
     * @param i
     */
    public void setNiveauSuivant(int i) {
        niveauSuivant = i;
    }

}