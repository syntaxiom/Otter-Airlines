package com.example.smlwc.otterairlines3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.smlwc.otterairlines3.Reservation.Reservation;
import com.example.smlwc.otterairlines3.Reservation.ReservationManager;

public class BookActivity extends AppCompatActivity {

    // UI components
    Toolbar toolbar;
    Spinner departureCitySpinner;
    Spinner arrivalCitySpinner;
    EditText numberOfTicketsEditText;
    FloatingActionButton fab;

    // Other necessary stuff
    String[] cities = new String[]{"Monterey", "Los Angeles", "Seattle"};
    ArrayAdapter<String> adapterArrivalCities;
    ArrayAdapter<String> adapterDepartureCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Set title on startup
        setTitle("Book reservation");

        // Get UI components
        toolbar = findViewById(R.id.toolbar);
        departureCitySpinner = findViewById(R.id.departureCitySpinner);
        arrivalCitySpinner = findViewById(R.id.arrivalCitySpinner);
        numberOfTicketsEditText = findViewById(R.id.numberOfTicketsEditText);
        fab = findViewById(R.id.fab);

        // Get other necessary stuff

        // Set toolbar properties
        setSupportActionBar(toolbar);

        // Departure city spinner properties
        adapterDepartureCities = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        departureCitySpinner.setAdapter(adapterDepartureCities);

        // Arrival city spinner properties
        adapterArrivalCities = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cities);
        arrivalCitySpinner.setAdapter(adapterArrivalCities);

        // FAB click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNumberOfTicketsEmpty()) {
                    Toaster.ticketsLeftBlankToast();
                }
                else {
                    String departureCity = getDepartureCity();
                    String arrivalCity = getArrivalCity();
                    int numberOfTickets = getNumberOfTickets();

                    if (departureCity.equals(arrivalCity)) {
                        Toaster.citiesMustDifferToast();
                    }
                    else if (numberOfTickets < 1) {
                        Toaster.atLeast1TicketToast();
                    }
                    else {
                        int ticketsAvailable = ReservationManager.getTickets(departureCity, arrivalCity);

                        if (numberOfTickets > ticketsAvailable) {
                            Toaster.notEnoughTicketsToast();
                        }
                        else {
                            
                        }
                    }
                }
            }
        });
    }

    private boolean isNumberOfTicketsEmpty() {
        return numberOfTicketsEditText.getText().toString().isEmpty();
    }

    private int getNumberOfTickets() {
        return Integer.parseInt(numberOfTicketsEditText.getText().toString());
    }

    private String getDepartureCity() {
        return departureCitySpinner.getSelectedItem().toString();
    }

    private String getArrivalCity() {
        return arrivalCitySpinner.getSelectedItem().toString();
    }
}
