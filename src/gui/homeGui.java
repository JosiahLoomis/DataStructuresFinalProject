package gui;

import javax.swing.*;
import data.Song;
import driver.MusicDriver;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * GUI class for the music management application.
 * Provides an interface for adding, viewing, sorting, and queuing songs.
 * 
 * @author Josiah Loomis
 * @version 1.0
 */
public class HomeGui {

    /** Reference to the main driver for accessing music data structures */
    private MusicDriver driver;
    
    /** Panel that displays the list of songs */
    private JPanel songsPanel;
    
    /** Main application window */
    private JFrame frame;
    
    /** Input field for song title */
    private JTextField titleField;
    
    /** Input field for artist name */
    private JTextField artistField;
    
    /** Input field for platform name */
    private JTextField platformField;
    
    /** Input field for song link/URL */
    private JTextField linkField;
    
    /** Input field for release date */
    private JTextField releaseDateField;
    
    /** Currently playing song from the queue */
    private Song songPlaying = null;
    
    /** Label displaying current song information */
    private JLabel infoLabel;
    
    /** Input field for searching for songs */
    private JTextField searchField;
    
    /** Panel that displays the queue. */
    private JPanel queueDisplayPanel;
    
    /**
     * Creates and displays the main GUI window.
     * Initializes all panels, buttons, and event listeners.
     * 
     * @param driver the MusicDriver instance containing music data
     */
    public void createAndShow(MusicDriver driver) {
        this.driver = driver;
        
        // Initialize main frame
        frame = new JFrame("Music Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 850);
        frame.setLayout(new BorderLayout());
        
        // Create and add all panels
        JPanel topPanel = createTopPanel();
        frame.add(topPanel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = createSongsPanel();
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel queuePanel = createQueuePanel();
        frame.add(queuePanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
    
    /**
     * Creates the top panel containing song input fields and sort buttons.
     * 
     * @return the configured top panel
     */
    private JPanel createTopPanel() {
        // Add Song Panel
        JPanel addSongPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        addSongPanel.setBorder(BorderFactory.createTitledBorder("Add a New Song"));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Songs"));
        
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        JButton clearSearchButton = new JButton("Show All");
        
        searchButton.addActionListener(e -> searchSongs());
        clearSearchButton.addActionListener(e -> {
            searchField.setText("");
            refreshSongsList();
        });
        
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(clearSearchButton);
        
        // Initialize text fields
        titleField = new JTextField();
        artistField = new JTextField();
        platformField = new JTextField();
        linkField = new JTextField();
        releaseDateField = new JTextField();
        
        // Add labels
        addSongPanel.add(new JLabel("Title:"));
        addSongPanel.add(new JLabel("Artist:"));
        addSongPanel.add(new JLabel("Platform:"));
        addSongPanel.add(new JLabel("Song Link:"));
        addSongPanel.add(new JLabel("Release Date (yyyy-MM-dd):"));

        // Add text fields
        addSongPanel.add(titleField);
        addSongPanel.add(artistField);
        addSongPanel.add(platformField);
        addSongPanel.add(linkField);
        addSongPanel.add(releaseDateField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        
        JButton addSongBtn = new JButton("Add Song");
        JButton alphabeticallyBtn = new JButton("Sort Alphabetically");
        JButton dateReleasedBtn = new JButton("Sort by Release Date");
        JButton dateAddedBtn = new JButton("Sort by Date Added");
        
        buttonPanel.add(addSongBtn);
        buttonPanel.add(alphabeticallyBtn);
        buttonPanel.add(dateReleasedBtn);
        buttonPanel.add(dateAddedBtn);
        
        // Add action listeners
        addSongBtn.addActionListener(e -> addSong());
        alphabeticallyBtn.addActionListener(e -> sortSongsByAlphabet());
        dateReleasedBtn.addActionListener(e -> sortSongsByReleaseDate());
        dateAddedBtn.addActionListener(e -> sortSongsByDateAdded());
        
        // Combine panels
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(addSongPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        
        return topPanel;
    }
    
    /**
     * Creates the scrollable panel that displays all songs.
     * 
     * @return a scroll pane containing the songs panel
     */
    private JScrollPane createSongsPanel() {
        songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));
        
        // Initial load of songs
        refreshSongsList();

        return new JScrollPane(songsPanel);
    }
    
    /**
     * Creates the bottom panel showing currently playing song and queue controls.
     * 
     * @return the configured queue panel
     */
    private JPanel createQueuePanel() {
    	JPanel mainQueuePanel = new JPanel(new BorderLayout());
        mainQueuePanel.setBorder(BorderFactory.createTitledBorder("Music Queue"));
        
        // Top section - Now Playing
        JPanel nowPlayingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoLabel = new JLabel("");
        updateSongPlaying();
        
        JButton nextButton = new JButton("Next Song");
        nextButton.addActionListener(e -> updateSongPlaying());
        
        nowPlayingPanel.add(infoLabel);
        nowPlayingPanel.add(nextButton);
        
        // Bottom section - Queue List
        JPanel queueListPanel = new JPanel();
        queueListPanel.setLayout(new BoxLayout(queueListPanel, BoxLayout.Y_AXIS));
        JScrollPane queueScrollPane = new JScrollPane(queueListPanel);
        queueScrollPane.setPreferredSize(new Dimension(0, 150));
        
        queueDisplayPanel = queueListPanel;
        
        // Initial queue display
        displayQueue(queueListPanel);
        
        // Combine panels
        mainQueuePanel.add(nowPlayingPanel, BorderLayout.NORTH);
        mainQueuePanel.add(queueScrollPane, BorderLayout.CENTER);
        
        return mainQueuePanel;
    }
    
    /**
     * Sorts the music list alphabetically by title and refreshes the display.
     */
    private void sortSongsByAlphabet() {
        System.out.println("Sorting songs alphabetically");
        driver.musicList.updateList(driver.musicList.getSongsAlphabetically());
        refreshSongsList();
    }
    
    /**
     * Sorts the music list by date added and refreshes the display.
     */
    private void sortSongsByDateAdded() {
        System.out.println("Sorting songs by date added");
        driver.musicList.updateList(driver.musicList.getSongsByDateAdded());
        refreshSongsList();
    }
    
    /**
     * Sorts the music list by release date and refreshes the display.
     */
    private void sortSongsByReleaseDate() {
        System.out.println("Sorting songs by release date");
        driver.musicList.updateList(driver.musicList.getSongsByDateCreated());
        refreshSongsList();
    }
    
    /**
     * Updates the display to show the next song in the queue.
     * Dequeues the next song and displays its information.
     */
    private void updateSongPlaying() {
        if (!driver.musicQueue.isEmpty()) {
            songPlaying = driver.musicQueue.dequeue();
            infoLabel.setText("<html>🎵 Now Playing: " + songPlaying.getTitle() + 
                            " by " + songPlaying.getArtist() + 
                            "<br>Link: " + songPlaying.getSongLink() + "</html>");
            if (queueDisplayPanel != null) {
                displayQueue(queueDisplayPanel);
            }
        } else {
            infoLabel.setText("🎵 No songs in the queue.");
        }
    }
    
    /**
     * Handles adding a new song to the music list.
     * Validates input fields and creates a new Song object.
     * Displays error messages for invalid input.
     */
    private void addSong() {
        // Get and trim input values
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String platform = platformField.getText().trim();
        String link = linkField.getText().trim();
        String releaseDateStr = releaseDateField.getText().trim();
        
        // Validate all fields are filled
        if (title.isEmpty() || artist.isEmpty() || platform.isEmpty() || 
            link.isEmpty() || releaseDateStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "All fields are required!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Parse the release date with error handling
        LocalDate releaseDate;
        try {
            releaseDate = LocalDate.parse(releaseDateStr);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, 
                "Invalid date format! Please use yyyy-MM-dd format.\nExample: 2024-11-19", 
                "Date Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Clear all text fields
        clearInputFields();
        
        // Create new song and add to list
        Song newSong = new Song(title, artist, platform, link, releaseDate, LocalDate.now());
        driver.musicList.addSong(newSong);
        
        // Refresh the display to show the new song
        refreshSongsList();
        
        JOptionPane.showMessageDialog(frame, "Song added successfully!");
    }
    
    /**
     * Clears all input fields in the add song panel.
     */
    private void clearInputFields() {
        titleField.setText("");
        artistField.setText("");
        platformField.setText("");
        linkField.setText("");
        releaseDateField.setText("");
    }
    
    /**
     * Refreshes the songs display panel with current data from the music list.
     * Clears existing display and rebuilds with updated song information.
     */
    private void refreshSongsList() {
        songsPanel.removeAll();
        
        // Add all songs from the list
        for (Song song : driver.musicList.getSongs()) {
            songsPanel.add(createSongPanel(song));
        }
        
        songsPanel.revalidate();
        songsPanel.repaint();
    }
    
    /**
     * Creates a panel displaying a single song with its information and action buttons.
     * 
     * @param song the song to display
     * @return a configured panel representing the song
     */
    private JPanel createSongPanel(Song song) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        itemPanel.setBackground(Color.WHITE);

        // Left side - Song information
        JLabel titleLabel = new JLabel("<html><b>" + song.getTitle() + 
                                       " - " + song.getArtist() + "</b></html>");
        JLabel infoLabel = new JLabel("<html>Platform: " + song.getPlatform() + 
                                      "<br>Release Date: " + song.getReleaseDate() + 
                                      "<br>Date Added: " + song.getDateAdded() + "</html>");
        infoLabel.setVisible(false); // Hide by default, show on hover

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(infoLabel);

        // Right side - Action buttons
        JPanel btnPanel = createButtonPanel(song, itemPanel);

        itemPanel.add(textPanel, BorderLayout.CENTER);
        itemPanel.add(btnPanel, BorderLayout.EAST);

        // Add hover effects
        addHoverEffects(titleLabel, textPanel, infoLabel);

        return itemPanel;
    }
    
    /**
     * Creates the button panel for a song with Queue, Priority Queue, and Delete buttons.
     * 
     * @param song the song associated with these buttons
     * @param parentPanel the parent panel (for delete confirmation dialog)
     * @return a panel containing the action buttons
     */
    private JPanel createButtonPanel(Song song, JPanel parentPanel) {
        JButton queueBtn = new JButton("Queue");
        JButton priorityQueueBtn = new JButton("Priority Queue");
        JButton deleteBtn = new JButton("Delete");
        
        // Queue button - adds song to normal queue
        queueBtn.addActionListener(e -> {
            driver.musicQueue.enqueue(song, false);
            System.out.println("Queued: " + song.getTitle());
            displayQueue(queueDisplayPanel);
        });
        
        // Priority queue button - adds song with priority
        priorityQueueBtn.addActionListener(e -> {
            driver.musicQueue.enqueue(song, true);
            System.out.println("Priority queued: " + song.getTitle());
            displayQueue(queueDisplayPanel);
        });
        
        // Delete button - removes song from list
        deleteBtn.addActionListener(e -> deleteSong(song));
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(queueBtn);
        btnPanel.add(priorityQueueBtn);
        btnPanel.add(deleteBtn);
        
        return btnPanel;
    }
    
    /**
     * Handles song deletion with confirmation dialog.
     * 
     * @param song the song to delete
     */
    private void deleteSong(Song song) {
        int confirm = JOptionPane.showConfirmDialog(frame,
            "Are you sure you want to delete \"" + song.getTitle() + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            driver.musicList.removeSong(song);
            refreshSongsList();
            JOptionPane.showMessageDialog(frame, "Song deleted successfully!");
        }
    }
    
    /**
     * Adds mouse hover effects to show additional song information.
     * Only triggers when hovering over the song title/text area, not the buttons.
     * 
     * @param titleLabel the clickable title label
     * @param textPanel the panel containing song text
     * @param infoLabel the label containing detailed song information
     */
    private void addHoverEffects(JLabel titleLabel, JPanel textPanel, JLabel infoLabel) {
        Color hoverColor = new Color(230, 240, 255);
        
        // Create a shared mouse listener for both components
        java.awt.event.MouseAdapter hoverListener = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoLabel.setVisible(true);
                textPanel.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoLabel.setVisible(false);
                textPanel.setBackground(Color.WHITE);
            }
        };
        titleLabel.addMouseListener(hoverListener);
        textPanel.addMouseListener(hoverListener);
    }
    /**
     * Filters and displays songs based on search query.
     * Searches in title, artist, and platform fields.
     */
    private void searchSongs() {
        String query = searchField.getText().trim();
        
        if (query.isEmpty()) {
            refreshSongsList();
            return;
        }
        
        ArrayList<Song> results = driver.musicList.searchSongs(query);
        
        songsPanel.removeAll();
        
        if (results.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No songs found matching: \"" + query + "\"");
            noResultsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            songsPanel.add(noResultsLabel);
        } else {
            for (Song song : results) {
                songsPanel.add(createSongPanel(song));
            }
        }
        
        songsPanel.revalidate();
        songsPanel.repaint();
    }
    
    /**
     * Displays all songs currently in the queue.
     * 
     * @param queueListPanel the panel to display the queue in
     */
    private void displayQueue(JPanel queueListPanel) {
        queueListPanel.removeAll();
        
        if (driver.musicQueue.isEmpty()) {
            JLabel emptyLabel = new JLabel("Queue is empty");
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            queueListPanel.add(emptyLabel);
        } else {
            // Get all songs from queue without removing them
            ArrayList<Song> queueSongs = driver.musicQueue.getAllSongs();
            
            int position = 1;
            for (Song song : queueSongs) {
                JPanel songItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                songItemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                
                String priorityIndicator = song.getPriorityQueued() ? "⭐ " : "";
                JLabel songLabel = new JLabel(position + ". " + priorityIndicator + 
                                              song.getTitle() + " - " + song.getArtist());
                
                songItemPanel.add(songLabel);
                queueListPanel.add(songItemPanel);
                position++;
            }
        }
        
        queueListPanel.revalidate();
        queueListPanel.repaint();
    }
}