package com.company;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.*;

public class Main {

    static Map<String,ArrayList<Word>> map = new TreeMap<String, ArrayList<Word>>();
    public static void main(String[] args) throws FileNotFoundException {
        final File folder = new File("dictionaries"); //folderul in care am dictionarele
        final List<File> fileList = Arrays.asList(folder.listFiles());
        int i;
        for (i = 0; i < fileList.size(); i++) {
            String name = fileList.get(i).getName();
            String[] parts = name.split("_");
            String language = parts[0];
            JsonReader reader = new JsonReader(new FileReader(fileList.get(i)));
            Word[] words = new Gson().fromJson(reader, Word[].class);
            ArrayList<Word> array =new ArrayList<>(Arrays.asList(words));
            map.put(language, array);

        }

        // creez un cuvant nou
        String[] text = new String[]{"Scriere cu un anumit subiect, tiparita si legata sau brosata in volum", "Cunostinte de scriere si de citire", "Registru" };
        Definition definition = new Definition("DEX'09", "definitions", 2009, text);
        Definition[] definitions = new Definition[]{definition};
        String[] singular = new String[]{"carte"};
        String[] plural = new String[]{"cărți"};
        Word new_word = new Word("carte", "book", "noun", singular, plural, definitions);

        // recreez un cuvant care exista deja
        String[] text_old1 = new String[]{"mâță", "cotoroabă", "cătușă"};
        String[] text_old2 = new String[]{"Mamifer domestic carnivor din familia felinelor, cu corpul suplu, acoperit cu blană deasă și moale de diferite culori," +
                " cu capul rotund, cu botul foarte scurt, cu maxilarele puternice și cu ghearele retractile și ascuțite",
                "Dispozitiv de agățare și de desprindere a berbecului din capătul cablului de ridicare de la sonetele cu cădere liberă",
                "Mănunchi de sârme de oțel, folosit pentru curățarea de noroi sau de pământ a utilajelor de foraj"};

        Definition definition_old_1 = new Definition("Dicționar de sinonime", "synonyms", 1998, text_old1);
        Definition definition_old_2 = new Definition("Dicționarul explicativ al limbii române, ediția a II-a", "definitions", 2002, text_old2);
        Definition[] definitions_old = new Definition[]{definition_old_1, definition_old_2};
        String[] singular_old = new String[]{"pisică"};
        String[] plural_old = new String[]{"pisici"};
        Word old_word = new Word("pisică", "cat", "noun", singular_old, plural_old, definitions_old);

        Methods object_for_test = new Methods(); //pentru a imi testa metodele

        boolean task_2 = object_for_test.addWord(new_word, "ro");
        System.out.println("Task_2_cuvant nou: " + task_2);
        task_2 = object_for_test.addWord(old_word, "ro");
        System.out.println("Task_2_cuvant existent: " + task_2);
        System.out.println();

        boolean task_3 = object_for_test.removeWord("caiet", "ro");
        System.out.println("Task_3_cuvant nou: " + task_3);
        task_3 = object_for_test.removeWord("jeu", "fr");
        System.out.println("Task_3_cuvant existent: " + task_3);
        System.out.println();

        String[] new_text = new String[]{"Scrisoare", "diviziune mai mare decat un capitol a unei scrieri de proportii mari", "Registru"};
        Definition new_definition = new Definition("MDA2", "synonyms", 2010, new_text);
        boolean task_4 = object_for_test.addDefinitionForWord("carte", "ro", new_definition);
        System.out.println("Task_4_cuvant existent: " + task_4);
        task_4 = object_for_test.addDefinitionForWord("pisică", "ro", definition_old_1);
        System.out.println("Task_4_cuvant care are definitia deja: " + task_4);
        System.out.println();

        boolean task_5 = object_for_test.removeDefinition("carte", "ro", "DEX5");
        System.out.println("Task_5_definitie care nu exista: " + task_5);
        task_5 = object_for_test.removeDefinition("pisică", "ro", "Dicționar de sinonime");
        System.out.println("Task_5_definitie gasita: " + task_5);
        System.out.println();

        String task_6 = object_for_test.translateWord("chat", "fr", "ro");
        System.out.println("Task_6_traducere cuvant care exista in baza de date: " + task_6);
        task_6 = object_for_test.translateWord("câine", "ro", "fr");
        System.out.println("Task_6_traducere invalida: " + task_6);
        System.out.println();

        Word word_for_translation = new Word("manâncă", "eat", "noun", null, null, null);//un nou cuvant pentru traducere
        object_for_test.addWord(word_for_translation, "ro");
        String task_7 = object_for_test.translateSentence("pisică manâncă", "ro", "fr");
        System.out.println("Task_7_traducere propozitie complet: " + task_7);
        task_7 = object_for_test.translateSentence("pisică albă și dolofană", "ro", "fr");
        System.out.println("Task_7_traducere propozitie incomplet: " + task_7);
        System.out.println();

        ArrayList<String> task_8 = object_for_test.translateSentences("pisică manâncă", "ro", "fr");
        System.out.println("Task_8_traducere completa: " + task_8.toString());
        task_8 = object_for_test.translateSentences("merge carte", "ro", "fr");
        System.out.println("Task_8_traducere invalida: " + task_8.toString());
        System.out.println();

        ArrayList<Definition> task_9 = object_for_test.getDefinitionsForWord("câine", "ro");
        List<Integer> list1 = new ArrayList<>();
        for(Definition definition1 : task_9){
            if(definition1 != null) {
                list1.add(definition1.year);
            }
        }
        System.out.println("Task_9_anii de aparitie ai dictionarelor " + list1);
        task_9 = object_for_test.getDefinitionsForWord("raton", "ro");
        List<Integer> list2 = new ArrayList<>();
        for(Definition definition1 : task_9){
            if(definition1 != null) {
                list2.add(definition1.year);
            }
        }
        System.out.println("Task_9_nu s-a gasit cuvantul: " + list2);
        System.out.println();

        // afisare cuvinte din dictionare
        for(Map.Entry<String, ArrayList<Word> > intrare : map.entrySet() )
        {
            System.out.print(intrare.getKey() + "\n");
            ArrayList<Word> array = intrare.getValue();
            List<String> list = new ArrayList<>();
            for(Word word : array){
                list.add(word.word);
            }
            System.out.println(list);
        }
    }

}

