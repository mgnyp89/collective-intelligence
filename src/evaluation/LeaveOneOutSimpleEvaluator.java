package evaluation;

import math.StatisticsHelper;
import model.Rating;
import model.RecordCollection;
import model.UserCollection;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Simple Leave one out test evaluator
 */
public class LeaveOneOutSimpleEvaluator extends LeaveOneOutBaseEvaluator {

    public LeaveOneOutSimpleEvaluator(UserCollection userCollection) {
        super(userCollection);
    }

    /**
     * Evaluates a given dataset (passed into the constructor of this evaluator) and writes predictions, rmse and actual
     * values of all possible userId, itemId pairs into a file.
     */
    @Override
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
}
