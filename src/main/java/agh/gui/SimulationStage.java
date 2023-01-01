package agh.gui;

import agh.oop.AbstractWorldMap;
import agh.simulation.SimulationParameters;
import agh.simulation.SimulationThreadController;
import agh.statistics.StatisticsModule;
import agh.statistics.StatisticsWriter;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Locale;

public class SimulationStage extends Stage {
    private final StatisticsModule statisticsModule;
    private final StatisticsWriter statisticsWriter;
    private GuiWorldMap guiWorldMap;

    private SimulationThreadController sThreadController;

    public SimulationStage( int mapWidth,int mapHeight, int jungleHeight) throws Exception {
        AbstractWorldMap aWorldMap;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.setX(bounds.getMinX());
        statisticsWriter=new StatisticsWriter();
        statisticsModule = new StatisticsModule(statisticsWriter);

        aWorldMap = new AbstractWorldMap(mapWidth, mapHeight, jungleHeight, statisticsModule);


        guiWorldMap = new GuiWorldMap(aWorldMap, this);
        GuiStatisticsModule guiStatisticsModule = new GuiStatisticsModule(statisticsModule, this);
        this.sThreadController = new SimulationThreadController(aWorldMap, guiWorldMap, statisticsModule, guiStatisticsModule);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

//        HBox.setHgrow(statisticsModule, Priority.ALWAYS);


        HBox layout = new HBox();
        VBox rightBox = new VBox();
        HBox simulationControls = new HBox();
        rightBox.getChildren().addAll(simulationControls);


        this.setOnCloseRequest(e -> this.sThreadController.stopSimulation());

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startSimulationButton(startButton));





        simulationControls.getChildren().addAll(startButton);

        layout.getChildren().addAll(guiWorldMap, spacer, guiStatisticsModule, rightBox);

        this.setY(bounds.getMinY());
        this.setScene(new Scene(
                layout,
                Math.min(bounds.getWidth(), GuiParameters.gridCellWidth * (SimulationParameters.mapWidth + 2) + 250),
                Math.min(bounds.getHeight(), GuiParameters.gridCellHeight * (SimulationParameters.mapHeight + 2))));
        this.show();
    }

    private void startSimulationButton(Button button) {
        sThreadController.startSimulation();
        button.setText("Stop");
        button.setOnAction(e -> stopSimulationButton(button));
    }


    private void stopSimulationButton(Button button) {
        sThreadController.stopSimulation();
        button.setText("Start");
        button.setOnAction(e -> startSimulationButton(button));
    }
}