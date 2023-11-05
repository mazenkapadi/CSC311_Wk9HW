package com.example.csc311_wk9hw;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.function.Predicate;

public class HelloController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField dobField;
    @FXML
    private TextField zipCodeField;
    @FXML
    private Button addButton;
    @FXML
    private Label savedLabel;

    @FXML
    private Label firstNameCheck;
    @FXML
    private Label lastNameCheck;
    @FXML
    private Label emailCheck;
    @FXML
    private Label dobCheck;
    @FXML
    private Label zipCodeCheck;

    private BooleanProperty firstNameValid = new SimpleBooleanProperty(false);
    private BooleanProperty lastNameValid = new SimpleBooleanProperty(false);
    private BooleanProperty emailValid = new SimpleBooleanProperty(false);
    private BooleanProperty dobValid = new SimpleBooleanProperty(false);
    private BooleanProperty zipCodeValid = new SimpleBooleanProperty(false);

    public void initialize() {
        addValidationListener(firstNameField, firstNameValid, "^[A-Za-z]{2,25}$");
        addValidationListener(lastNameField, lastNameValid, "^[A-Za-z]{2,25}$");
        addValidationListener(emailField, emailValid, this::isValidEmail);
        addValidationListener(dobField, dobValid, "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}$");
        addValidationListener(zipCodeField, zipCodeValid, "^\\d{5}$");

        enableAddButtonIfValid();
    }

    private void addValidationListener(TextField field, BooleanProperty property, String regex) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = newValue.matches(regex);
            setFieldStyle(field, isValid);
            property.set(isValid);
            enableAddButtonIfValid();
        });
    }

    private void addValidationListener(TextField field, BooleanProperty property, Predicate<String> validationFunction) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean isValid = validationFunction.test(newValue);
            setFieldStyle(field, isValid);
            property.set(isValid);
            enableAddButtonIfValid();
        });
    }

    private boolean isValidEmail(String email) {
        return email != null && email.toLowerCase().endsWith("@farmingdale.edu");
    }

    private void setFieldStyle(TextField field, boolean isValid) {
        if (isValid) {
            setFieldBorderColor(field, Color.GREEN);
            showCheckmark(field);
        } else {
            setFieldBorderColor(field, Color.RED);
            hideCheckmark(field);
        }
    }

    private void setFieldBorderColor(TextField field, Color color) {
        field.setStyle("-fx-border-color: " + toHexColor(color) + ";");
    }

    private String toHexColor(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void enableAddButtonIfValid() {
        addButton.disableProperty().bind(
                Bindings.not(
                        firstNameValid.and(lastNameValid).and(emailValid).and(dobValid).and(zipCodeValid)
                )
        );
    }

    private void clearFieldsWithDelay() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> clearFields())
        );
        timeline.play();
    }

    @FXML
    private void handleAddButtonClick() {
        if (allFieldsAreValid()) {
            savedLabel.setText("Data Saved");
            savedLabel.setVisible(true);
            clearFieldsWithDelay();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        dobField.clear();
        zipCodeField.clear();
        savedLabel.setVisible(false);
        setFieldBorderColor(firstNameField, Color.TRANSPARENT);
        setFieldBorderColor(lastNameField, Color.TRANSPARENT);
        setFieldBorderColor(emailField, Color.TRANSPARENT);
        setFieldBorderColor(dobField, Color.TRANSPARENT);
        setFieldBorderColor(zipCodeField, Color.TRANSPARENT);
        hideCheckmark(firstNameField);
        hideCheckmark(lastNameField);
        hideCheckmark(emailField);
        hideCheckmark(dobField);
        hideCheckmark(zipCodeField);
    }

    private boolean allFieldsAreValid() {
        return firstNameValid.get() && lastNameValid.get() && emailValid.get() && dobValid.get() && zipCodeValid.get();
    }

    private void showCheckmark(TextField field) {
        if (field == firstNameField) {
            firstNameCheck.setText("\u2713"); // Unicode checkmark
            firstNameCheck.setVisible(true);
        } else if (field == lastNameField) {
            lastNameCheck.setText("\u2713");
            lastNameCheck.setVisible(true);
        } else if (field == emailField) {
            emailCheck.setText("\u2713");
            emailCheck.setVisible(true);
        } else if (field == dobField) {
            dobCheck.setText("\u2713");
            dobCheck.setVisible(true);
        } else if (field == zipCodeField) {
            zipCodeCheck.setText("\u2713");
            zipCodeCheck.setVisible(true);
        }
    }

    private void hideCheckmark(TextField field) {
        if (field == firstNameField) {
            firstNameCheck.setText("");
            firstNameCheck.setVisible(false);
        } else if (field == lastNameField) {
            lastNameCheck.setText("");
            lastNameCheck.setVisible(false);
        } else if (field == emailField) {
            emailCheck.setText("");
            emailCheck.setVisible(false);
        } else if (field == dobField) {
            dobCheck.setText("");
            dobCheck.setVisible(false);
        } else if (field == zipCodeField) {
            zipCodeCheck.setText("");
            zipCodeCheck.setVisible(false);
        }
    }
}
