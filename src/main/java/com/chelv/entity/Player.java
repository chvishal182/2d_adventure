package com.chelv.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.chelv.inputHandler.KeyHandler;
import com.chelv.panel.GamePanel;

public class Player extends Entity {

    KeyHandler keyHandler;
    GamePanel gamePanel;

    // Idle transition
    private boolean transitioningToIdle = false;
    private int idleFrame = 1;


    public Player(KeyHandler keyHandler, GamePanel gamePanel) {
        this.keyHandler = keyHandler;
        this.gamePanel = gamePanel;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100.00;
        y = 100.00;
        dx = 0;
        dy = 0;
        speed = 200.00;
        spriteNum = 1;
        totalFrames = 2;
        spriteCounter = 0;
        direction = "down";
    }

    public void update(double deltaTime) {

        dx = 0;
        dy = 0;
        if (keyHandler.upPressed)
            dy -= 1;
        if (keyHandler.downPressed)
            dy += 1;
        if (keyHandler.rightPressed)
            dx += 1;
        if (keyHandler.leftPressed)
            dx -= 1;

        if (dx != 0 && dy != 0) {
            double inv = 1 / Math.sqrt(2);
            dx *= inv;
            dy *= inv;
        }

        // Apply movement
        x += dx * speed * deltaTime;
        y += dy * speed * deltaTime;

        // --- Update facing direction ---
        updateFacingDirection();

        updateSpriteCounterTimeBased();
    }

    private void updateFacingDirection() {
        if (dx == 0 && dy == 0) return; // keep last facing direction

        if (Math.abs(dx) > Math.abs(dy)) {
            direction = dx > 0 ? "right" : "left";
        } else {
            direction = dy > 0 ? "down" : "up";
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":    image = (spriteNum == 1) ? up1 : up2; break;
            case "down":  image = (spriteNum == 1) ? down1 : down2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
            case "left":  image = (spriteNum == 1) ? left1 : left2; break;
            case "ideal":  image = down1; break; // default standing pose
        }

        g2.drawImage(image, (int) x, (int) y,
                gamePanel.getTileSize(),
                gamePanel.getTileSize(),
                null);
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSpriteCounter() {
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum % totalFrames) + 1;
            spriteCounter = 0;
        }
    }

    private void updateSpriteCounterTimeBased(){
        long currentTime = System.nanoTime();
        if(dx != 0 || dy != 0){
            if(currentTime - lastFrameTime >= frameDuration){
                spriteNum = (spriteNum % totalFrames) + 1;
                lastFrameTime = currentTime;
            }
            transitioningToIdle = true;
        }else{
            if(transitioningToIdle){
                if(currentTime - lastFrameTime >= frameDuration){
                    if(spriteNum > idleFrame){
                        spriteNum--;
                    }else if(spriteNum < idleFrame){
                        spriteNum++;
                    }else{
                        transitioningToIdle = false;
                    }
                    lastFrameTime = currentTime;
                }
            }
        }
    }
}
