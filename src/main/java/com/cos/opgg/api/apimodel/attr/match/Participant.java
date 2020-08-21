package com.cos.opgg.api.apimodel.attr.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    private long participantId;
    private long teamId;
    private long championId;
    private long spell1Id;
    private long spell2Id;
    private Stats stats;

}