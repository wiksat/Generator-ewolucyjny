package agh.gui;


import agh.oop.AbstractWorldMap;
import agh.simulation.SimulationParameters;
import agh.statistics.StatisticsModule;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Locale;

public class GuiStatisticsModule extends VBox {

    private StatisticsModule statisticsModule;
    private GridPane statisticGrid = new GridPane();
    private Stage stage;

    public GuiStatisticsModule (StatisticsModule statisticsModule ,Stage stage) {
        this.statisticsModule = statisticsModule;
        this.stage = stage;
        this.statisticGrid.setGridLinesVisible(true);
        statisticGrid.setBackground(new Background(new BackgroundFill(Color.rgb(125, 210, 235), null, null)));
        getChildren().addAll(statisticGrid);
        createGrid();
    }

    public void createGrid() {

        this.statisticGrid.getChildren().clear();
        statisticGrid.getRowConstraints().clear();
        statisticGrid.getColumnConstraints().clear();

        this.statisticGrid.addRow(1, new Label("Number of animals alive: "),
                new Label(Integer.toString(statisticsModule.getAmountOfAnimals())));
        this.statisticGrid.addRow(2, new Label("Number of grasses: "),
                new Label(Integer.toString(statisticsModule.getAmountOfGrasses())));
        this.statisticGrid.addRow(3, new Label("Number of free places: "),
                new Label(Integer.toString(SimulationParameters.mapHeight * SimulationParameters.mapWidth -
                        statisticsModule.getAmountOfFreePlaces())));
        this.statisticGrid.addRow(4, new Label("Average energy of alive animals: "),
                new Label(String.format(Locale.ENGLISH, "%.2f", statisticsModule.getAverageEnergyLifeForAlive())));
        this.statisticGrid.addRow(5, new Label("Average life span of dead animals: "),
                new Label(String.format(Locale.ENGLISH, "%.2f", statisticsModule.getAverageAgeForDead())));
    }

    public void refresh() {
        Platform.runLater(this::createGrid);
    }
}
