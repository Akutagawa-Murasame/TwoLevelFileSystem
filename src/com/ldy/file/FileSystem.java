package com.ldy.file;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FileSystem {
    FileIO fileIO = new FileIO();
    Map<String, Boolean> opened = new HashMap<>();

    int nowuser = 0;
    public FileSystem() {
        fileIO.init();
    }

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        fs.help();
        while (true) {
            fs.command();
        }
    }
    public void help() {
        System.out.println("欢迎使用该文件系统");
        System.out.print("help     ");
        System.out.println("菜单");
        System.out.print("create 文件名");
        System.out.println("创建文件");
        System.out.print("dir    ");
        System.out.println("列出文件目录");
        System.out.println("exit    退出系统");
        System.out.print("open + 文件名   ");
        System.out.println("打开文件");
        System.out.print("close + 文件名   ");
        System.out.println("关闭文件");
        System.out.print("read + 文件名   ");
        System.out.println("读文件");
        System.out.print("write + 文件名  ");
        System.out.println("写文件");
        System.out.print("search + 文件名  ");
        System.out.println("搜索文件");
        System.out.print("delete + 文件名  ");
        System.out.println("删除文件");
        System.out.print("deldir + 目录名  ");
        System.out.println("删除目录");
        System.out.print("copy + 要被拷贝的文件名 +目的地址 +文件名  ");
        System.out.println("拷贝文件");
        System.out.print("cut + 要被移动的文件名 +目的地址 + 文件名  ");
        System.out.println("剪切文件");
        System.out.println("exit");
        System.out.println("退出系统");
    }

    public void command() {
        System.out.print("root:>");
        String comd;
        Scanner input = new Scanner(System.in);

        comd = input.nextLine();

        String[] cmd;
        cmd = comd.split(" ");
        switch (cmd[0]) {
            case "help":
                help();
                break;
            case "create":
                create(cmd[1]);
                break;
            case "dir":
                dir();
                break;
            case "delete":
                delete(cmd[1]);
                break;
            case "deldir":
                deldir(cmd[1]);
                break;
            case "open":
                open(cmd[1]);
                break;
            case "close":
                close(cmd[1]);
                break;
            case "read":
                read(cmd[1]);
                break;
            case "write":
                write(cmd[1]);
                break;
            case "search":
                search(cmd[1]);
                break;
            case "exit":
                System.out.println("退出系统");
                System.exit(0);
            case "copy":
                copy(cmd[1], cmd[2]);
                break;
            case "cut":
                cut(cmd[1], cmd[2]);
                break;
            default:
                System.out.println("未找到指令");
                break;
        }
    }

    //通过输入字符串是否带有文件后缀，判断该字符串是否代表一个文件
    //如果不是文件，那么就是目录，或者不存在
    boolean checkIfFile(String name) {
        int index = name.indexOf('.');
        return ((index != -1) && (index != name.length() - 1));
    }

    // 创建文件
    public void create(String filename) {
        try {
            filename = FileIO.path + filename;

            int index = filename.lastIndexOf('/');

            if (index == -1) {
                File file = new File(filename);
                if (checkIfFile(filename)) {
                    file.createNewFile();
                } else {
                    file.mkdir();
                }
                System.out.println("创建成功");
            } else {
                File parentDir = new File(filename.substring(0, index));
                String file = filename.substring(index + 1);

                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }

                File trueFile = new File(filename);
                if (!trueFile.exists() && checkIfFile(filename)) {
                    trueFile.createNewFile();
                    System.out.println("创建成功");
                } else  if (!checkIfFile(filename)) {
                    trueFile.mkdir();
                    System.out.println("创建成功");
                } else {
                    System.out.println("文件已存在");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // 目录
    public void dir() {
        File file = new File(FileIO.path);
        listChildren(file,0);
    }

    public void listChildren(File f,int level) {
        String preStr="";
        for(int i=0;i<level;i++) {
            preStr+="|--";
        }

        System.out.println(preStr+f.getName());
        if(f.isDirectory()) {
            File[] childs = f.listFiles();
            for (int i = 0; i < childs.length; i++) {
                listChildren(childs[i],level+1);
            }
        }
    }

    // 打开文件
    public void open(String file) {
        file = FileIO.path + file;
        if (!new File(file).exists()) {
            System.out.println("文件不存在");
        } else {
            if (checkIfFile(file)) {
                if (!opened.containsKey(file) || opened.get(file) == false) {
                    System.out.println("打开成功");
                    opened.put(file, true);
                } else if (opened.get(file)) {
                    System.out.println("文件已经打开");
                }
            } else {
                System.out.println(file + "不是一个文件");
            }
        }
    }
    // 关闭文件
    public void close(String file) {
        file = FileIO.path + file;
        if (checkIfFile(file)) {
            if (!opened.containsKey(file) || opened.get(file) == false) {
                System.out.println("文件已经关闭");
            } else {
                opened.put(file, false);
                System.out.println("关闭成功");
            }
        } else {
            System.out.println(file + "不是一个文件");
        }
    }

    // 读文件
    public void read(String file) {
        file = FileIO.path + file;
        if (opened.get(file) != null && opened.get(file)) {
            if (checkIfFile(file)) {
                FileIO.read(file);
            } else {
                System.out.println(file + "不是一个文件");
            }
        } else {
            System.out.println("文件未打开");
        }
    }

    // 写文件
    public void write(String file) {
        file = FileIO.path + file;
        if (opened.get(file) != null && opened.get(file)) {
            if (checkIfFile(file)) {
                FileIO.write(file);
            } else {
                System.out.println(file + "不是一个文件");
            }
        } else {
            System.out.println("文件未打开");
        }
    }

    public void find(File f, String fileName) {
        if (f.getName().equals(fileName)) {
            System.out.println(f.getPath());
        }

        if(f.isDirectory()) {
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                find(files[i], fileName);
            }
        }
    }

    // 查找文件
    public void search(String fileName) {
        find(new File(FileIO.path), fileName);
    }

    // 删除文件
    public void delete(String fileName) {
        fileName = FileIO.path + fileName;
        File file = new File(fileName);

        if (checkIfFile(fileName)) {
            file.delete();
            opened.remove(fileName);
            System.out.println("删除成功");
        } else {
            System.out.println(fileName + "不是一个文件");
        }
    }

    // 删除目录
    public void deldir(String directoryName) {
        directoryName = FileIO.path + directoryName;
        File file = new File(directoryName);

        if (file.isDirectory()) {
            deleteDirectory(file);
            System.out.println("删除成功");
        } else {
            System.out.println(directoryName + "不是一个目录");
        }
    }

    public void deleteDirectory(File directory) {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        directory.delete();
    }

    // 拷贝文件
    public void copy(String source,String dest) {
        source = FileIO.path + source;
        dest = FileIO.path + dest;
        FileIO.copy(source, dest);
        System.out.println("拷贝成功");
    }

    public void cut(String source,String dest) {
        copy(source, dest);
        delete(source);
        System.out.println("剪切成功");
    }
}
