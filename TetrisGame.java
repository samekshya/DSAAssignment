import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;


class TetrisBlock {
    int[][] shape;
    int x, y;
    Color color;

    public TetrisBlock(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        this.x = 4; // Spawn in the middle
        this.y = 0; // Spawn at the top
    }

    // Rotate block (basic implementation)
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                rotated[j][rows - 1 - i] = shape[i][j];

        shape = rotated;
    }
}

public class TetrisGame extends JPanel implements ActionListener {
    private static final int ROWS = 20, COLS = 10, TILE_SIZE = 30;
    private Timer timer;
    private Queue<TetrisBlock> queue = new LinkedList<>();
    private Stack<TetrisBlock> stack = new Stack<>();
    private int[][] board = new int[ROWS][COLS];
    private TetrisBlock currentBlock;
    private boolean gameOver = false;
    private int score = 0;

    public TetrisGame() {
        setPreferredSize(new Dimension(COLS * TILE_SIZE, ROWS * TILE_SIZE));
        setBackground(Color.BLACK);
        setFocusable(true);
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

        // ✅ Fixed Timer Issue
        timer = new javax.swing.Timer(500, this); // Falling speed
        timer.start();

        generateNewBlock();
    }

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

    private void move(int dx) {
        currentBlock.x += dx;
        if (collides()) currentBlock.x -= dx;
    }

    private void rotate() {
        currentBlock.rotate();
        if (collides()) currentBlock.rotate(); // Reverse if collision
    }

    private void drop() {
        currentBlock.y++;
        if (collides()) {
            currentBlock.y--;
            placeBlock();
        }
    }

    private boolean collides() {
        for (int i = 0; i < currentBlock.shape.length; i++)
            for (int j = 0; j < currentBlock.shape[i].length; j++)
                if (currentBlock.shape[i][j] == 1) {
                    int newX = currentBlock.x + j;
                    int newY = currentBlock.y + i;
                    if (newX < 0 || newX >= COLS || newY >= ROWS || (newY >= 0 && board[newY][newX] == 1))
                        return true;
                }
        return false;
    }

    private void placeBlock() {
        for (int i = 0; i < currentBlock.shape.length; i++)
            for (int j = 0; j < currentBlock.shape[i].length; j++)
                if (currentBlock.shape[i][j] == 1)
                    board[currentBlock.y + i][currentBlock.x + j] = 1;

        stack.push(currentBlock);
        checkLines();
        generateNewBlock();

        if (collides()) {
            gameOver = true;
            timer.stop();
        }
    }

    private void checkLines() {
        for (int i = ROWS - 1; i >= 0; i--) {
            boolean full = true;
            for (int j = 0; j < COLS; j++)
                if (board[i][j] == 0) full = false;

            if (full) {
                score += 10;
                System.arraycopy(board, 0, board, 1, i);
                Arrays.fill(board[0], 0);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++) {
                g.setColor(board[i][j] == 1 ? Color.WHITE : Color.BLACK);
                g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

        g.setColor(currentBlock.color);
        for (int i = 0; i < currentBlock.shape.length; i++)
            for (int j = 0; j < currentBlock.shape[i].length; j++)
                if (currentBlock.shape[i][j] == 1)
                    g.fillRect((currentBlock.x + j) * TILE_SIZE, (currentBlock.y + i) * TILE_SIZE, TILE_SIZE, TILE_SIZE);

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Game Over!", COLS * TILE_SIZE / 3, ROWS * TILE_SIZE / 2);
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
