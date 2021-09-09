package com.company.keybinds;

import com.company.view.mainPanel.MainPanel;
import com.company.algorithm.GameOfLife;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Start extends AbstractAction {

    private MainPanel mainPanel;
    private Thread run;

    @Getter private GameOfLife gameOfLife;

    public Start(MainPanel mainPanel){
        this.mainPanel = mainPanel;
        this.gameOfLife = new GameOfLife(mainPanel);
        run = new Thread(gameOfLife);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!run.isAlive()){
            gameOfLife = new GameOfLife(mainPanel);
            run = new Thread(gameOfLife);
            run.start();
        }
    }

}
