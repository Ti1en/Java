package com.apple.main;



import javax.swing.*;
import java.awt.*;
import java .awt.event.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class WordCountUI extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private JTextArea statsArea; //Here we display text stats


    public WordCountUI() {
        // Set up the JFrame
        setTitle("WORD COUNT UI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create a panel to hold the components
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a text field for input
        textField = new JTextField();
        panel.add(textField, BorderLayout.NORTH);

        // Create a text area to display the input text
        textArea = new JTextArea();	
        textArea.setEditable(false); // Make it read-only
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Create a button to submit the text
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = textField.getText();
                displayText(inputText);
                textField.setText(""); // Clear the input field
                textField.disable();   
            }
        });
        panel.add(submitButton, BorderLayout.WEST);
        
        //Create text area to display text stats
        statsArea = new JTextArea();
        statsArea.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(statsArea);
        panel.add(scrollPane1, BorderLayout.SOUTH);
        
        //Create a button to count the number of words in the text
  
        JButton countButton = new JButton("Show stats");
        countButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String inputText = textArea.getText();
        		displayStats(inputText);
        		}
        	});
        panel.add(countButton, BorderLayout.EAST);
        
        


        // Add the panel to the frame
        add(panel);
  
        
        
        // Display the frame
        setVisible(true);
    }

    private void displayText(String text) {
        // Append the input text to the text area
        //textArea.append("SUBMITED TEXT: " + text + "\n");
    	textArea.append(text );//+ "\n");
    }
    
    //Method that conuts number of words in the String
    private int wordCount(String s) {
    	int count = 0;
		int intOflastChar = s.charAt(s.length()-1);
    	for(int i=1; i<s.length(); i++) {
    		int intOfPreviousChar = s.charAt(i-1);
    		int intOfChar = s.charAt(i);
    		if(((48 <= intOfPreviousChar && intOfPreviousChar <= 57)||(65 <= intOfPreviousChar && intOfPreviousChar <= 90)||(97 <= intOfPreviousChar && intOfPreviousChar <= 122)) && (intOfChar == 32))
    			count++;
    	}
    	if((48 <= intOflastChar && intOflastChar <= 57)||(65 <= intOflastChar && intOflastChar <= 90)||(97 <= intOflastChar && intOflastChar <= 122))
    		count++;
    	
    	return count;
    }
    
    
    //Another method that counts words
    private int wordCount1(String s) {
    	int count = 0;
    	if(!s.isEmpty()) {
    		for(int i=1; i<s.length(); i++) {
    			if((s.charAt(i-1)!= ' ') && (s.charAt(i) == ' '))
    				count++;
    		}
    	if(s.charAt(s.length()-1) != ' ') 
    		count++;
    	}
    	
    	return count;
    }
    
    
    
    //Append the stats to the text area
    private void displayStats(String text){
    	statsArea.append("Number of words in text provided is: " + wordCount1(text));
    	//textArea.append("NUMBER OF WORDS IN TEXT SUBMITED IS: " + wordCount(text) +"\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WordCountUI();
            }
        });
    }
}
