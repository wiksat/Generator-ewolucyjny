package agh.gui;

import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ImageHandler {
    public static Map<String, Image> images = new HashMap<>();

    public static Image getImage(String path) throws FileNotFoundException {
        if (images.containsKey(path)) {
            return images.get(path);
        }
        Image image = new Image(new FileInputStream(path));
        images.put(path, image);
        return images.get(path);
    }
}
