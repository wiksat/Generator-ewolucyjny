package agh.gui;


import agh.statistics.StatisticsModule;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GuiStatisticsModule extends VBox {

    private StatisticsModule statisticsModule;
    private GridPane statisticGrid = new GridPane();
    private Stage stage;

    public GuiStatisticsModule (StatisticsModule statisticsModule ,Stage stage) {
        this.statisticsModule = statisticsModule;
        this.stage = stage;
    }

    public void createGrid() {

        statisticGrid.getChildren().clear();
        statisticGrid.addRow(1, new Label("Number of animals alive"),
                new Label(Integer.toString(statisticsModule.getAmountOfAnimals())));

    }
}
