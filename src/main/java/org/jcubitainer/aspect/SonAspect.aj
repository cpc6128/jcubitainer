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

import org.jcubitainer.display.DisplayBoard;
import org.jcubitainer.display.infopanel.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.sound.InterfaceMusique;
import org.jcubitainer.sound.Sons;
import org.jcubitainer.display.theme.*;
import org.jcubitainer.tools.Messages;

public aspect SonAspect {

	// Son quand une pièce se fixe
	pointcut sonfixPiece() : call(void MetaBoard.fixPiece(..));

	after() : sonfixPiece() {
		Sons.son1();
	}

	// Son quand une ligne disparaît
	pointcut sonLigne() : call(void Bonus.newBonusByLine(..));

	after() : sonLigne() {
		Sons.son2();
	}

	pointcut sonrefreshGame_over() : call(void MetaInfo.setGame_over(..));

	after() : sonrefreshGame_over() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		if (mi.isGame_over())
			InterfaceMusique.STOP_musique();
		else
			InterfaceMusique.START_musique();
	}
	
	pointcut refreshTheme() : call(boolean ThemeManager.swithTheme(..));

	after() : refreshTheme() {
        if (InterfaceMusique.STOP_musique())
            InterfaceMusique.START_musique();
	}

	pointcut showPlay() : call(boolean InterfaceMusique.switchPlayMusic(..));

	after() : showPlay() {
        DisplayBoard.getThis().getMetabox().getTexte().setTexte(
                Messages.getString("SonAspect.musique") //$NON-NLS-1$
                +
                ("true".equals(Configuration.getProperties("musique"))?"on":"off")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
}
