package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // constants for key strings in JSON
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String INGREDIENTS = "ingredients";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {

        JSONObject jsonObject;
        JSONObject nameJsonObject;
        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();
        Sandwich sandwich;

        sandwich = null;
        try {
            jsonObject = new JSONObject(json);

            nameJsonObject = jsonObject.getJSONObject(NAME);
            mainName = nameJsonObject.getString(MAIN_NAME);

            placeOfOrigin = jsonObject.getString(PLACE_OF_ORIGIN);
            description = jsonObject.getString(DESCRIPTION);

            image = jsonObject.getString(IMAGE);

            // Use JSONArray
            JSONArray alsoKnownAsArray = nameJsonObject.getJSONArray(ALSO_KNOWN_AS);
            int i = 0;
            while (i < alsoKnownAsArray.length()) {
                String alsoKnownAsString = alsoKnownAsArray.getString(i);
                alsoKnownAs.add(alsoKnownAsString);
                i++;
            }

            // Use JSONArray for list of ingredients
            JSONArray ingredientsArray = jsonObject.getJSONArray(INGREDIENTS);
            int j = 0;
            while (j < ingredientsArray.length()) {
                String ingredientsString = ingredientsArray.getString(j);
                ingredients.add(ingredientsString);
                j++;
            }

            //Make Sandwich with elements
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
