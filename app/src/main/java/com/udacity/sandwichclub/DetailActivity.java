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

    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);

        placeOfOriginTextView.setText(getPlaceOfOrigin(sandwich));
        descriptionTextView.setText(getDescription(sandwich));
        alsoKnownAsTextView.setText(getAliases(sandwich));
        ingredientsTextView.setText(getIngredients(sandwich));
    }


    /**
     * Creates a String containing the place of origin (or a default text if unavailable or unknown)
     * from a Sandwich object
     *
     * @param sandwich The Sandwich object
     * @return String containing the origin
     */
    private String getPlaceOfOrigin(Sandwich sandwich) {
        String origin = sandwich.getPlaceOfOrigin();
        if ((origin == null) || (origin.contentEquals(""))) {
            origin = getString(R.string.detail_place_of_origin_default);
        }
        return origin;
    }

    /**
     * Creates a String containing all the known aliases (or a default text if unavailable or unknown)
     * from a Sandwich object
     *
     * @param sandwich The Sandwich object
     * @return String containing all the known aliases
     */
    private String getAliases(Sandwich sandwich) {
        List<String> stringList = sandwich.getAlsoKnownAs();
        String alias;
        if ((stringList == null) || (stringList.size() == 0)) {
            alias = getString(R.string.detail_also_known_as_default);
        } else {
            alias =
                    getStringFromStringList(stringList,
                            getString(R.string.string_list_separator_also_known_as));
        }
        return alias;
    }

    /**
     * Creates a String containing the description (or a default text if unavailable or unknown)
     * from a Sandwich object
     *
     * @param sandwich The Sandwich object
     * @return String containing the description
     */
    private String getDescription(Sandwich sandwich) {
        String description = sandwich.getDescription();
        if ((description == null) || (description.contentEquals(""))) {
            description = getString(R.string.detail_description_default);
        }
        return description;
    }

    /**
     * Creates a String containing the list of ingredients from a Sandwich object
     *
     * @param sandwich The Sandwich object
     * @return String containing the ingredients list comma-separated
     */
    private String getIngredients(Sandwich sandwich) {
        String ingredients;
        List<String> stringList = sandwich.getIngredients();

        if ((stringList == null) || (stringList.size() == 0)) {
            ingredients = getString(R.string.detail_ingredients_default);
        } else {
            ingredients =
                    getStringFromStringList(stringList,
                            getString(R.string.string_list_separator_ingredients));
        }
        return ingredients;
    }

    /**
     * Helper method that creates a String by concatenating the contents of a List of Strings,
     * separating the members with the String provided to this means
     *
     * @param stringList List of Strings
     * @param separator String to separate the strings in the stringList
     * @return String built with the content of the List of String separated with the specified separator
     */
    private String getStringFromStringList(List<String> stringList, String separator) {
        return TextUtils.join(separator, stringList);
    }
}
