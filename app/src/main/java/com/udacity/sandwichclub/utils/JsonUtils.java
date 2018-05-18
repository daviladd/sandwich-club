package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String TAG = JsonUtils.class.getSimpleName();

    // Strings used in the JSON structure for Sandwich
    private final static String JSON_SANDWICH_NAME = "name";
    private final static String JSON_SANDWICH_NAME_MAIN_NAME = "mainName";
    private final static String JSON_SANDWICH_NAME_AKA = "alsoKnownAs";
    private final static String JSON_SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String JSON_SANDWICH_DESCRIPTION = "description";
    private final static String JSON_SANDWICH_IMAGE = "image";
    private final static String JSON_SANDWICH_INGREDIENTS = "ingredients";


    /**
     * Parses a String containing a JSON formatted Sandwich structure.
     * <p>
     * The JSON format for a Sandwich is the following:
     * {
     * "name": {
     * "mainName" : String,
     * "alsoKnownAs" : String[]
     * },
     * "placeOfOrigin" : String,
     * "description" : String,
     * "image": String,
     * "ingredients": String[]
     * }
     *
     * @param json The JSON String to be parsed
     * @return A Sandwich object created using the JSON data (or null if something failed)
     */

    public static Sandwich parseSandwichJson(String json) {
        try {
            // Create the base JSONObject:
            JSONObject jsonSandwich = new JSONObject(json);

            // Get all the object's members:
            String mainName = getMainName(jsonSandwich);
            List<String> alsoKnownAs = getAlsoKnownAs(jsonSandwich);
            String placeOfOrigin = getPlaceOfOrigin(jsonSandwich);
            String description = getDescription(jsonSandwich);
            String image = getImage(jsonSandwich);
            List<String> ingredients = getIngredients(jsonSandwich);

            // Create the Sandwich object and return it:
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, "Parsing the JSON Sandwich structure failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the "mainName" value from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String containing the value of "mainName"
     * @throws JSONException if failed getting the String from the JSONObject
     */
    private static String getMainName(JSONObject jsonSandwich) throws JSONException {
        JSONObject jsonName = jsonSandwich.getJSONObject(JSON_SANDWICH_NAME);
        return jsonName.getString(JSON_SANDWICH_NAME_MAIN_NAME);
    }

    /**
     * Gets the "alsoKnownAs" values from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String List containing all the values of "alsoKnownAs"
     * @throws JSONException if failed creating the JSONObject or the JSONArray
     */
    private static List<String> getAlsoKnownAs(JSONObject jsonSandwich) throws JSONException {
        JSONObject jsonName = jsonSandwich.getJSONObject(JSON_SANDWICH_NAME);
        JSONArray jsonArray = jsonName.getJSONArray(JSON_SANDWICH_NAME_AKA);
        return getStringListFromJsonArray(jsonArray);
    }

    /**
     * Gets the "placeOfOrigin" value from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String containing the value of "placeOfOrigin"
     * @throws JSONException if failed getting the String from the JSONObject
     */
    private static String getPlaceOfOrigin(JSONObject jsonSandwich) throws JSONException {
        return jsonSandwich.getString(JSON_SANDWICH_PLACE_OF_ORIGIN);
    }

    /**
     * Gets the "description" value from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String containing the value of "description"
     * @throws JSONException if failed getting the String from the JSONObject
     */
    private static String getDescription(JSONObject jsonSandwich) throws JSONException {
        return jsonSandwich.getString(JSON_SANDWICH_DESCRIPTION);
    }

    /**
     * Gets the "image" value -the path to an image- from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String containing the value of "image"
     * @throws JSONException if failed getting the String from the JSONObject
     */
    private static String getImage(JSONObject jsonSandwich) throws JSONException {
        return jsonSandwich.getString(JSON_SANDWICH_IMAGE);
    }

    /**
     * Gets the "ingredients" values from the JSON Sandwich structure
     *
     * @param jsonSandwich the root JSON object
     * @return A String List containing all the values of "ingredients"
     * @throws JSONException if failed creating the JSONArray
     */
    private static List<String> getIngredients(JSONObject jsonSandwich) throws JSONException {
        JSONArray jsonArray = jsonSandwich.getJSONArray(JSON_SANDWICH_INGREDIENTS);
        return getStringListFromJsonArray(jsonArray);
    }

    /**
     * Returns a List of Strings created from the members of a JSONArray
     *
     * @param jsonArray the original JSONArray
     * @return A List fo Strings with the values of retrieved from the JSONArray
     * @throws JSONException if failed getting the Strings from the JSONArray
     */
    private static List<String> getStringListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<String> stringList = new ArrayList<>();
        for (int item = 0; item < jsonArray.length(); item++) {
            stringList.add(jsonArray.getString(item));
        }
        return stringList;
    }
}
