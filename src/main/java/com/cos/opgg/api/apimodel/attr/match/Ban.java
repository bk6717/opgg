package com.cos.opgg.api.apimodel.attr.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ban {

    private long championId;
    private long pickTurn;

}