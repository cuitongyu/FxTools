package com.fx.tool.controller;

import com.fx.tool.common.component.MessageBox;
import com.fx.tool.common.util.SaveToFileUtil;
import com.fx.tool.domain.CoinMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TextField tCoinName;
    @FXML
    private TextField tPath;
    @FXML
    private RadioButton rbNeedValid;
    @FXML
    private TextField txtResources;
    @FXML
    private Label lyResult;
    @FXML
    private TextField aPath;
    @FXML
    private Label addressResult;

    public MainController() {
    }

    @FXML
    protected void btnSelectPath_OnClick_Event() throws Exception {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("数据源路径");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            String path = file.getPath();
            this.txtResources.setText(path);
        }
    }


    @FXML
    protected void btnBeginAnalyse_OnClick_Event(ActionEvent event) throws Exception {
        try {
            File file = new File(this.txtResources.getText());
            if (!file.exists()) {
                MessageBox.error("系统提示", "文件不存在");
                return;
            }

            String path = this.tPath.getText();
            String coinName = this.tCoinName.getText();
            if (StringUtils.isBlank(path) || StringUtils.isBlank(coinName)) {
                MessageBox.error("格式错误", "请填写必要信息");
            }

            List<String> jsonStr = FileUtils.readLines(file, "UTF-8");
            if (jsonStr.size() == 0) {
                MessageBox.error("格式错误", "文件没有包含必要数据");
            }

            int index = 0;
            int i = 0;

            for (int len = jsonStr.size(); i < len; ++i) {
                String j = (String) jsonStr.get(i);
                if (j.contains("List:")) {
                    index = i;
                    break;
                }
            }

            jsonStr = jsonStr.subList(index + 1, jsonStr.size());
            if (jsonStr.size() == 0) {
                MessageBox.error("格式错误", "文件没有包含必要数据");
            }

            boolean needValid = this.rbNeedValid.isSelected();
            CoinMessage coinMessage = new CoinMessage();
            coinMessage.setAddressList(jsonStr);
            coinMessage.setCoinName(coinName);
            coinMessage.setPath(path);
            coinMessage.setNeedAddressValid(needValid);
            SaveToFileUtil.outAddressToFileSQl(coinMessage);
            this.lyResult.setText("生成完成，请在：" + path + "\\" + coinName + "查找结果");
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }

    @FXML
    protected void btnSelectAddressPath_OnClick_Event() throws Exception {
        Stage stage = new Stage();
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("选择文件夹");
        File file = dirChooser.showDialog(stage);
        if (file != null) {
            String path = file.getPath();
            this.aPath.setText(path);
            this.tPath.setText(path);
        }
    }

    @FXML
    protected void btnGetAddress_OnClick_Event(ActionEvent event) throws Exception {
        try {
            String addressPath = this.aPath.getText();
            String coinName = this.tCoinName.getText();
            if (StringUtils.isBlank(addressPath) || StringUtils.isBlank(coinName)) {
                MessageBox.error("文件格式错误", "请填写必要信息");
            }

            CoinMessage coinMessage = new CoinMessage();
            coinMessage.setCoinName(coinName);

            List<File> files = this.getFiles(addressPath);
            int count = 0;
            for (File f : files) {
                coinMessage.setPath(addressPath);
                if (f.getName().toUpperCase().contains("COIN_OUT")) {
                    continue;
                }

                List<String> list = this.readFileList(addressPath + "\\" + f.getName());
                for (int i = 0; i < 4; i++) {
                    list.remove(0);
                }
                count += list.size();
                coinMessage.setAddressList(list);
                SaveToFileUtil.SaveAddressToFile(coinMessage);
            }

            List<String> heard = new ArrayList<>();
            heard.add("Address Type:" + coinName);
            heard.add("Address count:" + count);
            heard.add("---------------------------------------");
            heard.add("Address List:");
            coinMessage.setAddressList(heard);
            SaveToFileUtil.SaveAddressToFileHead(coinMessage);

            this.addressResult.setText("生成完成,请在:" + addressPath + "查找结果");
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }

    //获取目录下所有文件
    private List<File> getFiles(String path) throws IOException {
        File root = new File(path);
        List<File> files = new ArrayList<>();
        if (!root.isDirectory()) {
            files.add(root);
        } else {
            File[] subFiles = root.listFiles();
            for (File f : subFiles) {
                files.add(f);
            }

        }
        return files;
    }

    public List<String> readFileList(String path) throws IOException {

        // 使用ArrayList来存储每行读取到的字符串
        List<String> arrayList = new ArrayList<>();

        File file = new File(path);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));
        String temp = null;
        while ((temp = br.readLine()) != null) {
            arrayList.add(temp);
        }
        return arrayList;
    }

}
