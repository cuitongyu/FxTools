package com.fx.tool;

import com.fx.tool.common.util.FrameUtil;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToolApplication extends Application {

    public static void main(String[] args) {
        SpringApplication.run(ToolApplication.class, args);
        launch(args);
        System.out.println("ce");
    }

    /**
     * 重写javafx.application.Application的start方法
     * @param primaryStage 窗口对象
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 设置主题
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        // 设置窗口标题
        primaryStage.setTitle("ewSQL");

        // 加载窗体布局
        FrameUtil.loadFrame("/MainFrame.fxml", primaryStage);

    }
}
