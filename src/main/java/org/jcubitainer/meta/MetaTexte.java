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

/* History & changes **************************************************
 *                                                                     *
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.meta;

public class MetaTexte {

    StringBuffer texte = new StringBuffer(20);

    boolean display = false;

    float alpha = .3f;

    public void setTexte(String s) {
        texte.delete(0, texte.length());
        texte.append(s);
    }

    public String getTexte() {
        return texte.toString();
    }

    /**
     * @return
     */
    public boolean isDisplay() {
        return display;
    }

    /**
     * @param b
     */
    public void setDisplay(boolean b) {
        display = b;
    }

    /**
     * @return
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * @param f
     */
    public void setAlpha(float f) {
        alpha = f;
    }

}