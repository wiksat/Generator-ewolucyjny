package agh.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene firstScene = createFirstScene();

        primaryStage.setScene(firstScene);
        primaryStage.show();
    }

    private Scene createFirstScene() {

        GridPane grid = new GridPane();

        var mapHightNTBox = new NumberTextField(GuiParameters.mapHight);
        grid.addRow(1, new Label("Wysokość mapy: "), mapHightNTBox);

        var mapWidthNTBox = new NumberTextField(GuiParameters.mapWidth);
        grid.addRow(2, new Label("Szerokość mapy: "), mapWidthNTBox);

        var mapVariantComboBox = new ComboBox<>();
        mapVariantComboBox.getItems().addAll("kula ziemska", "magiczny portal");
//        mapVariantComboBox.setOnAction(e -> {
//            if (mapVariantComboBox.getValue() == "kula ziemska") {
//                GuiParameters.mapVariant = false;
//            }
//            else {
//                GuiParameters.mapVariant = true;
//            }
//        });
        grid.addRow(3, new Label("Wybierz wariant mapy: "), mapVariantComboBox);

        var startNumberOfPlantsNTBox = new NumberTextField(GuiParameters.startNumberOfPlants);
        grid.addRow(4, new Label("Startowa liczba roślin: "), startNumberOfPlantsNTBox);

        var plantEnergyNTBox = new NumberTextField(GuiParameters.plantEnergy);
        grid.addRow(5, new Label("Energia zapewniana przez zjedzenie jednej rośliny: "), plantEnergyNTBox);

        var numberOfNewPlantNTBox = new NumberTextField(GuiParameters.numberOfNewPlant);
        grid.addRow(6, new Label("Liczba roślin wyrastająca każdego dnia: "), numberOfNewPlantNTBox);

        var plantGrowthVariantComboBox = new ComboBox<>();
        plantGrowthVariantComboBox.getItems().addAll("zalesione równiki", "toksyczne trupt");
        grid.addRow(7, new Label("Wybierz wariant wzrostu roślin: "), plantGrowthVariantComboBox);

        var startNumberOfAnimalsNTBox = new NumberTextField(GuiParameters.startNumberOfAnimals);
        grid.addRow(8, new Label("Startowa liczba zwierzaków: "), startNumberOfAnimalsNTBox);

        var startAnimalEnergyNTBox = new NumberTextField(GuiParameters.startAnimalEnergy);
        grid.addRow(9, new Label("Startowa energia zwierzaków: "), startAnimalEnergyNTBox);

        var animalEnergyToBeFullNTBox = new NumberTextField(GuiParameters.animalEnergyToBeFull);
        grid.addRow(10, new Label("Energia konieczna, by uznać zwierzaka za najedzonego: "), animalEnergyToBeFullNTBox);

        var animalEnergyNeededToPropagationNTBox = new NumberTextField(GuiParameters.animalEnergyNeededToPropagation);
        grid.addRow(11, new Label("Energia rodziców zużywana by stworzyć potomka,: "), animalEnergyNeededToPropagationNTBox);

        var minNumberOfMutationsNTBox = new NumberTextField(GuiParameters.minNumberOfMutations);
        grid.addRow(12, new Label("Minimalna liczba mutacji u potomków: "), minNumberOfMutationsNTBox);

        var maxNumberOfMutationsNTBox = new NumberTextField(GuiParameters.maxNumberOfMutations);
        grid.addRow(13, new Label("Maksymalna liczba mutacji u potomków: "), maxNumberOfMutationsNTBox);

        var mutationVariantComboBox = new ComboBox<>();
        mutationVariantComboBox.getItems().addAll("pełna losowość", "lekka korekta");
        grid.addRow(14, new Label("Wybierz wariant mutacji: "), mutationVariantComboBox);

        var lengthOfAnimalGenomeNTBox = new NumberTextField(GuiParameters.lengthOfAnimalGenome);
        grid.addRow(15, new Label("Długość genomu zwierzaków: "), lengthOfAnimalGenomeNTBox);

        var behaviourVariantComboBox = new ComboBox<>();
        behaviourVariantComboBox.getItems().addAll("pełna predestynacja", "nieco szaleństwa");
        grid.addRow(16, new Label("Wybierz wariant zachowania zwierzaków"), behaviourVariantComboBox);


        Button startSimulationButton = new Button("Włącz symulacje");
        startSimulationButton.setOnAction(e -> startSimulation());
        grid.addRow(17, startSimulationButton);

        return new Scene(grid, 500, 700);
    }

    private void startSimulation() {

    }
}
