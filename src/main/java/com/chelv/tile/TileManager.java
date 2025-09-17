package com.chelv.tile;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.chelv.panel.GamePanel;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int numOfTiles = 10;
    int mapTileNum[][];
    String filePath;

    
    private void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[1] = new Tile();
            tile[2] = new Tile();

            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void loadMap(String filePath){
        try (InputStream    is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            
            int col = 0;
            int row = 0;
            while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
                String line = br.readLine();
                String[] numbers = line.split(" ");
                while(col < gp.getMaxScreenCol()){
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                    col++;
                }
                if(col == gp.getMaxScreenCol()){
                    col = 0;
                    row++;
                }
            }    
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public TileManager(GamePanel gp) {
        this.gp  = gp;
        tile = new Tile[numOfTiles];
        filePath = "/maps/mapOne.txt"; 
        mapTileNum = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
        getTileImage();
        loadMap(filePath);
    }    

    public void draw(Graphics2D g2){
        int col = 0, row = 0;
        int x = 0, y = 0;

        while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image,
                         x,y,
                         gp.getTileSize(), gp.getTileSize(),
                         null);
            
            col++;
            x += gp.getTileSize();

            if(col == gp.getMaxScreenCol()){
                col = 0;
                x   = 0;
                row++;
                y += gp.getTileSize();
            }
        }
    }
}
