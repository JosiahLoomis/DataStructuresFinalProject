package driver;

import java.time.LocalDate;

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
			new Song("The Less I Know The Better", "Tame Imapala", "Spotify", "https://open.spotify.com/track/6K4t31amVTZDgR3sKmwUJJ?si=7d6d46f627ad48ed", LocalDate.of(2015, 7, 17), LocalDate.of(2024, 2, 1)),
			new Song("Optimistic", "Radiohead", "Spotify", "https://open.spotify.com/track/1FoUsSi9BCTlNt2Vd7V8XA?si=447a6449a4234d95", LocalDate.of(2000, 10, 2), LocalDate.of(2025, 8, 5)),
			new Song("The Lost Art Of Keeping A Secre", "Queens of the Stone Age", "Spotify", "https://open.spotify.com/track/1RwDsaft1szD0FO3DVEtaT?si=f1bf3e15f6dc423f", LocalDate.of(2000, 7, 6), LocalDate.of(2025, 2, 8)),
			new Song("Echoes", "Spiral Drive", "Spotify", "https://open.spotify.com/track/3IRlMUig9uiFvcY48GYFEW?si=8ce83b5fa79244dc", LocalDate.of(2022, 3, 11), LocalDate.of(2025, 8, 30)),
			new Song("Bleed American", "Jimmy Eat World", "Spotify", "https://open.spotify.com/track/61XspFITuKmAlYdQacNCbF?si=f94af9df7cf4457f", LocalDate.of(2001, 7, 17), LocalDate.of(2025, 4, 15)),
			new Song("Robot Stop", "King Gizzard And The Lizard Wizard", "YouTube", "https://www.youtube.com/watch?v=X3Q4a5Eaqcg&t=5s", LocalDate.of(2016, 6, 29), LocalDate.of(2025, 5, 31)),
			new Song("Need 2", "Pinegrove", "YouTube", "https://www.youtube.com/watch?v=7T4OEZcnOJE", LocalDate.of(2017, 4, 18), LocalDate.of(2023, 11, 7)),
			new Song("King Of The Clouds", "Panic! At The Disco", "YouTube", "https://www.youtube.com/watch?v=6i7hIUegbQs", LocalDate.of(2018, 6, 18), LocalDate.of(2023, 8, 17)),
			new Song("Stockholm Syndrome", "Muse", "YouTube", "https://www.youtube.com/watch?v=gXN9acC9edU", LocalDate.of(2010, 2, 8), LocalDate.of(2023, 4, 2)),
			new Song("The Concept of Love", "Hideki Naganuma", "YouTube", "https://www.youtube.com/watch?v=HNy_retSME0", LocalDate.of(2002, 2, 22), LocalDate.of(2025, 11, 12))
			};
		
		Song[] startingQueue = {};
		
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
