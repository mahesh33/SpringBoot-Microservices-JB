package com.mahesh.moviecatalogservicems.controller;

import com.mahesh.moviecatalogservicems.models.CatalogItem;
import com.mahesh.moviecatalogservicems.models.Movie;
import com.mahesh.moviecatalogservicems.models.Rating;
import com.mahesh.moviecatalogservicems.models.UserRating;
import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {

//        RestTemplate restTemplate = new RestTemplate();
//        Movie movie = restTemplate.getForObject("http://localhost:8091/movies/haha", Movie.class);

//        Get all rated movie IDs
//        Hard code for now
/*        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 4),
                new Rating("5678", 3)
        );*/

//        UserRating ratings = restTemplate.getForObject("http://localhost:8092/ratingdata/users/hora" + userId, UserRating.class);

        UserRating ratings = restTemplate.getForObject("http://rating-data-service-ms/ratingdata/users/hora" + userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating -> {
//                    For Each movie ID, call movie info service and get details
            Movie movie = restTemplate.getForObject("http://movie-info-service-ms/movies/" + rating.getMovieId(), Movie.class);

/*            Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8091/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

//        Put them all together
            return new CatalogItem(movie.getName(), "TestDesc", rating.getRating());
        })
                .collect(Collectors.toList());

//        return Collections.singletonList(new CatalogItem("Transformers", "Test", 4));
    }
}
