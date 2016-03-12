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
import java.util.TreeMap;

public class CosineDistanceBasedPredictionEvaluator extends LeaveOneOutBaseEvaluator {

    public CosineDistanceBasedPredictionEvaluator(UserCollection userCollection) {
        super(userCollection);
    }

    public void runLeaveOneOutLoopTest(int neighbourhoodSize) {
        impossiblePredictionsCount = 0;

        Map<String, RecordCollection> userRatings = USER_COLLECTION.getUserCollection();
        Map<String, Rating> movieRatings = null;

        int actualRating;
        double predictedRating, rmse;

        long start = System.currentTimeMillis();

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("./resources/cosineBasedPredictionTest"+neighbourhoodSize+".csv"), StandardCharsets.UTF_8))) {
            for (String userId : userRatings.keySet()) {
                TreeMap<Double, String> similarUsers = statisticsPresenter.getListOfSimilarUsersUsingCosine(userId, USER_COLLECTION);
                movieRatings = userRatings.get(userId).getRatingCollection();
                for (String itemId : movieRatings.keySet()) {
                    try {
                        actualRating = USER_COLLECTION.getRecordRating(userId, itemId);
                        predictedRating = statisticsPresenter.getCosineSimilarityBasedPrediction(itemId, neighbourhoodSize, similarUsers, USER_COLLECTION);
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
