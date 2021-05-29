package com.kodikasgroup.model;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;
=======
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
>>>>>>> Development
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

<<<<<<< HEAD
import java.util.HashSet;
import java.util.Set;

=======
>>>>>>> Development

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
<<<<<<< HEAD
public class Vaccine {

    private long vaccineID;
    private String vaccineName;
    private Long quantity;

    private Set<Availability> availabilities = new HashSet<>();


    public Vaccine(String vaccineName, long quantity) {
        this.vaccineName = vaccineName;
        this.quantity = quantity;
    }

    public Vaccine(long vaccineID, String vaccineName, long quantity) {
        this.vaccineID = vaccineID;
=======
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vaccine {
    private long vaccineID;
    private String vaccineName;
    private Long quantity;
//    private Set<Availability> availabilities = new HashSet<>();
//    private Set<Entitled> entitleds = new HashSet<>();

    public Vaccine(String vaccineName, Long quantity) {
>>>>>>> Development
        this.vaccineName = vaccineName;
        this.quantity = quantity;
    }
}