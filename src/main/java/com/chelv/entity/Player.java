package com.chelv.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;

import com.chelv.inputHandler.KeyHandler;
import com.chelv.panel.GamePanel;

public class Player extends Entity{
    
    KeyHandler keyHandler;
    GamePanel gamePanel;

    public Player(KeyHandler keyHandler, GamePanel gamePanel){
        this.keyHandler = keyHandler;
        this.gamePanel  = gamePanel; 
        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 100.00;
        y = 100.00;
        speed = 200.00;
        direction = "right";
    }

    public void update(double deltaTime){
        if (keyHandler.upPressed) {
            direction = "up";
            y -= speed * deltaTime;
        }
        if (keyHandler.rightPressed) {
            direction = "right";
            x += speed * deltaTime;
        }
        if (keyHandler.downPressed) {
            direction = "down";
            y += speed * deltaTime;
        }
        if (keyHandler.leftPressed) {
            direction = "left";
            x -= speed * deltaTime;
        }
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.white);
        g2.fillRect((int)x, (int)y, gamePanel.getTileSize(), gamePanel.getTileSize());
    }

    public void getPlayerImage(){
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
}
