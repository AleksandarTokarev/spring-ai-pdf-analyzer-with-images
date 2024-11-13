package com.aleksandartokarev.spring_ai;

import lombok.*;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoadAIDTO {
    public Double rate;
    public List<LoadStopsAIDTO> loadStops;
}
