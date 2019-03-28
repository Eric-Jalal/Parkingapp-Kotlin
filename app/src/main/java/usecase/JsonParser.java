package usecase;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

import entities.Location;

public class JsonParser {
    public Location loadJSONFromAsset(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open("data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Reader reader = new InputStreamReader(inputStream);
        final Gson gson = new Gson();
        Type location = new TypeToken<Location>(){}.getType();
        return gson.fromJson(reader, location);
    }
}
