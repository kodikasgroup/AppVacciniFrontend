package com.kodikasgroup.wrapper;
import com.kodikasgroup.model.Reservation;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ReservationWrapper {
    List<Reservation> reservations;
}
