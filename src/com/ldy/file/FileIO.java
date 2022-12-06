package com.ldy.file;

import java.io.*;
import java.util.Scanner;

/**
 * @author 刘东阳
 */
public class FileIO {
    ObjectInputStream in = null;
    ObjectOutputStream out = null;

    //根目录
    public static String path = "root/";

    /**
     * 初始化文件系统，根目录已存在则不再创建
     */
    public void init() {
        File root = new File(path);
        if (!root.exists()) {
            root.mkdir();
        }
    }

    /**
     * 工具方法，通过流读取文件
     * @param file 文件名
     */
    public static void read(String file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String buf = "";
            while ((buf = br.readLine()) != null) {
                System.out.println(buf);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void write(String file) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            Scanner scanner = new Scanner(System.in);
            System.out.println("输入要写入的数据：");
            String input = scanner.nextLine();

            bw.write(input);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void copy(String source, String dest) {
        try {
            FileInputStream fis=new FileInputStream(source);
            FileOutputStream fos=new FileOutputStream(dest);

            int len=-1;
            byte[] b=new byte[1024];
            while ((len=fis.read(b))!=-1){
                fos.write(b,0,len);

            }
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

