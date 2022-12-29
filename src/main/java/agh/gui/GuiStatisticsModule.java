package agh.gui;


import agh.oop.AbstractWorldMap;
import agh.statistics.StatisticsModule;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiStatisticsModule extends VBox {

    private StatisticsModule statisticsModule;
    private GridPane statisticGrid = new GridPane();
    private Stage stage;

    public GuiStatisticsModule (StatisticsModule statisticsModule ,Stage stage) {
        this.statisticsModule = statisticsModule;
        this.stage = stage;
        this.statisticGrid.setGridLinesVisible(true);
        statisticGrid.setBackground(new Background(new BackgroundFill(Color.rgb(115, 180, 110), null, null)));
        getChildren().addAll(statisticGrid);
        createGrid();
    }

    public void createGrid() {

        this.statisticGrid.getChildren().clear();
        statisticGrid.getRowConstraints().clear();
        statisticGrid.getColumnConstraints().clear();

        Label xyLabel = new Label("y\\x");
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        this.statisticGrid.getColumnConstraints().add(new ColumnConstraints(200));
        this.statisticGrid.getRowConstraints().add(new RowConstraints(50));
        this.statisticGrid.add(xyLabel, 0, 0, 1, 1);

        this.statisticGrid.addRow(1, new Label("Number of animals alive"),
                new Label(Integer.toString(statisticsModule.getAmountOfAnimals())));

    }

    public void refresh() {
        Platform.runLater(this::createGrid);
    }
}
