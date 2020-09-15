package com.fx.tool.common.util;

import com.fx.tool.domain.CoinMessage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SaveToFileUtil {
    public SaveToFileUtil() {
    }

    public static void SaveAddressToFile(CoinMessage message) throws IOException {
        String coinName = message.getCoinName();
        List<String> addressList = message.getAddressList();
        String path = message.getPath();
        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMdd");
        String outPath = path + "\\address\\";
        File outf = new File(outPath);
        if (!outf.exists()) {
            outf.mkdir();
        }

        File fi = new File(outPath + coinName + format.format(new Date()) + ".txt");
        if (!fi.exists()) {
            fi.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(fi, true);

        //写入文件
        for (String address : addressList) {
            fileWriter.write(address);
            fileWriter.write("\r\n");
        }

        fileWriter.close();

    }

    public static void SaveAddressToFileHead(CoinMessage message) throws IOException {
        String coinName = message.getCoinName();
        List<String> addressList = message.getAddressList();
        String path = message.getPath();

        String outPath = path + "\\address\\";
        File outf = new File(outPath);
        if (!outf.exists()) {
            outf.mkdir();
        }

        // 源文件
        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMdd");
        File fi = new File(outPath + coinName + format.format(new Date()) + ".txt");
        if (!fi.exists()) {
            fi.createNewFile();
        }

        // 临时文件
        File outFile = File.createTempFile("name", ".tmp");

        // 输入
        FileInputStream fis = new FileInputStream(fi);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        // 输出
        FileOutputStream fos = new FileOutputStream(outFile);
        PrintWriter out = new PrintWriter(fos);

        // 保存一行数据
        String thisLine;
        // 行号从1开始
        int i = 1;
        while ((thisLine = in.readLine()) != null) {
            // 如果行号等于目标行，则输出要插入的数据
            if (i == 1) {
                for (String str : addressList)
                    out.println(str);
            }
            // 输出读取到的数据
            out.println(thisLine);
            // 行号增加
            i++;
        }
        out.flush();
        out.close();
        in.close();
        // 删除原始文件
        fi.delete();
        // 把临时文件改名为原文件名
        outFile.renameTo(fi);

    }

    public static void SaveLogToFile(CoinMessage message) throws IOException {
        String coinName = message.getCoinName();
        List<String> addressList = message.getAddressList();
        String path = message.getPath();

        File fi = new File(path + coinName);
        if (!fi.exists()) {
            fi.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(fi, true);

        //写入文件
        for (String address : addressList) {
            fileWriter.write(address);
            fileWriter.write("\r\n");
        }

        fileWriter.close();

    }

    public static boolean outAddressToFileSQl(CoinMessage message) {
        String coinName = message.getCoinName();
        List<String> addressList = message.getAddressList();
        String path = message.getPath();
        if (addressList != null && addressList.size() > 0) {
            String outPath = path + "/" + coinName;
            StringBuffer sb = new StringBuffer();
            sb.append("Address Type:");
            sb.append(coinName);
            sb.append("Address Count:");
            sb.append(addressList.size());
            sb.append("\r\n");
            sb.append("--------------------------------------------------------------------------------------------------------\r\n");
            File dir = new File(outPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File backupDir = new File(path);
            if (!backupDir.isHidden()) {
                String sets = "attrib +A \"" + backupDir.getAbsolutePath() + "\"";

                try {
                    Runtime.getRuntime().exec(sets);
                } catch (IOException var27) {
                    var27.printStackTrace();
                }
            }

            SimpleDateFormat format = new SimpleDateFormat("_yyyyMMdd_HH_mm_ss_SSS");
            outPath = outPath + "/" + coinName + format.format(new Date()) + ".txt";
            File summarizeFile = new File(outPath);
            OutputStream os = null;
            BufferedWriter out = null;

            try {
                os = new FileOutputStream(summarizeFile, false);
                out = new BufferedWriter(new OutputStreamWriter(os));
                out.write(sb.toString());

                String sql;
                for (Iterator var12 = addressList.iterator(); var12.hasNext(); out.write(sql + "\r\n")) {
                    String address = (String) var12.next();
                    String[] arr = address.split(",");
                    String addr = arr[0];
                    String usePassword = "";
                    if (arr.length > 1) {
                        usePassword = arr[1];
                    }

                    sql = "INSERT INTO finance_coin_in_address(walletTypeName,coinInputAddress,usePassword,importTime)VALUES('" + coinName + "','" + addr + "','" + usePassword + "',UNIX_TIMESTAMP());";
                    if (message.getNeedAddressValid()) {
                        sql = "INSERT INTO finance_coin_in_address(walletTypeName,coinInputAddress,addressValid,usePassword,importTime)VALUES('" + coinName + "','" + addr + "','" + getAESSecret(addr) + "','" + usePassword + "',UNIX_TIMESTAMP());";
                    }
                }
            } catch (IOException var28) {
                var28.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException var26) {
                        var26.printStackTrace();
                    }
                }

            }

            return true;
        } else {
            return false;
        }
    }

    private static String getAESSecret(String addr) {
        return AESUtil.encrypt(addr);
    }
}
