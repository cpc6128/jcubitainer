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

package org.jcubitainer.display.infopanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.jcubitainer.display.JCubitainerFrame;
import org.jcubitainer.display.StickyFrame;
import org.jcubitainer.display.dialog.DisplayLogin;
import org.jcubitainer.display.table.NetworkDisplay;
import org.jcubitainer.display.theme.ThemeManager;
import org.jcubitainer.manager.Configuration;
import org.jcubitainer.manager.Game;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.tools.Messages;
import org.jcubitainer.tools.Ressources;
import org.jxtainer.J3xta;
import org.jxtainer.StartJXTA;

public class DisplayInfo extends JPanel implements ActionListener {

    InfoLabel recherche = null;

    InfoValue score = null;

    InfoValue ligne = null;

    InfoValue bonus_des = null;

    InfoValue bonus_sup = null;

    InfoValue bonus_slow = null;

    JButton start = null;

    JButton network = null;

    JButton connexion = null;

    JToolBar toolbar = null;

    InfoLabel bonus = null;

    InfoValue level = null;

    InfoValue hit = null;

    MetaInfo mi = null;

    InfoImage ii = null;

    private static DisplayInfo this_ = null;

    private static final String NL = "\n"; //$NON-NLS-1$

    private static final String SUFFIX = "V0_1_7_"; //$NON-NLS-1$

    /**
     *  
     */
    public DisplayInfo(MetaInfo pmi) {
        super();
        this_ = this;
        mi = pmi;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.black);
        //new InfoLabel("JCubitainer", this).setFont(Font.getFont("Verdana"));
        ii = new InfoImage(ThemeManager.getCurrent().getImage("ititle"), this); //$NON-NLS-1$

        start = new JButton(Messages.getString("DisplayInfo.start")); //$NON-NLS-1$
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setBackground(Color.black);
        start.setForeground(Color.white);
        start.setIcon(Ressources.getImageIcon("/ressources/images/Play16.gif")); //$NON-NLS-1$
        start.addActionListener(this);

        network = new JButton("Network"); //$NON-NLS-1$
        network.setAlignmentX(Component.CENTER_ALIGNMENT);
        network.setBackground(Color.black);
        network.setForeground(Color.white);
        network.setIcon(Ressources
                .getImageIcon("/ressources/images/Search16.gif")); //$NON-NLS-1$
        network.addActionListener(this);

        // Bar pour le d�marrage :
        toolbar = new JToolBar();
        toolbar.setBackground(Color.black);
        toolbar.setForeground(Color.black);
        toolbar.setFloatable(false);

        toolbar.add(start);
        toolbar.add(network);

        add(toolbar);

        connexion = new JButton("Connexion..."); //$NON-NLS-1$
        connexion.setAlignmentX(Component.CENTER_ALIGNMENT);
        connexion.setBackground(Color.black);
        connexion.setForeground(Color.white);
        connexion.setVisible(false);
        add(connexion);

        recherche = new InfoLabel(
                Messages.getString("DisplayInfo.recherche"), this); //$NON-NLS-1$
        recherche.setVisible(false);
        new InfoLabel(Messages.getString("DisplayInfo.score"), this); //$NON-NLS-1$
        score = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.ligne"), this); //$NON-NLS-1$
        ligne = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.niveau"), this); //$NON-NLS-1$
        level = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.hit"), this); //$NON-NLS-1$
        hit = new InfoValue("" + mi.getHit(), this); //$NON-NLS-1$

        //level.setVisible(false);
        new InfoLabel("*", this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.musique"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.theme"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.piece"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.rotation"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.chutte"), this); //$NON-NLS-1$
        //        new InfoHelp(Messages.getString("DisplayInfo.ajouter"), this); //$NON-NLS-1$
        new InfoHelp(Messages.getString("DisplayInfo.pause"), this); //$NON-NLS-1$
        new InfoLabel("*", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.bonus_destruction"), this); //$NON-NLS-1$
        bonus_des = new InfoValue("-", this); //$NON-NLS-1$
        new InfoLabel(Messages.getString("DisplayInfo.bonus_lignes"), this); //$NON-NLS-1$
        bonus_sup = new InfoValue("-", this); //$NON-NLS-1$
        bonus = new InfoLabel(Messages.getString("DisplayInfo.bonus_slow"),
                this); //$NON-NLS-1$
        bonus_slow = new InfoValue("-", this); //$NON-NLS-1$
        new InfoMsg(Messages.getString("DisplayInfo.rien"), this); //$NON-NLS-1$
    }

    public void setLineDisplay(int l) {
        ligne.setText(String.valueOf(l));
    }

    public void setScoreDisplay(int l) {
        score.setText(String.valueOf(l));
    }

    public void setBonusDisplay(int l) {
        bonus_des.setText(String.valueOf(l));
    }

    public void setSlowDisplay(int l) {
        bonus_slow.setText(String.valueOf(l));
    }

    public void setDeleteLineDisplay(int l) {
        bonus_sup.setText(String.valueOf(l));
    }

    public void setLevelDisplay(int l) {
        level.setText(String.valueOf(l));
    }

    public void setHitDisplay(int l) {
        hit.setText(String.valueOf(l));
        hit.setBorder(BorderFactory.createLineBorder(Color.green));
    }

    public void setHitDisplayWithNoBorder(int l) {
        hit.setText(String.valueOf(l));
        hit.setBorder(BorderFactory.createEmptyBorder());
    }

    public void clearHit() {
        hit.setText("-");
        hit.setBorder(BorderFactory.createEmptyBorder());
    }

    public void activeSlowDisplay(boolean b) {
        if (b)
            bonus.setForeground(Color.cyan);
        else
            bonus.setForeground(Color.white);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == start) {

            String[] levels = { "1", "2", "3", "4", "5", "6", "7", "8", "9", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
                    "10" }; //$NON-NLS-1$
            String s = (String) JOptionPane.showInputDialog(this.getParent(),
                    Messages.getString("DisplayInfo.quel_niveau")
                            + Messages.getString("DisplayInfo.debutant") //$NON-NLS-1$ //$NON-NLS-2$
                            + Messages.getString("DisplayInfo.dieu"), Messages
                            .getString("DisplayInfo.choixniveau"), //$NON-NLS-1$ //$NON-NLS-2$
                    JOptionPane.PLAIN_MESSAGE, null, levels, "level"); //$NON-NLS-1$

            if (s == null)
                return;

            mi.setNiveau(s == null ? 1 : Integer.parseInt(s));

            JOptionPane.showMessageDialog(this.getParent(), Messages
                    .getString("DisplayInfo.pourgagner1") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner2") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner3") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner4") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner5") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner6") //$NON-NLS-1$
                    + Messages.getString("DisplayInfo.pourgagner7"), //$NON-NLS-1$
                    Messages.getString("DisplayInfo.pourgagner_titre"),
                    JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$

            Game.getGameService().start();
            mi.setGame_over(false);
            return;
        }

        if (arg0.getSource() == network) {
            DisplayLogin customDialog = new DisplayLogin(JCubitainerFrame
                    .getFrame(), Configuration.getProperties("login"));
            customDialog.pack();
            customDialog.setLocationRelativeTo(this.getParent());
            customDialog.setVisible(true);
            String s = customDialog.getLogin();
            Configuration.setPropertie("login", s);

            JOptionPane
                    .showMessageDialog(
                            this.getParent(),
                            Messages.getString("DisplayInfo.jxta_attention") //$NON-NLS-1$
                                    + NL
                                    + Messages
                                            .getString("DisplayInfo.jxta_minute"), //$NON-NLS-1$
                            Messages.getString("DisplayInfo.jxta_information"), JOptionPane.WARNING_MESSAGE); //$NON-NLS-1$

            JOptionPane
                    .showMessageDialog(
                            this.getParent(),
                            Messages.getString("DisplayInfo.jxta_malus") //$NON-NLS-1$
                                    + NL
                                    + Messages
                                            .getString("DisplayInfo.jxta_partie") //$NON-NLS-1$
                                    + NL
                                    + Messages
                                            .getString("DisplayInfo.jxta_supprimee") //$NON-NLS-1$
                                    + NL
                                    + Messages
                                            .getString("DisplayInfo.jxta_ajoutee"), //$NON-NLS-1$
                            Messages.getString("DisplayInfo.jxta_regle"), JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$

            File home = null;
            if (!"OUI".equals(Configuration.getProperties("DEBUG"))) { //$NON-NLS-1$ //$NON-NLS-2$
                home = new File(System.getProperty("user.home") //$NON-NLS-1$
                        + File.separator + Configuration.DIR + File.separator
                        + ".jxtainer"); //$NON-NLS-1$
                home.mkdirs();
            }
            StartJXTA.wakeUp(s, SUFFIX, home, false);
            clearHit();
            StickyFrame frame2 = new StickyFrame(JCubitainerFrame.getFrame(), s);
            frame2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            NetworkDisplay nd = new NetworkDisplay();
            frame2.getContentPane().add(nd);
            frame2.setResizable(false);
            frame2.pack();
            frame2.setVisible(true);

        }
    }

    public void setGameOverDisplay(boolean b) {
        toolbar.setVisible(b);
        toolbar.setEnabled(b);
        if (!b) {
            hit.setBorder(BorderFactory.createEmptyBorder());
        }
    }

    public void setRechercheVisible(boolean b) {
        recherche.setVisible(b);
    }

    public void setNetwork(int i) {
        boolean b = i == J3xta.JXTA_STATUT_ON || i == J3xta.JXTA_STATUT_CONNECT;
        toolbar.setVisible(!b);
        toolbar.setEnabled(!b);
        connexion.setVisible(b);
        connexion.setEnabled(b);
        switch (i) {
        case J3xta.JXTA_STATUT_ON:
            connexion.setText(Messages.getString("DisplayInfo.jxta_connecte")); //$NON-NLS-1$
            connexion.setIcon(Ressources
                    .getImageIcon("/ressources/images/online.png")); //$NON-NLS-1$
            setRechercheVisible(true);
            break;
        case J3xta.JXTA_STATUT_OFF:
            connexion.setText(Messages
                    .getString("DisplayInfo.jxta_nonconnecte")); //$NON-NLS-1$
            connexion.setIcon(Ressources
                    .getImageIcon("/ressources/images/throbber-small.gif")); //$NON-NLS-1$
            break;
        case J3xta.JXTA_STATUT_CONNECT:
            connexion.setText(Messages.getString("DisplayInfo.connexion")); //$NON-NLS-1$
            connexion.setIcon(Ressources
                    .getImageIcon("/ressources/images/throbber-small.gif")); //$NON-NLS-1$
            break;
        case J3xta.JXTA_STATUT_ERROR:
            connexion.setText(Messages.getString("DisplayInfo.jxta_erreur")); //$NON-NLS-1$
            connexion.setIcon(Ressources
                    .getImageIcon("/ressources/images/Stop16.gif")); //$NON-NLS-1$
            break;
        }
    }

    /**
     * @return
     */
    public static DisplayInfo getThis() {
        return this_;
    }

    /**
     * @return
     */
    public MetaInfo getMetaInfo() {
        return mi;
    }

    public void refreshTheme() {
        ii.setIcon(new ImageIcon(ThemeManager.getCurrent().getImage("ititle"))); //$NON-NLS-1$
    }

}