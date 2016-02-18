package model;

import data.DatasetReader;
import data.DatasetReaderCSV;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserCollectionTest {

    private DatasetReader datasetReaderCSV = new DatasetReaderCSV();
    private UserCollection userCollection;

    private final static String TEST_FILE_PATH = "./resources/test.csv";

    @Before
    public void setUp() throws Exception {
        userCollection = datasetReaderCSV.parseData(TEST_FILE_PATH);
    }

    @Test
    public void getUsersSizeTest() {
        assertNotNull(userCollection);
        assertEquals(userCollection.getUsersCount(), 15);
    }
}
