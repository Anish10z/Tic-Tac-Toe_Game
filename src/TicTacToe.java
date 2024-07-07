import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardwidth = 600;
    int boardheight = 650;
    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JPanel scorePanel = new JPanel();

    JButton [][] board = new JButton[3][3];
    JButton restartButton = new JButton("Restart Game");

    String PlayerX = "X";
    String PlayerO = "O";
    String CurrentPlayer = PlayerX;

    boolean gameover = false;
    int turns = 0;

    int playerXScore = 0;
    int playerOScore = 0;
    JLabel scoreLabel = new JLabel();

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardwidth, boardheight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel, BorderLayout.NORTH);
        frame.add(textPanel, BorderLayout.NORTH);

        scoreLabel.setBackground(Color.lightGray);
        scoreLabel.setForeground(Color.blue);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        updateScoreLabel();

        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(scoreLabel, BorderLayout.CENTER);
        textPanel.add(scorePanel, BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameover) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) {
                            tile.setText(CurrentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameover) {
                                CurrentPlayer = CurrentPlayer.equals(PlayerX) ? PlayerO : PlayerX;
                                textLabel.setText(CurrentPlayer + "'s turn");
                            }
                        }
                    }
                });
            }
        }

        restartButton.setBackground(Color.gray);
        restartButton.setForeground(Color.orange);
        restartButton.setFont(new Font("Arial", Font.BOLD, 30));
        restartButton.setFocusable(false);
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        bottomPanel.add(restartButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;
            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameover = true;
                updateScore();
                return;
            }
        }
        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameover = true;
                updateScore();
                return;
            }
        }
        // diagonal
        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameover = true;
            updateScore();
            return;
        }
        // antidiagonal
        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);

            gameover = true;
            updateScore();
            return;
        }
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameover = true;
            return;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(CurrentPlayer + " is the winner!");
        textLabel.setForeground(Color.green);
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.red);
        tile.setBackground(Color.gray);
        textLabel.setText("TIE!!");
        textLabel.setForeground(Color.red);
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
        gameover = false;
        turns = 0;
        CurrentPlayer = PlayerX;
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setForeground(Color.white);
    }

    void updateScore() {
        if (CurrentPlayer.equals(PlayerX)) {
            playerXScore++;
        } else {
            playerOScore++;
        }
        updateScoreLabel();
    }

    void updateScoreLabel() {
        scoreLabel.setText("Player X: " + playerXScore + "   Player O: " + playerOScore);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
