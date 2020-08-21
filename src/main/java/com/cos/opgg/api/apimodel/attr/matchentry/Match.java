package com.cos.opgg.api.apimodel.attr.matchentry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {

    private String platformId;
    private long gameId;
    private long champion;
    private long queue;
    private long season;
    private long timestamp;
    private String role;
    private String lane;

}