package com.chelv.entity;

import java.awt.image.BufferedImage;

public class Entity {
    
    public double x, y;
    public double speed;
    public double dx, dy;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int totalFrames;
    public int spriteNum;
    public int spriteCounter;

    public long lastFrameTime = 0;
    public int frameDuration = 150_000_000;
}
