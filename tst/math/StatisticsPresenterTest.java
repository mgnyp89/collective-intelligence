package math;

import data.DatasetReader;
import data.DatasetReaderCSV;
import model.UserCollection;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StatisticsPresenterTest {

    private DatasetReader datasetReaderCSV = new DatasetReaderCSV();
    private UserCollection userCollection;
    private StatisticsPresenter statisticsPresenter;

    private final static String DUPLICATES_FILE_PATH = "./resources/multiple.csv";

    @Before
    public void setUp() throws Exception {
        userCollection = datasetReaderCSV.parseData(DUPLICATES_FILE_PATH);
        statisticsPresenter = new StatisticsPresenter();
    }

    /*** USERS RELATED TESTS ***/

    @Test
    public void maxRatingPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMaxinumRatingCountForUsers(userCollection), Optional.of(5));
    }

    @Test
    public void minRatingPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMininumRatingCountForUsers(userCollection), Optional.of(1));
    }

    @Test
    public void meanPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMeanRatingCountForUsers(userCollection), 1.71, 0.1);
    }

    @Test
    public void medianPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMedianRatingCountForUsers(userCollection), 1.0, 0.1);
    }

    @Test
    public void standardDeviationPerUserTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getStandardDeviationRatingCountForUsers(userCollection), 1.06, 0.1);
    }

    /*** ITEM RELATED TESTS ***/

    @Test
    public void maxRatingPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMaximumRatingCountForItems(userCollection), Optional.of(1));
    }

    @Test
    public void minRatingPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMinimumRatingCountForItems(userCollection), Optional.of(1));
    }

    @Test
    public void meanPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMeanRatingCountForItems(userCollection), 1.33, 0.1);
    }

    @Test
    public void medianPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMedianRatingCountForItems(userCollection), 1.0, 0.1);
    }

    @Test
    public void standardDeviationPerItemTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getStandardDeviationRatingCountForItems(userCollection), 0.88, 0.1);
    }

    @Test
    public void getMeanItemRatingLeaveOneOutTest() {
        assertNotNull(userCollection);
        assertEquals(statisticsPresenter.getMeanItemRatingLeaveOneOut("187", "2", userCollection), 1.33, 0.1);
    }
}
