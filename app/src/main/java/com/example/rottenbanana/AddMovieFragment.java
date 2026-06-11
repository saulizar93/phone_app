package com.example.rottenbanana;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddMovieFragment extends Fragment {

    private EditText etTitle, etYear, etDescription;
    private Spinner spinnerGenre;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_movie, container, false);

        // Initialize Database Helper
        dbHelper = new DatabaseHelper(requireContext());

        etTitle = view.findViewById(R.id.et_title);
        etYear = view.findViewById(R.id.et_year);
        etDescription = view.findViewById(R.id.et_description);
        spinnerGenre = view.findViewById(R.id.spinner_genre);

        // Populate genre spinner
        String[] genres = {
                "Select a Genre",
                "Action", "Adventure", "Animation", "Comedy", "Crime",
                "Documentary", "Drama", "Fantasy", "Horror", "Musical",
                "Mystery", "Romance", "Sci-Fi", "Thriller", "Western"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                genres
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(adapter);

        Button btnSubmit = view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(v -> handleSubmit());

        return view;
    }

    private void handleSubmit() {
        String title = etTitle.getText().toString().trim();
        String year = etYear.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String genre = spinnerGenre.getSelectedItem().toString();

        if (title.isEmpty()) {
            etTitle.setError("Please enter a movie title");
            etTitle.requestFocus();
            return;
        }
        if (year.isEmpty()) {
            etYear.setError("Please enter a release year");
            etYear.requestFocus();
            return;
        }
        if (genre.equals("Select a Genre")) {
            Toast.makeText(getContext(), "Please select a genre", Toast.LENGTH_SHORT).show();
            return;
        }
        if (description.isEmpty()) {
            etDescription.setError("Please enter a short description");
            etDescription.requestFocus();
            return;
        }

        // Create a new Movie object
        Movie newMovie = new Movie(title, year, genre, description);

        // Insert into SQLite database
        boolean isSuccess = dbHelper.addMovie(newMovie);

        if (isSuccess) {
            Toast.makeText(getContext(),
                    "\"" + title + "\" saved to database successfully! 🍌",
                    Toast.LENGTH_LONG).show();

            // Clear form fields
            etTitle.setText("");
            etYear.setText("");
            etDescription.setText("");
            spinnerGenre.setSelection(0);
        } else {
            Toast.makeText(getContext(), "Error saving movie. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
