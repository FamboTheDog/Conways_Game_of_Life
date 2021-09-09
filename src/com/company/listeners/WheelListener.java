package com.company.listeners;

import com.company.view.mainPanel.MainPanel;
import lombok.AllArgsConstructor;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

@AllArgsConstructor
public class WheelListener implements MouseWheelListener {

    private MainPanel mainPanel;
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double wheelRotation = e.getPreciseWheelRotation() / 10;

        mainPanel.setScale(mainPanel.getScale() - wheelRotation);
        if (mainPanel.getScale() > 1) mainPanel.setScale(1);
        else if (mainPanel.getScale() < 0.2) mainPanel.setScale(0.2);

        mainPanel.repaint();
    }
}
