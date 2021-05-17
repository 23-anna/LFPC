package com.work.task;

// Variant 17

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Main {

    public static ArrayList<String> firstTable(HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, String presentKey){

        ArrayList<String> firstTab = new ArrayList<>();

        Stack<String> stack = new Stack<>();
        stack.push(presentKey);

        while (!(stack.empty())){
            presentKey = stack.pop();

            for (int i = 0; i < grammar.get(presentKey).size(); i++){

                int k = 0;

                if (!(firstTab.isEmpty())) {

                    for (int j = 0; j < firstTab.size(); j++) {
                        if (Character.toString(grammar.get(presentKey).get(i).charAt(0)).equals(firstTab.get(j))) {
                            k++;
                        }
                    }

                    if (k == 0) {
                        firstTab.add(Character.toString(grammar.get(presentKey).get(i).charAt(0)));
                        for (int j = 0; j < keys.size(); j++) {
                            if (Character.toString(grammar.get(presentKey).get(i).charAt(0)).equals(keys.get(j))) {
                                stack.push(keys.get(j));
                            }
                        }
                    }

                } else {
                    firstTab.add(Character.toString(grammar.get(presentKey).get(i).charAt(0)));
                    for (int j = 0; j < keys.size(); j++) {
                        if (Character.toString(grammar.get(presentKey).get(i).charAt(0)).equals(keys.get(j))) {
                            stack.push(keys.get(j));
                        }
                    }
                }
            }
        }

        return firstTab;
    }

    public static ArrayList<String> lastTable(HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, String presentKey){

        ArrayList<String> lastTab = new ArrayList<>();

        Stack<String> stack = new Stack<>();
        stack.push(presentKey);

        while (!(stack.empty())){
            presentKey = stack.pop();

            for (int i = 0; i < grammar.get(presentKey).size(); i++){

                int k = 0;

                if (!(lastTab.isEmpty())) {

                    for (int j = 0; j < lastTab.size(); j++) {
                        if (Character.toString(grammar.get(presentKey).get(i).charAt(grammar.get(presentKey).get(i).length()-1)).equals(lastTab.get(j))) {
                            k++;
                        }
                    }

                    if (k == 0) {
                        lastTab.add(Character.toString(grammar.get(presentKey).get(i).charAt(grammar.get(presentKey).get(i).length()-1)));
                        for (int j = 0; j < keys.size(); j++) {
                            if (Character.toString(grammar.get(presentKey).get(i).charAt(grammar.get(presentKey).get(i).length()-1)).equals(keys.get(j))) {
                                stack.push(keys.get(j));
                            }
                        }
                    }

                } else {
                    lastTab.add(Character.toString(grammar.get(presentKey).get(i).charAt(grammar.get(presentKey).get(i).length()-1)));
                    for (int j = 0; j < keys.size(); j++) {
                        if (Character.toString(grammar.get(presentKey).get(i).charAt(grammar.get(presentKey).get(i).length()-1)).equals(keys.get(j))) {
                            stack.push(keys.get(j));
                        }
                    }
                }
            }
        }

        return lastTab;
    }

    public static void equalPrecedenceSign(String[][] matrix, HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, HashMap<String, Integer> matrixkeys){

        for (int i = 0; i < keys.size(); i++){
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){
                if (grammar.get(keys.get(i)).get(j).length() > 1){
                    for (int k = 0; k < grammar.get(keys.get(i)).get(j).length()-1; k++){
                        int key1 = matrixkeys.get(Character.toString(grammar.get(keys.get(i)).get(j).charAt(k)));
                        int key2 = matrixkeys.get(Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)));
                        matrix[key1][key2] = "=";
                    }
                }
            }
        }
    }

    public static void lessPrecedenceSign(String[][] matrix, HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, HashMap<String, Integer> matrixkeys){

        for (int i = 0; i < keys.size(); i++){
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){
                if (grammar.get(keys.get(i)).get(j).length() > 1){
                    for (int k = 0; k < grammar.get(keys.get(i)).get(j).length()-1; k++){
                        for (int h = 0; h < keys.size(); h++){
                            if (Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)).equals(keys.get(h))){
                                int key1 = matrixkeys.get(Character.toString(grammar.get(keys.get(i)).get(j).charAt(k)));
                                ArrayList<String> firstTab;
                                firstTab = firstTable(grammar,keys,Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)));
                                for (int g = 0; g < firstTab.size(); g++){
                                    int key2 = matrixkeys.get(firstTab.get(g));
                                    matrix[key1][key2] = "<";
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void greaterPrecedenceSign(String[][] matrix, HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys, HashMap<String, Integer> matrixkeys){

        for (int i = 0; i < keys.size(); i++){
            for (int j = 0; j < grammar.get(keys.get(i)).size(); j++){
                if (grammar.get(keys.get(i)).get(j).length() > 1){
                    for (int k = 0; k < grammar.get(keys.get(i)).get(j).length()-1; k++){
                        for (int h = 0; h < keys.size(); h++){
                            if (Character.toString(grammar.get(keys.get(i)).get(j).charAt(k)).equals(keys.get(h))){

                                ArrayList<String> lastTab;
                                lastTab = lastTable(grammar,keys,Character.toString(grammar.get(keys.get(i)).get(j).charAt(k)));

                                int m = 0;

                                for (int g = 0; g < keys.size(); g++){
                                    if (keys.get(g).equals(Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)))){
                                        m++;
                                    }
                                }

//                              first case
                                if (m == 0){
                                    for (int g = 0; g < lastTab.size(); g++){
                                        int key1 = matrixkeys.get(lastTab.get(g));
                                        int key2 = matrixkeys.get(Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)));
                                        matrix[key1][key2] = ">";
                                    }
                                } else { // second case
                                    for (int g = 0; g < lastTab.size(); g++){
                                        int key1 = matrixkeys.get(lastTab.get(g));
                                        ArrayList<String> firstTab;
                                        firstTab = firstTable(grammar,keys,Character.toString(grammar.get(keys.get(i)).get(j).charAt(k+1)));
                                        for (int n = 0; n < firstTab.size(); n++){
                                            int key2 = matrixkeys.get(firstTab.get(n));
                                            matrix[key1][key2] = ">";
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static String stringProcessing(String string, String[][] matrix, HashMap<String, Integer> matrixkeys){

        String tmp;
        tmp = Character.toString(string.charAt(0));

        for (int i = 0; i < string.length()-1; i++){

            System.out.println(string.charAt(i) + " string.charAt(i)");
            System.out.println(string.charAt(i+1) + " string.charAt(i+1)");

            if (string.charAt(i+1) == '='){
                i++;
            } else {
                int key1 = matrixkeys.get(Character.toString(string.charAt(i)));
                System.out.println(key1 + " key1");
                int key2 = matrixkeys.get(Character.toString(string.charAt(i + 1)));
                System.out.println(key2 + " key2");
                tmp = tmp + matrix[key1][key2] + string.charAt(i + 1);
                System.out.println(tmp + " tmp");
            }
        }

        string = tmp;
        System.out.println(string);

        return string;
    }

    public static String symbolChange(String string, String[][] matrix, HashMap<String, Integer> matrixkeys, HashMap<String, ArrayList<String>> grammar, ArrayList<String> keys){
        Stack<Character> stack = new Stack<>();
        String tmp = "";
        String tag;
        int i = 0;

        for (i = 0; i < string.length()-1; i++){

            stack.push(string.charAt(i));

            if (string.charAt(i) == '>') {
                stack.pop();
                i--;
                while(stack.peek() != '<') {
                    tmp = stack.peek() + tmp;
                    stack.pop();
                    i--;
                }
                break;
            }
        }

        if (tmp.contains("=")){
            tmp = tmp.replace("=", "");
        }

        for (int k = 0; k < keys.size(); k++){
            for (int j = 0; j < grammar.get(keys.get(k)).size(); j++){
                if (tmp.equals(grammar.get(keys.get(k)).get(j))){
                    tag = keys.get(k);
                    string = string.substring(0,i) + tag + string.substring(i+tmp.length()+2);
                    break;
                }
            }
        }

        string = string.replace("<","");
        string = string.replace(">","");

        return string;
    }


    public static void main(String[] args) {

        String string = "dabacbacbac";

        HashMap<String, Integer> matrixkeys = new HashMap<String, Integer>();
        matrixkeys.put("S",0);
        matrixkeys.put("B",1);
        matrixkeys.put("A",2);
        matrixkeys.put("D",3);
        matrixkeys.put("a",4);
        matrixkeys.put("b",5);
        matrixkeys.put("c",6);
        matrixkeys.put("d",7);
        matrixkeys.put("$",8);

        ArrayList<String> terminals = new ArrayList<>();
        terminals.add("a");
        terminals.add("b");
        terminals.add("c");
        terminals.add("d");

        HashMap<String, ArrayList<String>> grammar = new HashMap<String, ArrayList<String>>();
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> productionsS = new ArrayList<>();
        ArrayList<String> productionsA = new ArrayList<>();
        ArrayList<String> productionsB = new ArrayList<>();
        ArrayList<String> productionsD = new ArrayList<>();

        keys.add("S");
        keys.add("A");
        keys.add("B");
        keys.add("D");

        System.out.println(keys.get(2));

        productionsS.add("dB");
        grammar.put(keys.get(0), productionsS);

        productionsA.add("Dc");
        grammar.put(keys.get(1), productionsA);

        productionsB.add("a");
        productionsB.add("aA");
        grammar.put(keys.get(2), productionsB);

        productionsD.add("bB");
        grammar.put(keys.get(3), productionsD);

        System.out.println("Input grammar: " + grammar.entrySet());

        String[][] matrix = new String[9][9];

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (j == 8 && j != i){
                    matrix[i][j] = ">";
                } else if (i == 8 && j != i){
                    matrix[i][j] = "<";
                } else {
                    matrix[i][j] = "";
                }
            }
        }

        equalPrecedenceSign(matrix, grammar, keys, matrixkeys);
        lessPrecedenceSign(matrix, grammar, keys, matrixkeys);
        greaterPrecedenceSign(matrix, grammar, keys, matrixkeys);

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (matrix[i][j].equals("")){
                    System.out.print("-" + " ");
                }else{
                    System.out.print(matrix[i][j] + " ");
                }
            }
            System.out.println();
        }

        string = "$" + string + "$";
        System.out.println(string);

        string = stringProcessing(string, matrix, matrixkeys);
        string = symbolChange(string, matrix, matrixkeys, grammar, keys);


        try{
            while (!(string.equals("$S$"))) {
                string = stringProcessing(string, matrix, matrixkeys);
                System.out.println(string);
                while (string.contains("="))
                    string = symbolChange(string, matrix, matrixkeys, grammar, keys);
                System.out.println(string);
            }
        } catch (NullPointerException e){
            System.out.println("This word cannot be parsed");
            System.exit(0);
        }

        stringProcessing(string, matrix, matrixkeys);
        System.out.println("The word has been successfully parsed");
    }
}
