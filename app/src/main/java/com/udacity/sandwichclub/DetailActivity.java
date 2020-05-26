package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
        TextView tvAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        TextView tvPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        TextView tvDescription = (TextView) findViewById(R.id.description_tv);
        TextView tvIngredients = (TextView) findViewById(R.id.ingredients_tv);

        TextView lblAlsoKnownas = (TextView) findViewById(R.id.lbl_also_known_as);
        TextView lblPlaceOfOrigin = (TextView) findViewById(R.id.lbl_place_of_origin);
        TextView lblDescription = (TextView) findViewById(R.id.lbl_description);
        TextView lblIngredients = (TextView) findViewById(R.id.lbl_ingredients);

        if (sandwich.getAlsoKnownAs().size() > 0) {
            tvAlsoKnownAs.setText(getStringList(sandwich.getAlsoKnownAs()));
            tvAlsoKnownAs.setVisibility(View.VISIBLE);
            lblAlsoKnownas.setVisibility(View.VISIBLE);
        }

        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            tvPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
            tvPlaceOfOrigin.setVisibility(View.VISIBLE);
            lblPlaceOfOrigin.setVisibility(View.VISIBLE);
        }

        if (!sandwich.getDescription().isEmpty()) {
            tvDescription.setText(sandwich.getDescription());
            tvDescription.setVisibility(View.VISIBLE);
            lblDescription.setVisibility(View.VISIBLE);
        }

        if (sandwich.getIngredients().size() > 0) {
            tvIngredients.setText(getStringList(sandwich.getIngredients()));
            tvIngredients.setVisibility(View.VISIBLE);
            lblIngredients.setVisibility(View.VISIBLE);
        }
    }

    private String getStringList(List<String> list) {
        StringBuilder strList = new StringBuilder();

        if (list.size() > 0) {
            strList.append(list.get(0));

            for (int i = 1; i < list.size(); i++) {
                strList.append(", " + list.get(i));
            }
        }

        return strList.toString();
    }
}
