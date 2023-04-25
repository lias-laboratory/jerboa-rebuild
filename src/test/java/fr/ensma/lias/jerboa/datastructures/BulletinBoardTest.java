package fr.ensma.lias.jerboa.datastructures;

import org.junit.Test;

/**
 * BulletinBoardTest
 */
public class BulletinBoardTest {

	BulletinBoard upBoard;
	BulletinBoard downBoard;

	public BulletinBoardTest() {
		upBoard = new BulletinBoardUp();
		downBoard = new BulletinBoardDown();
	}

}
