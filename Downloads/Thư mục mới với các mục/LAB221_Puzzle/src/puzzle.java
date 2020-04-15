//
//import java.awt.*;
//import javax.swing.*;
//import java.awt.event.*;
//import java.util.Random;
//import java.awt.BorderLayout;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.GridLayout;
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//public class puzzle extends JFrame implements ActionListener {
//
//    int nbGrid = 3;
//    JButton[] btn;
//    private int[] tiles;
//    private static final Random RANDOM = new Random(); // Random object to shuffle tiles
//    int blankPos = nbGrid * nbGrid - 1;
//    JButton newGame;
//    int countClick = 0;
//    JLabel moveCount;
//    JLabel demThoiGian;
//    JComboBox cbSize;
//    Integer second;
//    Timer thoigian;// Tao doi tuong dem thoi gianx
//    String cbSizeString[] = {"3x3", "4x4", "5x5", "6x6", "7x7", "8x8", "9x9"};
//    JPanel panelGridLayout;
//    private static final Color FOREGROUND_COLOR = new Color(239, 83, 80);
//
//    private void randomNumber() {
//        // don't include the blank tile in the shuffle, leave in the solved position
//        int n = tiles.length - 1;
//        reset();
//        while (n > 1) {
//            int r = RANDOM.nextInt(n--);
//            int tmp = tiles[r];
//            tiles[r] = tiles[n];
//            tiles[n] = tmp;
//        }
//    }
//
//    private void reset() {
//        for (int i = 0; i < tiles.length; i++) {
//            tiles[i] = (i + 1) % tiles.length;
//
//        }
//
//        // we set blank cell at the last
//        blankPos = tiles.length - 1;
//
//    }
//
//    public void resetButton() {
//        randomNumber();
//        for (int i = 0; i < btn.length; i++) {
//            if (i == tiles.length - 1) {
//                btn[i].setLabel(" ");
//            } else {
//                btn[i].setLabel("" + tiles[i]);
//            }
//
//        }
//    }
//
//    private void newGame() {
//        reset(); // reset in intial state
//        randomNumber(); // shuffle
//    }
//
//    public void createGrid(int nbGrid) {
//        tiles = new int[nbGrid * nbGrid];
//        //p2
//        panelGridLayout = new JPanel(new GridLayout(nbGrid, nbGrid, 10, 10));
//        panelGridLayout.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        panelGridLayout.setPreferredSize(new Dimension(300, 300));
////        panelGridLayout.setMaximumSize(new Dimension(600, 600));
//        btn = new JButton[nbGrid * nbGrid];
//        randomNumber();
//        for (int i = 0; i < btn.length; i++) {
//            if (i == tiles.length - 1) {
//                btn[i] = new JButton(" ");
//            } else {
//                btn[i] = new JButton("" + tiles[i]);
//            }
//
//            btn[i].setPreferredSize(new Dimension(50, 50));
////            btn[i].setMaximumSize(new Dimension(100, 100));
//
//            btn[i].setBackground(FOREGROUND_COLOR);
//            btn[i].setFont(new Font("arial", Font.BOLD, 22));
//            panelGridLayout.add(btn[i]);
//            btn[i].addActionListener(this);
//
//        }
//        Container c = getContentPane();
//        c.add(panelGridLayout, BorderLayout.NORTH);
//        pack();
//
//    }
//
//    public void createTxtScore() {
//
//    }
//
//    void puzzle() {
//
//        //p1 
//        JPanel panelTxtScore = new JPanel();
//        panelTxtScore.setLayout(new BoxLayout(panelTxtScore, BoxLayout.Y_AXIS));
//        panelTxtScore.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
//        panelTxtScore.setPreferredSize(new Dimension(150, 80));
//
//        //move count
//        panelTxtScore.add(moveCount = new JLabel("Move count: 0"));
//        moveCount.setFont(new Font("arial", Font.BOLD, 12));
//
//        //time
//        panelTxtScore.add(demThoiGian = new JLabel("Elaped: 00 sec"));
//        demThoiGian.setFont(new Font("arial", Font.BOLD, 12));
//        second = 0;
//        //combobox
//        JPanel pSize = new JPanel();
//        JLabel txtSize = new JLabel("size:  ");
//        //ComboBox
//        cbSize = new JComboBox(cbSizeString);
//        pSize.add(txtSize);
//        pSize.add(cbSize);
//        pSize.setAlignmentX(Component.LEFT_ALIGNMENT);
//        panelTxtScore.add(pSize);
//        pSize.add(cbSize);
//
//        thoigian = new Timer(1000, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String temp1 = second.toString();
//                if (temp1.length() == 1) {
//                    temp1 = "0" + temp1;
//                }
//                demThoiGian.setText("Elaped: " + temp1 + " sec");
//                second++;
//
//            }
//
//        });
//
//        createGrid(nbGrid);
//
//        //p3
//        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
//        p3.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
//
//        //button
//        newGame = new JButton("New Game");
////        newGame.setPreferredSize(new Dimension(100, 30));
//        newGame.setBackground(Color.black);
//        newGame.setForeground(Color.green);
//        newGame.addActionListener(this);
//        p3.add(newGame);
//
//        //add p1 p2 p3 frame
//        Container c = getContentPane();
//        c.add(panelTxtScore, BorderLayout.CENTER);
//        c.add(p3, BorderLayout.SOUTH);
//        setLocationRelativeTo(this);
//        setVisible(true);
//        pack();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//    }//end of constructor  
//
//    public void swap(ActionEvent e, JButton[] btn) {
//        for (int i = 0; i < btn.length; i++) {
//            if (e.getSource() == btn[i]) {
//                if ((blankPos - 1 == i) || (blankPos + 1 == i)
//                        || (blankPos - nbGrid == i) || (blankPos + nbGrid == i)) {
//                    String s = btn[i].getText();
//                    btn[blankPos].setText(s);
//                    btn[i].setText(" ");
//                    countClick++;
//                    moveCount.setText("Move count " + countClick);
//                    thoigian.start();
//                    blankPos = i;
//                }
//            }
//
//        }
//    }
//
//    public boolean checkWin() {
//        for (int i = 0; i < btn.length - 1; i++) {
//            
//            if (!btn[i].getText().equalsIgnoreCase(String.valueOf(i + 1))) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void actionPerformed(ActionEvent e) {
//        swap(e, btn);
//        
//        if (e.getSource() == newGame) {
//            String size = cbSize.getSelectedItem().toString().charAt(0) + "";
//            if (Integer.parseInt(size) != nbGrid) {
//                nbGrid = Integer.parseInt(size);
//                tiles = new int[nbGrid * nbGrid];
//                panelGridLayout.removeAll();
//                panelGridLayout.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//                int x = nbGrid * 70 + 40;
//                panelGridLayout.setPreferredSize(new Dimension(x, x));
//                GridLayout gridLayout = new GridLayout(nbGrid, nbGrid, 20, 20);
//                panelGridLayout.setLayout(gridLayout);
//                btn = new JButton[nbGrid * nbGrid];
//                randomNumber();
//                for (int i = 0; i < btn.length; i++) {
//                    if (i == tiles.length - 1) {
//                        btn[i] = new JButton(" ");
//                    } else {
//                        btn[i] = new JButton("" + tiles[i]);
//                    }
//
//                    btn[i].setPreferredSize(new Dimension(50, 50));
//                    btn[i].setBackground(FOREGROUND_COLOR);
//                    btn[i].setFont(new Font("arial", Font.BOLD, 22));
//                    panelGridLayout.add(btn[i]);
//                    btn[i].addActionListener(this);
//
//                }
//                Container c = getContentPane();
//                c.add(panelGridLayout, BorderLayout.NORTH);
//
//            }
//
//            reset();
//            resetButton();
//            second = 0;
//            moveCount.setText("Move count: 0");
//            countClick = 0;
//            demThoiGian.setText("Elaped: 00 sec");
//            thoigian.stop();
//        }
//        if (checkWin()) {
//            JOptionPane.showMessageDialog(rootPane, "you Win");
//            resetButton();
//            second = 0;
//            countClick = 0;
//            thoigian.stop();
//
//        }
//
//    }
//
////    public static void main(String[] args) {
////        new puzzle().puzzle();
////    }//end of main  
//
//}//end of class  
