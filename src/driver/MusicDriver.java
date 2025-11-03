package driver;

import java.util.Date;

import javax.swing.SwingUtilities;

import data.MusicList;
import data.MusicQueue;
import data.Song;
import gui.homeGui;

public class MusicDriver {
	
	public MusicList musicList = new MusicList();
	
	public MusicQueue musicQueue = new MusicQueue();
	
	public static void main(String[] args) {
		MusicDriver driver = new MusicDriver();
        
		Song[] startingSongs = {
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", new Date(17 ,7 ,2015), new Date(3 ,11 ,2025), true),
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", new Date(17 ,7 ,2015), new Date(3 ,11 ,2025), true),
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", new Date(17 ,7 ,2015), new Date(3 ,11 ,2025), true)
		};
		
		Song[] startingQueue = {
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", new Date(17 ,7 ,2015), new Date(3 ,11 ,2025), true),
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", new Date(17 ,7 ,2015), new Date(3 ,11 ,2025), true)		
		};
		
		for (Song song : startingSongs) {
			driver.musicList.addSong(song);
		}
		
		for (Song song : startingQueue) {
			driver.musicQueue.enqueue(song, false);
		}
		
		
        SwingUtilities.invokeLater(() -> {
            homeGui gui = new homeGui();
            gui.createAndShow(driver);
        });
    }
	
}
