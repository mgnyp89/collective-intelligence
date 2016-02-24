package math;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Collection of statistical / mathematical static methods used for evaluation of datasets
 */
public class StatisticsHelper {

    public static Optional<Integer> calculateMinimum(Collection<Integer> ratings) {
        return ratings.stream().min(Comparator.<Integer>naturalOrder());
    }

    public static Optional<Integer> calculateMaximum(Collection<Integer> ratings) {
        return ratings.stream().max(Comparator.<Integer>naturalOrder());
    }

    public static double calculateMean(Collection<? extends Number> ratings) {
        double sum = ratings.stream().mapToDouble(Number::intValue).sum();
        return sum / ratings.size();
    }

    public static double calculateMedian(List<Integer> ratings) {
        Collections.sort(ratings);
        int middle = ratings.size() / 2;
        if (ratings.size() % 2 == 1) {
            return ratings.get(middle);
        } else {
            return (ratings.get(middle - 1) + ratings.get(middle)) / 2.0;
        }
    }

    public static double calculateStandardDeviation(List<Integer> ratings) {

        double mean = calculateMean(ratings);
        // list of squared differences from the mean (substract the mean from each value and square the result)
        List<Double> squaredDifferences = ratings.stream().map(integer -> Math.pow(integer - mean, 2))
                    .collect(Collectors.toList());

        // calculate the variance (mean of squared differences)
        double variance = calculateMean(squaredDifferences);

        // take the square root of the variance
        return Math.sqrt(variance);
    }

    public static double calculateRootMeanSquareDifferences(List<Integer> ratings1, List<Integer> ratings2) {
        if (ratings1.size() != ratings2.size()) {
            throw new IllegalStateException("number of corresponding ratings does not match");
        }

        double squaredDifferencesSum = 0;
        for (int i = 0; i < ratings1.size(); i++) {
            squaredDifferencesSum += Math.pow(ratings1.get(i) - ratings2.get(i), 2);
        }
        return Math.sqrt(squaredDifferencesSum / ratings1.size());
    }

    public static double calculateRootMeanSquareDifferencesForPair(double value1, double value2) {
        double squaredDifferencesSum = Math.pow(value1 - value2, 2);
        return Math.sqrt(squaredDifferencesSum / 2);
    }

}
