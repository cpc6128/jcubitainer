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

import net.jxta.discovery.DiscoveryService;
import net.jxta.ext.config.Configurator;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.rendezvous.RendezVousService;

import org.jxtainer.util.Log;
import org.jxtainer.util.ProcessMg;

public class J3xtaConnect {

    private PeerGroup root = null;

    private DiscoveryService rootDiscoveryService = null;

    private ProcessMg groupDiscoveryServiceProcess = null;

    private J3Group j3root = null;

    private ProcessMg group = null;

    private RendezVousService rdv_root;

    public J3xtaConnect(File configuration, boolean proxy) {
        try {
            J3xta.setStatut(J3xta.JXTA_STATUT_CONNECT);
            Log.debug("! Connexion a JXTA !");

            // Configuration automatique :
            if (configuration != null)
                System.setProperty("JXTA_HOME", configuration.getAbsolutePath());

            File config_jxta = new File(Configurator.getHome(), "PlatformConfig").getAbsoluteFile();

            if (!proxy && !config_jxta.exists()) {
                try {
                    Log.debug("! Creation du fichier de configuration JXTA.");
                    String name = StartJXTA.getPeerName();
                    Configurator config = new Configurator(name, "JXTAConfiguration", name, "monmotdepasse2005");
                    config.save();

                } catch (Exception ce) {
                    Log.debug("! Creation du fichier de configuration JXTA impossible.");
                    // Impossible de faire une configuration automatique !
                }
            }

            root = PeerGroupFactory.newNetPeerGroup();
            rootDiscoveryService = root.getDiscoveryService();

            //          Extract the discovery and rendezvous services from our peer group
            rdv_root = root.getRendezVousService();

            // Wait until we connect to a rendezvous peer
            Log.debug("! On recherche un rendezvous");

            int boucle = 30 /*1 minute*/;

            while (!rdv_root.isConnectedToRendezVous() && boucle-- > 0) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                }
            }

            if (rdv_root.isConnectedToRendezVous())
                Log.debug("! Peer Rendez-vous trouve.");
            else
                Log.debug("! Peer Rendez-vous non trouve.");

            Log.debug("! vous etes connectez a JXTA.");

        } catch (Exception e) {
            Log.debug("! fatal error : group creation failure");
            e.printStackTrace();
            J3xta.setStatut(J3xta.JXTA_STATUT_ERROR);
        }
    }

    public void addGroupListener() {

        if (root == null) {
            Log.debug("! Pas de peer Root !");
            return;
        }

        // On lance le service qui va devoir trouver les groupes :
        groupDiscoveryServiceProcess = new ProcessMg(new J3GroupDiscoveryListener(rootDiscoveryService, root));
        groupDiscoveryServiceProcess.wakeUp();

        // On va attendre 1 minute pour essayer de trouver un groupe JXtainer

        Log.debug("! On va essaye de trouver une partie.");
        try {
            int boucle = 60 * 1; // 5 minutes
            while (!J3Group.isConnectToGroup() && --boucle > 0) {
                Thread.sleep(1000);
                Log.debug(".");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!J3Group.isConnectToGroup()) {
            Log.debug("! Pas de groupe trouve :-(");
            J3GroupRDV rdv = new J3GroupRDV(root, rootDiscoveryService);
            group = new ProcessMg(rdv);
            group.wakeUp();
        } else
            Log.debug("! Une partie trouvee sur Internet.");
    }
}