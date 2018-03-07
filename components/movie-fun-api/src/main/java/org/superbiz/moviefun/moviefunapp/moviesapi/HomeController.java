package org.superbiz.moviefun.moviefunapp.moviesapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.superbiz.moviefun.moviefunapp.moviesapi.Album.AlbumClient;
import org.superbiz.moviefun.moviefunapp.moviesapi.Album.AlbumFixtures;
import org.superbiz.moviefun.moviefunapp.moviesapi.Album.AlbumInfo;
import org.superbiz.moviefun.moviefunapp.moviesapi.Movies.MovieFixtures;
import org.superbiz.moviefun.moviefunapp.moviesapi.Movies.MovieInfo;
import org.superbiz.moviefun.moviefunapp.moviesapi.Movies.MoviesClient;


import java.util.Map;

@Controller
public class HomeController {

    private final MoviesClient moviesClient;
    private final AlbumClient albumClient;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MoviesClient moviesClient, AlbumClient albumClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
        this.albumClient = albumClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movieInfo : movieFixtures.load()) {
            moviesClient.addMovie(movieInfo);
        }

        for (AlbumInfo album : albumFixtures.load()) {
            albumClient.addAlbum(album);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumClient.getAlbums());

        return "setup";
    }
}
