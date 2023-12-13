package org.jcubitainer.display.table;

public class DisplayPlayer implements Comparable {

	private String name = null;

	private int hit = 0;

	public DisplayPlayer(String pname) {
		super();
		name = pname;
	}

	public int compareTo(Object o) {
		int temp = ((DisplayPlayer) o).getHit();
		if (temp == getHit())
			return 0;
		return temp > getHit() ? 1 : -1;
	}

	public String toString() {
		return getName() + " ( " + hit + " )";
	}

	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getHit() {
		return hit;
	}

	/**
	 * @param i
	 */
	public void setHit(int i) {
		hit = i;
	}

}
