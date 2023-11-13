package lk.ijse.hrms.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.hrms.dto.Employee;
import lk.ijse.hrms.model.EmployeeModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmpPreviewWindowFormController implements Initializable {
    @FXML
    private AnchorPane root;

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
    private TextField txtDob;

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
    private TextArea txtWorkExp;

    @FXML
    private ComboBox<String> cmbDepartment;

    @FXML
    private ComboBox<String> cmbDesignation;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbGender;

    static Employee employee;

    File file;
    Image image;
    private FileInputStream fileInputStream;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setValues();
        setDepartmentComboValues();
        setDesignationComboValues();
        setGenderComboValues();
    }

    private void setValues() {
        txtEmpId.setText(employee.getEmp_id());
        txtEmpName.setText(employee.getName());
        txtNic.setText(employee.getNic());
        txtAddresss.setText(employee.getAddress());
        txtAge.setText(employee.getAge());
        txtDob.setText(employee.getDob());
        txtMobile.setText(employee.getMobile());
        txtHome.setText(employee.getHome());
        txtEmail.setText(employee.getEmail());
        txtExperience.setText(employee.getExperience());
        txtAdcademic.setText(employee.getAcademic());
        txtProfessional.setText(employee.getProfessional());
        cmbDepartment.setValue(employee.getDep_id());
        cmbDesignation.setValue(employee.getDesignation());
        cmbGender.setValue(employee.getGender());
        getImage();
    }

    private void getImage() {

        try {

            Image image = EmployeeModel.getImage(employee.getEmp_id());
            System.out.println(10);
            imgPerson.setImage(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDepartmentComboValues() {
        String[] items = {null, "D01", "D02"};
        cmbDepartment.getItems().addAll(items);
    }

    private void setDesignationComboValues() {
        String[] items = {null, "Manager", "Employee"};
        cmbDesignation.getItems().addAll(items);
    }

    private void setGenderComboValues() {
        String[] items = {null, "Male", "Female"};
        cmbGender.getItems().addAll(items);
    }

    @FXML
    void btnChooseOnAction(ActionEvent event) {
        Stage primaryStage = new Stage();
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("JPEG files(*.jpeg)", "*.JPEG");
        fc.getExtensionFilters().addAll(ext1, ext2, ext3);
        file = fc.showOpenDialog(primaryStage);

        if(file!=null) {
            try {
                fileInputStream = new FileInputStream(file);
                System.out.println("file");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image = new Image(file.toURI().toString(), 100, 100, true, true);
            System.out.println(file.getAbsolutePath());
            imgPerson.setImage(image);
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String nic = txtNic.getText();
        String address = txtAddresss.getText();
        String age = txtAge.getText();
        String dob = txtDob.getText();
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

        if (!isEmpty) {
            try {
                String gender = cmbGender.getSelectionModel().getSelectedItem();
                String designation = cmbDesignation.getSelectionModel().getSelectedItem();
                String department = cmbDepartment.getSelectionModel().getSelectedItem();
                boolean isSaved = EmployeeModel.update(new Employee(department, id, name, nic, address, age, dob, gender, mobile
                        , home, email, designation, experience, academic, professional));

                if (file!=null){
                    boolean isAdded = EmployeeModel.addImage(fileInputStream, txtEmpId.getText(),file);
                }

                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated !!!").show();
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.close();
                } else {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee not Updated !!!").show();

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Sorry !!! Invalid Input...").show();

            }
        }


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
}

