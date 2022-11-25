package fr.ensma.lias.jerboa.datastructures;

public class LevelHR extends Level {
	protected LevelEvent levelEventHR;
	protected LevelOrbit levelOrbitHR;

	public LevelHR() {
		levelEventHR = new LevelEventHR();
		levelOrbitHR = new LevelOrbitHR();
	}
}
