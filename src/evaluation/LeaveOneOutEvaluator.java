package evaluation;

import math.StatisticsHelper;
import math.StatisticsPresenter;
import model.Rating;
import model.RecordCollection;
import model.UserCollection;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Simple Leave one out test evaluator
 */
public class LeaveOneOutEvaluator {

    private final UserCollection USER_COLLECTION;
    private final StatisticsPresenter statisticsPresenter;
    private final DecimalFormat df = new DecimalFormat("#.####");
    private List<Double> leaveOneOutTestTimings;
    private List<Double> allRMSEs;
    private double impossiblePredictionsCount;

    public LeaveOneOutEvaluator(UserCollection userCollection) {
        USER_COLLECTION = userCollection;
        statisticsPresenter = new StatisticsPresenter();
        leaveOneOutTestTimings = new ArrayList<>();
        allRMSEs = new ArrayList<>();
        impossiblePredictionsCount = 0;
        df.setRoundingMode(RoundingMode.CEILING);
    }

    /**
     * Rounds predicted values and RMSE to 3 decimal places
     * @param userId
     * @param itemId
     * @return an entry of format : "user_id, item_id, actual rating, predicted rating, RMSE"
     */
    public String printLeaveOneOutPredictionEntry(String userId, String itemId, int actualRating,
                                                  double predictedRating, double rmse) {
        return userId + "," + itemId + "," + actualRating + "," + df.format(predictedRating) + "," + df.format(rmse) + "\n";
    }

    /**
     * Evaluates a given dataset (passed into the constructor of this evaluator) and writes predictions, rmse and actual
     * values of all possible userId, itemId pairs into a file.
     */
    public void runLeaveOneOutLoopTest() {
        impossiblePredictionsCount = 0;

        Map<String, RecordCollection> userRatings = USER_COLLECTION.getUserCollection();
        Map<String, Rating> movieRatings = null;

        int actualRating;
        double predictedRating, rmse;

        long start = System.currentTimeMillis();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./resources/leaveOneOutLoopTest.csv"), StandardCharsets.UTF_8))) {
            for (String userId : userRatings.keySet()) {
                movieRatings = userRatings.get(userId).getRatingCollection();
                for (String itemId : movieRatings.keySet()) {
                    try {
                        actualRating = USER_COLLECTION.getRecordRating(userId, itemId);
                        predictedRating = statisticsPresenter.getMeanItemRatingLeaveOneOut(userId, itemId, USER_COLLECTION);
                        rmse = StatisticsHelper.calculateRootMeanSquareDifferencesForPair(actualRating, predictedRating);

                        allRMSEs.add(rmse);

                        writer.write(printLeaveOneOutPredictionEntry(userId, itemId, actualRating, predictedRating, rmse));
                    } catch (IllegalStateException e) {
                        impossiblePredictionsCount++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong " + e);
        }

        double end = System.currentTimeMillis() - start;

        System.out.println("Leave One Out Test completed in " + end / 1000 + " seconds");

        leaveOneOutTestTimings.add(end);
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

}
