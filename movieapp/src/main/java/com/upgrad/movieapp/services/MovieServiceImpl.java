package com.upgrad.movieapp.services;

import com.upgrad.movieapp.dao.MovieDAO;
import com.upgrad.movieapp.entities.Movie;
import com.upgrad.movieapp.entities.Theatre;
import com.upgrad.movieapp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.SecondaryTable;
import java.awt.print.Pageable;
import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Movie acceptMovieDetails(Movie movie) {
        return movieDAO.save(movie);
    }

    @Override
    public List<Movie> acceptMultipleMovieDetails(List<Movie> movies) {
        List<Movie> savedMovies = new ArrayList<>();
        for(Movie movie : movies){
            savedMovies.add(acceptMovieDetails(movie));
        }
        return savedMovies;
    }

    @Override
    public Movie getMovieDetails(int id) {
        return movieDAO.findById(id).get();
    }

    @Override
    public Movie updateMovieDetails(int id, Movie movie) {
        Movie savedMovie = getMovieDetails(id);
        savedMovie.setDuration(movie.getDuration());
        savedMovie.setMovieName(movie.getTrailerUrl());
        savedMovie.setCoverPhotoUrl(movie.getCoverPhotoUrl());
        savedMovie.setReleaseDate(movie.getReleaseDate());
        savedMovie.setMovieName(movie.getMovieName());
        savedMovie.setMovieDescription(movie.getMovieDescription());
        return movieDAO.save(savedMovie);
    }

    @Override
    public boolean deleteMovie(int id) {
        Movie savedMovie = getMovieDetails(id);

        if(savedMovie ==null){
            return false;
        }

        movieDAO.delete(savedMovie);
        return true;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.findAll();
    }

    @Override
    public Page<Movie> getPaginatedMovieDetails(Pageable pageable) {
        return movieDAO.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public Boolean bookMovie(User user, Movie movie, Theatre theatre) {
        //check whether requested movie is valid or not
        Optional<Movie> requestedMovie = movieDAO.findById(movie.getMovieId());
        if(!requestedMovie.isPresent())
            return false;

        //check whether the user is valid or not
        Map<String, String> userUriMap = new HashMap<>();
        userUriMap.put("id", String.valueOf(user.getUserId()));
        String userAppUri = "http://localhost:8080/user_app/v1/users/{id}";
        User receivedUser = restTemplate.getForObject(userAppUri, User.class, userUriMap);
        if(receivedUser==null)
            return false;

        //check whether movie and theatre combination is present or not
        Map<String, String> theatreUriMap = new HashMap<>();
        theatreUriMap.put("theatreid", String.valueOf(theatre.getTheatreId()));
        theatreUriMap.put("movieId",String.valueOf(theatre.getMovieId()));
        String theatreAppUrl = "http://localhost:8082/theatre_app/v1/theatres/{theatreId}/movies/{movieId}";
        Theatre receivedTheatre = restTemplate.getForObject(theatreAppUrl, Theatre.class, theatreUriMap);
        if(receivedTheatre==null)
            return false;

        return true;

    }
}
