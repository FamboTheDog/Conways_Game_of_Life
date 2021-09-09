package com.company;

import com.company.listeners.AddCell;
import com.company.view.toolbar.Toolbar;
import com.company.view.mainPanel.Container;
import com.company.view.mainPanel.MainPanel;

import javax.swing.*;
import java.awt.*;


public class Main {

    private static JFrame frame;
    private static final String APP_NAME = "Conway's Game of Life";

    public static void main(String[] args) {
        frame = new JFrame(APP_NAME);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(640,480));
        frame.setResizable(true);
        frame.setVisible(true);

        MainPanel mainPanel = new MainPanel();
        Toolbar toolbar = new Toolbar(mainPanel);
        Container container = new Container(mainPanel, toolbar);

        frame.add(container);

        mainPanel.requestFocusInWindow();

        frame.revalidate();
        frame.repaint();
    }
}
