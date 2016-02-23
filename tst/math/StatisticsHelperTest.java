package math;

import data.DatasetReader;
import data.DatasetReaderCSV;
import model.UserCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsHelperTest {

    private DatasetReader datasetReaderCSV = new DatasetReaderCSV();
    private List<Integer> ratingsDistributionPerUser;
    private List<Integer> ratingsDistributionPerItem;

    private final static String DUPLICATES_FILE_PATH = "./resources/test.csv";

    @Before
    public void setUp() throws Exception {
        UserCollection userCollection = datasetReaderCSV.parseData(DUPLICATES_FILE_PATH);
        ratingsDistributionPerUser = userCollection.getRatingsDistributionPerUser();
        ratingsDistributionPerItem = userCollection.getRatingsDistributionPerItem();

        assertNotNull(ratingsDistributionPerUser);
        assertNotNull(ratingsDistributionPerItem);
    }

    @Test
    public void calculateMinimumTest() {
        assertEquals(StatisticsHelper.calculateMinimum(ratingsDistributionPerUser), Optional.of(1));
        assertEquals(StatisticsHelper.calculateMinimum(ratingsDistributionPerItem), Optional.of(1));
    }

    @Test
    public void calculateMaximumTest() {
        assertEquals(StatisticsHelper.calculateMaximum(ratingsDistributionPerUser), Optional.of(6));
        assertEquals(StatisticsHelper.calculateMaximum(ratingsDistributionPerItem), Optional.of(2));
    }

    @Test
    public void calculateMeanTest() {
        assertEquals(StatisticsHelper.calculateMean(ratingsDistributionPerUser), 2.25, 0.1);
        assertEquals(StatisticsHelper.calculateMean(ratingsDistributionPerItem), 1.125, 0.1);
    }

    @Test
    public void calculateMedianTest() {
        assertEquals(StatisticsHelper.calculateMedian(ratingsDistributionPerUser), 1, 0.1);
        assertEquals(StatisticsHelper.calculateMedian(ratingsDistributionPerItem), 1, 0.1);
    }

    @Test
    public void calculateStandardDeviationTest() {
        assertEquals(StatisticsHelper.calculateStandardDeviation(ratingsDistributionPerUser), 2.06, 0.1);
        assertEquals(StatisticsHelper.calculateStandardDeviation(ratingsDistributionPerItem), 0, 0.1);
    }

}