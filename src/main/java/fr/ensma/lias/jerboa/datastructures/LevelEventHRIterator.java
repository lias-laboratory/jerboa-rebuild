package fr.ensma.lias.jerboa.datastructures;

import java.util.Iterator;

/**
 * HistoryRecordIterator
 */
public class LevelEventHRIterator implements Iterator<LevelEventHR> {

	LevelEventHR current;

	public LevelEventHRIterator(LevelEventHR levelEvent) {

		current = levelEvent;
	}

	@Override
	public boolean hasNext() {
		return current != null;
	}

	@Override
	public LevelEventHR next() {
		LevelEventHR level = current;
		current = current.getNextLevelOrbit().getNextLevelEventAtIndex(0);
		return level;
	}

}
