/***********************************************************************
 * JCubitainer                                                         *
 * Version release date : May 5, 2004                                  *
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
 ******** May 5, 2004 **************************************************
 *   - First release                                                   *
 ***********************************************************************/


package org.jcubitainer.aspect;

import org.jcubitainer.move.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.display.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.display.theme.*;
import org.jcubitainer.tools.Messages;

public aspect RefreshDisplayAspect {

	pointcut refresh() : call(int MovePiece.downPieces(..))
		|| call(void MovePiece.up(..))
		|| call(void MovePiece.left(..))
		|| call(void MovePiece.right(..))
		|| call(boolean MovePiece.forceAddPiece(..))
		|| call(void MovePiece.rotation(..))
		|| call(MetaPiece MetaBoard.changeCurrentPiece(..))
		|| call(boolean MovePiece.addPiece(..))
		|| call(void MetaBoard.removeLastLines(..))
		|| call(void MetaBoard.removePiece(..))
		|| call(void MetaBoard.upLines(..))
		|| call(void Game.pause(..))
		|| call(void Game.start(..))
		|| call(void MetaTexte.setDisplay(..))
		;

	after() : refresh() {
		DisplayBoard.getThis().repaint();
		//System.out.println("RefreshDisplayAspect from : " + thisJoinPoint);
	}

	pointcut refreshTheme() : call(boolean ThemeManager.swithTheme(..));

	before() : refreshTheme() {
        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
                Messages.getString("RefreshDisplayAspect.chargement"));	     //$NON-NLS-1$
	}
	after() : refreshTheme() {
		DisplayBoard.getThis().repaint();
        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
                ThemeManager.getCurrent().getNom());
	}

	pointcut showPlay() : call(void Messages.switchLangue(..));

	after() : showPlay() {
        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
                Messages.getString("RefreshDisplayAspect.langue") //$NON-NLS-1$
                +
                Configuration.getProperties("langue")); //$NON-NLS-1$
	}
	

}
