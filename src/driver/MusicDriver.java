package driver;

import java.time.LocalDate;

import javax.swing.SwingUtilities;

import data.MusicList;
import data.MusicQueue;
import data.Song;
import gui.HomeGui;

/**
 * Starts the app by initializing the different data structure and the gui.
 * 
 * @author Josiah Loomis
 * @version 1.0
 */
public class MusicDriver {
	
	public MusicList musicList = new MusicList();
	public MusicQueue musicQueue = new MusicQueue();
	
	public static void main(String[] args) {
		//Creates the driver
		MusicDriver driver = new MusicDriver();
		
		//Load data from file
		driver.musicList.loadFromFile();
		driver.musicQueue.loadFromFile();
		
		//Loads the gui
        SwingUtilities.invokeLater(() -> {
            HomeGui gui = new HomeGui();
            gui.createAndShow(driver);
        });
        
        //Tells the app to save when we close the app
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down... Saving data...");
            // Your cleanup code here
            driver.musicList.saveToFile();
    		driver.musicQueue.saveToFile();
        }));
    }
	
}
