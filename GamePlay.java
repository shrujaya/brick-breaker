package com.shrujaya;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private final Timer timer;

    private int paddleX = 300;

    private int ballPosX = 120;
    private int ballPosY = 350;

    private int ballXChange = -2;
    private int ballYChange = -4;

    private MapGenerator m;

    public GamePlay() {
        m = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        int delay = 8;
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) { //from JComponent
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //drawing map
        m.draw((Graphics2D)g);

        //borders
        g.setColor(Color.green);
        g.fillRect(0, 0, 3, 592); //left
        g.fillRect(0, 0, 692, 3); //top
        g.fillRect(691, 0, 3, 592); //right

        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 550, 30);

        //paddle
        g.setColor(Color.blue);
        g.fillRect(paddleX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);

        //game over
        if (ballPosY > 570) {
            play = false;
            ballXChange = ballYChange= 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("GAME OVER!", 250, 350);
            g.drawString("Press Enter to Restart", 205, 400);
        }

        //winning
        if (totalBricks == 0) {
            play = false;
            ballXChange = ballYChange= 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("YOU WON!", 260, 350);
            g.drawString("Press Enter to Restart", 210, 400);
        }
        g.dispose();
    }

    @Override //from ActionListener
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if (play) {
            ballPosX += ballXChange; //x-coordinates increase from left to right
            ballPosY += ballYChange; //y-coordinates increase from top to bottom
            if (ballPosX < 0) { //left wall
                ballXChange = -ballXChange;
            }
            if (ballPosY < 0) { //top wall
                ballYChange = -ballYChange;
            }
            if (ballPosX > 670) { //right wall
                ballXChange = -ballXChange;
            }

            //checking if ball is hitting paddle
            Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
            Rectangle paddleRect = new Rectangle(paddleX, 550, 100, 8);
            if (ballRect.intersects(paddleRect)) {
                ballYChange = -ballYChange;
            }

            //checking if ball is hitting bricks
            for (int i = 0; i < m.map.length; i++) {
                for (int j = 0; j < m.map[0].length; j++) {
                    if (m.map[i][j] > 0) {
                        int brickX = j * m.brickWidth + 80;
                        int brickY = i * m.brickHeight + 50;
                        int brickWidth = m.brickWidth;
                        int brickHeight = m.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        if (ballRect.intersects(brickRect)) {
                            m.setBrickValue(0, i, j);
                            totalBricks = totalBricks - 1;
                            score += 5;

                            //slightly unsure
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXChange = -ballXChange;
                            } else {
                                ballYChange = -ballYChange;
                            }
                        }
                    }
                }
            }
        }
        repaint(); //update() and paint() are taken care of
    }

    @Override //from KeyListener
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (paddleX >= 600) {
                paddleX = 600; //rightmost limit
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (paddleX <= 0) { //leftmost limit
                paddleX = 0;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) { //reset game
            if (!play) {
                play = true;
                score = 0;
                totalBricks = 21;

                paddleX = 300;

                ballPosX = 120;
                ballPosY = 350;

                ballXChange = -2;
                ballYChange = -4;

                m = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        paddleX += 30;
    }

    public void moveLeft() {
        play = true;
        paddleX -= 30;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}