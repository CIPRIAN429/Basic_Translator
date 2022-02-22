package com.company;

import java.util.*;

public class Methods extends Main {
    boolean addWord(Word word, String language){
        if(!map.containsKey(language)) { // cazul in care adaug un cuvant dintr-o limba noua
            ArrayList<Word> lista = new ArrayList<>();
            lista.add(word);
            map.put(language, lista);
            return true;
        }
        ArrayList<Word> array = map.get(language);
        for(Word word_1 : array){
            if(word_1.word.equals(word.word)){
                return false;
            }
        }
        array.add(word);
        map.put(language, array);
        return true;

    }
    boolean removeWord(String word, String language){
        ArrayList<Word> array = map.get(language);
        for(Word word_1 : array){
            if(word_1.word.equals(word)){
                array.remove(word_1);
                return true;
            }
        }
        return false;
    }

    boolean addDefinitionForWord(String word, String language, Definition definition){
        ArrayList<Word> array = map.get(language);
        for(Word word_1 : array){
            if(word_1.word.equals(word)){
                Definition[] definitions = word_1.definitions;
                for(Definition definition1: definitions){
                    if(definition1.dict.equals(definition.dict)){
                        return false;
                    }
                }
                ArrayList<Definition> arrayList = new ArrayList<>(Arrays.asList(definitions));
                arrayList.add(definition);
                definitions = arrayList.toArray(definitions);
                return true;
            }
        }
        return false;
    }
    boolean removeDefinition(String word, String language, String dictionary){
        ArrayList<Word> array = map.get(language);
        for(Word word_1 : array){
            if(word_1.word.equals(word)){
                Definition[] definitions = word_1.definitions;
                for(Definition definition1: definitions){
                    if(definition1.dict.equals(dictionary)){
                        ArrayList<Definition> arrayList = new ArrayList<>(Arrays.asList(definitions));
                        arrayList.remove(definition1);
                        definitions = arrayList.toArray(definitions);
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    String translateWord(String word, String fromLanguage, String toLanguage){
        ArrayList<Word> array = map.get(fromLanguage);
        if(array == null)
            return "Nu s-a putut furniza o traducere";
        for(Word word_1 : array){
            if(word_1.word.equals(word)){
                String word_en = word_1.word_en;
                ArrayList<Word> array_2 = map.get(toLanguage);
                for(Word word_2 : array_2){
                    if(word_2.word_en.equals(word_en)){
                        return word_2.word;
                    }
                }
            }
        }
        return "Nu s-a putut furniza o traducere";
    }

    String translateSentence(String sentence, String fromLanguage, String toLanguage){
        String sentence_translated = "";
        String[] parts = sentence.split(" ");
        for(int i = 0; i < parts.length; i++){
            String word_translated = translateWord(parts[i], fromLanguage, toLanguage);
            if(word_translated.equals("Nu s-a putut furniza o traducere")){
                sentence_translated += parts[i];
            }
            else{
                sentence_translated += word_translated;
            }
            sentence_translated += " ";
        }
        return sentence_translated;
    }

    ArrayList<String> translateSentences(String sentence, String fromLanguage, String toLanguage){
        ArrayList<String> translated_sentences = new ArrayList<>();
        int nr = 0, ok;
        ArrayList<String[]> synonyms = new ArrayList<>();
        ArrayList<String> words = new ArrayList<>();
        String sentence_translated = "";
        String[] parts = sentence.split(" ");
        for(int i = 0; i < parts.length; i++){
            String word_translated = translateWord(parts[i], fromLanguage, toLanguage);
            ArrayList<Word> array = map.get(toLanguage);
            ok = 0;
            for(Word word : array){
                if(word.word.equals(word_translated)){
                    Definition[] definitions = word.definitions;
                    for(Definition definition: definitions){
                        if(definition.dictType.equals("synonyms")){
                            synonyms.add(definition.text);
                            ok = 1; //cuvantul are sinonime
                            words.add(null);
                            break;
                        }

                    }
                }
            }
            word_translated = parts[i];
            if(ok == 0){
                synonyms.add(null);
                words.add(word_translated);
            }
        }
        ok = 0;
        while(nr < 3){
            for(int i = 0; i < parts.length; i++){
                String[] s = synonyms.get(i);
                String w = words.get(i);
                if(s == null){
                   // daca un cuvant nu are sinonime nu putem forma o propozitie care sa contina sinonimele sale
                    sentence_translated += w;
                    sentence_translated += " ";

                }
                else {
                    sentence_translated += s[nr];
                    sentence_translated += " ";
                    ok = 1;
                }
            }
            nr++;
            translated_sentences.add(sentence_translated);
            sentence_translated = "";
            if(ok == 0){// nu exista sinonime
                break;
            }
        }
        return translated_sentences;
    }



    ArrayList<Definition> getDefinitionsForWord(String word, String language){
        ArrayList<Definition> definitions = new ArrayList<Definition>();
        ArrayList<Word> array = map.get(language);
        for(Word word1 : array){
            if(word1.word.equals(word)){
                definitions = new ArrayList<>(Arrays.asList(word1.definitions));
                Collections.sort(definitions, Definition::compareTo); //sortare definitii dupa an
            }
        }
        return definitions;

    }
}



