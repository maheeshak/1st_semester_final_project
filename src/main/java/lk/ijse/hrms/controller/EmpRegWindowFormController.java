package lk.ijse.hrms.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.EmployeeModel;
import lk.ijse.hrms.regexPatterns.RegexPatterns;


import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class EmpRegWindowFormController implements Initializable {

    @FXML
    private DatePicker txtDob;

    @FXML
    private ImageView imgPerson;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtNic;

    @FXML
    private TextField txtAddresss;


    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtHome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtExperience;

    @FXML
    private TextArea txtAdcademic;

    @FXML
    private TextArea txtProfessional;


    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private ComboBox<String> cmbDesignation;

    @FXML
    private ComboBox<String> cmbGender;
    @FXML
    private Label txtCurrentEmpId;

    private FileChooser fileChooser;
    private File file = new File("D:\\IJ PROJECTS\\novo-tech-solutions\\human-resource-management-system\\src\\main\\resources\\assest\\designation.png");
    private Image image;

    private FileInputStream fileInputStream;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setGenderComboValues();
        setDesignationComboValues();
        setDepartmentComboValues();
        setEmpId();
    }

    private void setEmpId() {
        try {

            String currentId = EmployeeModel.getCurrentID();
            txtCurrentEmpId.setText(currentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void setDepartmentComboValues() {
        String[] items = {null, "D01", "D02","D03","D04","D05","D06"};
        cmbDepartment.getItems().addAll(items);
    }

    private void setDesignationComboValues() {
        String[] items = {null, "Manager", "Trainee", "Junior Executive", "Senior Executive", "Assistant Manager", "Accountant", "Senior Manager"};
        cmbDesignation.getItems().addAll(items);
    }

    private void setGenderComboValues() {
        String[] items = {null, "Male", "Female"};
        cmbGender.getItems().addAll(items);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String nic = txtNic.getText();
        String address = txtAddresss.getText();
        String age = txtAge.getText();
        String dob = txtDob.getValue().toString();
        Integer genderId = cmbGender.getSelectionModel().getSelectedIndex();
        String mobile = txtMobile.getText();
        String home = txtHome.getText();
        String email = txtEmail.getText();
        Integer designationId = cmbDesignation.getSelectionModel().getSelectedIndex();
        Integer departmentId = cmbDepartment.getSelectionModel().getSelectedIndex();
        String experience = txtExperience.getText();
        String academic = txtAdcademic.getText();
        String professional = txtProfessional.getText();


        boolean isEmpty = checkEmpty(id, name, nic, address, age, dob, genderId, mobile, designationId, departmentId);

        //get Image

        try {
            fileInputStream = new FileInputStream(file);
            System.out.println("file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        if (!isEmpty) {
            try {
                String gender = cmbGender.getSelectionModel().getSelectedItem();
                String designation = cmbDesignation.getSelectionModel().getSelectedItem();
                String department = cmbDepartment.getSelectionModel().getSelectedItem();

                boolean isValid = checkValidity();
                if (isValid) {


                    boolean isSaved = EmployeeModel.save(new Employee(department, id, name, nic, address, age, dob, gender, mobile
                            , home, email, designation, experience, academic, professional));


                    boolean isAdded = EmployeeModel.addImage(fileInputStream, txtEmpId.getText(), file);

                    if (isSaved) {
                        if (isAdded) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Employee Added !!!").show();
                            String currentID = EmployeeModel.getCurrentID();
                            txtCurrentEmpId.setText(currentID);
                            clearText();
                        }
                    }
                }
            } catch (SQLException e) {
                /*new Alert(Alert.AlertType.ERROR, "Sorry !!! Invalid Input...").show();*/
                e.printStackTrace();

            }
        }

    }

    private void clearText() {
        txtEmpId.setText("");
        txtEmpName.setText("");
        txtNic.setText("");
        txtAddresss.setText("");
        txtAge.setText("");
        txtMobile.setText("");
        txtHome.setText("");
        txtEmail.setText("");
        txtExperience.setText("");
        txtAdcademic.setText("");
        txtProfessional.setText("");
        txtHome.setText("");
        txtDob.setValue(null);


    }

    private boolean checkEmpty(Object... args) {
        boolean isEmpty = false;


        if (args[0].equals("")) {
            txtEmpId.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtEmpId.setStyle(null);
        }

        if (args[1].equals("")) {
            txtEmpName.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtEmpName.setStyle(null);
        }
        if (args[2].equals("")) {
            txtNic.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtNic.setStyle(null);
        }
        if (args[3].equals("")) {
            txtAddresss.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtAddresss.setStyle(null);
        }
        if (args[4].equals("")) {
            txtAge.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtAge.setStyle(null);
        }
        if (args[5].equals("")) {
            txtDob.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtDob.setStyle(null);
        }
        if (args[6].equals(0)) {
            cmbGender.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            cmbGender.setStyle(null);
        }
        if (args[7].equals("")) {
            txtMobile.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            txtMobile.setStyle(null);
        }
        if (args[8].equals(0)) {
            cmbDesignation.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            cmbDesignation.setStyle(null);
        }
        if (args[9].equals(0)) {
            cmbDepartment.setStyle("-fx-border-color: red ; -fx-border-radius: 2px");
            isEmpty = true;
        } else {
            cmbDepartment.setStyle(null);
        }
        return isEmpty;
    }

    @FXML
    void btnChooseOnAction(ActionEvent event) throws IOException, SQLException {
        Stage primaryStage = new Stage();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("JPEG files(*.jpeg)", "*.JPEG");
        fc.getExtensionFilters().addAll(ext1, ext2, ext3);
        file = fc.showOpenDialog(primaryStage);

        if (file != null) {
            image = new Image(file.toURI().toString(), 100, 100, true, true);
            System.out.println(file.getAbsolutePath());
            imgPerson.setImage(image);

        } else {
            image = new Image("assets/designation.png");
        }

    }

    @FXML
    void cmbDepartmentOnAction(ActionEvent event) {
        txtExperience.requestFocus();
    }

    @FXML
    void cmbDesignationOnAction(ActionEvent event) {
        cmbDepartment.requestFocus();
    }

    @FXML
    void cmbGenderOnAction(ActionEvent event) {
        txtMobile.requestFocus();
    }

    @FXML
    void txtAdcademicOnAction(MouseEvent event) {
        txtProfessional.requestFocus();
    }

    @FXML
    void txtAddresssOnAction(ActionEvent event) {
        txtAge.requestFocus();
    }

    @FXML
    void txtAgeOnAction(ActionEvent event) {
        txtDob.requestFocus();
    }


    @FXML
    void txtEmailOnAction(ActionEvent event) {
        cmbDesignation.requestFocus();
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtEmpName.requestFocus();
    }

    @FXML
    void txtEmpNameOnAction(ActionEvent event) {
        txtNic.requestFocus();
    }

    @FXML
    void txtExperienceOnAction(ActionEvent event) {
        txtAdcademic.requestFocus();
    }

    @FXML
    void txtHomeOnAction(ActionEvent event) {
        txtEmail.requestFocus();
    }

    @FXML
    void txtMobileOnAction(ActionEvent event) {
        txtHome.requestFocus();
    }

    @FXML
    void txtNicOnAction(ActionEvent event) {
        txtAddresss.requestFocus();
    }

    @FXML
    void txtProfessionalOnAction(MouseEvent event) {

    }

    private boolean checkValidity() {
        String empId = txtEmpId.getText();
        String nic = txtNic.getText();
        String age = txtAge.getText();
        String mobile = txtMobile.getText();
        String email = txtEmail.getText();

        boolean isValid = true;

        if (!RegexPatterns.getEmpIdPattern().matcher(empId).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid emp_id !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getNewIDPattern().matcher(nic).matches() || RegexPatterns.getOldIDPattern().matcher(nic).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid NIC !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getIntPattern().matcher(age).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Age !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getMobilePattern().matcher(mobile).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Mobile !!!").show();
            isValid = false;
        } else if (!RegexPatterns.getEmailPattern().matcher(email).matches()) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email !!!").show();
            isValid = false;
        }
        return isValid;

    }


}
