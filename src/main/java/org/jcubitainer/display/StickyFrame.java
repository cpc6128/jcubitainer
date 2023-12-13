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
 ******** December 28, 2004 ********************************************
 *   - First release                                                   *
 ***********************************************************************/

package org.jcubitainer.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.jcubitainer.display.theme.ThemeManager;

public class StickyFrame extends JFrame {

	JFrame parent = null;
	StickyFrame this_ = null;
	Rectangle current_rec = null;

	public StickyFrame(JFrame pparent, String name) {
		super(name);
		parent = pparent;
		this_ = this;
		parent.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}

			public void windowClosing(WindowEvent e) {
			}

			public void windowClosed(WindowEvent e) {
			}

			public void windowIconified(WindowEvent e) {
				this_.setVisible(false);
			}

			public void windowDeiconified(WindowEvent e) {
				this_.setVisible(true);
				this_.toFront();
			}

			public void windowActivated(WindowEvent e) {
			}

			public void windowDeactivated(WindowEvent e) {
			}
		});

		parent.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
			}

			public void mouseMoved(MouseEvent e) {
				this_.stickyIt();
			}
		});
		
		setIconImage(ThemeManager.getCurrent().getImage("ilogo"));
	}

	public void pack() {
		super.pack();
		stickyIt();
	}

	private void stickyIt() {
		if (parent != null
			&& (current_rec == null || (current_rec != null && !parent.getBounds().equals(current_rec)))) {
			Point point = parent.getLocation();
			Dimension dim = parent.getSize();
			point.setLocation(point.getX() + dim.getWidth(), point.getY());
			setLocation(point);
			setSize(getWidth(), (int) dim.getHeight());
			current_rec = parent.getBounds();
		}
	}
}
