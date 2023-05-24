package com.example.emple;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    private static final String TAG = "AddFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = requireContext(); //ensure this is always here. else, no context = many complications
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        Button addPostButton = view.findViewById(R.id.AddPost);
        TextInputEditText PostTitle = view.findViewById(R.id.PostTitle);
        EditText PostContent = view.findViewById(R.id.PostContent);
        Spinner PostDegreeChoice = view.findViewById(R.id.DegreesChoice);

        // Set the OnClickListener for the button
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String givenTitle = PostTitle.getText().toString();
                String givenContent = PostContent.getText().toString();
                String givenDegreeChoice = PostDegreeChoice.getSelectedItem().toString();

                if (givenTitle.isEmpty() || givenContent.isEmpty()) {
                    Toast.makeText(requireContext(), "Title or post content cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    handleAddPost(view, givenTitle, givenContent, givenDegreeChoice);
                }
            }
        });

        return view;
    }

    private void handleAddPost(View view, String PostTitle, String PostContent, String PostDegreeChoice) {
        Button button = (Button) view;

        //get the datetime it was created once add post is triggered
        String datetimeNow = getCurrentDateTime();

        Log.d(TAG, "handleAddPost: \n" + "Post Title: " + PostTitle + "Post Content: " + PostContent);

        // Insert the values into the database
        DatabaseHelper databaseHelper = new DatabaseHelper(requireContext());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Log.d(TAG, "DatabaseHelper created successfully.");
        if (db != null) {
            boolean isInserted = databaseHelper.addOne(PostTitle, PostContent, PostDegreeChoice, datetimeNow);

            if (isInserted) {
                // Insertion successful
                Toast.makeText(requireContext(), "Post added successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Insertion failed
                Toast.makeText(requireContext(), "Failed to add post", Toast.LENGTH_SHORT).show();
            }

            db.close(); // Close the database connection
        }
    }

    private String getCurrentDateTime() {
        // Get current datetime
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        return dateFormat.format(now);
    }
}