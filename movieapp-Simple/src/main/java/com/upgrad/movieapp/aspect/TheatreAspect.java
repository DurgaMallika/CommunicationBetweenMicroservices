package com.upgrad.movieapp.aspect;

import com.upgrad.movieapp.entities.Movie;
import com.upgrad.movieapp.entities.Theatre;
import com.upgrad.movieapp.entities.User;
import com.upgrad.movieapp.feign.TheatreServiceClient;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TheatreAspect {

    @Autowired
    TheatreServiceClient theatreServiceClient;

    @Before(value = "execution(* com.upgrad.movieapp.services.MovieServiceImpl.bookMovie(..)) && args(user, movie, theatre)")
    public void beforeAdvice(JoinPoint joinPoint, User user, Movie movie, Theatre theatre){
        Theatre receivedTheater = theatreServiceClient.getTheatre(theatre.getTheatreId(), theatre.getMovieId());
        if(receivedTheater==null){
            throw new RuntimeException("No theatre movie combination available");
        }
    }
}
