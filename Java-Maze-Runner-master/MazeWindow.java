import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.geom.*;
import java.awt.geom.Rectangle2D.Double;

/*MAZE GRAPHIC CLASS DRAWN ON A JPANEL*/
public class MazeWindow extends JPanel
{
    /*VARIABLES*/
    private static final boolean WALL=false;
    private static final boolean CELL=true;
    boolean[][] maze;
    boolean flashlightMode;
    Point player,exit;
    int height, width;
    // Load images for the wall
    private Image wallImage;
    private Image playerImage;
    private Image exitImage;
    public MazeWindow(boolean[][] maze, Point player, Point exit)
    {
        height=maze.length;
        width=maze[0].length;
        this.maze=maze;
        this.exit=exit;
        this.player=player;
        flashlightMode=false;
        try {
            // Load the wall image (replace "wall_image.png" with the actual file name)
            wallImage = ImageIO.read(new File("C:\\Users\\Admin\\Downloads\\Java-Maze-Runner-master\\Java-Maze-Runner-master\\grey_wall.png"));
            playerImage = ImageIO.read(new File("C:\\Users\\Admin\\Downloads\\Java-Maze-Runner-master\\Java-Maze-Runner-master\\darkknight.gif"));
            exitImage = ImageIO.read(new File("C:\\Users\\Admin\\Downloads\\Java-Maze-Runner-master\\Java-Maze-Runner-master\\Checkpoint.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /*UPDATE DISPLAY WITH NEW INFORMATION*/
    public void setPoints(boolean[][] maze, Point player, Point exit)
    {
        height=maze.length;
        width=maze[0].length;
        this.maze = maze;
        this.exit=exit;
        this.player=player;
        repaint();
    }
    
    /*REPAINT METHOD*/
    public void paintComponent (Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black); //cover old frame with black rectangle
        g2.fill(new Rectangle2D.Double(0, 0, 700, 700));
        paintMaze(g2); //print new maze graphic
    }
    
    /*PAINT MAZE*/
    public void paintMaze(Graphics2D g2) {
        /*LOOP THROUGH MAZE 2D ARRAY*/
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                /*DETERMINE COLOR*/
                if (player.getY() == y && player.getX() == x) // player
                    g2.drawImage(playerImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                            (int) (500.0 / width), (int) (500.0 / height), null);
                else if (exit.getY() == y && exit.getX() == x) // exit
                    g2.drawImage(exitImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                            (int) (500.0 / width), (int) (500.0 / height), null);
                else if (maze[x][y] == WALL) // wall
                    g2.drawImage(wallImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
                            (int) (500.0 / width), (int) (500.0 / height), null);
                else if (flashlightMode && (x > player.getX() + 3 || y > player.getY() + 3 || x < player.getX() - 3
                        || y < player.getY() - 3)) { // darkness
                    g2.setPaint(Color.black);
                    // Fill the grid cell for darkness
                    g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
                            (500.0 / width), (500.0 / height)));
                } else { // cell
                    g2.setPaint(Color.black);
                    // Fill the grid cell
                    g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
                            (500.0 / width), (500.0 / height)));
                }
            }
        }
    }




//    public void paintMaze(Graphics2D g2) {
//        /*LOOP THROUGH MAZE 2D ARRAY*/
//        for (int y = 0; y < maze.length; y++) {
//            for (int x = 0; x < maze[0].length; x++) {
//                /*DETERMINE COLOR*/
//                if (player.getY() == y && player.getX() == x) // player
//                    g2.drawImage(playerImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
//                            (int) (500.0 / width), (int) (500.0 / height), null);
//                else if (exit.getY() == y && exit.getX() == x) // exit
//                    g2.drawImage(checkpoint, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
//                            (int) (500.0 / width), (int) (500.0 / height), null);
//                else if (maze[x][y] == WALL) // wall
//                    g2.drawImage(wallImage, (int) (x * (500.0 / width)), (int) (y * (500.0 / height)),
//                            (int) (500.0 / width), (int) (500.0 / height), null);
//                else if (flashlightMode && isDark(x, y)) { // darkness
//                    //g2.setPaint(Color.black);
//                    // Fill the grid cell for darkness
//                    g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
//                            (500.0 / width), (500.0 / height)));
//                } else { // cell
//                    g2.setPaint(Color.black);
//                    // Fill the grid cell
//                    g2.fill(new Rectangle2D.Double(x * (500.0 / width), y * (500.0 / height),
//                            (500.0 / width), (500.0 / height)));
//                }
//            }
//        }
//    }

//    private boolean isDark(int x, int y) {
//        int distance = (int) Math.sqrt(Math.pow(player.getX() - x, 2) + Math.pow(player.getY() - y, 2));
//        return distance > 3; // Adjust the distance as needed
//    }




    public void setOriginalMode()
    {
        flashlightMode=false;
        repaint();
    }
    
    public void setFlashlightMode()
    {
        flashlightMode=true;
        repaint();
    }
    
    public void setPlayer(Point player)
    {
        this.player=player;
        repaint();
    }
}