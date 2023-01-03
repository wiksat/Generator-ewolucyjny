package agh.gui;

import agh.oop.AbstractWorldMap;
import agh.oop.AbstractWorldMapElement;
import agh.oop.Animal;
import agh.oop.Vector2d;
import agh.simulation.SimulationParameters;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GuiWorldMap extends VBox {

    AbstractWorldMap aWorldMap;
    private GridPane gridMap = new GridPane();
    private int gridCellWidth;
    private int gridCellHeight;
    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    private Stage stage;

    public GuiWorldMap(AbstractWorldMap aWorldMap, Stage stage) {
        this.aWorldMap = aWorldMap;
        this.stage = stage;

        gridCellWidth = GuiParameters.gridCellWidth;
        gridCellHeight = GuiParameters.gridCellHeight;

        minX = 0;
        minY = 0;
        maxX = SimulationParameters.mapWidth;
        maxY = SimulationParameters.mapHeight;

        if (maxX > 20 || maxY > 20) {
            gridCellWidth = GuiParameters.gridCellWidth / 2;
            gridCellHeight = GuiParameters.gridCellHeight / 2;
        }
        this.gridMap.setGridLinesVisible(true);
        gridMap.setBackground(new Background(new BackgroundFill(Color.rgb(115, 180, 110), null, null)));
        getChildren().addAll(gridMap);
        createGrid();
    }

    public void createGrid() {
        gridMap.getRowConstraints().clear();
        gridMap.getColumnConstraints().clear();





        Label xyLabel = new Label("y\\x");
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        this.gridMap.getColumnConstraints().add(new ColumnConstraints(gridCellWidth));
        this.gridMap.getRowConstraints().add(new RowConstraints(gridCellHeight));

        this.gridMap.add(xyLabel, 0, 0, 1, 1);


        for (int i = minX; i < maxX; i++) {
            Label label = new Label(Integer.toString(i));
            this.gridMap.add(label, i + 1, 0, 1, 1);
            this.gridMap.getColumnConstraints().add(new ColumnConstraints(gridCellWidth));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = minY; i < maxY; i++) {
            Label label = new Label(Integer.toString(i));
            this.gridMap.add(label, 0, maxY - i, 1, 1);
            this.gridMap.getRowConstraints().add(new RowConstraints(gridCellHeight));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                Vector2d position = new Vector2d(i, j);
                StackPane stackPane = new StackPane();
                stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(5, 155, 25), null, null)));


                if (this.aWorldMap.getEquatorBoundary().isInside(position)) {
                    stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(15, 95, 20), null, null)));
                }

                if (aWorldMap.isOccupied(position)) {
                    AbstractWorldMapElement worldMapElement = aWorldMap.getTopWorldMapElementAt(position);
                    GuiWorldMapElement element = new GuiWorldMapElement(worldMapElement, maxX, maxY);
                    stackPane.getChildren().add(element);
                }

                GridPane.setHalignment(stackPane, HPos.CENTER);
                this.gridMap.add(stackPane, i + 1 , maxY - j, 1, 1);
            }
        }
    }
    public void refresh(AbstractWorldMap map) {
        Platform.runLater(this::createGrid);
    }
}
