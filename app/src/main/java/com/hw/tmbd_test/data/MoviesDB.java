package com.hw.tmbd_test.data;

import java.util.ArrayList;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true) @Data
public class MoviesDB{
    private ArrayList<Movie> movies = new ArrayList<>();
}
