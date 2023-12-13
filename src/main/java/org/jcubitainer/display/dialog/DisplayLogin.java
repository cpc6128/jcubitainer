/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : December 28, 2004                            *
 * Author : Mounès Ronan metalm@users.berlios.de                       *
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
 ******** December 30, 2004 ********************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.display.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jcubitainer.tools.Messages;

public class DisplayLogin extends JDialog implements ActionListener,
        PropertyChangeListener {

    public static final int MAX = 10;

    private String login = null;

    private JTextField champ;

    private JOptionPane fenetre;

    Object[] options_on = { Messages.getString("DisplayLogin.go") }; //$NON-NLS-1$

    Object[] options_off = { "X" }; //$NON-NLS-1$

    public DisplayLogin(JFrame frame, String old) {

        super(frame, true);

        champ = new JTextField(old, MAX);

        Object[] tableau = { Messages.getString("DisplayLogin.nom"), //$NON-NLS-1$
                champ };

        fenetre = new JOptionPane(tableau, JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION, null, options_off, options_off[0]);

        setContentPane(fenetre);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent ce) {
                champ.requestFocusInWindow();
            }
        });

        champ.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                check();
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
                check();
            }

        });

        champ.addActionListener(this);
        fenetre.addPropertyChangeListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        // Pour forcer le bouton "GO !"
        fenetre.setValue(options_on[0]);
    }

    private boolean check() {
        String s = champ.getText();
        boolean retour = false;
        if (s == null || s.length() < 1 || s.length() > MAX) {
            fenetre.setOptions(options_off);
        } else {
            fenetre.setOptions(options_on);
            retour = true;
        }

        champ.requestFocusInWindow();
        return retour;
    }

    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();

        if (isVisible()
                && (e.getSource() == fenetre)
                && (JOptionPane.VALUE_PROPERTY.equals(prop) || JOptionPane.INPUT_VALUE_PROPERTY
                        .equals(prop))) {
            Object value = fenetre.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                return;
            }

            fenetre.setValue(JOptionPane.UNINITIALIZED_VALUE);

            if (options_on[0].equals(value)) {
                login = champ.getText();
                if (check())
                    quit();
            } else
                champ.requestFocusInWindow();
        }
    }

    private void quit() {
        setVisible(false);
    }

    public String getLogin() {
        return login;
    }

    public void setVisible(boolean visible) {
        if (visible) {
            check();
            String s = champ.getText();
            if (s != null) {
                champ.setSelectionStart(0);
                champ.setSelectionEnd(s.length());
            }
        }
        super.setVisible(visible);
    }

}