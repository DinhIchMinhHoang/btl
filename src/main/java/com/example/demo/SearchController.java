package com.example.demo;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private DictionaryManagement dictionaryManagement;
    private ObservableList list = FXCollections.observableArrayList();

    @FXML private TextField textField;
    @FXML private TextArea textArea;
    @FXML private ListView<String> listView;
    @FXML private JFXButton MinimizeButton;
    @FXML private JFXButton CloseButton;
    @FXML private JFXButton HomeButton;
    @FXML private JFXButton SearchButton;
    @FXML private JFXButton TranslaterButton;
    @FXML private JFXButton GameButton;
    @FXML private HBox headBar;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dictionaryManagement = new DictionaryManagement();
        try {
            dictionaryManagement.insertFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Enable window dragging
        headBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        headBar.setOnMouseDragged(event -> {
            Stage stage = (Stage) headBar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        // Add text change listener for suggestions
        textField.textProperty().addListener((observable, oldValue, newValue) -> suggestEvent());
    }

    @FXML
    protected void onMinimizeButtonClick() {
        Stage stage = (Stage) MinimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void onCloseButtonClick() {
        Stage stage = (Stage) CloseButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onHomeButtonClick() {
        navigateToView("home-view.fxml", "Dictionary Application");
    }

    @FXML
    protected void onSearchButtonClick() {
        // Current view, no need to navigate
    }

    @FXML
    protected void onTranslaterButtonClick() {
        navigateToView("translate-view.fxml", "Translate");
    }

    @FXML
    protected void onGameButtonClick() {
        navigateToView("game-view.fxml", "Game");
    }

    private void navigateToView(String fxmlFile, String title) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) HomeButton.getScene().getWindow();
            stage.setScene(new Scene(view));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Navigation Error", "Could not load " + title + " view");
        }
    }

    @FXML
    protected void searchEvent() {
        String text = textField.getText().trim();
        String result = dictionaryManagement.dictionarySearch(text);
        if (result != null) {
            textArea.setText(text + " " + result);
        } else {
            String nearlyWords = dictionaryManagement.dictionarySearchNearly(text);
            textArea.setText("Similar words found:\n" + nearlyWords);
        }
    }

    @FXML
    protected void suggestEvent() {
        listView.getItems().clear();
        list.clear();

        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            ArrayList suggestions = dictionaryManagement.dictionarySuggest(text);
            list.addAll(suggestions);
            listView.getItems().addAll(list);
        }
    }

    @FXML
    protected void clickEvent() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            textField.setText(selected);
            String meaning = dictionaryManagement.dictionarySearch(selected);
            if (meaning != null) {
                textArea.setText(selected + " " + meaning);
            }
        }
    }

    @FXML
    protected void addEvent() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Word");
        dialog.setHeaderText("Enter the word and its meaning");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane grid = createFormGrid();
        TextField wordField = new TextField();
        TextArea meaningArea = new TextArea();

        wordField.setPromptText("Enter word...");
        meaningArea.setPromptText("Enter meaning...");

        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordField, 1, 0);
        grid.add(new Label("Meaning:"), 0, 1);
        grid.add(meaningArea, 1, 1);

        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);
        wordField.textProperty().addListener((observable, oldValue, newValue) ->
                addButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                String word = wordField.getText().trim();
                if (dictionaryManagement.dictionarySearch(word) != null) {
                    showError("Word Exists", "This word is already in the dictionary");
                    return null;
                }
                return new Pair<>(word, meaningArea.getText().trim());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(wordMeaning -> {
            dictionaryManagement.dictionaryAdd(new Word(wordMeaning.getKey(), wordMeaning.getValue()));
            suggestEvent();
            saveAndShowConfirmation("Word added successfully");
        });
    }

    @FXML
    protected void deleteEvent() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Delete Word");
        dialog.setHeaderText("Enter the word to delete");

        ButtonType deleteButton = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButton, ButtonType.CANCEL);

        GridPane grid = createFormGrid();
        TextField wordField = new TextField();
        wordField.setPromptText("Enter word to delete...");
        grid.add(wordField, 0, 0);

        Node deleteButtonNode = dialog.getDialogPane().lookupButton(deleteButton);
        deleteButtonNode.setDisable(true);
        wordField.textProperty().addListener((observable, oldValue, newValue) ->
                deleteButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButton) {
                String word = wordField.getText().trim();
                if (dictionaryManagement.dictionarySearch(word) == null) {
                    showError("Word Not Found", "This word does not exist in the dictionary");
                    return null;
                }

                if (confirmAction("Delete Word", "Are you sure you want to delete '" + word + "'?")) {
                    return word;
                }
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(word -> {
            dictionaryManagement.dictionaryDelete(word);
            suggestEvent();
            saveAndShowConfirmation("Word deleted successfully");
        });
    }

    @FXML
    protected void changeEvent() {
        String word = textField.getText().trim();
        String currentMeaning = dictionaryManagement.dictionarySearch(word);

        if (currentMeaning == null) {
            showError("Word Not Found", "Please enter an existing word to edit");
            return;
        }

        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Word");
        dialog.setHeaderText("Edit meaning for '" + word + "'");

        ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

        GridPane grid = createFormGrid();
        TextArea meaningArea = new TextArea(currentMeaning);
        meaningArea.setPrefRowCount(3);
        grid.add(meaningArea, 0, 0);

        Node saveButtonNode = dialog.getDialogPane().lookupButton(saveButton);
        saveButtonNode.setDisable(true);
        meaningArea.textProperty().addListener((observable, oldValue, newValue) ->
                saveButtonNode.setDisable(newValue.trim().isEmpty()));

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton ->
                dialogButton == saveButton ? meaningArea.getText().trim() : null);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newMeaning -> {
            dictionaryManagement.dictionaryChange(word, newMeaning);
            textArea.setText(word + " " + newMeaning);
            saveAndShowConfirmation("Word updated successfully");
        });
    }

    @FXML
    protected void speechEvent() {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            DictionaryManagement.dictionarySpeak(text);
        }
    }

    @FXML
    protected void saveEvent() {
        try {
            dictionaryManagement.dictionarySave();
            showInformation("Save Successful", "Dictionary has been saved successfully");
        } catch (IOException e) {
            showError("Save Error", "Could not save the dictionary: " + e.getMessage());
        }
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInformation(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean confirmAction(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void saveAndShowConfirmation(String message) {
        try {
            dictionaryManagement.dictionarySave();
            showInformation("Success", message);
        } catch (IOException e) {
            showError("Save Error", "Changes were made but could not be saved: " + e.getMessage());
        }
    }
}