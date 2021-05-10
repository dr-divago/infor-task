package com.company.infor.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Optional;

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class Root {
    private Movie movie;

    public Root() {

    }

    public void setMovieInfo(Movie movie) {
        this.movie = movie;
    }


    public Root(Movie movie) {
        super();
        this.movie = movie;
    }

    public Optional<Movie> getMovieInfo() {
        return Optional.ofNullable(movie);
    }
}
