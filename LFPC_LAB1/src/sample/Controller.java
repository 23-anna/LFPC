package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane scene;

    @FXML
    private Region canvas;

    @FXML
    private Button checkButton;

    @FXML
    private TextField wordField;

    @FXML
    private TextArea resultField;

    @FXML
    private Circle nodeEnd;

    @FXML
    private Circle nodeB;

    @FXML
    private Circle nodeA;

    @FXML
    private Circle nodeS;

    @FXML
    void initialize() {

//      Variant 16
//      This is the grammar of type 3 - a right-linear Regular Grammar
        
//      **************************************************************
//      Definition of the Non-terminal symbols
        
        ArrayList<String> vn = new ArrayList<>();

        vn.add("S");
        vn.add("A");
        vn.add("B");
        vn.add("End");

//      **************************************************************
//      Creation of the Finite Automaton
        
        Graph FA = new Graph();

        FA.addNode(vn.get(0));
        FA.addNode(vn.get(1));
        FA.addNode(vn.get(2));
        FA.addNode(vn.get(3));

        FA.addEdge(vn.indexOf("S"), vn.indexOf("S"), 'b');
        FA.addEdge(vn.indexOf("S"), vn.indexOf("A"), 'd');
        FA.addEdge(vn.indexOf("A"), vn.indexOf("A"), 'a');
        FA.addEdge(vn.indexOf("A"), vn.indexOf("End"), 'b');
        FA.addEdge(vn.indexOf("A"), vn.indexOf("B"), 'd');
        FA.addEdge(vn.indexOf("B"), vn.indexOf("B"), 'c');
        FA.addEdge(vn.indexOf("B"), vn.indexOf("End"), 'a');

//      **************************************************************
//      Interaction with the Check button:
        
        checkButton.setOnAction(event -> {

            String word = wordField.getText(); // get string

            resultField.clear();

            int j = 0;
            int n = 0;
            int m = 0;
            int k = 0;

//          checking if the string belongs to the given grammar
            
            breakpoint:
            for (int i = 0; i < word.length(); i++){

                for (m = 0; m < vn.size(); m++){
                    if (word.charAt(i) == FA.matrix[n][m]){
                        resultField.insertText(resultField.getLength(), "Node " + FA.nodes.get(j).name + " -> " + word.charAt(i) + " -> " + " Node " + FA.nodes.get(m).name + "\n");

                        if ((m == vn.size()-1)&&(i < word.length()-1)){  // condition 1: there are left no symbols in the word after the End node has been reached
                            resultField.insertText(resultField.getLength(), "Node " + FA.nodes.get(m).name + " -> " + word.charAt(i+1) + " -> " + " ?\n");
                            resultField.insertText(resultField.getLength(), "The End node has been already reached\n");
                            break breakpoint;
                        }

                        if ((i == word.length()-1)&&(!FA.nodes.get(m).name.equals("End"))){ // condition 2: the word should end with the End node
                            resultField.insertText(resultField.getLength(), "The End node has not been reached\n");
                            break breakpoint;
                        }

                        n = m;
                        j = m;
                        k++;
                        break;
                    }
                    if ((word.charAt(i) != FA.matrix[n][m])&&(m == vn.size()-1)){ //condition 3: after the certain node doesn't follow the unknown edge symbol
                        resultField.insertText(resultField.getLength(), "Node " + FA.nodes.get(j).name + " -> " + word.charAt(i) + " -> " + " ?\n");
                        resultField.insertText(resultField.getLength(), "The End node has not been reached\n");
                        break breakpoint;
                    }
                }
            }

            if ((k == word.length())&&(m == vn.size()-1)){
                resultField.insertText(resultField.getLength(), "This word belongs to the grammar👍");
            } else {
                resultField.insertText(resultField.getLength(), "This word doesn't belong to the grammar👎");
            }
        });
    }
}

