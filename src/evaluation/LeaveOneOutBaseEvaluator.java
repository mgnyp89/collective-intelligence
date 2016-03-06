package evaluation;

import math.StatisticsHelper;
import math.StatisticsPresenter;
import model.UserCollection;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for evaluators that run leave on out tests
 */
public class LeaveOneOutBaseEvaluator {

    protected final UserCollection USER_COLLECTION;
    protected final StatisticsPresenter statisticsPresenter;
    protected final DecimalFormat df = new DecimalFormat("#.####");
    protected List<Double> leaveOneOutTestTimings;
    protected List<Double> allRMSEs;
    protected double impossiblePredictionsCount;

    public LeaveOneOutBaseEvaluator(UserCollection userCollection) {
        USER_COLLECTION = userCollection;
        statisticsPresenter = new StatisticsPresenter();
        leaveOneOutTestTimings = new ArrayList<>();
        allRMSEs = new ArrayList<>();
        impossiblePredictionsCount = 0;
        df.setRoundingMode(RoundingMode.CEILING);
    }

    /**
     * Average runtime for 1 complete L10 test-cycle (that is a complete L1O test over all of the ratings data)
     */
    public void printAverageRunTime() {
        // milliseconds to seconds conversion
        System.out.println("The average run time is: " +
                df.format(StatisticsHelper.calculateMean(leaveOneOutTestTimings) / 1000) + " seconds");
    }

    /**
     * Mean of RMSE achieved during all runs of the tests
     */
    public void printAverageRMSE() {
        System.out.println("The average RMSE is: " + df.format(StatisticsHelper.calculateMean(allRMSEs)));
    }

    /**
     * Overall coverage value for the L1O test as the percentage of user-item pairs for which a recommendation
     * can be calculated
     */
    public void printCoverage() {
        double totalUserItemPairs = USER_COLLECTION.getUsersCount() * USER_COLLECTION.getTotalMoviesCount();
        System.out.println("The coverage value is: " + df.format( 100 - (
                (impossiblePredictionsCount * 100) / totalUserItemPairs) ) + " %");
    }

    /**
     * Evaluates a given dataset (passed into the constructor of this evaluator) and writes predictions, rmse and actual
     * values of all possible userId, itemId pairs into a file, implementation specific to the classes that extend
     * the base class.
     */
    public void runLeaveOneOutLoopTest() {
    }

    /**
     * Rounds predicted values and RMSE to 3 decimal places
     * @param userId
     * @param itemId
     * @return an entry of format : "user_id, item_id, actual rating, predicted rating, RMSE"
     */
    protected String printLeaveOneOutPredictionEntry(String userId, String itemId, int actualRating,
                                                     double predictedRating, double rmse) {
        return userId + "," + itemId + "," + actualRating + "," + df.format(predictedRating) + "," + df.format(rmse) + "\n";
    }
}
