package com.shrujaya;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
	    JFrame frame = new JFrame();
	    GamePlay gamePlay = new GamePlay();
	    frame.setBounds(10, 10, 708, 600);
	    frame.setTitle("Atari Breakout");
	    frame.setResizable(true);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(gamePlay);
		frame.setVisible(true);
    }
}
