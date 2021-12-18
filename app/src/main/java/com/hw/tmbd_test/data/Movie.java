package com.hw.tmbd_test.data;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true) @Data
public class Movie {
    private String              poster_path                                 ;
    private String              title                                       ;
    private String              overview                                    ;
    private String              release_date                                ;
    private float               vote_average                                ;
    private int                 vote_count                                  ;
    private String              backdrop_path                               ;

    private String              original_title                               ;
}
