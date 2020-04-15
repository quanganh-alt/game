package Controller;

import View.HappyFrog;
import java.awt.Rectangle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author MyPC
 */
public class Control {

    HappyFrog main;
    ArrayList<JButton> ong;
    int point, width, height;
    double speed;
    boolean stop = false, pause, ready = true;
    JPanel game;
    JLabel frog;
    JButton[] pipe = new JButton[6];
    Rectangle[] saver = new Rectangle[4];
    Key key;
    Random RANDOM = new Random();
    Thread move = new MyThread();

    //Constructor
    public Control(HappyFrog Main) {
        ong = new ArrayList<>();
        key = new Key(this);
        main = Main;
        game = Main.getjPanelGame();
        width = game.getWidth();
        height = game.getHeight();
        frog = new JLabel(new ImageIcon("/Volumes/Untitled/J2.L.P0005 FrogGame/src/View/frog.jpg"));
        main.getPauseBtn().addKeyListener(key);
        main.getSaveBtn().addKeyListener(key);
        for (int i = 0; i < 6; i++) {
            pipe[i] = new JButton();
        }
        if (new File("data.bin").exists() && JOptionPane.showConfirmDialog(game, "Do you want to continue?", "New game", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            load();
        } else {
            newGame();
        }
    }

    //Create new game
    void newGame() {
        ong = new ArrayList<>();
        ready = true;
        pause = false;
        point = 0;
        speed = 0;
        main.getLabelPoint().setText("Points: " + point);
        frog.setBounds(width / 3 - 20, height / 2 - 20, 40, 40);
        game.add(frog);
        for (int i = 0; i < 3; i++) {
            pipe[i].setBounds(width + i * (width + 50) / 3, 0, 50, RANDOM.nextInt(height - 100));
            pipe[i + 3].setBounds(width + i * (width + 50) / 3, pipe[i].getHeight() + 100, 50, height - 100 - pipe[i].getHeight());
            game.add(pipe[i]);
            game.add(pipe[i + 3]);
            pipe[i].addKeyListener(key);
            pipe[i + 3].addKeyListener(key);
            ong.add(pipe[i]);
            ong.add(pipe[i+3]);
        }
        move = new MyThread();
        move.start();
    }

    void endGame() {
        ready = true;
        stop = true;
        String medal;
        if (point <= 10) {
            medal = "no";
        } else if (point < 20) {
            medal = "Bronze";
        } else if (point < 30) {
            medal = "Sliver";
        } else if (point < 40) {
            medal = "Goldd";
        } else {
            medal = "Platinum";
        }
        int option;
        if (!new File("data.bin").exists()) {
            option = JOptionPane.showOptionDialog(main, "You god " + medal + " medal. Do you want to continue?", "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"New game", "Exit"}, "New game");
        } else {
            option = JOptionPane.showOptionDialog(main, "You god " + medal + " medal. Do you want to continue?", "Game Over", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"New game", "Exit", "Continue"}, "New game");
        }
        switch (option) {
            case JOptionPane.YES_OPTION:
                newGame();
                break;
            case JOptionPane.CANCEL_OPTION:
                load();
                break;
            default:
                System.exit(0);
                break;
        }
    }

    public void pause() {
        if (!pause) {
            ready = true;
            move.suspend();
            pause = true;
            main.getPauseBtn().setText("Continue");
        } else {
//            move.resume();
            move = new MyThread();
            ready = true;
            move.start();
            pause = false;
            main.getPauseBtn().setText("Pause");
        }
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("data.bin");
            DataOutputStream dos = new DataOutputStream(fos);
            fos = new FileOutputStream("datapipe.bin");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            dos.writeInt(point);
            dos.writeInt(frog.getY());
            dos.writeDouble(speed);
            oos.writeObject(pipe);
            oos.writeObject(ong);
            fos.close();
            dos.close();
            oos.close();
        } catch (Exception e) {
        }
    }

    void load() {
        try {
            pause = false;
            game.removeAll();
            game.repaint();
            FileInputStream fis = new FileInputStream("data.bin");
            DataInputStream dis = new DataInputStream(fis);
            fis = new FileInputStream("datapipe.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            main.getLabelPoint().setText("Points: " + (point = dis.readInt()));
            frog.setBounds(width / 3 - 20, dis.readInt(), 40, 40);
            speed = dis.readDouble();
            pipe = (JButton[]) ois.readObject();
            ong = (ArrayList<JButton>) ois.readObject();
            dis.close();
            ois.close();
            game.add(frog);
            for (int i = 0; i < 6; i++) {
                game.add(pipe[i]);
                pipe[i].addKeyListener(key);
                //ong.add(pipe[i]);
            }
            for(JButton btn : ong){
                btn.addKeyListener(key);
            }
            move = new MyThread();
            move.start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Load file error");
            newGame();
            //System.exit(0);
        }
    }

    public void exit() {
        System.exit(0);
    }

    public class MyThread extends Thread {

        @Override
        public void run() {
            if (ready) {
                for (int i = 3; i >= 1; i--) {
                    try {
                        main.getReadyLabel().setText("Ready : " + i);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                }
                ready = false;
            }
            while (!stop) {
                try {
                    sleep(15);
                } catch (InterruptedException ex) {
                    //            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
                }

                main.getReadyLabel().setText("Start!!!");
                for (int i = 0; i < ong.size()/2; i++) {
                    JButton pipe1 = ong.get(2*i);
                    JButton pipe2 = ong.get(2*i+1);
                    if (pipe1.getX() + 50 < 0) {
                        pipe1.setBounds(width, 0, 50, RANDOM.nextInt(height - 100));
                        pipe2.setBounds(width, pipe1.getHeight() + 100, 50, height - 100 - pipe1.getHeight());
                    } else {
                        pipe1.setLocation(pipe1.getX() - 1, pipe1.getY());
                        pipe2.setLocation(pipe2.getX() - 1, pipe2.getY());
                    }
                    if (pipe1.getX() == frog.getX()) {
                        main.getLabelPoint().setText("Points: " + ++point);
                    }
                }
                speed += 0.2;
                frog.setLocation(frog.getX(), (int) (frog.getY() + speed));
                for (int i = 0; i < ong.size(); i++) {
                    if (frog.getBounds().intersects(ong.get(i).getBounds())) {
                        endGame();
                    }
                }
                if (frog.getY() < 0 || frog.getY() + 40 > height) {
                    endGame();
                }
            }
            stop = false;
        }
    };
}
