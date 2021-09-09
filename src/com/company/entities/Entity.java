package com.company.entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Entity {

    @Getter @Setter
    private Entity[][] neighbours;

    public Entity(double x, double y, int cellSize, boolean alive){
        this.x = x;
        this.y = y;
        bounds = new Rectangle2D.Double(x,y,cellSize,cellSize);
        this.alive = alive;
    }

    @Getter private Rectangle2D.Double bounds;

    @Getter @Setter private boolean alive;

    @Getter private double y;
    @Getter private double x;

}
