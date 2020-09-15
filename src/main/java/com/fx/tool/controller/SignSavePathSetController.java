package com.fx.tool.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class SignSavePathSetController {
    @FXML
    TextField txtAddressSavePath;
    @FXML
    RadioButton rdoAssignPath;
    @FXML
    RadioButton rdoDefaultPath;
    @FXML
    Button btnSelect;

    public SignSavePathSetController() {
    }

    @FXML
    protected void btnSelectPath_OnClick_Event() throws Exception {
        Stage stage = new Stage();
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("选择路径");
        File file = folderChooser.showDialog(stage);
        if (file != null) {
            String path = file.getPath();
            this.txtAddressSavePath.setText(path);
        }

    }

    @FXML
    protected void rdoGetPathMethod_OnClick_Event() throws Exception {
        if (this.rdoAssignPath.isSelected()) {
            this.txtAddressSavePath.setDisable(false);
            this.btnSelect.setDisable(false);
        }

        if (this.rdoDefaultPath.isSelected()) {
            this.txtAddressSavePath.setDisable(true);
            this.btnSelect.setDisable(true);
        }

    }

    @FXML
    protected void btnSaveSet_OnClick_Event() throws Exception {
    }
}
