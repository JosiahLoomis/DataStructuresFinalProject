package data;

import java.util.Date;

public class Song implements Comparable<Song> {

	private String title;
	private String artist;
	private String platform;
	private String songLink;
	private Date releaseDate;
	private Date dateAdded;
	private Boolean liked;
	
	private Boolean priorityQueued;
	
 	public Song() {
		super();
		this.title = "";
		this.artist = "";
		this.platform = "";
		this.songLink = "";
		this.releaseDate = new Date();
		this.dateAdded = new Date();
		this.liked = false;
	}
	
	public Song(String title, String artist, String platform, String songLink, Date releaseDate, Date dateAdded, Boolean liked) {
		super();
		this.title = title;
		this.artist = artist;
		this.platform = platform;
		this.songLink = songLink;
		this.releaseDate = releaseDate;
		this.dateAdded = dateAdded;
		this.liked = liked;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @param artist the artist to set
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @return the platform
	 */
	public String getPlatform() {
		return platform;
	}

	/**
	 * @param platform the platform to set
	 */
	public void setPlatform(String platform) {
		this.platform = platform;
	}

	/**
	 * @return the songLink
	 */
	public String getSongLink() {
		return songLink;
	}

	/**
	 * @param songLink the songLink to set
	 */
	public void setSongLink(String songLink) {
		this.songLink = songLink;
	}

	/**
	 * @return the releaseDate
	 */
	public Date getReleaseDate() {
		return releaseDate;
	}

	/**
	 * @param releaseDate the releaseDate to set
	 */
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	/**
	 * @return the dateAdded
	 */
	public Date getDateAdded() {
		return dateAdded;
	}

	/**
	 * @param dateAdded the dateAdded to set
	 */
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	/**
	 * @return the liked
	 */
	public Boolean getLiked() {
		return liked;
	}

	/**
	 * @param liked the liked to set
	 */
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

	
	/**
	 * @return the priorityQueued
	 */
	public Boolean getPriorityQueued() {
		return priorityQueued;
	}

	
	/**
	 * @param priorityQueued the priorityQueued to set
	 */
	public void setPriorityQueued(Boolean priorityQueued) {
		this.priorityQueued = priorityQueued;
	}

	@Override
    public int compareTo(Song other) {
        if (this.priorityQueued && !other.priorityQueued) {
            return -1;
        }
        if (!this.priorityQueued && other.priorityQueued) {
            return 1;
        }
        return 0;
    }
}
