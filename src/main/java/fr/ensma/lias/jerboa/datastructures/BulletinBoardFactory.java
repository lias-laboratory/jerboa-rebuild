package fr.ensma.lias.jerboa.datastructures;

public abstract class BulletinBoardFactory {

	public BulletinBoard create() {
		BulletinBoard bulletinBoard = createBulletinBoard();
		bulletinBoard.query();
		return bulletinBoard;
	}

	protected abstract BulletinBoard createBulletinBoard();

}
