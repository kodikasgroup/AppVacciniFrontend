package com.kodikasgroup.wrapper;

import com.kodikasgroup.model.Citizen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CitizenWrapper {
    private List<Citizen> citizens;
}
