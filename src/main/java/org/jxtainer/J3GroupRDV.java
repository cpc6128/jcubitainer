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

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;

import org.jxtainer.util.Log;
import org.jxtainer.util.Process;
import org.jxtainer.util.ProcessMg;

public class J3GroupRDV extends Process {

	public static final String NAME = "Partie_";

	public static final String DESCRIPTION = "J3xtainer Groupe";

	private PeerGroup peerGroup = null;

	private PeerGroup rootGroup = null;

	private DiscoveryService discoSvc = null;

	private ProcessMg pipe = null;

	private PeerGroupAdvertisement adv = null;

	/**
	 *  
	 */
	public J3GroupRDV(PeerGroup proot, DiscoveryService pdiscoSvc) {
		super(3 * 60 * 1000);

		String group_name = J3xta.getJXTA_ID() + NAME + StartJXTA.getPeerName();

		rootGroup = proot;
		discoSvc = pdiscoSvc;

		try {
			ModuleImplAdvertisement implAdv = rootGroup
					.getAllPurposePeerGroupImplAdvertisement();

			PeerGroupAdvertisement newPGAdv = (PeerGroupAdvertisement) AdvertisementFactory
					.newAdvertisement(PeerGroupAdvertisement
							.getAdvertisementType());

			// Nouveau IDFactory :
			PeerGroupID id = IDFactory.newPeerGroupID();

			newPGAdv.setPeerGroupID(id);
			newPGAdv.setModuleSpecID(implAdv.getModuleSpecID());
			newPGAdv.setName(group_name);
			newPGAdv.setDescription(DESCRIPTION);
			peerGroup = rootGroup.newGroup(newPGAdv);

			peerGroup.getRendezVousService().startRendezVous();

			adv = peerGroup.getPeerGroupAdvertisement();

			Log.debug("! Groupe cree :" + peerGroup.getPeerGroupName());

		} catch (Exception e) {
			System.err.println("! Group creation failed");
			e.printStackTrace();
		}

	}

	public void publishGroup() {

		try {
			discoSvc.publish(adv, PeerGroup.DEFAULT_LIFETIME,
					PeerGroup.DEFAULT_EXPIRATION);
		} catch (Exception e) {
			System.out
					.println("! Failed to publish peer advertisement in the group ["
							+ peerGroup.getPeerGroupName() + "]");
		}

	}

	public void action() throws InterruptedException {
		publishGroup();

	}

	public DiscoveryService getDiscoveryService() {
		return peerGroup.getDiscoveryService();
	}

	public PeerGroupID getPeerGroupID() {
		return peerGroup.getPeerGroupID();
	}

	public String toString() {

		return peerGroup.getPeerGroupName();

	}
}