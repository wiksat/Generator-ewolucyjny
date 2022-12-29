package agh.gui;

import agh.oop.AbstractWorldMap;
import agh.simulation.SimulationParameters;
import agh.simulation.SimulationThreadController;
import agh.statistics.StatisticsModule;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SimulationStage extends Stage {
    private final StatisticsModule statisticsModule;
    private GuiWorldMap guiWorldMap;

    private SimulationThreadController sThreadController;

    public SimulationStage(int mapHeight, int mapWidth, int jungleHeight) {
        AbstractWorldMap aWorldMap;
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        this.setX(bounds.getMinX());
        statisticsModule = new StatisticsModule();
        aWorldMap = new AbstractWorldMap(mapWidth, mapHeight, jungleHeight,statisticsModule);


        guiWorldMap = new GuiWorldMap(aWorldMap, this);
        this.sThreadController = new SimulationThreadController(aWorldMap, guiWorldMap,statisticsModule);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

//        HBox.setHgrow(statisticsBox, Priority.ALWAYS);

        HBox layout = new HBox();
        VBox rightBox = new VBox();
        HBox simulationControls = new HBox();
        rightBox.getChildren().addAll(simulationControls);


        this.setOnCloseRequest(e -> this.sThreadController.stopSimulation());

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> startSimulationButton(startButton));

        simulationControls.getChildren().addAll(startButton);

        layout.getChildren().addAll(guiWorldMap, spacer, rightBox);

        this.setY(bounds.getMinY());
        this.setScene(new Scene(layout, bounds.getWidth() / 3, bounds.getHeight() / 2));
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