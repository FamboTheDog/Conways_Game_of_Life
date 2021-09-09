package com.company.listeners;

import com.company.view.mainPanel.MainPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class PanelMover extends MouseAdapter implements MouseMotionListener {

    private MainPanel mainPanel;
    public PanelMover(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    int startX;
    int startY;

    @Override
    public void mousePressed(MouseEvent e) {
        startX = e.getX();
        startY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mainPanel.setOffSetX(-(-mainPanel.getOffSetX() + startX - e.getX()));
        mainPanel.setOffSetY(-(-mainPanel.getOffSetY() + startY - e.getY()));
        startX = e.getX();
        startY = e.getY();
        mainPanel.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}

