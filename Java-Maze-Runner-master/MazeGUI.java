import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import java.awt.geom.Ellipse2D;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MazeGUI extends JPanel implements ActionListener, KeyListener {
    // Buttons and Labels
    private JLabel title, levelBoard;
    private MazeWindow mazeDisplay;
    private MazeCreator mazeBuilder;
    private int width, height;
    private JButton flashlightMode, originalMode, increaseSize, decreaseSize, reset;
    private int level;

    public static void main(String[] args) {
        new MazeGUI();
    }

    public MazeGUI() {
        /*initialize game variables*/
        level = 1;
        width = level * 5;
        height = level * 5;
        mazeBuilder = new MazeCreator(width, height);
        mazeBuilder.createMaze();

        /*CREATE AND SETUP WINDOW*/
        JFrame window = new JFrame("Maze Runner Interface");
        window.setBackground(new Color(255, 0, 0));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Maze Runner Interface");
        window.setSize(655, 620);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        /*SIDE BAR PANEL - buttons and level tracker*/
        JPanel sideBar = new JPanel();
        sideBar.setBackground(new Color(0, 0, 0));
        sideBar.setLayout(new GridLayout(4, 1));
        levelBoard = new JLabel("LEVEL: " + level, SwingConstants.CENTER);
        levelBoard.setFont(new Font("Times Roman", Font.PLAIN, 24));
        levelBoard.setForeground(Color.WHITE);
        sideBar.add(levelBoard);

        flashlightMode = createButton("Flashlight Mode");
        originalMode = createButton("Original Mode");
        reset = createButton("Restart");

        sideBar.add(flashlightMode);
        sideBar.add(originalMode);
        sideBar.add(reset);

        /*MAZE DISPLAY - holds MazeWindow*/
        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridLayout(1, 1));
        mazePanel.setBackground(new Color(0, 0, 0));
        mazeDisplay = new MazeWindow(mazeBuilder.getMaze(), mazeBuilder.getPlayerPos(), mazeBuilder.getExit());
        mazePanel.add(mazeDisplay);

        /*TITLE PANEL*/
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(33, 89, 122));
        title = new JLabel("MAZE RUNNER");
        title.setFont(new Font("Times Roman", Font.PLAIN, 57));
        title.setForeground(Color.WHITE);
        titleBar.add(title);

        /*CREATE MAIN PANEL (borderlayout) and ADD CHILDREN PANELS*/
        JPanel userInterface = new JPanel();
        userInterface.setLayout(new BorderLayout(20, 10));
        // menu panel background color
        userInterface.setBackground(new Color(0, 0, 0));
        userInterface.add(mazeDisplay, BorderLayout.CENTER);
        userInterface.add(titleBar, BorderLayout.NORTH);
        userInterface.add(sideBar, BorderLayout.EAST);

        /*FINISH WINDOW*/
        window.add(userInterface);
        window.addKeyListener(this);
        window.setVisible(true);
    }


    private JButton createButton(String text) {
        JButton button = new JButton("<html><center>" + text + "</center></html>");
        button.setFocusable(false);
        button.addActionListener(this);
        button.setBackground(new Color(33, 89, 122)); // Set the background color
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        /*BUTTON LISTENERS*/
        if (e.getSource() == originalMode)
            mazeDisplay.setOriginalMode();

        if (e.getSource() == flashlightMode)
            mazeDisplay.setFlashlightMode();

        if (e.getSource() == reset) {
            level = 1;
            levelBoard.setText("LEVEL: " + level);
            width = level * 5;
            height = level * 5;
            mazeBuilder = new MazeCreator(width, height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(), mazeBuilder.getPlayerPos(), mazeBuilder.getExit());
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        /*ARROW CONTROLS*/
        if (e.getExtendedKeyCode() == e.VK_DOWN)
            mazeBuilder.movePlayer(3);
        if (e.getExtendedKeyCode() == e.VK_LEFT)
            mazeBuilder.movePlayer(2);
        if (e.getExtendedKeyCode() == e.VK_RIGHT)
            mazeBuilder.movePlayer(1);
        if (e.getExtendedKeyCode() == e.VK_UP)
            mazeBuilder.movePlayer(4);

        /*UPDATE PLAYER DISPLAY*/
        mazeDisplay.setPlayer(mazeBuilder.getPlayerPos());

        /*MAZE BUILDER*/
        if (mazeBuilder.win()) {
            level++;
            levelBoard.setText("LEVEL: " + level);
            width = level * 5;
            height = level * 5;
            mazeBuilder = new MazeCreator(width, height);
            mazeBuilder.createMaze();
            mazeDisplay.setPoints(mazeBuilder.getMaze(), mazeBuilder.getPlayerPos(), mazeBuilder.getExit());
        }
    }
}
