package agh.gui;

import agh.oop.AbstractWorldMap;
import agh.oop.AbstractWorldMapElement;
import agh.oop.Animal;
import agh.oop.Vector2d;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import static javafx.geometry.VPos.*;

public class GuiWorldMap extends VBox {

    AbstractWorldMap aWorldMap;
    private GridPane gridMap = new GridPane();

    private Stage stage;

    public GuiWorldMap(AbstractWorldMap aWorldMap, Stage stage) {
        this.aWorldMap = aWorldMap;
        this.stage = stage;
        this.gridMap.setGridLinesVisible(true);
        gridMap.setBackground(new Background(new BackgroundFill(Color.rgb(115, 180, 110), null, null)));
        getChildren().addAll(gridMap);
        createGrid();
    }

    public void createGrid() {
        gridMap.getRowConstraints().clear();
        gridMap.getColumnConstraints().clear();

        int minX = 0;
        int minY = 0;
        int maxX = GuiParameters.mapWidth;
        int maxY = GuiParameters.mapHeight;



//        this.gridMap.getRowConstraints().add(new RowConstraints(GuiParameters.gridCellHeight));
//        this.gridMap.getColumnConstraints().add(new ColumnConstraints(GuiParameters.gridCellWidth));


        Label xyLabel = new Label("y\\x");
        GridPane.setHalignment(xyLabel, HPos.CENTER);
        this.gridMap.getColumnConstraints().add(new ColumnConstraints(GuiParameters.gridCellWidth));
        this.gridMap.getRowConstraints().add(new RowConstraints(GuiParameters.gridCellHeight));
        this.gridMap.add(xyLabel, 0, 0, 1, 1);


        for (int i = minX; i < maxX; i++) {
            Label label = new Label(Integer.toString(i));
            this.gridMap.add(label, i + 1, 0, 1, 1);
            this.gridMap.getColumnConstraints().add(new ColumnConstraints(GuiParameters.gridCellWidth));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = minY; i < maxY; i++) {
            Label label = new Label(Integer.toString(i));
            this.gridMap.add(label, 0, maxY - i, 1, 1);
            this.gridMap.getRowConstraints().add(new RowConstraints(GuiParameters.gridCellHeight));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                Vector2d position = new Vector2d(i, j);
                StackPane stackPane = new StackPane();
                stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(5, 155, 25), null, null)));
//                if równik

//                if isOccupied

                if (this.aWorldMap.getJungleBoundary().isInside(position)) {
                    stackPane.setBackground(new Background(new BackgroundFill(Color.rgb(15, 95, 20), null, null)));
                }

                if (aWorldMap.isOccupied(position)) {

                    if (aWorldMap.getTopWorldMapElementAt(position) instanceof Animal){
                        System.out.print(((Animal) aWorldMap.getTopWorldMapElementAt(position)).getLifeEnergy() + "__");
                        System.out.print(((Animal) aWorldMap.getTopWorldMapElementAt(position)).getStatus() + "  ");
                        System.out.print(((Animal) aWorldMap.getTopWorldMapElementAt(position)).getUniqueID() + "  ");
                        System.out.println(aWorldMap.getTopWorldMapElementAt(position).toString() + position.toString());
                    }
                    else {
//                        System.out.println(aWorldMap.getTopWorldMapElementAt(position).toString() + position.toString());
                    }

                    AbstractWorldMapElement worldMapElement = aWorldMap.getTopWorldMapElementAt(position);
                    GuiWorldMapElement element = new GuiWorldMapElement(worldMapElement);
//                    element.addGuiWorldMapElementClickObservers((IGuiWorldMapElementClickObserver) parentStage);
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
