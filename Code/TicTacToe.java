import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToe extends JFrame {
    public static final int BOXES = 3;
    public static final char PLAYER_1 = 'X';
    public static final char PLAYER_2 = 'O';
    private char[][] grids;
    private char currentPlayer;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private JPanel buttonPanel;
    private JButton exitButton;
    private JButton playAgainButton;
    private boolean gameOver;
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;

    public TicTacToe() {
        initialize();
    }

    private void initialize() {
        setTitle("Tic Tac Toe");
        setBounds(100, 100, 400, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Menu");
        newGameMenuItem = new JMenuItem("New Game");
        exitMenuItem = new JMenuItem("Exit");

        newGameMenuItem.addActionListener(e -> resetGame());
        exitMenuItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameMenuItem);
        gameMenu.add(exitMenuItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

    
        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(Color.LIGHT_GRAY);
        statusLabel.setPreferredSize(new Dimension(400, 50));
        add(statusLabel, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(BOXES, BOXES, 5, 5));
        add(gridPanel, BorderLayout.CENTER);

        grids = new char[BOXES][BOXES];
        buttons = new JButton[BOXES][BOXES];
        initializeGrid();
        initializeButtons(gridPanel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); 

        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> resetGame());

        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        buttonPanel.setVisible(false); 
        add(buttonPanel, BorderLayout.SOUTH);

        currentPlayer = PLAYER_1;
        gameOver = false;
        updateStatus();
    }

    private void initializeGrid() {
        int gridNumbers = 1;
        for (int i = 0; i < BOXES; i++) {
            for (int j = 0; j < BOXES; j++) {
                grids[i][j] = (char) ('0' + gridNumbers);
                gridNumbers++;
            }
        }
    }

    private void initializeButtons(JPanel gridPanel) {
        for (int i = 0; i < BOXES; i++) {
            for (int j = 0; j < BOXES; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("Arial", Font.BOLD, 60));
                button.setFocusPainted(false);
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
                button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); 
                button.addActionListener(new ButtonClickListener(i, j));
                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return; 

            if (grids[row][col] != PLAYER_1 && grids[row][col] != PLAYER_2) {
                grids[row][col] = currentPlayer;
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setBackground(currentPlayer == PLAYER_1 ? Color.CYAN : Color.PINK); // Color buttons based on player

                if (winnerChecker(currentPlayer)) {
                    statusLabel.setText("Player " + currentPlayer + " WON!!");
                    statusLabel.setBackground(Color.GREEN);
                    gameOver = true;
                    buttonPanel.setVisible(true); s
                } else if (isGridsFull()) {
                    statusLabel.setText("Game was drawn!");
                    statusLabel.setBackground(Color.YELLOW);
                    gameOver = true;
                    buttonPanel.setVisible(true); 
                } else {
                    currentPlayer = (currentPlayer == PLAYER_1) ? PLAYER_2 : PLAYER_1;
                    updateStatus();
                }
            } else {
                statusLabel.setText("Box already occupied. Try again.");
                statusLabel.setBackground(Color.RED);
            }
        }
    }

    private boolean winnerChecker(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < BOXES; i++) {
            if (grids[i][0] == player && grids[i][1] == player && grids[i][2] == player) {
                return true;
            }
        }
        for (int j = 0; j < BOXES; j++) {
            if (grids[0][j] == player && grids[1][j] == player && grids[2][j] == player) {
                return true;
            }
        }
        if (grids[0][0] == player && grids[1][1] == player && grids[2][2] == player) {
            return true;
        }
        if (grids[2][0] == player && grids[1][1] == player && grids[0][2] == player) {
            return true;
        }
        return false;
    }

    private boolean isGridsFull() {
        for (int i = 0; i < BOXES; i++) {
            for (int j = 0; j < BOXES; j++) {
                if (Character.isDigit(grids[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        initializeGrid();
        for (int i = 0; i < BOXES; i++) {
            for (int j = 0; j < BOXES; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.LIGHT_GRAY); 
            }
        }
        currentPlayer = PLAYER_1;
        gameOver = false;
        buttonPanel.setVisible(false);
        updateStatus();
    }

    private void updateStatus() {
        statusLabel.setText("Player " + currentPlayer + "'s turn");
        statusLabel.setBackground(Color.LIGHT_GRAY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe window = new TicTacToe();
            window.setVisible(true);
        });
    }
}
