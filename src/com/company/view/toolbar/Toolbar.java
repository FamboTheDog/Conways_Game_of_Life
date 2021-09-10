package com.company.view.toolbar;

import com.company.algorithm.GameOfLife;
import com.company.keybinds.Start;
import com.company.listeners.AddCell;
import com.company.listeners.PanelMover;
import com.company.view.mainPanel.MainPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends JPanel implements ChangeListener {
    private final Start starter;
    private final Color background = Color.gray;

    @Getter private static JButton moveMap;
    @Getter private static JButton addCellsController;

    @Getter private static boolean drawing = true;

    public Toolbar(MainPanel mainPanel){
        this.setBackground(background);
        this.setBorder(BorderFactory.createLineBorder(Color.lightGray, 5));
        JPanel container = new JPanel();

        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");

        starter = new Start(mainPanel);
        start.addActionListener(e->{
            starter.actionPerformed(e);
            container.remove(start);
            container.add(stop);
            this.revalidate();
            this.repaint();
        });

        stop.addActionListener(e->{
            starter.getGameOfLife().setRunning(false);
            mainPanel.setEntities(new ArrayList<>());
            mainPanel.repaint();
            container.remove(stop);
            container.add(start);
            this.revalidate();
            this.repaint();
        });
        container.add(start);

        JPanel speedController = new JPanel();
        speedController.setLayout(new BorderLayout());

        JLabel gameSpeedLabel = new JLabel("Delay:", JLabel.CENTER);
        gameSpeedLabel.setForeground(Color.white);

        JSlider gameSpeed = new JSlider();
        gameSpeed.setBackground(background);
        gameSpeed.setMinimum(1);
        gameSpeed.setMaximum(500);
        gameSpeed.setValue(starter.getGameOfLife().getTimeout());
        gameSpeed.addChangeListener(this);

        speedController.add(gameSpeedLabel, BorderLayout.NORTH);
        speedController.add(gameSpeed, BorderLayout.SOUTH);
        this.add(speedController);

        this.add(container);

        PanelMover panelMover = new PanelMover(mainPanel);
        AddCell addCell = new AddCell(mainPanel);

        JPanel mouseListenerContainer = new JPanel();
        addCellsController = new JButton("Add cells");
        moveMap = new JButton("Move map ");
        addCellsController.addActionListener(e->{
            drawing = true;
            mainPanel.removeMouseMotionListener(panelMover);
            mainPanel.removeMouseListener(panelMover);
            mainPanel.addMouseMotionListener(addCell);
            mouseListenerContainer.remove(addCellsController);
            mouseListenerContainer.add(moveMap);
            this.revalidate();
            this.repaint();
        });
        moveMap.addActionListener(e->{
            drawing = false;
            mainPanel.removeMouseMotionListener(addCell);
            mainPanel.addMouseMotionListener(panelMover);
            mainPanel.addMouseListener(panelMover);
            mouseListenerContainer.remove(moveMap);
            mouseListenerContainer.add(addCellsController);
            this.revalidate();
            this.repaint();
        });
        mouseListenerContainer.add(moveMap);
        mainPanel.addMouseMotionListener(addCell);
        this.add(mouseListenerContainer);

        // paint every child's background with parent's background color
        // I wanted to call this recursively on components subcomponents, but it made some components look horrible
        for (Component component: this.getComponents()) component.setBackground(background);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        GameOfLife gol = starter.getGameOfLife();
        if(gol != null){
            gol.setRunning(false);
            gol.setChangingSpeed(true);
            JSlider source = (JSlider) e.getSource();
            gol.setTimeout(source.getValue());
            starter.setTimeout(source.getValue());
        }
    }
}
