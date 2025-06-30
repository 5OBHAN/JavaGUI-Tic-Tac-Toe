import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private int movesCount = 0;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3, 3, 3)); // 3px gaps between buttons
        panel.setBackground(Color.DARK_GRAY); // to show grid lines

        Font font = new Font("monospace", Font.BOLD, 60);

        // Initialize buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(font);
                btn.setFocusPainted(false);
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(50, 50, 50)); // dark text color
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setOpaque(true);

                // Add hover effect
                btn.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        if (btn.getText().equals("")) {
                            btn.setBackground(new Color(220, 220, 220));
                        }
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        if (btn.getText().equals("")) {
                            btn.setBackground(Color.WHITE);
                        }
                    }
                });

                int row = i;
                int col = j;
                btn.addActionListener(e -> handleMove(btn, row, col));
                buttons[i][j] = btn;
                panel.add(btn);
            }
        }

        add(panel);
        setResizable(false);

        setupKeyBindings(panel);

        setVisible(true);
    }

    private void handleMove(JButton btn, int row, int col) {
        if (!btn.getText().equals("")) {
            return;
        }
        btn.setText(String.valueOf(currentPlayer));
        btn.setForeground(currentPlayer == 'X' ? new Color(0, 102, 204) : new Color(200, 0, 0));
        movesCount++;

        if (checkWin()) {
            showMessage("Player-" + currentPlayer + " Wins!");
            resetBoard();
        } else if (movesCount == 9) {
            showMessage("It's a draw!");
            resetBoard();
        } else {
            switchPlayer();
        }
    }

    private void setupKeyBindings(JPanel panel) {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

        int[][] numpadToCell = {
            {0,0}, {0,1}, {0,2},
            {1,0}, {1,1}, {1,2},
            {2,0}, {2,1}, {2,2}
        };
        int[] keys = {
            KeyEvent.VK_NUMPAD7, KeyEvent.VK_NUMPAD8, KeyEvent.VK_NUMPAD9,
            KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6,
            KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3
        };

        for (int k = 0; k < keys.length; k++) {
            int row = numpadToCell[k][0];
            int col = numpadToCell[k][1];
            String actionKey = "numpad" + keys[k];
            inputMap.put(KeyStroke.getKeyStroke(keys[k], 0), actionKey);
            actionMap.put(actionKey, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = buttons[row][col];
                    if (btn.getText().equals("")) {
                        btn.doClick();
                    }
                }
            });
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer)))
                return true;

            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer)))
                return true;
        }
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer)))
            return true;

        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer)))
            return true;

        return false;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(Color.WHITE);
            }
        currentPlayer = 'X';
        movesCount = 0;
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeGUI::new);
    }
}
