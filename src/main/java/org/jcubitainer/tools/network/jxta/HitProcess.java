package org.jcubitainer.tools.network.jxta;

import org.jcubitainer.display.infopanel.DisplayInfo;
import org.jcubitainer.meta.MetaInfo;
import org.jcubitainer.tools.Process;

public class HitProcess extends Process {

    DisplayInfo di = DisplayInfo.getThis();

    MetaInfo mi = di.getMetaInfo();

    int score, hit;

    static int max = 0;

    public static int getMax() {
        return max;
    }

    public static void setMax(int pmax) {
        max = pmax;
    }

    public HitProcess() {
        super(10 * 1000);
    }

    public void action() throws InterruptedException {
        score = mi.getScore();
        //        hit = mi.getHit();
        //        if (hit > score)
        //            setMax(hit);
        //        else
        setMax(score);
    }

}