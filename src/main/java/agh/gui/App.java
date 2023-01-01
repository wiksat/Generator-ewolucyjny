package agh.gui;

import agh.simulation.SimulationParameters;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App extends Application {

    SimulationStage WorldMapSimulationStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene firstScene = createFirstScene();

        primaryStage.setScene(firstScene);
        primaryStage.show();
    }

    private Scene createFirstScene() throws IOException {


        String[] cars = {"Volvo", "BMW", "Ford", "Mazda"};


//        OptionReader.save(cars);

        GridPane grid = new GridPane();

        var mapHeightNTBox = new NumberTextField(GuiParameters.mapHeight);
        grid.addRow(1, new Label("Wysokosc mapy: "), mapHeightNTBox);

        var mapWidthNTBox = new NumberTextField(GuiParameters.mapWidth);
        grid.addRow(2, new Label("Szerokosc mapy: "), mapWidthNTBox);

        var jungleHightNTBox = new NumberTextField(GuiParameters.jungleHight);
        grid.addRow(3, new Label("Wysokosc pasa rownikowego: "), jungleHightNTBox);

        var mapVariantComboBox = new ComboBox<>();
        mapVariantComboBox.getItems().addAll("kula ziemska", "magiczny portal");
        mapVariantComboBox.getSelectionModel().selectFirst();
        grid.addRow(4, new Label("Wybierz wariant mapy: "), mapVariantComboBox);

        var startNumberOfPlantsNTBox = new NumberTextField(GuiParameters.startNumberOfPlants);
        grid.addRow(5, new Label("Startowa liczba roslin: "), startNumberOfPlantsNTBox);

        var plantEnergyNTBox = new NumberTextField(GuiParameters.plantEnergy);
        grid.addRow(6, new Label("Energia zapewniana przez zjedzenie jednej rosliny: "), plantEnergyNTBox);

        var numberOfNewPlantNTBox = new NumberTextField(GuiParameters.numberOfNewPlant);
        grid.addRow(7, new Label("Liczba roslin wyrastajaca kazdego dnia: "), numberOfNewPlantNTBox);

        var plantGrowthVariantComboBox = new ComboBox<>();
        plantGrowthVariantComboBox.getItems().addAll("zalesione rowniki", "toksyczne trupt");
        plantGrowthVariantComboBox.getSelectionModel().selectFirst();
        grid.addRow(8, new Label("Wybierz wariant wzrostu roslin: "), plantGrowthVariantComboBox);

        var startNumberOfAnimalsNTBox = new NumberTextField(GuiParameters.startNumberOfAnimals);
        grid.addRow(9, new Label("Startowa liczba zwierzakow: "), startNumberOfAnimalsNTBox);

        var startAnimalEnergyNTBox = new NumberTextField(GuiParameters.startAnimalEnergy);
        grid.addRow(10, new Label("Startowa energia zwierzakow: "), startAnimalEnergyNTBox);

        var animalEnergyToReproduceNTBox = new NumberTextField(GuiParameters.animalEnergyToReproduce);
        grid.addRow(11, new Label("Energia konieczna, by uznac zwierzaka za najedzonego: "), animalEnergyToReproduceNTBox);

        var animalEnergySpendForPropagationNTBox = new NumberTextField(GuiParameters.animalEnergySpendForPropagation);
        grid.addRow(12, new Label("Energia rodzicow zuzywana by stworzyc potomka: "), animalEnergySpendForPropagationNTBox);

        var minNumberOfMutationsNTBox = new NumberTextField(GuiParameters.minNumberOfMutations);
        grid.addRow(13, new Label("Minimalna liczba mutacji u potomkow: "), minNumberOfMutationsNTBox);

        var maxNumberOfMutationsNTBox = new NumberTextField(GuiParameters.maxNumberOfMutations);
        grid.addRow(14, new Label("Maksymalna liczba mutacji u potomkow: "), maxNumberOfMutationsNTBox);

        var mutationVariantComboBox = new ComboBox<>();
        mutationVariantComboBox.getItems().addAll("pelna losowosc", "lekka korekta");
        mutationVariantComboBox.getSelectionModel().selectFirst();
        grid.addRow(15, new Label("Wybierz wariant mutacji: "), mutationVariantComboBox);

        var lengthOfAnimalGenomeNTBox = new NumberTextField(GuiParameters.lengthOfAnimalGenome);
        grid.addRow(16, new Label("Dlugosc genomu zwierzakow: "), lengthOfAnimalGenomeNTBox);

        var behaviourVariantComboBox = new ComboBox<>();
        behaviourVariantComboBox.getItems().addAll("pelna predestynacja", "nieco szalenstwa");
        behaviourVariantComboBox.getSelectionModel().selectFirst();
        grid.addRow(17, new Label("Wybierz wariant zachowania zwierzakow"), behaviourVariantComboBox);

        grid.addRow(18, new Label());

        var simulationMoveDelayNTBox = new NumberTextField(GuiParameters.simulationMoveDelay);
        grid.addRow(19, new Label("Dlugosc dnia w milisekundach: "), simulationMoveDelayNTBox);

        var saveStatisticsComboBox = new ComboBox<>();
        saveStatisticsComboBox.getItems().addAll("Tak", "Nie");
        saveStatisticsComboBox.getSelectionModel().selectFirst();
        grid.addRow(20, new Label("Zapisuj statystyki do pliku"), saveStatisticsComboBox);

        var loadParametersComboBox = new ComboBox<>();
        loadParametersComboBox.getItems().addAll("Tak", "Nie");
        loadParametersComboBox.getSelectionModel().selectLast();
        grid.addRow(21, new Label("Wczytaj konfiguracje z pliku"), loadParametersComboBox);

        grid.addRow(22, new Label());
        Button startSimulation = new Button("Wlacz symulacje");
        grid.addRow(23, startSimulation);

        startSimulation.setOnAction(e -> {

            SimulationParameters.mapWidth = mapWidthNTBox.getNumber();
            SimulationParameters.mapHeight = mapHeightNTBox.getNumber();
            SimulationParameters.jungleHeight = jungleHightNTBox.getNumber();
            SimulationParameters.startNumberOfPlants = startNumberOfPlantsNTBox.getNumber();
            SimulationParameters.plantEnergy = plantEnergyNTBox.getNumber();
            SimulationParameters.numberOfNewPlant = numberOfNewPlantNTBox.getNumber();
            SimulationParameters.startNumberOfAnimals = startNumberOfAnimalsNTBox.getNumber();
            SimulationParameters.startAnimalEnergy = startAnimalEnergyNTBox.getNumber();
            SimulationParameters.minLifeEnergyToReproduce = animalEnergyToReproduceNTBox.getNumber();
            SimulationParameters.lifeEnergySpendForPropagation = animalEnergyToReproduceNTBox.getPrefColumnCount();
            SimulationParameters.minNumberOfMutations = minNumberOfMutationsNTBox.getNumber();
            SimulationParameters.maxNumberOfMutations = maxNumberOfMutationsNTBox.getNumber();
            SimulationParameters.lengthOfAnimalGenome = lengthOfAnimalGenomeNTBox.getNumber();
            SimulationParameters.simulationMoveDelay = simulationMoveDelayNTBox.getNumber();
            if (mapVariantComboBox.getValue().toString().equals("kula ziemska")) {
                SimulationParameters.mapVariant = false;
            } else {
                SimulationParameters.mapVariant = true;
            }
            if (plantGrowthVariantComboBox.getValue().toString().equals("zalesione rowniki")) {
                SimulationParameters.plantGrowthVariant = false;
            } else {
                SimulationParameters.plantGrowthVariant = true;
            }
            if (mutationVariantComboBox.getValue().toString().equals("pelna losowosc")) {
                SimulationParameters.mutationVariant = false;
            } else {
                SimulationParameters.mutationVariant = true;
            }
            if (behaviourVariantComboBox.getValue().toString().equals("pelna predestynacja")) {
                SimulationParameters.behaviourVariant = false;
            } else {
                SimulationParameters.behaviourVariant = true;
            }

            if (saveStatisticsComboBox.getValue().toString().equals("Tak")) {
                SimulationParameters.withSaveStatistics = true;
            } else {
                SimulationParameters.withSaveStatistics = false;
            }

            if (loadParametersComboBox.getValue().toString().equals("Tak")) {
                try {
                    OptionReader.read();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                List<String[]> options = OptionReader.getOptions();

                String[] temp = options.get(0);
                SimulationParameters.mapHeight = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(1);
                SimulationParameters.mapWidth = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(2);
                SimulationParameters.jungleHeight = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(3);
                if (temp[1].equals("true")) {
                    SimulationParameters.mapVariant = true;
                } else {
                    SimulationParameters.mapVariant = false;
                }
                temp = options.get(4);
                SimulationParameters.startNumberOfPlants = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(5);
                SimulationParameters.plantEnergy = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(6);
                SimulationParameters.numberOfNewPlant = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(7);
                if (temp[1].equals("true")) {
                    SimulationParameters.plantGrowthVariant = true;
                } else {
                    SimulationParameters.plantGrowthVariant = false;
                }
                temp = options.get(8);
                SimulationParameters.startNumberOfAnimals = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(9);
                SimulationParameters.startAnimalEnergy = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(10);
                SimulationParameters.minLifeEnergyToReproduce = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(11);
                SimulationParameters.lifeEnergySpendForPropagation = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(12);
                SimulationParameters.minNumberOfMutations = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(13);
                SimulationParameters.maxNumberOfMutations = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(14);
                if (temp[1].equals("true")) {
                    SimulationParameters.mutationVariant = true;
                } else {
                    SimulationParameters.mutationVariant = false;
                }
                temp = options.get(15);
                SimulationParameters.lengthOfAnimalGenome = Integer.parseInt(String.valueOf(temp[1]));
                temp = options.get(16);
                if (temp[1].equals("true")) {
                    SimulationParameters.behaviourVariant = true;
                } else {
                    SimulationParameters.behaviourVariant = false;
                }
            }

            WorldMapSimulationStage = new SimulationStage(
                    SimulationParameters.mapWidth,
                    SimulationParameters.mapHeight,
                    SimulationParameters.jungleHeight);
            WorldMapSimulationStage.setTitle("Symulacja swiata zwierzat");

        });

//        grid.getChildren().add(startSimulation);
        return new Scene(grid, 500, 700);
    }


}
