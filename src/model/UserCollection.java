package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserCollection {

    /**
     * Represents internally mapping of users to collections of movies they rated
     * <userId, Map<movieId, movieRating>> where movieRating contains rating and timestamp
     */
    private Map<String, RecordCollection> userCollection;
    // <movie_id, List<Rating> associated with the movie>
    private Map<String, List<Rating>> movieCollection;
    // <number of ratings given by the user>
    private List<Integer> ratingsDistributionPerUser;
    // <number of ratings given to the item>
    private List<Integer> ratingsDistributionPerItem;
    private List<Rating> allRatings;
    private int[] ratingClasses = null;

    public UserCollection() {
        this.userCollection = new HashMap<>();
        this.movieCollection = new HashMap<>();
        this.ratingsDistributionPerUser = null;
        this.ratingsDistributionPerItem = null;
        this.allRatings = new ArrayList<>();
    }

    /**
     * If the userId is present in the collection retrieve the movie rating map and add a new entry
     * Otherwise create a new rating map and insert it into the collection under the userId key
     *
     * Create movie collection -> mapping of movie id to ratings from all users.
     * Same principle: if movie id exists, add the rating to the list of ratings, otherwise create it.
     * @param userId record collection key
     * @param movieId movie collection key
     * @param rating movie rating (numeric rating + timestamp)
     */
    public void addRecord(String userId, String movieId, Rating rating) {

        allRatings.add(rating);

        if (movieCollection.containsKey(movieId)) {
            movieCollection.get(movieId).add(rating);
        }
        else {
            List<Rating> ratings = new ArrayList<>();
            ratings.add(rating);
            movieCollection.put(movieId, ratings);
        }

        RecordCollection recordCollection = new RecordCollection();
        recordCollection.addRating(movieId, rating);

        userCollection.merge(userId, recordCollection, (stringRatingMap, stringRatingMap2) -> {
            stringRatingMap.addRating(movieId, rating);
            return stringRatingMap;
        });
    }

    /**
     *
     * @return number of users with present records in the collection
     */
    public int getUsersCount() {
        return userCollection.size();
    }

    public int getTotalMoviesCount() {
        return movieCollection.size();
    }

    public int getTotalRatingsCount() {
        return movieCollection.values().stream().mapToInt(value -> value.size()).sum();
    }

    public int getRatingCountForRatingClass(int ratingClass) {
        if (ratingClasses == null) {
            ratingClasses = new int[6];

            for (Map.Entry<String, List<Rating>> entry : movieCollection.entrySet()) {
                for (Rating rating : entry.getValue()) {
                    ratingClasses[rating.getRating()]++;
                }
            }
        }

        return ratingClasses[ratingClass];
    }

    public List<Rating> getRatingCollectionForMovie(String movieId) {
        return movieCollection.get(movieId);
    }

    public List<Integer> getRatingsDistributionPerUser() {
        if (ratingsDistributionPerUser == null) {
            ratingsDistributionPerUser = userCollection.values().stream().map(RecordCollection::getRatingsCount)
                    .collect(Collectors.toList());
        }
        return ratingsDistributionPerUser;
    }

    public List<Integer> getRatingsDistributionPerItem() {
        if (ratingsDistributionPerItem == null) {
            ratingsDistributionPerItem = movieCollection.values().stream().map(Collection::size)
                    .collect(Collectors.toList());
        }
        return ratingsDistributionPerItem;
    }
}
