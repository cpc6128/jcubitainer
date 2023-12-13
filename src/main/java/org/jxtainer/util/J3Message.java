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

import org.jxtainer.J3Pipe;

import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;

public class J3Message {

    private String who = null;

    private String what = null;

    private boolean system = false;
    
    private String peer_id = null;

    Message message = null;

    public J3Message(Message pmessage) {
        message = pmessage;
        who = getValue(J3Pipe.SENDERNAME, "Anonyme");
        what = getValue(J3Pipe.SENDERMESSAGE, "??");
        peer_id = getValue(J3Pipe.PEERID, "??");
        system = "yes".equals(getValue(J3Pipe.SYSTEMMESSAGE, ""));
    }

    public String getWhat() {
        return what;
    }

    public boolean isSystem() {
        return system;
    }

    public String getWho() {
        return who;
    }

    private String getValue(String tag, String defaut) {
        MessageElement elem = message.getMessageElement(null, tag);
        return elem == null ? defaut : new String(elem.getBytes(false));
    }

    public String getPeer_id() {
        return peer_id;
    }
}