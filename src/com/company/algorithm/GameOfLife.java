package com.company.algorithm;

import com.company.view.mainPanel.MainPanel;
import com.company.entities.Entity;
import com.company.view.toolbar.Toolbar;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameOfLife implements Runnable{

    public GameOfLife(MainPanel mainPanel, int timeout){
        this.mainPanel = mainPanel;
        this.timeout = timeout;
    }

    private final MainPanel mainPanel;

    private ArrayList<Entity> entities;
    private ArrayList<Entity> entitiesCopy;
    private ArrayList<Entity> addedEntities;

    @Getter @Setter boolean isRunning = true;
    @Getter @Setter boolean changingSpeed = false;
    @Getter @Setter int timeout;

    @Override
    public void run() {
        entities      = new ArrayList<>(mainPanel.getEntities());
        entitiesCopy  = new ArrayList<>();
        addedEntities = new ArrayList<>();
        Toolbar.getMoveMap().doClick();
        Toolbar.getAddCellsController().setEnabled(false);
        startScheduler(timeout);
    }

    private void startScheduler(int delay){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(()->{
            if(isRunning){
                entities.forEach(this::checkAliveTiles);
                entities = new ArrayList<>(entitiesCopy);

                mainPanel.setEntities(new ArrayList<>(entitiesCopy));

                entitiesCopy.clear();
                addedEntities.clear();

                mainPanel.repaint();
            }else{
                if(!changingSpeed){
                    executor.shutdown();
                    Toolbar.getAddCellsController().setEnabled(true);
                }else{
                    executor.shutdown();
                    changingSpeed = false;
                    isRunning = true;
                    startScheduler(timeout);
                }
            }
        }, 0, delay, TimeUnit.MILLISECONDS);
    }

    private void checkAliveTiles(Entity entity){
        int cellSize = mainPanel.getCELL_SIZE();

        int boundX = (int) (entity.getX() - cellSize);
        int boundY = (int) (entity.getY() - cellSize);
        Rectangle neighbourCollisionBox = new Rectangle(boundX, boundY, 3*cellSize, 3*cellSize);
        Entity[][] neighbours = new Entity[3][3];

        int aliveNeighbours = -1; // the checked entity will always be added to aliveNeighbours
        for (Entity en : entities) {
            if(en.getBounds().intersects(neighbourCollisionBox)) {
                int entityX = evaluatePosition((int) en.getX(),(int) entity.getX());
                int entityY = evaluatePosition((int) en.getY(),(int) entity.getY());
                neighbours[entityY][entityX] = en;
                aliveNeighbours++;
                if (aliveNeighbours > 3) break;
            }
        }

        checkDeadTiles(neighbours, boundX, boundY, cellSize);
        if(aliveNeighbours < 2 || aliveNeighbours > 3){
            entity.setAlive(false);
        }else{
            entitiesCopy.add(entity);
        }

    }

    private void checkDeadTiles(Entity[][] neighbours, int boundX, int boundY, int cellSize){
        for (int i = 0; i < neighbours.length; i++) {
            OUTER:
            for (int j = 0; j < neighbours[0].length; j++) {
                if(neighbours[i][j] == null) neighbours[i][j] = new Entity(boundX + (j * cellSize), boundY + (i * cellSize), cellSize, false);
                if(!neighbours[i][j].isAlive()){
                    int ex = (int) neighbours[i][j].getX() - cellSize;
                    int ey = (int) neighbours[i][j].getY() - cellSize;
                    Rectangle neighbourCollisionBox = new Rectangle(ex, ey, 3*cellSize, 3*cellSize);

                    int aliveNeighbours = 0;
                    for (Entity en : entities) {
                        if(en.getBounds().intersects(neighbourCollisionBox)) {
                            aliveNeighbours++;
                            if (aliveNeighbours > 3) continue OUTER;
                        }
                    }
                    if(aliveNeighbours == 3){
                        Entity newEntity = new Entity(neighbours[i][j].getX(), neighbours[i][j].getY(), cellSize, true);
                        for (Entity e: addedEntities) {
                            if(e.getBounds().intersects(newEntity.getBounds())){
                                continue OUTER;
                            }
                        }

                        entitiesCopy.add(newEntity);
                        addedEntities.add(newEntity);
                    }
                }
            }
        }
    }

    private int evaluatePosition(int toCheck, int checkWith){
        int pos;
        if(toCheck < checkWith) {
            pos = 0;
        }else if(toCheck == checkWith){
            pos = 1;
        }else{
            pos = 2;
        }
        return pos;
    }
}
