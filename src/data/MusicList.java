package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Manages a collection of songs using a LinkedList structure.
 * Provides functionality for adding, removing, sorting, saving, and loading songs.
 * 
 * @author Josiah Loomis
 * @version 1.0
 */
public class MusicList {

	private LinkedList<Song> songList = new LinkedList<>();
	private static final String SAVE_FILE = "musiclist_data.txt";
	
	/**
	 * Adds a song to the end of the list.
	 * 
	 * @param song the song to add
	 */
	public void addSong(Song song) {
		songList.add(song);
	}
	
	/**
	 * Adds a song to the beginning of the list.
	 * 
	 * @param song the song to add at the front
	 */
	public void addFirst(Song song) {
		songList.addFirst(song);
	}
	
	/**
	 * Adds a song at the specified index position.
	 * 
	 * @param index the position where the song should be inserted
	 * @param song the song to add
	 */
	public void addAt(int index, Song song) {
		songList.add(index, song);
	}
	
	/**
	 * Removes the specified song from the list.
	 * 
	 * @param song the song to remove
	 * @return true if the song was found and removed, false otherwise
	 */
	public boolean removeSong(Song song) {
		return songList.remove(song);
	}
	
	/**
	 * Removes and returns the song at the specified index.
	 * 
	 * @param index the position of the song to remove
	 * @return the removed song
	 */
	public Song removeAt(int index) {
		return songList.remove(index);
	}
	
	/**
	 * Gets the song at the specified index without removing it.
	 * 
	 * @param index the position of the song to retrieve
	 * @return the song at the specified index
	 */
	public Song getSong(int index) {
		return songList.get(index);
	}
	
	/**
	 * Gets all songs as an ArrayList in their current order.
	 * 
	 * @return an ArrayList containing all songs
	 */
	public ArrayList<Song> getSongs() {
		return new ArrayList<>(songList);
	}
	
	/**
	 * Gets all songs sorted alphabetically by title using selection sort.
	 * 
	 * @return an ArrayList of songs sorted alphabetically
	 */
	public ArrayList<Song> getSongsAlphabetically() {
		ArrayList<Song> sortedList = new ArrayList<>(songList);
		SelectionSort.selectionSort(sortedList);
	    return sortedList;
	}
	
	/**
	 * Gets all songs sorted by the date they were added to the collection.
	 * 
	 * @return an ArrayList of songs sorted by date added (earliest first)
	 */
	public ArrayList<Song> getSongsByDateAdded() {
		ArrayList<Song> sortedList = new ArrayList<>(songList);
	    sortedList.sort((s1, s2) -> s1.getDateAdded().compareTo(s2.getDateAdded()));
	    return sortedList;
	}
	
	/**
	 * Gets all songs sorted by their release date.
	 * 
	 * @return an ArrayList of songs sorted by release date (earliest first)
	 */
	public ArrayList<Song> getSongsByDateCreated() {
	    ArrayList<Song> sortedList = new ArrayList<>(songList);
	    sortedList.sort((s1, s2) -> s1.getReleaseDate().compareTo(s2.getReleaseDate()));
	    return sortedList;
	}
	
	/**
	 * Gets the number of songs in the list.
	 * 
	 * @return the size of the music list
	 */
	public int size() {
		return songList.size();
	}
	
	/**
	 * Checks if the music list is empty.
	 * 
	 * @return true if the list contains no songs, false otherwise
	 */
	public boolean isEmpty() {
		return songList.isEmpty();
	}
	
	/**
	 * Removes all songs from the list.
	 */
	public void clear() {
		songList.clear();
	}
	
	/**
	 * Checks if the list contains the specified song.
	 * 
	 * @param song the song to search for
	 * @return true if the song is in the list, false otherwise
	 */
	public boolean contains(Song song) {
		return songList.contains(song);
	}
	
	/**
	 * Replaces the current list with the provided collection of songs.
	 * 
	 * @param songs the new collection of songs to use
	 */
	public void updateList(ArrayList<Song> songs) {
		songList.clear();
		songList.addAll(songs);
	}
	
	/**
	 * Prints all songs in the list to the console.
	 */
	public void display() {
		for (Song song : songList) {
			System.out.println(song);
		}
	}
	
	@Override
	public String toString() {
		return songList.toString();
	}
	
	/**
	 * Saves the music list to a text file.
	 * Each song is saved as a line.
	 * The file is saved to musiclist_data.txt in the current directory.
	 */
	public void saveToFile() {
		try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
			for (Song song : songList) {
				// Save each song's data separated by a delimiter
				// Format: title|artist|album|releaseDate|dateAdded|priorityQueued
				writer.println(song.getAsSaveString()); // You'll need to add this method to Song class
			}
		} catch (IOException e) {
			System.err.println("Error saving music list: " + e.getMessage());
		}
	}
	
	/**
	 * Loads the music list from a text file.
	 * Reads from musiclist_data.txt and parses each line as a song.
	 * If the file doesn't exist, the list remains empty.
	 */
	public void loadFromFile() {
		File file = new File(SAVE_FILE);
		if (!file.exists()) {
			return; // No saved data, start with empty list
		}
		
		try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
			String line;
			while ((line = reader.readLine()) != null) {
				Song song = new Song(line);
				if (song != null) {
					songList.add(song);
				}
			}
		} catch (IOException e) {
			System.err.println("Error loading music list: " + e.getMessage());
		}
	}
	
	/**
	 * Searches for songs matching the query in title, artist, or platform.
	 * Case-insensitive search.
	 * 
	 * @param query the search term
	 * @return an ArrayList of songs matching the query
	 */
	public ArrayList<Song> searchSongs(String query) {
	    ArrayList<Song> results = new ArrayList<>();
	    String lowerQuery = query.toLowerCase();
	    
	    for (Song song : songList) {
	        if (song.getTitle().toLowerCase().contains(lowerQuery) ||
	            song.getArtist().toLowerCase().contains(lowerQuery) ||
	            song.getPlatform().toLowerCase().contains(lowerQuery)) {
	            results.add(song);
	        }
	    }
	    
	    return results;
	}
}
