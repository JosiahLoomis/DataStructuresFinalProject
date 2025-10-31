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
		return new ArrayList<>(songList);
	}
	
	public ArrayList<Song> getSongsByDateAdded() {
		return new ArrayList<>(songList);
	}
	
	public ArrayList<Song> getSongsByDateCreated() {
		return new ArrayList<>(songList);
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
