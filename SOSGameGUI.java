package defaultPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SOSGameGUI {
    private JFrame frame;
    private JButton[][] buttons;
    private Board board;
    private JLabel statusLabel;

    public SOSGameGUI(int size) {
        board = new Board(size);
        buttons = new JButton[size][size];
    }

    public void createAndShowGUI() {
        frame = new JFrame("SOS Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawLines(g);
            }
        };
        boardPanel.setLayout(new GridLayout(buttons.length, buttons.length));

        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new JButton("");
                final int row = i;
                final int col = j;
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleMove(row, col);
                    }
                });
                boardPanel.add(buttons[i][j]);
            }
        }

        statusLabel = new JLabel("Blue's turn");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusLabel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void handleMove(int row, int col) {
        String[] options = {"S", "O"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose a letter:", "Make a move",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        char letter = options[choice].charAt(0);
        if (board.makeMove(row, col, letter)) {
            buttons[row][col].setText(String.valueOf(letter));
            buttons[row][col].setForeground(board.getCurrentPlayer().equals("Blue") ? Color.RED : Color.BLUE);
            List<int[]> sosSequence = board.getSOSSequence(row, col, letter);
            if (!sosSequence.isEmpty()) {
                ((JPanel) frame.getContentPane().getComponent(0)).repaint(); // repaint to draw lines
            }
            updateStatus();
            if (board.isFull()) {
                endGame();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Square already occupied. Try again.");
        }
    }

    private void drawLines(Graphics g) { // draw lines through SOS sequence to show point
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                char letter = board.getCell(i, j);
                if (letter != ' ') {
                    List<int[]> sosSequence = board.getSOSSequence(i, j, letter);
                    if (!sosSequence.isEmpty()) {
                        g2.setColor(board.getCurrentPlayer().equals("Blue") ? Color.RED : Color.BLUE);
                        int[] start = sosSequence.get(0);
                        int[] end = sosSequence.get(sosSequence.size() - 1);
                        int startX = buttons[start[0]][start[1]].getX() + buttons[start[0]][start[1]].getWidth() / 2;
                        int startY = buttons[start[0]][start[1]].getY() + buttons[start[0]][start[1]].getHeight() / 2;
                        int endX = buttons[end[0]][end[1]].getX() + buttons[end[0]][end[1]].getWidth() / 2;
                        int endY = buttons[end[0]][end[1]].getY() + buttons[end[0]][end[1]].getHeight() / 2;
                        g2.drawLine(startX, startY, endX, endY);
                    }
                }
            }
        }
    }

    private void updateStatus() {
        String currentPlayer = board.getCurrentPlayer();
        statusLabel.setText(currentPlayer + "'s turn");
    }

    private void endGame() {
        String winner;
        int blueScore = board.getBlueScore();
        int redScore = board.getRedScore();

        if (blueScore > redScore) {
            winner = "Blue wins!";
        } else if (redScore > blueScore) {
            winner = "Red wins!";
        } else {
            winner = "It's a draw!";
        }

        JOptionPane.showMessageDialog(frame, "Game over!\n" + winner +
                "\nBlue: " + blueScore + " - Red: " + redScore);
        frame.dispose(); // close the game window
    }
}
