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

    private Timer timer;
    private int delay = 8;

    private int paddleX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXDir = -1;
    private int ballYDir = -2;

    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1,692, 592);

        //borders
        g.setColor(Color.green);
        g.fillRect(0, 0, 3, 592); //left
        g.fillRect(0, 0, 692, 3); //top
        g.fillRect(691, 0, 3, 592); //right

        //paddle
        g.setColor(Color.blue);
        g.fillRect(paddleX, 550, 100, 8);

        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, 20, 20);
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(paddleX >= 600) {
                paddleX = 600;
            }
            else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(paddleX < 8) {
                paddleX = 8;
            }
            else {
                moveLeft();
            }
        }
    }

    public void moveRight() {
        play = true;
        paddleX += 20;
    }

    public void moveLeft() {
        play = true;
        paddleX -= 20;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
