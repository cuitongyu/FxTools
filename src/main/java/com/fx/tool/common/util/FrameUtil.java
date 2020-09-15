package com.fx.tool.common.util;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * 窗口工具类
 */
public class FrameUtil {
    /**
     * 加载窗口布局
     *
     * @param uri   窗口资源文件路径
     * @param stage 窗口实例
     */
    public static void loadFrame(String uri, Stage stage) throws Exception {
        // 使用FXML加载器，加载界面布局
        Parent root = FXMLLoader.load(FrameUtil.class.getResource(uri));
        root.prefWidth(stage.getWidth());
        root.prefHeight(stage.getHeight());

        // 设置布局场景大小
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // 将布局场景设置到窗口
        stage.setScene(scene);

        // 设置窗口关闭事件(主窗体退出，则整个应用退出)
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.gc();
                System.exit(0);
            }
        });

        // 显示窗口
        stage.show();
    }

    /**
     * 加载窗口布局
     *
     * @param uri 窗口资源文件路径
     * @return 返回窗口实例
     */
    public static Stage loadFrame(String uri) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Stage");

        // 使用FXML加载器，加载界面布局
        Parent root = FXMLLoader.load(FrameUtil.class.getResource(uri));
        root.prefWidth(stage.getWidth());
        root.prefHeight(stage.getHeight());

        // 设置布局场景大小
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());

        // 将布局场景设置到窗口
        stage.setScene(scene);

        return stage;
    }
}
