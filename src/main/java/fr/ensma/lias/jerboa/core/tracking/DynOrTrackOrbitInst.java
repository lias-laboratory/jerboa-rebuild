package fr.ensma.lias.jerboa.core.tracking;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DynOrTrackOrbitInst {
	
	public static final int ENCODED_CREATED = 1<<0; // 1
	public static final int ENCODED_DELETED = 1<<1; // 2
	public static final int ENCODED_SPLIT = 1<<2;   // 4
	public static final int ENCODED_MERGE = 1<<3;   // 8
	public static final int ENCODED_MODIFIED = 1<<4;// 16
	
	
	
	public int orbitID; // referee dartID
	
	public int state;
	
	public DynOrTrackOrbitInst(int orbitID, int state) {
		this.orbitID = orbitID;
		this.state = state;
	}
	
	@Override
	public String toString() {
		ArrayList<String> sstate = new ArrayList<>();
		StringBuilder sb = new StringBuilder("OrbitID: ");
		sb.append(orbitID).append(" -> ");
		if((state & ENCODED_CREATED) > 0)
			sstate.add("CREATED");
		if((state & ENCODED_DELETED) > 0)
			sstate.add("DELETED");
		if((state & ENCODED_SPLIT) > 0)
			sstate.add("SPLIT");
		if((state & ENCODED_MERGE) > 0)
			sstate.add("MERGE");
		if((state & ENCODED_MODIFIED) > 0)
			sstate.add("MODIFIED");
		sb.append(sstate.stream().collect(Collectors.joining(" & ")));
		return sb.toString();
	}
	
}
