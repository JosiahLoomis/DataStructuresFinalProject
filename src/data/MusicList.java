package data;

import java.util.ArrayList;
import java.util.LinkedList;

public class MusicList {

	private LinkedList<Song> songList = new LinkedList<>();
	
	public void addSong(Song song) {
		songList.add(song);
	}
	
	public void addFirst(Song song) {
		songList.addFirst(song);
	}
	
	public void addAt(int index, Song song) {
		songList.add(index, song);
	}
	
	public boolean removeSong(Song song) {
		return songList.remove(song);
	}
	
	public Song removeAt(int index) {
		return songList.remove(index);
	}
	
	public Song getSong(int index) {
		return songList.get(index);
	}
	
	public ArrayList<Song> getSongs() {
		return new ArrayList<>(songList);
	}
	
	public ArrayList<Song> getSongsAlphabetically() {
		ArrayList<Song> sortedList = new ArrayList<>(songList);
		SelectionSort.selectionSort(sortedList);
	    return sortedList;
	}
	
	public ArrayList<Song> getSongsByDateAdded() {
		ArrayList<Song> sortedList = new ArrayList<>(songList);
	    sortedList.sort((s1, s2) -> s1.getDateAdded().compareTo(s2.getDateAdded()));
	    return sortedList;
	}
	
	public ArrayList<Song> getSongsByDateCreated() {
	    ArrayList<Song> sortedList = new ArrayList<>(songList);
	    sortedList.sort((s1, s2) -> s1.getReleaseDate().compareTo(s2.getReleaseDate()));
	    return sortedList;
	}
	
	public int size() {
		return songList.size();
	}
	
	public boolean isEmpty() {
		return songList.isEmpty();
	}
	
	public void clear() {
		songList.clear();
	}
	
	public boolean contains(Song song) {
		return songList.contains(song);
	}
	
	public void updateList(ArrayList<Song> songs) {
		songList.clear();
		songList.addAll(songs);
	}
	
	public void display() {
		for (Song song : songList) {
			System.out.println(song);
		}
	}
	
	@Override
	public String toString() {
		return songList.toString();
	}
}
