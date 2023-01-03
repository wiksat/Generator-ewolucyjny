package agh.gui;

import agh.oop.AbstractWorldMapElement;
import agh.oop.Animal;
import agh.oop.Grass;
import agh.simulation.SimulationParameters;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;


public class GuiWorldMapElement extends StackPane {

    protected AbstractWorldMapElement worldMapElement;
    private int gridCellWidth = 40;
    private int gridCellHeight = 40;

    public GuiWorldMapElement(AbstractWorldMapElement worldMapElement, int maxX, int  maxY) {
        super();
        this.worldMapElement = worldMapElement;
        Image image = new WritableImage(32, 32);
        try {
            if (worldMapElement instanceof Animal) {
                image = ImageHandler.getImage(worldMapElement.getImageSRC());
            }
            else if ( worldMapElement instanceof Grass) {
                image = ImageHandler.getImage(worldMapElement.getImageSRC());
            }
            else {
                System.out.println("GUIWorldMapElement" + worldMapElement.getPosition());
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found Exception from GUIWorldMapElement");
        }
        catch (NullPointerException e) {
            System.out.println("Null Pointer Exception from GUIWorldMapElement");
        }

        if (maxX > 20 || maxY > 20) {
            gridCellWidth = gridCellWidth / 2;
            gridCellHeight = gridCellWidth / 2;
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(gridCellWidth);
        imageView.setFitHeight(gridCellHeight);

        this.getChildren().add(imageView);
        this.setAlignment(Pos.CENTER);
        if (this.worldMapElement instanceof Animal animal) {
            Label energyLabel = new Label(Integer.toString(animal.getLifeEnergy()));
            energyLabel.setTextFill(Color.WHITE);
            this.getChildren().add(energyLabel);
        }
    }

}
