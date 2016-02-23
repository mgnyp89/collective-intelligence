package model;

import java.util.HashMap;
import java.util.Map;

public class RecordCollection {

    private Map<String, Rating> ratingCollection;

    public RecordCollection() {
        ratingCollection = new HashMap<>();
    }

    public void addRating(String movieId, Rating rating) {
        ratingCollection.put(movieId, rating);
    }

    public int getRatingsCount() {
        return ratingCollection.entrySet().size();
    }

}
