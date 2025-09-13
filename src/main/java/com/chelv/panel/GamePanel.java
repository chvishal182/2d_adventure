package com.chelv.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.chelv.entity.Player;
import com.chelv.inputHandler.KeyHandler;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    public int getTileSize() {
        return tileSize;
    }

    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    Player player;

    double playerX = 100;
    double playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        player = new Player(keyHandler, this); 
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        int fps = 60; // 60 frames per second
        double drawInterval = 1_000_000_000.00/fps; // nanoseconds per frame
        double delta = 0; //to represent the frame worth of time
        long lastTime = System.nanoTime();
        long timer = 0; // (tfinal - tinitial) ~ dt
        int drawCount = 0;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            delta += ((currentTime - lastTime)/drawInterval);
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update(1.0/fps);
                repaint();
                delta--;
                drawCount++;
            }

            if(timer > 1_000_000_000){
                System.out.println("FPS:" + drawCount + " " + delta);
                drawCount = 0;
                timer = 0;
            }
            

        }

    }
     
    double playerSpeedDouble = 200.00;

    public void update(double deltaTime){
       
        player.update(deltaTime);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        player.draw(g2);
        g2.dispose();
    }

}
