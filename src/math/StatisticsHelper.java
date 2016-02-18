package math;

import model.Rating;
import model.RecordCollection;
import model.UserCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Collection of statistical / mathematical static methods used for evaluation of datasets
 */
public class StatisticsHelper {

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the user
     * it contains at least one valid rating
     * @param userId
     * @param userCollection
     * @return
     */
    public static int minRatingPerUser(String userId, UserCollection userCollection) {
        RecordCollection recordCollection = userCollection.getRatingsForUser(userId);
        return Collections.min(recordCollection.getRatingCollection().values()).getRating();
    }

    /**
     * returns 0 if the movie is not present in the dataset
     * @param movieId
     * @param userCollection
     * @return minimal rating for a given movie
     */
    public static int minRatingPerMovie(String movieId, UserCollection userCollection) {
        List<Rating> ratings = userCollection.getRatingCollectionForMovie(movieId);
        // assume the highest rating to begin with
        int min = 5;
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                if (rating.getRating() < min) min = rating.getRating();
            }
        }
        return min;
    }

    /**
     * Assuming that the data is not corrupted, that is if there is an entry for the user
     * it contains at least one valid rating
     * @param userId
     * @param userCollection
     * @return highest rating
     */
    public static int maxRatingPerUser(String userId, UserCollection userCollection) {
        RecordCollection recordCollection = userCollection.getRatingsForUser(userId);
        return Collections.max(recordCollection.getRatingCollection().values()).getRating();
    }

    /**
     * returns 0 if the movie is not present in the dataset
     * @param movieId
     * @param userCollection
     * @return maximum rating for a given movie
     */
    public static int maxRatingPerMovie(String movieId, UserCollection userCollection) {
        List<Rating> ratings = userCollection.getRatingCollectionForMovie(movieId);
        int max = 0;
        if (!ratings.isEmpty()) {
            for (Rating rating : ratings) {
                if (rating.getRating() > max) max = rating.getRating();
            }
        }
        return max;
    }

    /**
     *
     * @param userId
     * @param userCollection
     * @return arithemetic mean of all rating for a specific user
     */
    public static double meanOfRatingsPerUser(String userId, UserCollection userCollection) {
        Collection<Rating> ratingCollection = userCollection.getRatingsForUser(userId).getRatingCollection().values();
        return calculateMean(ratingCollection);
    }

    /**
     *
     * @param movieId
     * @param userCollection
     * @return arithemetic mean of all rating for a specific item (movie)
     */
    public static double meanOfRatingsPerItem(String movieId, UserCollection userCollection) {
        List<Rating> ratings = userCollection.getRatingCollectionForMovie(movieId);
        return calculateMean(ratings);
    }

    /**
     *
     * @param userId
     * @param userCollection
     * @return mean of all ratings given by a specific user
     */
    public static double medianOfRatingsPerUser(String userId, UserCollection userCollection) {
        List<Rating> ratings = new ArrayList<>(userCollection.getRatingsForUser(userId).getRatingCollection().values());
        return calculateMedian(ratings);
    }

    /**
     *
     * @param movieId
     * @param userCollection
     * @return median (middle item) of all rating for a specific item (movie)
     */
    public static double medianfRatingsPerItem(String movieId, UserCollection userCollection) {
        List<Rating> ratings = userCollection.getRatingCollectionForMovie(movieId);
        return calculateMedian(ratings);
    }

    /**
     *
     * @param userId
     * @param userCollection
     * @return standard deviation of all ratings given by a specific user
     */
    public static double standardDeviationOfRatingsPerUser(String userId, UserCollection userCollection) {
        List<Rating> ratings = new ArrayList<>(userCollection.getRatingsForUser(userId).getRatingCollection().values());
        return calculateStandardDeviation(ratings);
    }

    /**
     * Calculates population standard deviation
     * @param movieId
     * @param userCollection
     * @return standard deviation of all rating for a specific item (movie)
     */
    public static double standardDeviationOfRatingsPerItem(String movieId, UserCollection userCollection) {
        List<Rating> ratings = userCollection.getRatingCollectionForMovie(movieId);
        return calculateStandardDeviation(ratings);
    }

    /**
     * Provided ratings (number of all ratings) divided by
     * All possible ratings (number of all users * number of all movies)
     * @param userCollection
     * @return
     */
    public static double ratingsDensityMetric(UserCollection userCollection) {
        double totalRatings = userCollection.getTotalRatingsCount();
        double allPossibleRatings = userCollection.getUsersCount() * userCollection.getTotalMoviesCount();

        return totalRatings / allPossibleRatings;
    }

    private static double calculateMean(Collection<Rating> ratingCollection) {
        Integer mean = 0;
        if(!ratingCollection.isEmpty()) {
            for (Iterator<Rating> it = ratingCollection.iterator(); it.hasNext();) {
                mean += it.next().getRating();
            }
            return mean.doubleValue() / ratingCollection.size();
        }
        return mean;
    }

    private static double calculateMean(List<Double> values) {
        Double mean = 0.0;
        if(!values.isEmpty()) {
            for (Double value : values) {
                mean += value;
            }
            return mean / values.size();
        }
        return mean;
    }

    private static double calculateMedian(List<Rating> ratingCollection) {
        Collections.sort(ratingCollection);
        int middle = ratingCollection.size() /2;
        if (ratingCollection.size() % 2 == 1) {
            return ratingCollection.get(middle).getRating();
        } else {
            return (ratingCollection.get(middle - 1).getRating() + ratingCollection.get(middle).getRating()) / 2.0;
        }
    }

    private static double calculateStandardDeviation(List<Rating> ratings) {
        double mean = calculateMean(ratings);
        List<Double> squaredDifferences = new ArrayList<>();

        for (Rating rating : ratings) {
            // substract the mean from each value and square the result
            squaredDifferences.add(Math.pow((rating.getRating() - mean), 2));
        }

        // calculate the variance (mean of squared differences)
        double variance = calculateMean(squaredDifferences);

        // take the square root of the variance
        return Math.sqrt(variance);
    }

}
