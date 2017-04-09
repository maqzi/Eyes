package com.mealmaker.munaf.eyes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

/**
 * Created by munaf on 4/8/17.
 */

public class ImageRecognizer {

    // Provide your Client ID
    private final static String CLIENT_ID = "Ez1LtFnigj87GgP-J-ELx2SS-z4JCGI-bF8hxkR3";
    private final static String TAG = "IR";
    // Provider Your Client Secret Key
    private final static String CLIENT_SECRET_KEY = "v94OKWEcFnticobZmlqfs4bPWXVyZ7qG7uGm78Yj";

    public static List<String> recognize(byte[] imageUrl) {
        Log.i(TAG,"Entering Function");

        // Defining List Object
        List<String> resultList = new ArrayList<String>();

        if (imageUrl != null){// && !imageUrl.isEmpty()) {
            Log.i(TAG,"New List Created");

            final ClarifaiClient client = new ClarifaiBuilder(CLIENT_ID, CLIENT_SECRET_KEY).buildSync();
            Log.i(TAG,"Client Created");

            final List<ClarifaiOutput<Concept>> predictionResults =
                    client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(ClarifaiImage.of(imageUrl))
                            )
                            .executeSync()
                            .get();
            Log.i(TAG,"Response Got");

            if (predictionResults != null && predictionResults.size() > 0) {

                // Prediction List Iteration
                for (int i = 0; i < predictionResults.size(); i++) {

                    ClarifaiOutput<Concept> clarifaiOutput = predictionResults.get(i);

                    List<Concept> concepts = clarifaiOutput.data();

                    if (concepts != null && concepts.size() > 0) {
                        for (int j = 0; j < concepts.size(); j++) {

                            resultList.add(concepts.get(j).name());
                        }
                    }
                }
            }

        }
        return resultList;
    }
}

