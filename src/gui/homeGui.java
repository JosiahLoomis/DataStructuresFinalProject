package gui;

import javax.swing.*;
import data.Song;
import driver.MusicDriver;
import java.awt.*;
import java.time.LocalDate;

public class homeGui {

    MusicDriver driver;
    JPanel songsPanel;
    JFrame frame;
    JTextField titleField;
    JTextField artistField;
    JTextField platformField;
    JTextField linkField;
    JTextField releaseDateField;
    Song songPlaying = null;
    JLabel infoLabel;
    
    public void createAndShow(MusicDriver driver) {

    	this.driver = driver;
        
        frame = new JFrame("Item List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLayout(new BorderLayout());
        
        // Add Panel
        JPanel addSongPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        addSongPanel.setBorder(BorderFactory.createTitledBorder("Add a New Song"));
        
        titleField = new JTextField();
        artistField = new JTextField();
        platformField = new JTextField();
        linkField = new JTextField();
        releaseDateField = new JTextField();
        
        addSongPanel.add(new JLabel("Title:"));
        addSongPanel.add(new JLabel("Artist:"));
        addSongPanel.add(new JLabel("Platform:"));
        addSongPanel.add(new JLabel("Song Link:"));
        addSongPanel.add(new JLabel("Release Date (yyyy-MM-dd):"));

        addSongPanel.add(titleField);
        addSongPanel.add(artistField);
        addSongPanel.add(platformField);
        addSongPanel.add(linkField);
        addSongPanel.add(releaseDateField);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        
        JButton addSongBtn = new JButton("Add Song");
        JButton alphabeticallyBtn = new JButton("Alphabetically");
        JButton dateReleasedBtn = new JButton("Date Released");
        JButton dateAddedBtn = new JButton("Date Added");
        
        buttonPanel.add(addSongBtn);
        buttonPanel.add(alphabeticallyBtn);
        buttonPanel.add(dateReleasedBtn);
        buttonPanel.add(dateAddedBtn);
        
        alphabeticallyBtn.addActionListener(e -> sortSongsByAlphabet());
        dateReleasedBtn.addActionListener(e -> sortSongsByReleaseDate());
        dateAddedBtn.addActionListener(e -> sortSongsByDateAdded());
        
        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(addSongPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(topPanel, BorderLayout.NORTH);
        
        // Songs Panel
        songsPanel = new JPanel();
        songsPanel.setLayout(new BoxLayout(songsPanel, BoxLayout.Y_AXIS));
        
        // Initial load of songs
        refreshSongsList();

        JScrollPane scrollPane = new JScrollPane(songsPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Queue Panel
        JPanel queuePanel = new JPanel();
        
        infoLabel = new JLabel("");
        
        updateSongPlaying();
        
        
        JButton nextButton = new JButton("Next");
        
        queuePanel.add(infoLabel);
        queuePanel.add(nextButton);
        frame.add(queuePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        
        nextButton.addActionListener(e -> updateSongPlaying());
        // Add Song Button Action Listener
        addSongBtn.addActionListener(e -> addSong());
    }
    private void sortSongsByAlphabet() {
    	System.out.println("sortSongsByAlphabet");
    	
    	driver.musicList.updateList(driver.musicList.getSongsAlphabetically());
    	
    	refreshSongsList();
    }
    
    private void sortSongsByDateAdded() {
    	System.out.println("sortSongsByDateAdded");
    	
    	driver.musicList.updateList(driver.musicList.getSongsByDateAdded());
    	
    	refreshSongsList();
    }
    
    private void sortSongsByReleaseDate() {
    	System.out.println("sortSongsByReleaseDate");
    	
    	driver.musicList.updateList(driver.musicList.getSongsByDateCreated());
    	
    	refreshSongsList();
    }
    
    private void updateSongPlaying() {
    	if (!driver.musicQueue.isEmpty()) {
            songPlaying = driver.musicQueue.dequeue();
            
            
            infoLabel.setText("<html>🎵 Song Info: " + songPlaying.getTitle() + ", " + songPlaying.getArtist() + "<br>Song Link: " + songPlaying.getSongLink() + "</html>");
        }
        else {
            infoLabel.setText("🎵 No songs in the queue.");
        }
    	
    	
    }
    
    // Separate function to handle adding a song
    private void addSong() {
        String title = titleField.getText().trim();
        String artist = artistField.getText().trim();
        String platform = platformField.getText().trim();
        String link = linkField.getText().trim();
        String releaseDateStr = releaseDateField.getText().trim();
        
        // Validate all fields are filled
        if (title.isEmpty() || artist.isEmpty() || platform.isEmpty() || link.isEmpty() || releaseDateStr.isEmpty()) {
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
                "Invalid date format! Please use yyyy-MM-dd format.\nExample: 2000-01-01", 
                "Date Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Clear all text fields
        titleField.setText("");
        artistField.setText("");
        platformField.setText("");
        linkField.setText("");
        releaseDateField.setText("");
        
        // Create new song and add to list
        Song newSong = new Song(title, artist, platform, link, releaseDate, LocalDate.now());
        driver.musicList.addSong(newSong);
        
        // Refresh the display to show the new song
        refreshSongsList();
        
        JOptionPane.showMessageDialog(frame, "Song added successfully!");
    }
    
    // Refresh the songs display
    private void refreshSongsList() {
    	songsPanel.removeAll();  // Clear existing songs
        
        // Add all songs from the list
        for (Song song : driver.musicList.getSongs()) {
            songsPanel.add(createSongPanel(
                song,
                song.getTitle() + ", " + song.getArtist(), 
                "<html>Platform: " + song.getPlatform() + 
                "<br>Release Date:" + song.getReleaseDate().toString() + 
                "<br>Date Added:" + song.getDateAdded().toString() + "</html>"
            ));
        }
        
        songsPanel.revalidate();
        songsPanel.repaint();
    }
    
    JPanel createSongPanel(Song song, String title, String shortInfo) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        itemPanel.setBackground(Color.WHITE);

        // Left side
        JLabel titleLabel = new JLabel("<html><b>" + title + "</b></html>");
        JLabel infoLabel = new JLabel(shortInfo);
        infoLabel.setVisible(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.add(titleLabel);
        textPanel.add(infoLabel);

        // Right side
        JButton queueBtn = new JButton("Queue");
        JButton pQueueBtn = new JButton("Priorety Queue");
        JButton deleteBtn = new JButton("Delete");
        
        // Add delete functionality
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame,
                "Are you sure you want to delete \"" + song.getTitle() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                driver.musicList.removeSong(song); // or whatever your remove method is called
                refreshSongsList();
                JOptionPane.showMessageDialog(frame, "Song deleted successfully!");
            }
        });
        
        queueBtn.addActionListener(e -> {
        	driver.musicQueue.enqueue(song, false);
        	System.out.println(driver.musicQueue.peek());
        });
        
        pQueueBtn.addActionListener(e -> {
        	driver.musicQueue.enqueue(song, true);
        	System.out.println(driver.musicQueue.peek());
        });
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(queueBtn);
        btnPanel.add(pQueueBtn);
        btnPanel.add(deleteBtn);

        itemPanel.add(textPanel, BorderLayout.CENTER);
        itemPanel.add(btnPanel, BorderLayout.EAST);

        // Hover effects stay the same
        itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                infoLabel.setVisible(true);
                itemPanel.setBackground(new Color(230, 240, 255));
                textPanel.setBackground(new Color(230, 240, 255));
                btnPanel.setBackground(new Color(230, 240, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                infoLabel.setVisible(false);
                itemPanel.setBackground(Color.WHITE);
                textPanel.setBackground(Color.WHITE);
                btnPanel.setBackground(Color.WHITE);
            }
        });

        return itemPanel;
    }
}