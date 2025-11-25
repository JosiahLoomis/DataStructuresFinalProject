package data;

import java.time.LocalDate;

/**
 * Represents a song with data including title, artist, platform, and dates.
 * Implements Comparable to support priority-based ordering in queue structures.
 * 
 * @author Josiah Loomis
 * @version 1.0
 */
public class Song implements Comparable<Song> {

	private String title;
	private String artist;
	private String platform;
	private String songLink;
	private LocalDate releaseDate;
	private LocalDate dateAdded;
	
	private Boolean priorityQueued;
	private long queueSequence; 
	
	/**
	 * Default constructor that creates an empty Song object with null/empty values.
	 */
 	public Song() {
		super();
		this.title = "";
		this.artist = "";
		this.platform = "";
		this.songLink = "";
		this.releaseDate = null;
		this.dateAdded = null;
	}
	
	/**
	 * Constructs a Song with specified attributes.
	 * 
	 * @param title the title of the song
	 * @param artist the artist who performed the song
	 * @param platform the platform where the song is hosted (e.g., Spotify, YouTube)
	 * @param songLink the URL link to the song
	 * @param releaseDate the date the song was released
	 * @param dateAdded the date the song was added to the collection
	 */
	public Song(String title, String artist, String platform, String songLink, LocalDate releaseDate, LocalDate dateAdded) {
		super();
		this.title = title;
		this.artist = artist;
		this.platform = platform;
		this.songLink = songLink;
		this.releaseDate = releaseDate;
		this.dateAdded = dateAdded;
	}
	
	/**
	 * Constructs a Song from a save string.
	 * Format: title|artist|platform|songLink|releaseDate|dateAdded[|priorityQueued]
	 * 
	 * @param saveString the string containing song data
	 */
	public Song(String saveString) {
		try {
	        String[] parts = saveString.split("\\|");
	        if (parts.length >= 6) {
	            this.title = parts[0];
	    		this.artist = parts[1];
	    		this.platform =  parts[2];
	    		this.songLink = parts[3];
	    		this.releaseDate = LocalDate.parse(parts[4]);
	    		this.dateAdded = LocalDate.parse(parts[5]);
	    		
	    		if (parts.length >= 7) {
	    			this.priorityQueued = Boolean.parseBoolean(parts[6]);
	    		}
	    		if (parts.length >= 8) {
					this.queueSequence = Long.parseLong(parts[7]);
				}
	        }
	    } catch (Exception e) {
	        System.err.println("Error parsing song from file: " + e.getMessage());
	    }
	}

	/**
	 * Gets the title of the song.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the song.
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the artist of the song.
	 * 
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * Sets the artist of the song.
	 * 
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * Gets the platform where the song is hosted.
	 * 
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * Sets the platform where the song is hosted.
	 * 
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * Gets the URL link to the song.
	 * 
	 * @return the songLink
	 */
	public String getSongLink() {
		return songLink;
	}

	/**
	 * Sets the URL link to the song.
	 * 
	 * @param songLink the songLink to set
	 */
	public void setSongLink(String songLink) {
		this.songLink = songLink;
	}

	/**
	 * Gets the release date of the song.
	 * 
	 * @return the releaseDate
	 */
	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	/**
	 * Sets the release date of the song.
	 * 
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * Gets the date the song was added to the collection.
	 * 
	 * @return the dateAdded
	 */
	public LocalDate getDateAdded() {
		return dateAdded;
	}

	/**
	 * Sets the date the song was added to the collection.
	 * 
	 * @param dateAdded the dateAdded to set
	 */
	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}
	
	/**
	 * Gets whether the song is priority queued.
	 * 
	 * @return the priorityQueued status
	 */
	public Boolean getPriorityQueued() {
		return priorityQueued;
	}
	
	/**
	 * Sets whether the song is priority queued.
	 * Only set if song is in the MusicQueue.
	 * 
	 * @param priorityQueued the priorityQueued status to set
	 */
	public void setPriorityQueued(Boolean priorityQueued) {
		this.priorityQueued = priorityQueued;
	}
	
	/**
	 * Gets the queue sequence number.
	 * 
	 * @return the queueSequence
	 */
	public long getQueueSequence() {
		return queueSequence;
	}
	
	/**
	 * Sets the queue sequence number.
	 * 
	 * @param queueSequence the sequence number to set
	 */
	public void setQueueSequence(long queueSequence) {
		this.queueSequence = queueSequence;
	}
	
	/**
	 * Creates a copy of this song.
	 * Used when adding to queue so multiple queue entries are independent.
	 * 
	 * @return a new Song object with the same data
	 */
	public Song clone() {
		Song copy = new Song(this.title, this.artist, this.platform, 
		                     this.songLink, this.releaseDate, this.dateAdded);
		copy.setPriorityQueued(this.priorityQueued);
		copy.setQueueSequence(this.queueSequence);
		return copy;
	}

	@Override
    public int compareTo(Song other) {
        if (this.priorityQueued && !other.priorityQueued) {
            return -1;
        }
        if (!this.priorityQueued && other.priorityQueued) {
            return 1;
        }
        return Long.compare(this.queueSequence, other.queueSequence);
    }
	
	/**
	 * Converts the song to a string for file storage.
	 * Format: title|artist|platform|songLink|releaseDate|dateAdded
	 * 
	 * @return the pipe-delimited string representation
	 */
	public String getAsSaveString() {
		return title + "|" + artist + "|" + platform + "|" + songLink + "|" + releaseDate + "|"+ dateAdded;
	}
	
	/**
	 * Converts the song to a string including priority queue status.
	 * Format: title|artist|platform|songLink|releaseDate|dateAdded|priorityQueued
	 * 
	 * @return the pipe-delimited string representation with priority status
	 */
	public String getAsSaveStringForPriorityQueue() {
		return title + "|" + artist + "|" + platform + "|" + songLink + "|" + releaseDate + "|"+ dateAdded + "|" + priorityQueued;
	}
}
