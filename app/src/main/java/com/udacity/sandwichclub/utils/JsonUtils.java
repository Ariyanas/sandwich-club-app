package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            final JSONObject data = new JSONObject(json);

            final JSONObject name = data.getJSONObject("name");

            return new Sandwich(name.getString("mainName"), getList(name.getJSONArray("alsoKnownAs")),
                    data.getString("placeOfOrigin"), data.getString("description"),
                    data.getString("image"), getList(data.getJSONArray("ingredients")));
        } catch (Exception e) {
            return null;
        }
    }

    private static List<String> getList(JSONArray arr) {
        List<String> list = new ArrayList<>();

        try {
            if (arr != null) {
                for(int i = 0; i < arr.length(); i++) {
                    list.add(arr.getString(i));
                }
            }
        } catch (Exception e) {
            Log.e("LISTERROR", "Error getting list", e);
        }

        return list;
    }
}
