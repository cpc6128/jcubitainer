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

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jxtainer.util.JxMessageListener;
import org.jxtainer.util.JxPeerListener;
import org.jxtainer.util.JxStatutListener;
import org.jxtainer.util.Process;
import org.jxtainer.util.ProcessMg;

public class StartJXTA extends Process {

    static J3xtaConnect connect = null;

    static private String name = null;

    static private String peer_ID = null;

    private static ProcessMg manager = new ProcessMg(new StartJXTA());

    private static File config_dir = null;

    private static boolean proxy = false;

    // Les listeners :
    public static List jxStatutListenerList = new ArrayList();

    public static List jxPeerListenerList = new ArrayList();

    public static List jxMessageListenerList = new ArrayList();

    public StartJXTA() {
        super(1000);
        setPriority(Thread.MIN_PRIORITY);
    }

    public void action() throws InterruptedException {
        //Demarrage de JXTA :
        if (connect == null) {
            connect = new J3xtaConnect(config_dir,proxy);
            //Pour etre a l'ecoute des autres :
            connect.addGroupListener();
        }
    }

    /**
     * @param login
     * @param suffix
     * @param object
     * @param firewall
     */
    public static void wakeUp(String login, String suffix_group,
            File configuration_dir, boolean pproxy) {
        J3xta.setSuffix(suffix_group);
        config_dir = configuration_dir;
        name = login;
        proxy = pproxy;
        manager.wakeUp();
    }

    public static String getPeerName() {
        return name;
    }

    public static void setPeer_ID(String peer_ID) {
        StartJXTA.peer_ID = peer_ID;
    }

    public static String getPeer_ID() {
        return peer_ID;
    }

    /**
     * @param lister
     */
    public static void addJxMessageListener(JxMessageListener listener) {
        jxMessageListenerList.add(listener);
    }

    /**
     * @param listener
     */
    public static void addJxStatutListener(JxStatutListener listener) {
        jxStatutListenerList.add(listener);
    }

    /**
     * @param listener
     */
    public static void addJxPeerListener(JxPeerListener listener) {
        jxPeerListenerList.add(listener);
    }

    /**
     * @return
     */
    public static J3Pipe getPipe() {
        Enumeration liste = J3Group.getJ3Groups();
        J3Pipe pipe = null;
        J3Group group = null;
        while(liste.hasMoreElements()){
            group = (J3Group)liste.nextElement();
            if (group.isJoinnedToGroup()) {
                pipe = group.getPipe();    
                break;
            }
        }
        return pipe;
    }
}