package com.kodikasgroup.wrapper;

import com.kodikasgroup.model.Availability;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class AvailabilityWrapper {
    private List<Availability> availability;
}
