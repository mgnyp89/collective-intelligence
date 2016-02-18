package math;

import data.DatasetReader;
import data.DatasetReaderCSV;
import model.UserCollection;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsHelperTest {

    private DatasetReader datasetReaderCSV = new DatasetReaderCSV();
    private UserCollection userCollection;

    private final static String DUPLICATES_FILE_PATH = "./resources/multiple.csv";

    @Before
    public void setUp() throws Exception {
        userCollection = datasetReaderCSV.parseData(DUPLICATES_FILE_PATH);
    }

    /*** USERS RELATED TESTS ***/

    @Test
    public void maxRatingPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.maxRatingPerUser("196", userCollection), 5);
        assertEquals(StatisticsHelper.maxRatingPerUser("186", userCollection), 5);
        assertEquals(StatisticsHelper.maxRatingPerUser("244", userCollection), 5);
        assertEquals(StatisticsHelper.maxRatingPerUser("22", userCollection), 2);
    }

    @Test
    public void minRatingPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.minRatingPerUser("196", userCollection), 1);
        assertEquals(StatisticsHelper.minRatingPerUser("186", userCollection), 5);
        assertEquals(StatisticsHelper.minRatingPerUser("244", userCollection), 1);
    }

    @Test
    public void meanPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("196", userCollection), 3.25, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("186", userCollection), 5.0, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("244", userCollection), 3.0, 0.1);
    }

    @Test
    public void medianPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("196", userCollection), 3.25, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("186", userCollection), 5.0, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerUser("244", userCollection), 3.0, 0.1);
    }

    @Test
    public void standardDeviationPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerUser("196", userCollection), 1.47, 0.1);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerUser("186", userCollection), 0.0, 0.1);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerUser("244", userCollection), 1.41, 0.1);
    }

    /*** ITEM RELATED TESTS ***/

    @Test
    public void maxRatingPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.maxRatingPerMovie("1", userCollection), 4);
        assertEquals(StatisticsHelper.maxRatingPerMovie("2", userCollection), 3);
        assertEquals(StatisticsHelper.maxRatingPerMovie("3", userCollection), 4);
    }

    @Test
    public void minRatingPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.minRatingPerMovie("1", userCollection), 1);
        assertEquals(StatisticsHelper.minRatingPerMovie("2", userCollection), 1);
        assertEquals(StatisticsHelper.minRatingPerMovie("3", userCollection), 4);
    }

    @Test
    public void meanPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.meanOfRatingsPerItem("1", userCollection), 2.5, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerItem("2", userCollection), 1.75, 0.1);
        assertEquals(StatisticsHelper.meanOfRatingsPerItem("3", userCollection), 4.0, 0.1);
    }

    @Test
    public void medianPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.medianfRatingsPerItem("1", userCollection), 2.5, 0.1);
        assertEquals(StatisticsHelper.medianfRatingsPerItem("2", userCollection), 1.5, 0.1);
        assertEquals(StatisticsHelper.medianfRatingsPerItem("3", userCollection), 4.0, 0.1);
    }

    @Test
    public void standardDeviationPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerItem("1", userCollection), 1.11, 0.1);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerItem("2", userCollection), 0.82, 0.1);
        assertEquals(StatisticsHelper.standardDeviationOfRatingsPerItem("3", userCollection), 0.0, 0.1);
    }

}