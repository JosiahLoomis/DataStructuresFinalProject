package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Date;
import data.*;

public class MusicManagerTest {

	private Song song1;
    private Song song2;
    private Song song3;
    private Song song4;
    private Date date1;
    private Date date2;
    private Date date3;
    private Date date4;
    
    @BeforeEach
    public void setUp() {
        // Create dates for testing
        date1 = new Date(2023, 1, 15); // Earlier date
        date2 = new Date(2023, 6, 20);
        date3 = new Date(2024, 3, 10);
        date4 = new Date(2024, 8, 5);  // Latest date
        
        // Create test songs
        song1 = new Song("Bohemian Rhapsody", "Queen", "Spotify", 
                        "spotify.com/song1", date1, date2, true);
        song2 = new Song("Stairway to Heaven", "Led Zeppelin", "Apple Music",
                        "apple.com/song2", date2, date3, false);
        song3 = new Song("Hotel California", "Eagles", "YouTube",
                        "youtube.com/song3", date3, date1, true);
        song4 = new Song("Another Song", "Artist", "Spotify",
                        "spotify.com/song4", date4, date4, false);
    }
    
    // ==================== SONG CLASS TESTS ====================
    
    @Test
    public void testSongDefaultConstructor() {
        Song song = new Song();
        assertNotNull(song.getTitle());
        assertEquals("", song.getTitle());
        assertFalse(song.getLiked());
        assertNotNull(song.getDateAdded());
    }
    
    @Test
    public void testSongParameterizedConstructor() {
        assertEquals("Bohemian Rhapsody", song1.getTitle());
        assertEquals("Queen", song1.getArtist());
        assertEquals("Spotify", song1.getPlatform());
        assertEquals("spotify.com/song1", song1.getSongLink());
        assertTrue(song1.getLiked());
    }
    
    @Test
    public void testSongGettersAndSetters() {
        Song song = new Song();
        song.setTitle("Test Title");
        song.setArtist("Test Artist");
        song.setPlatform("Test Platform");
        song.setSongLink("test.com");
        song.setLiked(true);
        
        assertEquals("Test Title", song.getTitle());
        assertEquals("Test Artist", song.getArtist());
        assertEquals("Test Platform", song.getPlatform());
        assertEquals("test.com", song.getSongLink());
        assertTrue(song.getLiked());
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
        song2.setPriorityQueued(false);
        
        // song1 should have higher priority (return negative)
        assertTrue(song1.compareTo(song2) < 0);
        
        // song2 should have lower priority (return positive)
        assertTrue(song2.compareTo(song1) > 0);
        
        // Same priority should return 0
        song3.setPriorityQueued(true);
        assertEquals(0, song1.compareTo(song3));
    }
    
    // ==================== MUSIC QUEUE TESTS ====================
    
    @Test
    public void testMusicQueueEnqueueDequeue() {
        MusicQueue queue = new MusicQueue();
        
        queue.enqueue(song1, false);
        assertEquals(1, queue.size());
        
        Song dequeued = queue.dequeue();
        assertEquals(song1, dequeued);
        assertEquals(0, queue.size());
    }
    
    @Test
    public void testMusicQueuePriorityOrder() {
        MusicQueue queue = new MusicQueue();
        
        // Add non-priority song first
        queue.enqueue(song2, false);
        // Add priority song second
        queue.enqueue(song1, true);
        // Add another non-priority
        queue.enqueue(song3, false);
        
        // Priority song should come out first
        Song first = queue.dequeue();
        assertTrue(first.getPriorityQueued());
    }
    
    @Test
    public void testMusicQueuePeek() {
        MusicQueue queue = new MusicQueue();
        queue.enqueue(song1, true);
        queue.enqueue(song2, false);
        
        Song peeked = queue.peek();
        assertEquals(song1, peeked);
        // Size should still be 2 (peek doesn't remove)
        assertEquals(2, queue.size());
    }
    
    @Test
    public void testMusicQueueIsEmpty() {
        MusicQueue queue = new MusicQueue();
        assertTrue(queue.isEmpty());
        
        queue.enqueue(song1, false);
        assertFalse(queue.isEmpty());
        
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void testMusicQueueSize() {
        MusicQueue queue = new MusicQueue();
        assertEquals(0, queue.size());
        
        queue.enqueue(song1, false);
        queue.enqueue(song2, false);
        queue.enqueue(song3, true);
        assertEquals(3, queue.size());
        
        queue.dequeue();
        assertEquals(2, queue.size());
    }
    
    @Test
    public void testMusicQueueDequeueEmpty() {
        MusicQueue queue = new MusicQueue();
        Song result = queue.dequeue();
        assertNull(result);
    }
    
    // ==================== MUSIC LIST TESTS ====================
    
    @Test
    public void testMusicListAddSong() {
        MusicList list = new MusicList();
        list.addSong(song1);
        
        assertEquals(1, list.size());
        assertEquals(song1, list.getSong(0));
    }
    
    @Test
    public void testMusicListAddFirst() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addFirst(song2);
        
        assertEquals(song2, list.getSong(0));
        assertEquals(song1, list.getSong(1));
    }
    
    @Test
    public void testMusicListAddAt() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song3);
        list.addAt(1, song2);
        
        assertEquals(song1, list.getSong(0));
        assertEquals(song2, list.getSong(1));
        assertEquals(song3, list.getSong(2));
    }
    
    @Test
    public void testMusicListRemoveSong() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        
        assertTrue(list.removeSong(song1));
        assertEquals(1, list.size());
        assertFalse(list.contains(song1));
        
        // Try to remove song that doesn't exist
        assertFalse(list.removeSong(song3));
    }
    
    @Test
    public void testMusicListRemoveAt() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        
        Song removed = list.removeAt(1);
        assertEquals(song2, removed);
        assertEquals(2, list.size());
    }
    
    @Test
    public void testMusicListGetSong() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        
        assertEquals(song1, list.getSong(0));
        assertEquals(song2, list.getSong(1));
    }
    
    @Test
    public void testMusicListGetSongs() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        
        ArrayList<Song> songs = list.getSongs();
        assertEquals(3, songs.size());
        assertTrue(songs.contains(song1));
        assertTrue(songs.contains(song2));
        assertTrue(songs.contains(song3));
    }
    
    @Test
    public void testMusicListSize() {
        MusicList list = new MusicList();
        assertEquals(0, list.size());
        
        list.addSong(song1);
        assertEquals(1, list.size());
        
        list.addSong(song2);
        assertEquals(2, list.size());
    }
    
    @Test
    public void testMusicListIsEmpty() {
        MusicList list = new MusicList();
        assertTrue(list.isEmpty());
        
        list.addSong(song1);
        assertFalse(list.isEmpty());
    }
    
    @Test
    public void testMusicListClear() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        list.addSong(song3);
        
        list.clear();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testMusicListContains() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        
        assertTrue(list.contains(song1));
        assertTrue(list.contains(song2));
        assertFalse(list.contains(song3));
    }
    
    @Test
    public void testMusicListUpdateList() {
        MusicList list = new MusicList();
        list.addSong(song1);
        
        ArrayList<Song> newSongs = new ArrayList<>();
        newSongs.add(song2);
        newSongs.add(song3);
        newSongs.add(song4);
        
        list.updateList(newSongs);
        
        assertEquals(3, list.size());
        assertFalse(list.contains(song1));
        assertTrue(list.contains(song2));
        assertTrue(list.contains(song3));
        assertTrue(list.contains(song4));
    }
    
    @Test
    public void testMusicListDisplay() {
        // This test just ensures display() doesn't throw an exception
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song2);
        
        // Should not throw exception
        list.display();
    }
    
    // ==================== EDGE CASE TESTS ====================
    
    @Test
    public void testEmptyListOperations() {
        MusicList list = new MusicList();
        
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
        assertFalse(list.contains(song1));
        list.clear(); // Should not throw exception
    }
    
    @Test
    public void testSingleItemList() {
        MusicList list = new MusicList();
        list.addSong(song1);
        
        assertEquals(1, list.size());
        assertTrue(list.removeSong(song1));
        assertTrue(list.isEmpty());
    }
    
    @Test
    public void testMultiplePriorityQueueItems() {
        MusicQueue queue = new MusicQueue();
        
        queue.enqueue(song1, true);
        queue.enqueue(song2, true);
        queue.enqueue(song3, false);
        queue.enqueue(song4, false);
        
        // First two should be priority
        Song first = queue.dequeue();
        Song second = queue.dequeue();
        
        assertTrue(first.getPriorityQueued());
        assertTrue(second.getPriorityQueued());
    }
    
    @Test
    public void testDuplicateSongsInList() {
        MusicList list = new MusicList();
        list.addSong(song1);
        list.addSong(song1); // Add same song twice
        
        assertEquals(2, list.size());
        assertTrue(list.removeSong(song1));
        assertEquals(1, list.size()); // Should only remove first occurrence
    }
}
