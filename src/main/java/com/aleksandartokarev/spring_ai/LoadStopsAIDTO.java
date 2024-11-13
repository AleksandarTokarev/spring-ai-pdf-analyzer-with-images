package com.aleksandartokarev.spring_ai;

import lombok.*;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoadStopsAIDTO {
    public String type;
    public String date;
    public String time;
    public String shipper;
    public String address;
    public String city;
    public String state;
    public String zip;
}
