
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class controller implements ActionListener {

    boolean start = false;
    boolean moved = false;
    gameForm p;
    JButton[] btn;
    JLabel jLabelCount, jLabelTime;
    public int count = 0;
    static int time = 0;
    public int height = 0;
    public int width = 0;
    int size = 3;
    int blankPos = size * size - 1;
    private Integer[] tiles;
    private static final Random RANDOM = new Random();
    Thread cTime = new Thread() {
        @Override
        public void run() {
            while (true) {
                //if(count > 0) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    jLabelTime.setText("Elapsed: " + time++ + " sec");
              //  }
            }
        }
    };

    public controller(gameForm p) {
        this.p = p;
        jLabelCount = p.getjLabelCount();
        jLabelTime = p.getjLabelTime();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("width = " + Math.min(screenSize.getHeight(), screenSize.getWidth()));
        for (int i = 0; i < (Math.min(screenSize.getHeight(), screenSize.getWidth()) - 180) / 60 - 4; i++) {
            p.getjComboBox1().insertItemAt((i + 2) + " x " + (i + 2), i);
        }
        p.getjComboBox1().setSelectedIndex(0);
    }

    private boolean isSolvable() {
        int Inversions = 0;
        for (int i = 0; i < tiles.length - 1; i++) {
            for (int j = i + 1; j < tiles.length; j++) {
                if (tiles[i] > tiles[j] && tiles[i] != 0 && tiles[j] != 0) {
                    Inversions++;
                }
            }
        }
        if (size % 2 != 0) {
            return Inversions % 2 == 0;
        }
        return (Inversions % 2) != (((blankPos / size) % 2));
    }

    public void createNewGame() {
        size = p.getjComboBox1().getSelectedIndex() + 2;
        time = 0;

        create(size);
    }

    private void randomNumber() {
        // don't include the blank tile in the shuffle, leave in the solved position
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = i;
        }
        List<Integer> intList = Arrays.asList(tiles);
        Collections.shuffle(intList);
        intList.toArray(tiles);

        //tìm vị trí của ôn trống
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                blankPos = i;
                break;
            }
        }
        if (!isSolvable()) {
            if (blankPos != 1 && blankPos != 0) {
                int temp = tiles[0];
                tiles[0] = tiles[1];
                tiles[1] = temp;
            } else {
                int temp = tiles[tiles.length - 1];
                tiles[tiles.length - 1] = tiles[tiles.length - 2];
                tiles[tiles.length - 2] = temp;
            }
        }

    }

    public void create(int nbGrid) {
        tiles = new Integer[nbGrid * nbGrid];
        //p2
        p.getjPanelBut().removeAll();
        width = height = nbGrid * 60;
        p.getjPanelBut().setPreferredSize(new Dimension(width, height));
        p.getjPanelBut().setLayout(new GridLayout(nbGrid, nbGrid));
        //Begin set button
        btn = new JButton[nbGrid * nbGrid];
        {
            randomNumber();
            for (int i = 0; i < btn.length; i++) {
                if (tiles[i] == 0) {
                    btn[i] = new JButton(" ");
                } else {
                    btn[i] = new JButton("" + tiles[i]);
                }
                System.out.print(tiles[i] + " ");

                btn[i].setFont(new Font("arial", Font.BOLD, 22));
                p.getjPanelBut().add(btn[i]);
                  btn[i].addActionListener(this);
            }
        }
        System.out.println();
    }

    public void swap(ActionEvent e, JButton[] btn) {
        for (int i = 0; i < btn.length; i++) {
            if (e.getSource() == btn[i]) {

                if ((blankPos - 1 == i && blankPos % size != 0) || (blankPos + 1 == i && i % size != 0)
                        || (blankPos - size == i) || (blankPos + size == i)) {
                    btn[blankPos].setText(btn[i].getText());
                    btn[i].setText(" ");
                    count++;
                    jLabelCount.setText("Move count: " + count);
                    blankPos = i;
                    start = true;
                    moved = true;
                }
                return;
            }
        }

    }

    public boolean checkWin() {
        if (blankPos != btn.length - 1) {
            return false;
        }
        for (int i = 0; i < btn.length - 1; i++) {
            if (!btn[i].getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        swap(e, btn);
        if (checkWin()) {
            cTime.suspend();
            for (int i = 0; i < btn.length; i++) {
                btn[i].setEnabled(false);
            }
            JOptionPane.showMessageDialog(null, "You win!");
        }
    }

    public void start() {
        jLabelTime.setText("Elapsed: " + 0 + " sec");
        if (cTime.isAlive()) {
            cTime.resume();
        } else {
            cTime.start();
        }
        moved = false;
        createNewGame();
        p.setBounds(p.getBounds().x, p.getBounds().y, width + 50, height + 200);
        count = 0;
        p.getjLabelCount().setText("move count: " + count);

    }
}
