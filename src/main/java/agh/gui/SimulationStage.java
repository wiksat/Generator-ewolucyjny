package agh.gui;

import agh.oop.AbstractWorldMap;
import agh.simulation.SimulationParameters;
import agh.simulation.SimulationThreadController;
import agh.statistics.StatisticsModule;
import agh.statistics.StatisticsWriter;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;



public class SimulationStage extends Stage {

    private final SimulationThreadController sThreadController;

    public SimulationStage( int mapWidth,int mapHeight, int jungleHeight) throws Exception {
        AbstractWorldMap aWorldMap;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.setX(bounds.getMinX());
        StatisticsWriter statisticsWriter = new StatisticsWriter();
        StatisticsModule statisticsModule = new StatisticsModule(statisticsWriter);

        aWorldMap = new AbstractWorldMap(mapWidth, mapHeight, jungleHeight, statisticsModule);


        GuiWorldMap guiWorldMap = new GuiWorldMap(aWorldMap, this);
        GuiStatisticsModule guiStatisticsModule = new GuiStatisticsModule(statisticsModule, this);
        this.sThreadController = new SimulationThreadController(aWorldMap, guiWorldMap, statisticsModule, guiStatisticsModule);



        HBox layout = new HBox();
        VBox vBox = new VBox();
        HBox simulationControls = new HBox();
        vBox.getChildren().addAll(simulationControls);


        this.setOnCloseRequest(e -> this.sThreadController.stopSimulation());

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startSimulationButton(startButton));



        simulationControls.getChildren().addAll(startButton);

        layout.getChildren().addAll(guiWorldMap, guiStatisticsModule, vBox);

        int gridCellWidth = GuiParameters.gridCellWidth;
        int gridCellHeight = GuiParameters.gridCellHeight;
        if (SimulationParameters.mapWidth > 20 || SimulationParameters.mapHeight > 20) {
            gridCellWidth = GuiParameters.gridCellWidth / 2;
            gridCellHeight = GuiParameters.gridCellHeight / 2;
        }

        this.setY(bounds.getMinY());
        this.setScene(new Scene(
                layout,
                Math.min(bounds.getWidth(), gridCellWidth * (SimulationParameters.mapWidth + 2) + 250),
                Math.min(bounds.getHeight(), gridCellHeight * (SimulationParameters.mapHeight + 2))));
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