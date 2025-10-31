package data;

import java.util.PriorityQueue;

public class MusicQueue {

	 private PriorityQueue<Song> pq = new PriorityQueue<>();
	
	 public void enqueue(Song song, Boolean priorityQueue) {
		 song.setPriorityQueued(priorityQueue);
		 pq.offer(song);
	 }
	    
	 public Song dequeue() {
	     return pq.poll();
	 }
	    
	 public Song peek() {
	     return pq.peek();
	 }
	    
	 public boolean isEmpty() {
	     return pq.isEmpty();
	 }
	    
	 public int size() {
		 return pq.size();
	 }
}
