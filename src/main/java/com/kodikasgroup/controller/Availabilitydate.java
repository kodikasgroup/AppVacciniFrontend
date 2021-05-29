package com.kodikasgroup.controller;

import com.kodikasgroup.model.Availability;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Availabilitydate {

    @FXML
    ListView dateview ;

    @FXML
    TextArea datearea;

    private List<Availability> availabilities;

    private LocalDate date ;
    public int month = 0;

    @FXML
    public void initialize () throws IOException {

       date = LocalDate.now();

       getListViewDate();
    }

    private void getListViewDate(){
        LocalDate day ;
        if(month != 0){
            day = date.plusMonths(month);
            day = day.minusDays(date.getDayOfMonth()-1);
        }
        else
            day = date;
        datearea.setText(day.getMonth().toString() +"  "+ day.getYear());
        LocalDate monthend = day.plusDays((day.lengthOfMonth()) -day.getDayOfMonth());

        //clean List
        dateview.getItems().clear();
        for (; day.isBefore(monthend.plusDays(1)); day = day.plusDays(1)) {
            dateview.getItems().addAll(day);
        }
    }

    public void indietroclick(){
        month -= 1;
        getListViewDate();
    }

    public void avanticlick(){
        month += 1;
        getListViewDate();
    }
}
