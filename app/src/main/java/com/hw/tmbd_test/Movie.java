package com.hw.tmbd_test;

import java.util.ArrayList;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true) @Data
public class Movie {
    private boolean             adult                                       ;
    private String              backdrop_path                               ;
    private ArrayList<Integer>  genre_ids           = new ArrayList<>()     ;
    private int                 id                                          ;
    private String              original_language                           ;
    private String              original_title                              ;
    private String              overview                                    ;
    private double              popularity                                  ;
    private String              poster_path                                 ;
    private String              release_date                                ;
    private String              title                                       ;
    private boolean             video                                       ;
    private double              vote_average                                ;
    private int                 vote_count                                  ;
}
