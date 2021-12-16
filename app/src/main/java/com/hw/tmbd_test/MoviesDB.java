package com.hw.tmbd_test;

import java.util.ArrayList;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true) @Data
public class MoviesDB{
    private ArrayList<Movie> movies = new ArrayList<>();
}
