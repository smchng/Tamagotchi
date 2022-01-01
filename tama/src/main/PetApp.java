package main;
import javax.swing.JFrame;

//App to make the game window

public class PetApp extends JFrame{
	public PetApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,800);
		PetPanel panel = new PetPanel(this);
		this.add(panel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		PetApp app = new PetApp("Tamagotchi");
	}
}
