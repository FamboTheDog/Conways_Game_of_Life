package com.company.view.mainPanel;

import com.company.view.toolbar.Toolbar;

import javax.swing.*;
import java.awt.*;

public class Container extends JPanel {

    private MainPanel mainPanel;
    private Toolbar toolbar;

    public Container(MainPanel mainPanel, Toolbar toolbar){
        this.mainPanel = mainPanel;
        this.toolbar   = toolbar;

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(toolbar  , BorderLayout.SOUTH);
    }
}
