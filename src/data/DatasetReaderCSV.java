package data;

import model.Rating;
import model.RecordCollection;
import model.UserCollection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by mon on 17/02/16.
 */
public class DatasetReaderCSV implements DatasetReader {

    public UserCollection parseData(String filePath) throws FileNotFoundException, IOException {
        UserCollection userCollection = new UserCollection();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String [] values;
            String line = br.readLine();
            while (line != null) {
                values = line.split(",");
                userCollection.addRecord(values[0], values[1], new Rating(Integer.valueOf(values[2]), Integer.valueOf(values[3])));
                line = br.readLine();
            }
        }

        return userCollection;
    }

}
