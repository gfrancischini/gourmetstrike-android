package br.francischini.gourmetstrike.model;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import br.francischini.util.FileUtils;

/**
 * Created by gabriel on 11/24/15.
 */
public class Strike {
    private static final String STRIKE_CONFIG_FILE_PATH = "json/config_en.json";
    private static final String STRIKE_WORDS_FILE_PATH = "json/words_en.json";
    Random r = new Random();

    private enum CATEGORY {
        ADJETIVO_CHEFE,
        ADJETIVO_VAZIO,
        INGREDIENTE,
        INICIO_FRASE,
        OBJETO_ELOGIO,
        REGIONAL,
        UNIDADE
    }


    /**
     * The formulas to create the phrases
     */
    List<String> formulas = new ArrayList<>();

    /**
     * The wordForFoods to create the phrases
     */
    List<String> wordsForFoods = new ArrayList<>();

    /**
     * The words to create the phrases
     */
    HashMap<Integer, String[]> words = new HashMap<Integer, String[]>();

    Context context;

    /**
     * Construct a new Strike class
     *
     * @param context
     */
    public Strike(String language, Context context) {
        this.context = context;

        loadConfig();
        loadWords();
    }

    /**
     * Loads the configuration to create new phrases
     */
    private void loadConfig() {
        String fileText = FileUtils.loadJSONFromAsset(STRIKE_CONFIG_FILE_PATH, context);

        try {
            JSONObject jsonObject = new JSONObject(fileText);
            JSONArray formulas = jsonObject.getJSONArray("FORMULA");
            JSONArray wordsForFoods = jsonObject.getJSONArray("WORDS_FOR_FOOD");


            for (int i = 0; i < formulas.length(); i++) {
                this.formulas.add(formulas.getString(i));
            }
            for (int i = 0; i < wordsForFoods.length(); i++) {
                this.wordsForFoods.add(wordsForFoods.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * Loads the words to create new phrases
     */
    private void loadWords() {
        String fileText = FileUtils.loadJSONFromAsset(STRIKE_WORDS_FILE_PATH, context);

        try {
            JSONObject jsonObject = new JSONObject(fileText);
            for (CATEGORY category : CATEGORY.values()) {
                JSONArray categoryArray = jsonObject.getJSONArray(category.name());

                String strings[] = new String[categoryArray.length()];
                for (int i = 0; i < categoryArray.length(); i++) {
                    strings[i] = categoryArray.getString(i);
                }
                this.words.put(category.ordinal(), strings);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String randomWordForFood() {
        int index = r.nextInt(wordsForFoods.size() - 1);
        return wordsForFoods.get(index);
    }

    private String randomPhrase() {
        int index = r.nextInt(formulas.size() - 1);
        return formulas.get(index);
    }

    private String phraseForFormula(String formula) {
        String[] categories = formula.split(",");
        String phrase = "";
        for (String category : categories) {
            String[] categoryWords = this.words.get(CATEGORY.valueOf(category).ordinal());
            phrase = phrase + categoryWords[r.nextInt(categoryWords.length - 1)] + " ";
        }
        return phrase;
    }


    public String getPhrase() {
        return phraseForFormula(randomPhrase());
    }

    public String getWordForFood() {
        return randomWordForFood();
    }

}
