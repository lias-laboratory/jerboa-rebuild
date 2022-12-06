package fr.ensma.lias.jerboa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;

public class JerboaLongTaskWait implements ActionListener {
	
	private boolean res;
	private Semaphore semaphore;
	
	private JDialog dialog;
	private JButton buttonOK;
	private JButton buttonSTOP;
	
	public JerboaLongTaskWait() {
		semaphore = new Semaphore(0);
		dialog = new JDialog();
		buttonOK = new JButton("OK");
		buttonSTOP = new JButton("STOP");
		
		buttonOK.addActionListener(this);
		buttonSTOP.addActionListener(this);
		Box horiz = Box.createHorizontalBox();
		
		horiz.add(buttonOK);
		horiz.add(buttonSTOP);
		
		dialog.getContentPane().add(horiz);
		dialog.pack();
		dialog.setAlwaysOnTop(true);
		dialog.setModal(false);
	}
	
	/**
	 * Cette fonction est la fonction qui faut appeler. Son role est d'ouvrir
	 * la boite de dialog et de faire une attente via le verrou connu.
	 * 
	 * @return
	 */
	public boolean waitUI() {
		
		
		
		// Je ne sais pas si c'est utile comme on lance un nouveau truc
		// bref a teste si c'est suffisant???
		/*SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {*/
				dialog.setVisible(true);
		//	}
		//});
				
		semaphore.acquireUninterruptibly();
		return res;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// je mets a jour mon etat pour savoir quoi faire au retour
		// je ferme ma boite de dialog
		// et je libère un slot de mon verrou
		if(e.getSource() == buttonOK)
			res = true;
		else // buttonSTOP
			res = false;
		
		dialog.setVisible(false);
		semaphore.release();
	}

}
