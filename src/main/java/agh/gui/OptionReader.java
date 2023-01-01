package agh.gui;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class OptionReader {
    private static final String CSV_FILE = "src/main/resources/config.csv";

    private static List<String[]> options;

    public static void read() throws FileNotFoundException {
        options = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));

        List<String[]> options = new LinkedList<>();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            options.add(line.split("="));
        }
    }

    public static List<String[]> getOptions() {
        return options;
    }

    public static void save(String[] config) throws IOException {

        String[] titles = {"height","width","height_of_equator","map_variant",
                "start_amount_of_plants",
                "energy_of_one_plant",
                "amount_of_plants_every_day",
                "grow_variant",
                "start_amount_of_animals",
                "start_energy_of_animals",
                "energy_required_to_okay_animal",
                "energy_of_reproduce",
                "min_amount_of_mutation",
                "max_amount_of_mutation",
                "mutation_variant",
                "length_of_genotype",
                "behavoiur_variant"};

        new FileWriter(CSV_FILE, false).close();
        FileWriter writer = new FileWriter(CSV_FILE, true);

        for (int i = 0; i < titles.length; i++) {
            String line = titles[i];
            line+="=";
            if (i>=config.length){
                line+="null";
            }else{
                line+=config[i];
            }

            line += "\n";
            writer.write(line);
        }



        writer.flush();
        writer.close();
    }

//    public static String[] find(String name) throws FileNotFoundException {
//        List<String[]> configs = read();
//        for (String[] config : configs) {
//            if (config[0].equals(name)) {
//                return Arrays.copyOfRange(config, 1, config.length);
//            }
//        }
//        return null;
//    }
//
//    public static String[] names() throws FileNotFoundException {
//        List<String[]> configs = read();
//        String[] list = new String[configs.size()-1];
//        for (int i = 0; i < list.length; i++) {
//            list[i] = configs.get(i + 1)[0];
//        }
//        return list;
//    }

}
