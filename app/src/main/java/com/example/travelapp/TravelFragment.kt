package com.example.travelapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class TravelFragment : Fragment() {

    private lateinit var spinnerFromLocation: Spinner
    private lateinit var spinnerToLocation: Spinner
    private lateinit var radioGroupCabinClass: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_travel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etPassengerName: EditText = view.findViewById(R.id.et_passenger_name)
        val etDateOfTravel: EditText = view.findViewById(R.id.et_date_of_travel)
        val btnBookItinerary: Button = view.findViewById(R.id.btn_book_itinerary)

        spinnerFromLocation = view.findViewById(R.id.FromSpinner)
        spinnerToLocation = view.findViewById(R.id.ToSpinner)
        radioGroupCabinClass = view.findViewById(R.id.radioGroupCabinClass)

        // Initialize spinners with locations
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.from_locations,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFromLocation.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.to_locations,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerToLocation.adapter = adapter
        }

        etDateOfTravel.setOnClickListener {
            // Show date picker dialog
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                etDateOfTravel.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)
            datePickerDialog.show()
        }

        btnBookItinerary.setOnClickListener {
            val passengerName = etPassengerName.text.toString()
            val dateOfTravel = etDateOfTravel.text.toString()
            val fromLocation = spinnerFromLocation.selectedItem.toString()
            val toLocation = spinnerToLocation.selectedItem.toString()
            val cabinClass = when (radioGroupCabinClass.checkedRadioButtonId) {
                R.id.radio_economy -> "Economy"
                R.id.radio_business -> "Business"
                R.id.radio_first -> "First"
                else -> ""
            }

            if (passengerName.isNotEmpty() && dateOfTravel.isNotEmpty() && cabinClass.isNotEmpty()) {
                val trip = Trip(passengerName, dateOfTravel, fromLocation, toLocation, cabinClass)
                TripRepository.addTrip(trip)

                // Show success message
                Toast.makeText(context, "Trip saved successfully", Toast.LENGTH_SHORT).show()

                // Clear input fields
                etPassengerName.text.clear()
                etDateOfTravel.text.clear()
                spinnerFromLocation.setSelection(0)
                spinnerToLocation.setSelection(0)
                radioGroupCabinClass.clearCheck()
            } else {
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun saveTrip(passengerName: String, dateOfTravel: String, fromLocation: String, toLocation: String, cabinClass: String) {
        val trip = Trip(passengerName, dateOfTravel, fromLocation, toLocation, cabinClass)
        TripRepository.addTrip(trip)

        // Show success message
        Toast.makeText(context, "Trip saved successfully", Toast.LENGTH_SHORT).show()

        // Clear input fields
        view?.findViewById<EditText>(R.id.et_passenger_name)?.text?.clear()
        view?.findViewById<EditText>(R.id.et_date_of_travel)?.text?.clear()
        spinnerFromLocation.setSelection(0)
        spinnerToLocation.setSelection(0)
        radioGroupCabinClass.clearCheck()
    }
}
