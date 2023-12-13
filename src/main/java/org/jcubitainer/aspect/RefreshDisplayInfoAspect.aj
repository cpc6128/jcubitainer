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

import org.jcubitainer.display.infopanel.*;
import org.jcubitainer.meta.*;
import org.jcubitainer.manager.*;
import org.jcubitainer.sound.InterfaceMusique;
import org.jcubitainer.display.theme.*;


public aspect RefreshDisplayInfoAspect {

	pointcut refreshLigne() : call(void MetaInfo.setLigne(..));

	after() : refreshLigne() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setLineDisplay(mi.getLigne());
	}

	pointcut refreshScore() : call(void MetaInfo.setScore(..));

	after() : refreshScore() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setScoreDisplay(mi.getScore());
	}

	pointcut refreshLevel() : call(void MetaInfo.setNiveau(..));

	after() : refreshLevel() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setLevelDisplay(mi.getNiveau());
	}

	pointcut refreshHit() : call(void MetaInfo.setHit(..));

	after() : refreshHit() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setHitDisplay(mi.getHit());
	}

	pointcut refreshBonus_des() : call(void MetaInfo.setBonus_des(..));

	after() : refreshBonus_des() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setBonusDisplay(mi.getBonus_des());
	}

	pointcut refreshBonus_slow() : call(void MetaInfo.setBonus_slow(..));

	after() : refreshBonus_slow() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setSlowDisplay(mi.getBonus_slow());
	}

	pointcut refreshStartSlow() : call(void Bonus.slow(..));

	after() : refreshStartSlow() {
		DisplayInfo di = DisplayInfo.getThis();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.activeSlowDisplay(true);
	}

	pointcut refreshStopSlow() : call(void Bonus.stopSlow(..));

	after() : refreshStopSlow() {
		DisplayInfo di = DisplayInfo.getThis();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.activeSlowDisplay(false);
	}

	pointcut refreshBonus_sup() : call(void MetaInfo.setBonus_sup(..));

	after() : refreshBonus_sup() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setDeleteLineDisplay(mi.getBonus_sup());
	}

	pointcut refreshGame_over() : call(void MetaInfo.setGame_over(..));

	after() : refreshGame_over() {
		DisplayInfo di = DisplayInfo.getThis();
		MetaInfo mi = di.getMetaInfo();
		//System.out.println("RefreshDisplayInfoAspect from : " + thisJoinPoint);
		di.setGameOverDisplay(mi.isGame_over());
		if (mi.isGame_over())
			InterfaceMusique.STOP_musique();
		else
			InterfaceMusique.START_musique();
	}

	pointcut refreshTheme() : call(boolean ThemeManager.swithTheme(..));

	after() : refreshTheme() {
		DisplayInfo di = DisplayInfo.getThis();
		di.refreshTheme();
		
	}
	

}
