package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView placeOfOriginTv;
    private TextView alsoKnownAsTv;
    private TextView ingredientsTv;
    private TextView descriptionTv;
    private ImageView ingredientsIv;
    private TextView placeOfOriginLabel;
    private TextView alsoKnownAsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        descriptionTv = findViewById(R.id.description_tv);
        placeOfOriginLabel = findViewById(R.id.place_of_origin_label);
        alsoKnownAsLabel = findViewById(R.id.also_known_as_label);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        if (sandwich.getPlaceOfOrigin().isEmpty())
            placeOfOriginTv.setText("Unknown");
        else
            placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        if (alsoKnownAsList.isEmpty())
            alsoKnownAsTv.setText("N/A");
        else {
            String alsoKnownAsString = TextUtils.join(", ", alsoKnownAsList);
            alsoKnownAsTv.setText(alsoKnownAsString);
        }

        List<String> ingredientsList = sandwich.getIngredients();
        if (ingredientsList.isEmpty())
            ingredientsTv.setText("Unknown");
        else {
            String ingredientsString = TextUtils.join(", ", ingredientsList);
            ingredientsTv.setText(ingredientsString);
        }

        descriptionTv.setText(sandwich.getDescription());
    }
}
