package fr.ensma.lias.jerboa.datastructures;

/**
 * BulletinBoardUpFactory
 */
public class BulletinBoardUpFactory extends BulletinBoardFactory {

	@Override
	protected BulletinBoard createBulletinBoard() {
		return new BulletinBoardUp();
	}

}
