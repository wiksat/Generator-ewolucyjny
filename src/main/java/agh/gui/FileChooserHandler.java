package agh.gui;

import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class FileChooserHandler extends Stage {

    public FileChooserHandler() {
        Screen screen = Screen.getPrimary();
    }
    public static class InnerClass {
        public void findPlace(FileChooserHandler Stage) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(Stage);

            if (file != null) {
                if (!file.getName().endsWith(".csv")) {
                    file = new File(file.getAbsolutePath() + ".csv");
                }
                OptionReader.CSV_FILE = file.toString();
            }
        }
    }
}
