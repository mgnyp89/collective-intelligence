package data;

import static org.junit.Assert.assertNotNull;
import model.UserCollection;
import org.junit.Test;

import java.io.FileNotFoundException;

public class DatasetReaderCSVTest {

    private DatasetReader datasetReaderCSV = new DatasetReaderCSV();
    private UserCollection userCollection;

    private final static String TEST_FILE_PATH = "./resources/test.csv";
    private final static String FILE_NOT_FOUND_FILE_PATH = "./resources/non_existent_file.csv";

    @Test
    public void loadDatasetTest() throws Exception {
        userCollection = datasetReaderCSV.parseData(TEST_FILE_PATH);
        assertNotNull(userCollection);
    }

    @Test(expected = FileNotFoundException.class)
    public void loadNonExistentDataset() throws Exception {
        userCollection = datasetReaderCSV.parseData(FILE_NOT_FOUND_FILE_PATH);
    }

}
