package com.example.rottenbanana;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class RatingsFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private ListView lvMovies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ratings, container, false);

        dbHelper = new DatabaseHelper(requireContext());
        lvMovies = view.findViewById(R.id.lv_movies);

        displayMovies();

        return view;
    }

    private void displayMovies() {
        // Fetch the list of movies from the database
        List<Movie> movieList = dbHelper.getAllMovies();
        List<String> movieStrings = new ArrayList<>();

        // Format how each movie will look in the list item
        for (Movie movie : movieList) {
            movieStrings.add(movie.getTitle() + " (" + movie.getYear() + ")\nGenre: " + movie.getGenre());
        }

        // Bind the list array to the basic Android text list item layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                movieStrings
        );

        lvMovies.setAdapter(adapter);
    }
}
