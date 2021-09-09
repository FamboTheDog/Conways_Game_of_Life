package com.company.listeners;

import com.company.view.mainPanel.MainPanel;
import com.company.entities.Entity;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class AddCell extends MouseAdapter {

    private MainPanel mainPanel;

    public AddCell(MainPanel mainPanel){
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            leftClick(e);
        }else{
            rightClick(e);
        }
        mainPanel.repaint();
    }

    private void rightClick(MouseEvent e) {
        double x =  calculatePosition(e.getX(), mainPanel.getOffSetX());
        double y =  calculatePosition(e.getY(), mainPanel.getOffSetY());

        for (Entity entity : mainPanel.getEntities()) {
            if(entity.getBounds().intersects(new Rectangle2D.Double(x, y,1,1))){
                mainPanel.getEntities().remove(entity);
                break;
            }
        }
    }

    private void leftClick(MouseEvent e){
        double x =  calculatePosition(e.getX(), mainPanel.getOffSetX());
        double y =  calculatePosition(e.getY(), mainPanel.getOffSetY());

        int cellSize = mainPanel.getCELL_SIZE();

        boolean canPlace = true;
        for (Entity entity : mainPanel.getEntities()) {
            if(entity.getBounds().intersects(new Rectangle2D.Double(x, y,1,1))){
                canPlace = false;
                break;
            }
        }
        if(canPlace){
            Entity newEntity = new Entity(x, y, cellSize, true);
            mainPanel.getEntities().add(newEntity);
        }
    }

    private double calculatePosition(double x, int offset){
        x /= mainPanel.getScale();
        x -= offset;

        if(x < 0) x -= 10 + (x % 10);
        else x -= x % 10;

        return x;
    }
}
