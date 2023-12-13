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


package org.jxtainer.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jxtainer.StartJXTA;

public class J3MessagePipe {

    private static List pipe = new ArrayList();

    public static J3Message drop() {
        synchronized (pipe) {
            if (!pipe.isEmpty())
                return (J3Message) pipe.remove(0);
            else
                return null;
        }
    }

    public static void put(J3Message mes) {
        synchronized (pipe) {
            pipe.add(mes);
        }
        // Listener :
        Iterator list = StartJXTA.jxMessageListenerList.iterator();
        JxMessageListener listener = null;
        while ( list.hasNext() ) {
            listener = (JxMessageListener) list.next();                
            listener.receiveMessage(drop());
        }
    }

}