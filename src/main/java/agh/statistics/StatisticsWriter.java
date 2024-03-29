package agh.statistics;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class StatisticsWriter {
    private static String name_of_file;
    public void createFile() throws Exception {

        do {
            String date = "";
            Calendar calendar = Calendar.getInstance();
            date += calendar.get(Calendar.DAY_OF_MONTH);
            date += calendar.get(Calendar.MONTH);
            date += calendar.get(Calendar.YEAR);
            date += calendar.get(Calendar.HOUR_OF_DAY);
            date += calendar.get(Calendar.MINUTE);
            date += calendar.get(Calendar.SECOND);

            name_of_file = "src/main/resources/stats/" +  date + ".csv";
        }while (new File(name_of_file).exists());

        FileWriter writer = new FileWriter(name_of_file, true);
        String header = "day;animals quantity;grass quantity;amount of free places;average energy life of alive animals;average age of dead animals\n";
        writer.write(header);
        writer.flush();
        writer.close();
    }
    public void save(int nrOfDay,int amountOfAnimals,int amountOfGrasses,int amountOfFreePlaces,double averageEnergyLifeForAlive,double averageAgeForDead) throws IOException {

        FileWriter writer = new FileWriter(name_of_file, true);

        String line = nrOfDay+";"+amountOfAnimals+";"+amountOfGrasses+";"+amountOfFreePlaces+";"+averageEnergyLifeForAlive+";"+averageAgeForDead;
        line += "\n";
        writer.write(line);

        writer.flush();
        writer.close();
    }
}
