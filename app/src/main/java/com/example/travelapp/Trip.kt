package com.example.travelapp

data class Trip(
    val passengerName: String,
    val dateOfTravel: String,
    val fromLocation: String,
    val toLocation: String,
    val cabinClass: String
) {
    val details: String
        get() = "Passenger: $passengerName\nDate of Travel: $dateOfTravel\nFrom: $fromLocation\nTo: $toLocation\nCabin Class: $cabinClass"
}
