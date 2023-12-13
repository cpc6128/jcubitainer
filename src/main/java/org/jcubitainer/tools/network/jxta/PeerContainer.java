package org.jcubitainer.tools.network.jxta;

import org.jcubitainer.display.table.DisplayPlayer;
import org.jxtainer.J3Peer;

public class PeerContainer extends DisplayPlayer {

    J3Peer peer = null;

    public PeerContainer(J3Peer ppeer) {
        super(ppeer.getName());
        peer = ppeer;
    }

}