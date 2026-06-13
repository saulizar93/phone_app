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
import android.app.AlertDialog;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class RatingsFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private ListView lvMovies;
    private List<Movie> movieList;

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
        movieList = dbHelper.getAllMovies();
        List<String> movieStrings = new ArrayList<>();

        for (Movie movie : movieList) {
            movieStrings.add(movie.getTitle() + " (" + movie.getYear() + ")\nGenre: " + movie.getGenre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                movieStrings
        );

        lvMovies.setAdapter(adapter);

        lvMovies.setOnItemLongClickListener((parent, view, position, id) -> {
            Movie selectedMovie = movieList.get(position);

            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Movie")
                    .setMessage("Delete \"" + selectedMovie.getTitle() + "\"?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        boolean deleted = dbHelper.deleteMovie(selectedMovie.getId());

                        if (deleted) {
                            Toast.makeText(requireContext(), "Movie deleted", Toast.LENGTH_SHORT).show();
                            displayMovies();
                        } else {
                            Toast.makeText(requireContext(), "Could not delete movie", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

            return true;
        });
    }
}
