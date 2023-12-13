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

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.jxtainer.util.JxPeerListener;
import org.jxtainer.util.Log;
import org.jxtainer.util.Process;
import org.jxtainer.util.ProcessMg;

public class J3PeerManager extends Process {

    static Hashtable peers_int = new Hashtable(20);

    static Hashtable peers_name = new Hashtable(20);

    static ProcessMg manager = new ProcessMg(new J3PeerManager());
    
    static J3Peer latest = null;

    static J3Peer latest_remove = null;

    public J3PeerManager() {
        super(15 * 1000);
    }

    public void action() throws InterruptedException {
        synchronized (peers_int) {
            Enumeration keys = peers_int.keys();
            while (keys.hasMoreElements()) {
                String peer_id = (String) keys.nextElement();
                J3Peer peer = getPeer(peer_id);
                int nb = getInt(peer);
                if (nb < 1) {
                    Log.debug("! Suppression du peer non actif : "
                            + peer.getName());
                    remove(peer);
                } else
                    put(peer, String.valueOf(nb - 1));
            }
        }
    }

    public static void addPeer(J3Peer peer) {
        manager.wakeUp();
        int nb = getInt(peer);
        if (nb == 0) {
            Log.debug("! Nouveau Peer actif : " + peer.getName());
            put(peer, String.valueOf(3));
            // Listener :
            Iterator list = StartJXTA.jxPeerListenerList.iterator();
            JxPeerListener listener = null;
            while ( list.hasNext() ) {
                listener = (JxPeerListener) list.next();                
                listener.newPeer(peer);
            }            
            
        } else if (nb < 11)
            put(peer, String.valueOf(nb + 1));
        latest = peer;
    }

    private static int getInt(J3Peer p) {
        String temp = (String) peers_int.get(p.toString());
        int nb = 0;
        try {
            nb = Integer.valueOf(temp).intValue();
        } catch (NumberFormatException e) {
        }
        return nb;
    }

    protected static void remove(J3Peer peer) {
        peers_int.remove(peer.toString());
        peers_name.remove(peer.toString());
        latest_remove = peer;
        // Listener :
        Iterator list = StartJXTA.jxPeerListenerList.iterator();
        JxPeerListener listener = null;
        while ( list.hasNext() ) {
            listener = (JxPeerListener) list.next();                
            listener.deletePeer(peer);
        }
    }

    private static void put(J3Peer peer, String s) {
        peers_int.put(peer.toString(), s);
        peers_name.put(peer.toString(), peer);
    }

    private static J3Peer getPeer(String s) {
        return (J3Peer) peers_name.get(s);
    }

    public static int size() {
        return peers_name.size();
    }

    public static Enumeration getAll() {
        return peers_name.elements();
    }
    public static J3Peer getLatest() {
        return latest;
    }
    public static J3Peer getLatest_remove() {
        return latest_remove;
    }
    
    public static boolean existPeer(String peer_id) {
        return peers_name.containsKey(peer_id);
    }
}