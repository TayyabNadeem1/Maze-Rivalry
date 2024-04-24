import java.util.*;
import java.awt.Point;

/*Generates random maze using depth-first search and game mechanics for movement*/
public class MazeCreator
{
    /*STATIC BOOLEAN LABELS*/
    private static final boolean WALL=false;
    private static final boolean CELL=true;
    /*VARIABLES*/
    boolean[][] grid;
    Stack<Point> route;
    int width,height;
    Point exit;
    Point player;
    
    public MazeCreator(int width, int height)
    {
        this.width=width;
        this.height=height;
        route = new Stack<Point>();
        grid = new boolean[width][height];
        exit = null;
        player=new Point(1,1);//players starts in top right corner
    }
    
    public void createMaze()
    {
        /*create grid space*/
        for(int x = 0; x< grid[0].length; x++)
            for(int y = 0; y<grid.length; y++)
                grid[x][y]=WALL;

        /*create entrance*/
        route.push(new Point(1,1));
        grid[1][1]=CELL;
        
        /*create maze*/
        while(route.size()>0)
            next();
        
        /*create exit*/
        makeExit();
    }
    
    public void next()
    {
        /*use top of route stack*/
        int y=(int)(route.peek().getY());
        int x=(int)(route.peek().getX());
        
        /*if there is a cell to expand to adjacent to (x,y)*/
        if(isPossibleCell(x+1,y,1)||isPossibleCell(x-1,y,2)
               ||isPossibleCell(x,y-1,4)||isPossibleCell(x,y+1,3))
        {
            boolean cellCreated=false;
            while(!cellCreated)
            {
                /*randomaly attempt directions until one works
                 *Could be more efficient with more consistant big(o), but at my scale it is uneccessary*/
                int direction=(int)(Math.random()*4)+1;
                
                if(direction==1&&isPossibleCell(x+1,y,direction))//right
                {
                    grid[x+1][y]=CELL;
                    route.push(new Point(x+1,y));
                    cellCreated=true;
                }
                
                else if(direction==2&&isPossibleCell(x-1,y,direction))//left
                {
                    grid[x-1][y]=CELL;
                    route.push(new Point(x-1,y));
                    cellCreated=true;
                }
                
                else if(direction==3&&isPossibleCell(x,y+1,direction))//down
                {
                    grid[x][y+1]=CELL;
                    route.push(new Point(x,y+1));
                    cellCreated=true;
                }
                
                else if(direction==4&&isPossibleCell(x,y-1,direction))//up
                {
                    grid[x][y-1]=CELL;
                    route.push(new Point(x,y-1));
                    cellCreated=true;
                }
            }
        }
        
        else //if there is no other way to go from a cell, pop it, its done
            route.pop();
    }
    
    /*CHOOSES random corner, besides top-left, for exit*/
    public void makeExit()
    {
        int corner=(int)(Math.random()*3)+1;
        int x=0;
        int y=0;
        if(corner==1) //bottom right
        {
            y=height-1;
            x=width-1;
            while(grid[x][y]!=CELL) //check next diagnol spot to find cell
            {
                x--;
                y--;
            }
        }
        else if(corner==2) //top right
        {
            y=0;
            x=width-1;
            while(grid[x][y]!=CELL) //check next diagnol spot to find cell
            {
                x--;
                y++;
            }
        }
        else //bottom left
        {
            y=height-1;
            x=0;
            while(grid[x][y]!=CELL) //check next diagnol spot to find cell
            {
                x++;
                y--;
            }
        }
        exit=new Point(x,y);
    }
    
    /*Move player in specified direction if possible*/
    public void movePlayer(int direction)
    {
        int y=(int)(player.getY());
        int x=(int)(player.getX());
        
        /*check to see if it is a CELL in the direction attempted*/
        if(direction==1&&grid[x+1][y]==CELL)//Right
            player=new Point(x+1,y);
        
        else if(direction==2&&grid[x-1][y]==CELL)//left
            player=new Point(x-1,y);
        
        else if(direction==3&&grid[x][y+1]==CELL)//down
            player=new Point(x,y+1);
        
        else if(direction==4&&grid[x][y-1]==CELL)//up
            player=new Point(x,y-1);
    }
    
    /*Check if (x,y) is a possible cell to expand to*/
    public boolean isPossibleCell(int x, int y, int direction)
    {
        int amtTouchingCells=0;
      
        if(x!=width-1 && x!= 0 && y!=height-1 && y!=0 && grid[x][y]!=CELL) //bounds check
        {
            /*adjacent*/
            if(grid[x-1][y]==CELL)
                amtTouchingCells++;
            if(grid[x+1][y]==CELL)
                amtTouchingCells++;
            if(grid[x][y-1]==CELL)
                amtTouchingCells++;
            if(grid[x][y+1]==CELL)
                amtTouchingCells++;
            /*diaganol*/
            if(direction==1&&(grid[x+1][y+1]==CELL||grid[x+1][y-1]==CELL))//Right
                amtTouchingCells++;
            if(direction==2&&(grid[x-1][y+1]==CELL||grid[x-1][y-1]==CELL))//left
                amtTouchingCells++;
            if(direction==4&&(grid[x-1][y-1]==CELL||grid[x+1][y-1]==CELL))//up
                amtTouchingCells++;
            if(direction==3&&(grid[x-1][y+1]==CELL||grid[x+1][y+1]==CELL))//down
                amtTouchingCells++;
            /*needs to be less than 2 for there to be a possible cell to continue*/
            if(amtTouchingCells <= 1)
                return true;
        }
        return false;
        
    }
    
    /*check win case: player in exit*/
    public boolean win()
    {
        if(player.equals(exit))
            return true;
        return false;
    }
    
    /*RETURNS 2d array of maze (boolean values*/
    public boolean[][] getMaze()
    {
        return grid;
    }
    
    /*RETURNS player position (point)*/
    public Point getPlayerPos()
    {
        return player;
    }
    
    /*RETURNs exit location (point)*/
    public Point getExit()
    {
        return exit;
    }
    
    /*string version of 2d array map, helps visualize with character*/
    public String toString()
    {
        String result="";
        for(int y = 0; y< grid.length; y++)
        {
            for(int x = 0; x<grid[0].length; x++)
            {
                if(grid[x][y]==CELL)
                    result+= " ";
                else
                    result+= "#";
            }
            result+= "\n";
        }
        result+="\nEXIT:" + exit;
        return result;
    }
}