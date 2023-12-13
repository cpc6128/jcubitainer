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

package org.jcubitainer.tools.network.jxta;

import java.util.Hashtable;

import org.jxtainer.J3Peer;

public class PeersManager {

    private static Hashtable peers = new Hashtable();

    public static PeerContainer getPeers(String peer_id) {
        return (PeerContainer) peers.get(peer_id);
    }

    public static PeerContainer getPeers(J3Peer peer) {
        PeerContainer pc = getPeers(peer.getPeerID());
        if (pc == null) {
            pc = new PeerContainer(peer);
            peers.put(peer.getPeerID(), pc);
        }
        return pc;
    }

}