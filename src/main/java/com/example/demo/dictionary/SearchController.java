package com.example.demo.dictionary;

import com.example.demo.common.TransitionController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * lop controller cho search-view.fxml
 */
public class SearchController extends TransitionController {
    //quan ly du lieu va cac medthod voi tu dien
    private DictionaryManagement dictionaryManagement;
    //danh sach suggestion cho listview
    private ObservableList list = FXCollections.observableArrayList();

    //UI
    @FXML private TextField textField;
    @FXML private TextArea textArea;
    @FXML private ListView<String> listView;

    /**
     * khoi tao controller, doc data tu file txt
     * thiet lap transition
     * @param location vi tri duoc su dung de giai quyet duong dan tuong doi
     * @param resources tai nguyen duoc su dung de dia phuong hoa
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);//goi medthod tu lop cha
        dictionaryManagement = new DictionaryManagement();//khoi tao tu dien
        try {
            //doc data tu file txt
            dictionaryManagement.insertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //update danh sach suggestion khi nguoi dung go chu
        textField.textProperty().addListener((observable, oldValue, newValue) -> suggestEvent());

        //them transition
        fadeInTransition(listView);
        fadeInTransition(textArea);
        slideInTransition(textField, true);
    }

    @FXML
    protected void onSearchButtonClick() {

    }

    /**
     * tim kiem tu va hien thi nghia
     * neu khong thay -> hien thi cac tu gan giong
     */
    @FXML
    protected void searchEvent() {
        //lay tu can tim
        String text = textField.getText().trim();
        //tim nghia cua tu
        String result = dictionaryManagement.dictionarySearch(text);
        if (result != null) {
            //tim thay tu -> hien thi nghia
            textArea.setText(text + " " + result);
        } else {
            //khong tim thay -> hien thi cac tu gan giong
            String nearlyWords = dictionaryManagement.dictionarySearchNearly(text);
            textArea.setText("Similar words found:\n" + nearlyWords);
        }
    }

    /**
     * update danh sach tu goi y nguoi dung nhap
     */
    @FXML
    protected void suggestEvent() {
        //xoa data cu
        listView.getItems().clear();
        list.clear();

        //lay tu nhap vao
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            //tim nghia cua tu neu text khong trong
            ArrayList suggestions = dictionaryManagement.dictionarySuggest(text);
            list.addAll(suggestions);
            listView.getItems().addAll(list);
        }
    }

    /**
     * xu ly nguoi dung cho word tu danh sach goi y
     */
    @FXML
    protected void clickEvent() {
        //lay tu duoc chon
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            //dien tu vao textfield
            textField.setText(selected);
            //tim va hien thi nghia cua tu
            String meaning = dictionaryManagement.dictionarySearch(selected);
            if (meaning != null) {
                textArea.setText(selected + " " + meaning);
            }
        }
    }

    /**
     * them tu moi vao tu dien
     */
    @FXML
    protected void addEvent() {
        //tao window nhap tu moi
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Word");
        dialog.setHeaderText("Enter the word and its meaning");

        //tao button them word
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        //tao form nhap lieu
        GridPane grid = createFormGrid();
        TextField wordField = new TextField();
        TextArea meaningArea = new TextArea();

        wordField.setPromptText("Enter word...");
        meaningArea.setPromptText("Enter meaning...");

        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordField, 1, 0);
        grid.add(new Label("Meaning:"), 0, 1);
        grid.add(meaningArea, 1, 1);

        //disable add button khi chua nhap cai gi
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        wordField.textProperty().addListener((observable, oldValue, newValue) ->
                addButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        // xu ly khi nguoi dung bam add
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String word = wordField.getText().trim();
                //kiem tra xem tu da ton tai trong danh sach
                if (dictionaryManagement.dictionarySearch(word) != null) {
                    showError("Word Exists", "This word is already in the dictionary");//thong bao neu da ton tai
                    return null;
                }
                return new Pair<>(word, meaningArea.getText().trim());
            }
            return null;
        });

        //xu ly ket qua tu dialog
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(wordMeaning -> {
            //them tu moi vao tu dien
            dictionaryManagement.dictionaryAdd(new Word(wordMeaning.getKey(), wordMeaning.getValue()));
            suggestEvent();// update suggestion
            saveAndShowConfirmation("Word added successfully");
        });
    }

    /**
     * xoa tu khoi tu dien
     */
    @FXML
    protected void deleteEvent() {
        //tao window xoa tu
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Word");
        dialog.setHeaderText("Enter the word to delete");

        //tao button xoa tu
        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButton, ButtonType.CANCEL);

        //tao form xoa tu
        GridPane grid = createFormGrid();
        TextField wordField = new TextField();
        wordField.setPromptText("Enter word to delete...");
        grid.add(wordField, 0, 0);

        //disable delete button khi chua nhap gi
        Node deleteButtonNode = dialog.getDialogPane().lookupButton(deleteButton);
        deleteButtonNode.setDisable(true);
        wordField.textProperty().addListener((observable, oldValue, newValue) ->
                deleteButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        //xu ly khi nguoi dung bam delete
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButton) {
                String word = wordField.getText().trim();
                //kiem tra xem tu co ton tai khong de xoa
                if (dictionaryManagement.dictionarySearch(word) == null) {
                    showError("Word Not Found", "This word does not exist in the dictionary");// thong bao
                    return null;
                }

                //confirm?
                if (confirmAction("Delete Word", "Are you sure you want to delete '" + word + "'?")) {
                    return word;
                }
            }
            return null;
        });

        //xu ly ket qua tu dialog
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(word -> {
            //xoa tu va luu
            dictionaryManagement.dictionaryDelete(word);
            suggestEvent();//update goi y
            saveAndShowConfirmation("Word deleted successfully");
        });
    }

    /**
     * sua nghia cua tu trong tu dien
     */
    @FXML
    protected void changeEvent() {
        //lay tu
        String word = textField.getText().trim();
        String currentMeaning = dictionaryManagement.dictionarySearch(word);

        //kiem tra tu co ton tai khong
        if (currentMeaning == null) {
            showError("Word Not Found", "Please enter an existing word to edit");
            return;
        }

        //tao window sua tu
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Word");
        dialog.setHeaderText("Edit meaning for '" + word + "'");

        //tao save button
        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        //tao form sua nghia
        GridPane grid = createFormGrid();
        TextArea meaningArea = new TextArea(currentMeaning);
        meaningArea.setPrefRowCount(3);
        grid.add(meaningArea, 0, 0);

        //disable save button
        Node saveButtonNode = dialog.getDialogPane().lookupButton(saveButton);
        saveButtonNode.setDisable(true);
        meaningArea.textProperty().addListener((observable, oldValue, newValue) ->
                saveButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        //xu ly sau khi nhan save
        dialog.setResultConverter(dialogButton ->
                dialogButton == saveButton ? meaningArea.getText().trim() : null);

        //xu ly ket qua tu dialog
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newMeaning -> {
            //cap nhap nghia va luu
            dictionaryManagement.dictionaryChange(word, newMeaning);
            textArea.setText(word + " " + newMeaning);
            saveAndShowConfirmation("Word updated successfully");
        });
    }

    /**
     * phat am tu tren textarea
     */
    @FXML
    protected void speechEvent() {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            //dung TTS de doc
            DictionaryManagement.dictionarySpeak(text);
        }
    }

    @FXML
    protected void saveEvent() {
        try {
            //luu tu dien
            dictionaryManagement.dictionarySave();
            showInformation("Save Successful", "Dictionary has been saved successfully");
        } catch (IOException e) {
            showError("Save Error", "Could not save the dictionary: " + e.getMessage());
        }
    }

    //layout form nhap lieu
    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    //hien thi thong bao error
    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //hien thi inf thong bao
    private void showInformation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //hien thi hop thoai xac nhan
    private boolean confirmAction(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    //luu tu dien va thong bao ket qua
    private void saveAndShowConfirmation(String message) {
        try {
            //luu tu dien
            dictionaryManagement.dictionarySave();
            showInformation("Success", message);
        } catch (IOException e) {
            showError("Save Error", "Changes were made but could not be saved: " + e.getMessage());
        }
    }
}