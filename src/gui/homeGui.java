package gui;

import javax.swing.*;

import data.Song;
import driver.MusicDriver;

import java.awt.*;

public class homeGui {

	MusicDriver driver;
	
    public void createAndShow(MusicDriver driver) {
    	this.driver = driver;
    	
        JFrame frame = new JFrame("Item List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());
        
        // Add Panel
        JPanel addSongPanel = new JPanel(new GridLayout(2, 5, 10, 5));
        addSongPanel.setBorder(BorderFactory.createTitledBorder("Add a New Song"));
        
        JTextField titleField = new JTextField();
        JTextField artistField = new JTextField();
        JTextField platformField = new JTextField();
        JTextField linkField = new JTextField();
        JTextField releaseDateField = new JTextField();
        
        addSongPanel.add(new JLabel("Title:"));
        addSongPanel.add(new JLabel("Artist:"));
        addSongPanel.add(new JLabel("Platform:"));
        addSongPanel.add(new JLabel("Song Link:"));
        addSongPanel.add(new JLabel("Release Date:"));

        addSongPanel.add(titleField);
        addSongPanel.add(artistField);
        addSongPanel.add(platformField);
        addSongPanel.add(linkField);
        addSongPanel.add(releaseDateField);


        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Add Song"));
        buttonPanel.add(new JButton("Alphabetically"));
        buttonPanel.add(new JButton("Date Released"));
        buttonPanel.add(new JButton("Date Added"));
        
        
        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(addSongPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        frame.add(topPanel, BorderLayout.NORTH);
        

        // Songs Panel
        JPanel sognsPanel = new JPanel();
        sognsPanel.setLayout(new BoxLayout(sognsPanel, BoxLayout.Y_AXIS)); // vertical stacking

        // Add each item panel
        for (Song song : driver.musicList.getSongs()) {
        	sognsPanel.add(createSongPanel(song.getTitle() + ", " + song.getArtist(), "<html>Platform: " + song.getPlatform() + "<br>Song Link:" + song.getSongLink() + "<br>Liked:" + song.getLiked() + "<br>Release Date:" + song.getReleaseDate().toString() + "<br>Date Added:" + song.getDateAdded().toString() + "</html>"));
        }

        JScrollPane scrollPane = new JScrollPane(sognsPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Queue Panel
        JPanel queuePanel = new JPanel();
        
        Song songPlaying = null;
        if (!driver.musicQueue.isEmpty()) {
        	songPlaying = driver.musicQueue.dequeue();
        }
        
        JLabel infoLabel;
        if (songPlaying != null) {
        	 infoLabel = new JLabel("<html>🎵 Song Info: " + songPlaying.getTitle() + ", " + songPlaying.getArtist() + "<br>Song Link: " + songPlaying.getSongLink() + "</html>");
        } else {
            infoLabel = new JLabel("🎵 No songs in the queue.");
        }
        
        JButton viewButton = new JButton("View");
        
        queuePanel.add(infoLabel);
        queuePanel.add(new JButton("Next"));
        frame.add(queuePanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    
    
    JPanel createSongPanel(String title, String shortInfo) {
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

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(queueBtn);
        btnPanel.add(pQueueBtn);
        btnPanel.add(deleteBtn);

        itemPanel.add(textPanel, BorderLayout.CENTER);
        itemPanel.add(btnPanel, BorderLayout.EAST);

        // Hover
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
