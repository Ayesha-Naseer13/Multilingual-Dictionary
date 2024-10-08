package dto;

import java.util.Map;

public class DictionaryEntry {
    private String word;
    private Map<String, String> translations;

    public DictionaryEntry(String word, Map<String, String> translations) {
        this.word = word;
        this.translations = translations;
    }

    public String getWord() {
        return word;
    }

    public Map<String, String> getTranslations() {
        return translations;
    }

    @Override
    public String toString() {
        return word + " -> " + translations.toString();
    }
}
