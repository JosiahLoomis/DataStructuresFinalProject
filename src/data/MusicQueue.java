package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Manages a priority queue of songs where songs can be marked as high priority.
 * Priority songs are automatically ordered before non-priority songs.
 * Uses Java's PriorityQueue with Song's natural ordering (Comparable).
 * 
 * @author JosiahLoomis
 * @version 1.0
 */
public class MusicQueue {

	 private PriorityQueue<Song> pq = new PriorityQueue<>();
	 private static final String SAVE_FILE = "musicqueue_data.txt";
	 private long sequenceCounter = 0;
	
	 /**
	  * Adds a song to the priority queue with the specified priority status.
	  * Priority songs will be ordered before non-priority songs when dequeued.
	  * 
	  * @param song the song to add to the queue
	  * @param priorityQueue true if the song should have priority, false otherwise
	  */
	 public void enqueue(Song song, Boolean priorityQueue) {
		 Song queuedSong = song.clone();
		 queuedSong.setPriorityQueued(priorityQueue);
		 queuedSong.setQueueSequence(sequenceCounter++);
		 pq.offer(queuedSong);
	 }
	    
	 /**
	  * Removes and returns the highest priority song from the queue.
	  * Priority songs are returned before non-priority songs.
	  * 
	  * @return the highest priority song, or null if the queue is empty
	  */
	 public Song dequeue() {
	     Song song = pq.poll();
	     
	     if (pq.isEmpty()) {
	 		sequenceCounter = 0;
	 	 }
	    
	     return song;
	 }
	    
	 /**
	  * Returns the highest priority song without removing it from the queue.
	  * 
	  * @return the highest priority song, or null if the queue is empty
	  */
	 public Song peek() {
	     return pq.peek();
	 }
	   
	 /**
	  * Checks if the queue is empty.
	  * 
	  * @return true if the queue contains no songs, false otherwise
	  */
	 public boolean isEmpty() {
	     return pq.isEmpty();
	 }
	    
	 /**
	  * Gets the number of songs in the queue.
	  * 
	  * @return the size of the music queue
	  */
	 public int size() {
		 return pq.size();
	 }
	 
	 public ArrayList<Song> getAllSongs() {
		 ArrayList<Song> songs = new ArrayList<>();
			
			// Create a NEW PriorityQueue with the same elements
			// This forces the heap to re-heapify properly
			PriorityQueue<Song> tempQueue = new PriorityQueue<>(pq);
			
			// Poll from the copy to get songs in correct priority order
			while (!tempQueue.isEmpty()) {
				songs.add(tempQueue.poll());
			}
			
			return songs;
	 }
	 
	 /**
	  * Saves the music queue to a text file.
	  * Each song is saved as a pipe-delimited line including priority status.
	  * The file is saved to musicqueue_data.txt in the current directory.
	  */
	 public void saveToFile() {
		 try (PrintWriter writer = new PrintWriter(new FileWriter(SAVE_FILE))) {
			 
			 writer.println("SEQUENCE:" + sequenceCounter);
			 
			 for (Song song : pq) {
				 // Save each song's data
				 writer.println(song.getAsSaveStringForPriorityQueue());
			 }
		 } catch (IOException e) {
			 System.err.println("Error saving music queue: " + e.getMessage());
		 }
	 }
	 
	 /**
	  * Loads the priority queue from a text file.
	  * Reads from musicqueue_data.txt and parses each line as a song.
	  * Songs are automatically ordered by priority when added to the queue.
	  * If the file doesn't exist, the queue remains empty.
	  */
	 public void loadFromFile() {
		 File file = new File(SAVE_FILE);
		 if (!file.exists()) {
			 return; // No saved data, start with empty queue
		 }
		 
		 try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
			 String line;
			 
			 line = reader.readLine();
				if (line != null && line.startsWith("SEQUENCE:")) {
					sequenceCounter = Long.parseLong(line.substring(9));
				}
			 
			 while ((line = reader.readLine()) != null) {
				 Song song = new Song(line);
				 if (song != null) {
					 pq.offer(song);
				 }
			 }
		 } catch (IOException e) {
			 System.err.println("Error loading music queue: " + e.getMessage());
		 }
	 }
}
