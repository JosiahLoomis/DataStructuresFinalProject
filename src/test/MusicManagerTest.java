package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import data.*;

public class MusicManagerTest {

	// ==================== TEST SET UP ====================
	private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;

    private LocalDate date1;
    private LocalDate date2;
    private LocalDate date3;
    private LocalDate date4;

    @BeforeEach
    public void setUp() {
        // Create test LocalDates
        date1 = LocalDate.of(2023, 1, 15);
        date2 = LocalDate.of(2023, 6, 20);
        date3 = LocalDate.of(2024, 3, 10);
        date4 = LocalDate.of(2024, 8, 5);

        // Create test songs
        song1 = new Song("Bohemian Rhapsody", "Queen", "Spotify",
                "spotify.com/song1", date1, date2);
        song2 = new Song("Stairway to Heaven", "Led Zeppelin", "Apple Music",
                "apple.com/song2", date2, date3);
        song3 = new Song("Hotel California", "Eagles", "YouTube",
                "youtube.com/song3", date3, date1);
        song4 = new Song("Another Song", "Artist", "Spotify",
                "spotify.com/song4", date4, date4);
    }
    
    // ==================== SONG CLASS TESTS ====================
    @Test
    public void testSongDefaultConstructor() {
        Song song = new Song();
        assertEquals("", song.getTitle());
        assertEquals("", song.getArtist());
        assertEquals("", song.getPlatform());
        assertEquals("", song.getSongLink());
        assertNull(song.getReleaseDate());
        assertNull(song.getDateAdded());
    }

    @Test
    public void testSongParameterizedConstructor() {
        assertEquals("Bohemian Rhapsody", song1.getTitle());
        assertEquals("Queen", song1.getArtist());
        assertEquals("Spotify", song1.getPlatform());
        assertEquals("spotify.com/song1", song1.getSongLink());
        assertEquals(date1, song1.getReleaseDate());
        assertEquals(date2, song1.getDateAdded());
    }

    @Test
    public void testSongGettersAndSetters() {
        Song song = new Song();
        song.setTitle("Test Title");
        song.setArtist("Test Artist");
        song.setPlatform("Test Platform");
        song.setSongLink("test.com");
        song.setReleaseDate(date1);
        song.setDateAdded(date2);

        assertEquals("Test Title", song.getTitle());
        assertEquals("Test Artist", song.getArtist());
        assertEquals("Test Platform", song.getPlatform());
        assertEquals("test.com", song.getSongLink());
        assertEquals(date1, song.getReleaseDate());
        assertEquals(date2, song.getDateAdded());
    }

    @Test
    public void testSongPriorityQueued() {
        song1.setPriorityQueued(true);
        assertTrue(song1.getPriorityQueued());

        song1.setPriorityQueued(false);
        assertFalse(song1.getPriorityQueued());
    }

    @Test
    public void testSongCompareTo() {
    	song1.setPriorityQueued(true);
        song1.setQueueSequence(0);
        
        song2.setPriorityQueued(false);
        song2.setQueueSequence(1);

        assertTrue(song1.compareTo(song2) < 0);
        assertTrue(song2.compareTo(song1) > 0);

        song3.setPriorityQueued(true);
        song3.setQueueSequence(0);
        
        assertEquals(0, song1.compareTo(song3));
    }

    // ==================== MUSIC LIST TESTS ====================

    @Test
    public void testMusicListAddAndGetSong() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);

        assertEquals(2, list.size());
        assertEquals(song1, list.getSong(0));
        assertEquals(song2, list.getSong(1));
    }

    @Test
    public void testMusicListSortingAlphabetically() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        list.addSong(song4);

        ArrayList<Song> sorted = list.getSongsAlphabetically();

        assertEquals("Another Song", sorted.get(0).getTitle());
        assertEquals("Bohemian Rhapsody", sorted.get(1).getTitle());
        assertEquals("Hotel California", sorted.get(2).getTitle());
        assertEquals("Stairway to Heaven", sorted.get(3).getTitle());
    }

    @Test
    public void testMusicListSortingByDateAdded() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        list.addSong(song4);

        ArrayList<Song> sorted = list.getSongsByDateAdded();

        assertEquals(song3, sorted.get(0));
        assertEquals(song1, sorted.get(1));
        assertEquals(song2, sorted.get(2));
        assertEquals(song4, sorted.get(3));
    }

    @Test
    public void testMusicListSortingByDateCreated() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        list.addSong(song4);

        ArrayList<Song> sorted = list.getSongsByDateCreated();

        for (int i = 0; i < sorted.size() - 1; i++) {
            LocalDate d1 = sorted.get(i).getReleaseDate();
            LocalDate d2 = sorted.get(i + 1).getReleaseDate();
            assertTrue(!d1.isAfter(d2), "Songs should be sorted by creation date ascending");
        }
    }

    @Test
    public void testMusicListEmptySortsReturnEmpty() {
        MusicList list = new MusicList();
        assertTrue(list.getSongsAlphabetically().isEmpty());
        assertTrue(list.getSongsByDateAdded().isEmpty());
        assertTrue(list.getSongsByDateCreated().isEmpty());
    }
    
    @Test
	public void testMusicListSearchSongs() {
	    MusicList list = new MusicList();
	    list.addSong(song1);
	    list.addSong(song2);
	    list.addSong(song3);
	    list.addSong(song4);
	    
	    ArrayList<Song> results = list.searchSongs("Queen");
	    assertEquals(1, results.size());
	    assertEquals("Bohemian Rhapsody", results.get(0).getTitle());
	    
	    results = list.searchSongs("Spotify");
	    assertEquals(2, results.size());
	}
	
	@Test
	public void testMusicListRemoveSong() {
	    MusicList list = new MusicList();
	    list.addSong(song1);
	    list.addSong(song2);
	    
	    assertTrue(list.removeSong(song1));
	    assertEquals(1, list.size());
	    assertFalse(list.contains(song1));
	}
    
 // ==================== MUSIC QUEUE TESTS ====================
	@Test
	public void testMusicQueueEnqueueAndDequeue() {
	    MusicQueue queue = new MusicQueue();
	    queue.enqueue(song1, false);
	    queue.enqueue(song2, false);
	    
	    assertEquals(2, queue.size());
	    
	    Song first = queue.dequeue();
	    Song second = queue.dequeue();
	    
	    assertEquals("Bohemian Rhapsody", first.getTitle());
	    assertEquals("Stairway to Heaven", second.getTitle());
	    assertTrue(queue.isEmpty());
	}
	
	@Test
	public void testMusicQueuePriorityOrdering() {
	    MusicQueue queue = new MusicQueue();
	    
	    // Add normal songs
	    queue.enqueue(song1, false);
	    queue.enqueue(song2, false);
	    
	    queue.enqueue(song3, true);
	    
	    Song first = queue.dequeue();
	    Song second = queue.dequeue();
	    Song third = queue.dequeue();
	    
	    assertEquals("Hotel California", first.getTitle());
	    assertEquals("Bohemian Rhapsody", second.getTitle());
	    assertEquals("Stairway to Heaven", third.getTitle());
	}
	
	@Test
	public void testMusicQueueFIFOWithinPriority() {
	    MusicQueue queue = new MusicQueue();
	    
	    queue.enqueue(song1, true);
	    queue.enqueue(song2, true);
	    queue.enqueue(song3, true);
	    
	    Song first = queue.dequeue();
	    Song second = queue.dequeue();
	    Song third = queue.dequeue();
	    
	    assertEquals("Bohemian Rhapsody", first.getTitle());
	    assertEquals("Stairway to Heaven", second.getTitle());
	    assertEquals("Hotel California", third.getTitle());
	}
	
	@Test
	public void testMusicQueueGetAllSongs() {
	    MusicQueue queue = new MusicQueue();
	    queue.enqueue(song1, true);
	    queue.enqueue(song2, false);
	    queue.enqueue(song3, true);
	    
	    ArrayList<Song> allSongs = queue.getAllSongs();
	    
	    assertEquals(3, allSongs.size());
	    
	    assertTrue(allSongs.get(0).getPriorityQueued());
	    assertTrue(allSongs.get(1).getPriorityQueued());
	    assertFalse(allSongs.get(2).getPriorityQueued());
	}
	
	@Test
	public void testMusicQueueCloningSongs() {
	    MusicQueue queue = new MusicQueue();
	    
	    queue.enqueue(song1, true);
	    queue.enqueue(song1, false);
	    
	    Song first = queue.dequeue();
	    Song second = queue.dequeue();
	    
	    assertTrue(first.getPriorityQueued());
	    assertFalse(second.getPriorityQueued());
	}
}