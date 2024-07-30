package com.example.travelapp

object TripRepository {
    private val trips = mutableListOf<Trip>()

    fun addTrip(trip: Trip) {
        trips.add(trip)
    }

    fun getTrips(): List<Trip> {
        return trips
    }

    fun deleteTrip(trip: Trip) {
        trips.remove(trip)
    }
}
