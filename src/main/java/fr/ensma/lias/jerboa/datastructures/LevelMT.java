package fr.ensma.lias.jerboa.datastructures;

public class LevelMT extends Level {
	protected LevelEvent levelEventMT;
	protected LevelOrbit levelOrbitMT;

	public LevelMT() {
		levelEventMT = new LevelEventMT();
		levelOrbitMT = new LevelOrbitMT();
	}

}
