package fr.ensma.lias.jerboa.datastructures;

/**
 * BulletinBoardDownFactory
 */
public class BulletinBoardDownFactory extends BulletinBoardFactory {

	@Override
	protected BulletinBoard createBulletinBoard() {
		return new BulletinBoardDown();
	}

}
