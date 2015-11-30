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
    private static final String STRIKE_JSON_PATH = "json/";
    private static final String STRIKE_CONFIG_FILE_NAME = "config.json";
    private static final String STRIKE_WORDS_FILE_NAME = "words.json";

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
     * The current strike language
     */
    String language;

    /**
     * Random class
     */
    Random r = new Random();

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
        this.language = language;
        loadConfig();
        loadWords();
    }

    /**
     * @return the config file path at assets folder
     */
    private String getConfigFilePath() {
        return STRIKE_JSON_PATH + language + "/" + STRIKE_CONFIG_FILE_NAME;
    }

    /**
     * @return the word file path at assets folder
     */
    private String getWordsFilePath() {
        return STRIKE_JSON_PATH + language + "/" + STRIKE_WORDS_FILE_NAME;
    }

    /**
     * Loads the configuration to create new phrases
     */
    private void loadConfig() {
        String fileText = FileUtils.loadJSONFromAsset(getConfigFilePath(), context);

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
        String fileText = FileUtils.loadJSONFromAsset(getWordsFilePath(), context);

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

    /**
     *
     * @return a random word for food
     */
    private String randomWordForFood() {
        int index = r.nextInt(wordsForFoods.size() - 1);
        return wordsForFoods.get(index);
    }

    /**
     *
     * @return a random phrase
     */
    private String randomPhrase() {
        int index = r.nextInt(formulas.size() - 1);
        return formulas.get(index);
    }

    /**
     * Create a random phrase based on a fomula
     * @param formula The formula to create the phrase
     * @return a complete gourmet phrase
     */
    private String phraseForFormula(String formula) {
        String[] categories = formula.split(",");
        String phrase = "";
        for (String category : categories) {
            String[] categoryWords = this.words.get(CATEGORY.valueOf(category).ordinal());
            phrase = phrase + categoryWords[r.nextInt(categoryWords.length - 1)] + " ";
        }
        return phrase;
    }

    /**
     *
     * @return a gourmet phrase
     */
    public String getPhrase() {
        return phraseForFormula(randomPhrase());
    }

    /**
     *
     * @return a gourmet woord for food
     */
    public String getWordForFood() {
        return randomWordForFood();
    }

}
