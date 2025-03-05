// Question no 3 (b)
// Description:
// This program implements a simple Tetris game using Java Swing.
// - It uses a **queue** to manage upcoming blocks and a **stack** to track placed blocks.
// - The game allows **moving, rotating, and dropping** Tetris blocks.
// - Implements **collision detection** to prevent illegal moves.
// - Clears completed rows and updates the **score** accordingly.
// - The game ends when blocks reach the top of the board.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

// Represents a single Tetris block with a shape, position, and color
class TetrisBlock {
    int[][] shape; // 2D array representing the block structure
    int x, y; // Position of the block on the grid
    Color color; // Color of the block

    public TetrisBlock(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.x = 4; // Blocks spawn in the middle of the grid
        this.y = 0; // Blocks spawn at the top
    }

    // Rotates the block 90 degrees clockwise
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }
}

// Main game panel implementing Tetris logic
public class TetrisGame extends JPanel implements ActionListener {
    private static final int ROWS = 20, COLS = 10, TILE_SIZE = 30;
    private Timer timer; // Controls block fall speed
    private Queue<TetrisBlock> queue = new LinkedList<>(); // Queue for upcoming blocks
    private Stack<TetrisBlock> stack = new Stack<>(); // Stack for placed blocks
    private int[][] board = new int[ROWS][COLS]; // Game board grid
    private TetrisBlock currentBlock;
    private boolean gameOver = false;
    private int score = 0;

    public TetrisGame() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Handles keyboard input for moving and rotating blocks
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (gameOver) return;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT: move(-1); break;
                    case KeyEvent.VK_RIGHT: move(1); break;
                    case KeyEvent.VK_UP: rotate(); break;
                    case KeyEvent.VK_DOWN: drop(); break;
                }
                repaint();
            }
        });

        timer = new Timer(500, this); // Block falling speed
        timer.start();
        generateNewBlock();
    }

    // Generates a new random Tetris block
    private void generateNewBlock() {
        int[][][] shapes = {
            {{1, 1, 1, 1}}, // Line block
            {{1, 1, 1}, {0, 1, 0}}, // T-block
            {{1, 1, 0}, {0, 1, 1}} // Z-block
        };
        Color[] colors = {Color.CYAN, Color.MAGENTA, Color.RED};
        Random rand = new Random();

        if (queue.isEmpty()) {
            queue.add(new TetrisBlock(shapes[rand.nextInt(shapes.length)], colors[rand.nextInt(colors.length)]));
        }
        currentBlock = queue.poll();
        queue.add(new TetrisBlock(shapes[rand.nextInt(shapes.length)], colors[rand.nextInt(colors.length)]));
    }

    // Moves the block left or right
    private void move(int dx) {
        currentBlock.x += dx;
        if (collides()) currentBlock.x -= dx;
    }

    // Rotates the block and checks for collisions
    private void rotate() {
        currentBlock.rotate();
        if (collides()) currentBlock.rotate(); // Reverse rotation if it collides
    }

    // Drops the block down by one row
    private void drop() {
        currentBlock.y++;
        if (collides()) {
            currentBlock.y--;
            placeBlock();
        }
    }

    // Checks if the current block collides with the board boundaries or placed blocks
    private boolean collides() {
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] == 1) {
                    int newX = currentBlock.x + j;
                    int newY = currentBlock.y + i;
                    if (newX < 0 || newX >= COLS || newY >= ROWS || (newY >= 0 && board[newY][newX] == 1))
                        return true;
                }
            }
        }
        return false;
    }

    // Places the block on the board and checks for completed rows
    private void placeBlock() {
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] == 1) {
                    board[currentBlock.y + i][currentBlock.x + j] = 1;
                }
            }
        }
        stack.push(currentBlock); // Store the placed block in stack
        checkLines(); // Check if any row is completed
        generateNewBlock();

        if (collides()) {
            gameOver = true;
            timer.stop();
        }
    }

    // Checks for completed rows and clears them
    private void checkLines() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean full = true;
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == 0) full = false;
            }
            if (full) {
                score += 10; // Increase score when a row is cleared
                System.arraycopy(board, 0, board, 1, i);
                Arrays.fill(board[0], 0);
            }
        }
    }

    // Draws the game board and blocks
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                g.setColor(board[i][j] == 1 ? Color.WHITE : Color.BLACK);
                g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        g.setColor(currentBlock.color);
        for (int i = 0; i < currentBlock.shape.length; i++) {
            for (int j = 0; j < currentBlock.shape[i].length; j++) {
                if (currentBlock.shape[i][j] == 1)
                    g.fillRect((currentBlock.x + j) * TILE_SIZE, (currentBlock.y + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        drop();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris");
        TetrisGame game = new TetrisGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

// Summary:
// - This program implements basic Tetris mechanics, including movement, rotation, and block placement.
// - Uses a **queue** to manage upcoming blocks and a **stack** to store placed blocks.
// - Implements **collision detection** to prevent illegal movements.
// - The game continuously drops blocks until the board fills up.
// - Score increases when a row is cleared.

// Expected Gameplay:
// - Players use arrow keys: Left/Right to move, Up to rotate, Down to drop faster.
// - Blocks fall automatically every 500 milliseconds.
// - Completed rows disappear, increasing the score.
// - The game ends when blocks reach the top.
