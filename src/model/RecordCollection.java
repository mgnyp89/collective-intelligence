package model;

import java.util.HashMap;
import java.util.Map;

public class RecordCollection {

    private Map<String, Rating> ratingCollection;

    public RecordCollection() {
        ratingCollection = new HashMap<>();
    }

    public void addRating(String id, Rating rating) {
        ratingCollection.put(id, rating);
    }

    public int getRatingsCount() {
        return ratingCollection.entrySet().size();
    }

    public Map<String, Rating> getRatingCollection() {
        return ratingCollection;
    }

}
