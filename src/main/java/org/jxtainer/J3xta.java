/***********************************************************************
 * JXtainer                                                            *
 * Version release date : April 3, 2005                                *
 * Author : Mounes Ronan metalm@users.berlios.de                       *
 *                                                                     *
 *     http://jcubitainer.berlios.de/jxtainer/                         *
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


package org.jxtainer;

import java.util.Iterator;

import org.jxtainer.util.JxStatutListener;

public class J3xta {

    private static final String JXTA_ID = "J3xtainer";
    
    private static String suffix = "";

    public static final int JXTA_STATUT_OFF = 0;

    public static final int JXTA_STATUT_ON = 1;

    public static final int JXTA_STATUT_ERROR = 2;

    public static final int JXTA_STATUT_CONNECT = 3;

    private static int statut = JXTA_STATUT_OFF;

    /**
     * @return
     */
    public static int getStatut() {
        return statut;
    }

    /**
     * @param i
     */
    public static void setStatut(int i) {
        statut = i;
        // Listener :
        Iterator list = StartJXTA.jxStatutListenerList.iterator();
        JxStatutListener listener = null;
        while ( list.hasNext() ) {
            listener = (JxStatutListener) list.next();                
            listener.newStatut(i);
        }    
    }

    public static void setSuffix(String suffix) {
        J3xta.suffix = suffix;
    }

    public static String getJXTA_ID() {
        return JXTA_ID + suffix;
    }
}