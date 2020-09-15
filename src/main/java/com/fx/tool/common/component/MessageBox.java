package com.fx.tool.common.component;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

public class MessageBox {
    public MessageBox() {
    }

    public static void info(String title, String msg) {
        alert(title, msg, "信息提示", Alert.AlertType.INFORMATION);
    }

    public static void warn(String title, String msg) {
        alert(title, msg, "警告提示", Alert.AlertType.WARNING);
    }

    public static void error(String title, String msg) {
        alert(title, msg, "错误提示", Alert.AlertType.ERROR);
    }

    public static ButtonType confirm(String title, String msg) {
        return (ButtonType)alert(title, msg, "确认提示", Alert.AlertType.CONFIRMATION).get();
    }

    private static Optional<ButtonType> alert(String title, String msg, String headerText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(msg);
        alert.initOwner((Window)null);
        return alert.showAndWait();
    }
}
