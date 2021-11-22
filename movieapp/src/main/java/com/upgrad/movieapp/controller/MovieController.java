package com.upgrad.movieapp.controller;

import com.upgrad.movieapp.dto.MovieBookingDTO;
import com.upgrad.movieapp.dto.MovieDTO;
import com.upgrad.movieapp.entities.Movie;
import com.upgrad.movieapp.entities.Theatre;
import com.upgrad.movieapp.entities.User;
import com.upgrad.movieapp.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="movie_app/v1")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    ModelMapper modelMapper;

    /**
     * Method for creating movies
     * http://localhost:8080/movie_app/v1/movies
     */

    @PostMapping(value="/movies",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMovie(MovieDTO movieDTO){
        //convert MovieDTO to MovieEntity

        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie savedMovie = movieService.acceptMovieDetails(newMovie);

        MovieDTO savedMovieDTO = modelMapper.map(savedMovie, MovieDTO.class);

        return new ResponseEntity(savedMovieDTO, HttpStatus.CREATED);
    }

    @GetMapping(value="/movies",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllMovies(){
        List<Movie> moviesList = movieService.getAllMovies();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for(Movie movie : moviesList){
            movieDTOList.add(modelMapper.map(movie,MovieDTO.class));
        }
        return new ResponseEntity(movieDTOList,HttpStatus.OK);
    }

    @GetMapping(value="/movie/{id}")
    public ResponseEntity getMovieBasedOnId(@PathVariable(name="id") int id){
        Movie responseMovie = movieService.getMovieDetails(id);
        MovieDTO responseMovieDTO = modelMapper.map(responseMovie,MovieDTO.class);
        return new ResponseEntity(responseMovieDTO,HttpStatus.OK);
    }

    @PutMapping(value="/movie/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMovieDetails(@PathVariable(name="id") int id, @RequestBody MovieDTO movieDTO){
        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie updatedMovie = movieService.updateMovieDetails(id, newMovie);

        MovieDTO updatedMovieDTO = modelMapper.map(updatedMovie, MovieDTO.class);

        return new ResponseEntity(updatedMovieDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping(value= "/bookings/movie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookMovieDetails(@RequestBody MovieBookingDTO movieBookingDTO){
        Movie requestedMovie = modelMapper.map(movieBookingDTO.getMovie(),Movie.class);
        User fromUser = modelMapper.map(movieBookingDTO.getUser(), User.class);
        Theatre requestedTheatre = modelMapper.map(movieBookingDTO.getTheatre(), Theatre.class);

        boolean isValidBooking = movieService.bookMovie(fromUser, requestedMovie , requestedTheatre);

        if(!isValidBooking)
            return new ResponseEntity("Not Booked !!", HttpStatus.OK);
        return new ResponseEntity("Booked Successfully !!",HttpStatus.OK);
    }
}
