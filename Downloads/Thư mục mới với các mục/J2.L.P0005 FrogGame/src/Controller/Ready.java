/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.HappyFrog;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author quanganh
 */
public class Ready extends Thread{
    HappyFrog main;
    public Ready(HappyFrog main) {
        super();
        this.main = main;
    }

    
    @Override
    public void run() {
        for(int i=3;i>=1;i--) {
            try {
                System.out.println(i);
                main.getReadyLabel().setText("Ready: " + i);
                System.out.println(main.getReadyLabel().getText());
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Ready.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        main.getReadyLabel().setText("Start!");
    }
    
}
