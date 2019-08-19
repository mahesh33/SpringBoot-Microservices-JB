package com.mahesh.ratingsdataservicems.controller;

import com.mahesh.ratingsdataservicems.models.Rating;
import com.mahesh.ratingsdataservicems.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingdata")
public class RatingResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 5),
                new Rating("5678", 6)
        );

//        return ratings;
        UserRating userRating = new UserRating();
        userRating.setUserRating(ratings);

        return userRating;
    }
}
