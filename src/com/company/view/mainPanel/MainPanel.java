package com.company.view.mainPanel;

import com.company.entities.Entity;
import com.company.keybinds.Start;
import com.company.listeners.AddCell;
import com.company.listeners.PanelMover;
import com.company.listeners.WheelListener;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    @Getter private final int CELL_SIZE = 10;

    @Getter @Setter private int offSetX;
    @Getter @Setter private int offSetY;

    @Getter @Setter private ArrayList<Entity> entities = new ArrayList<>();

    @Getter @Setter private double scale = 1;

    public MainPanel(){
        offSetX = 0;
        offSetY = 0;

        this.addMouseWheelListener(new WheelListener(this));
        // different mouseListeners are added via Toolbar class.
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

        gd.fillRect(0, 0, this.getWidth(), this.getHeight()); // bg

        gd.scale(scale,scale);

        // grid pattern
        gd.setColor(Color.gray);
        for (int i = (offSetY % CELL_SIZE); i < this.getHeight() / scale; i+=CELL_SIZE) gd.drawLine(0, i, (int) (this.getWidth()  + (this.getWidth()  / scale)), i);      // horizontal lines
        for (int i = (offSetX % CELL_SIZE) - 2; i < this.getWidth()  / scale;  i+=CELL_SIZE)  gd.drawLine (i, 0, i, (int) (this.getHeight() + (this.getHeight() / scale))); // vertical lines

        // drawing entities
        gd.translate(offSetX, offSetY);
        gd.setColor(Color.white);
        entities.forEach(e-> gd.fill(new Rectangle2D.Double(e.getX() - 1, e.getY() + 1, CELL_SIZE - 1, CELL_SIZE - 1)));
    }
}
