package com.ytb.shopping.util;


import lombok.Data;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Data
public class FTPUtils {

    private static final String FTP_IP = PropertiesUtils.readByKey("ftp.server.ip");

    private static final String FTP_USER = PropertiesUtils.readByKey("ftp.server.user");

    private static final String FTP_PASSWORD = PropertiesUtils.readByKey("ftp.server.password");

    private String ftpIp;

    private String ftpUser;

    private String ftpPass;

    private Integer ftpPort;

    public FTPUtils(String ftpIp, String ftpUser, String ftpPass, Integer ftpPort) {
        this.ftpIp = ftpIp;
        this.ftpUser = ftpUser;
        this.ftpPass = ftpPass;
        this.ftpPort = ftpPort;
    }

    /**
     * 图片上传到ftp
     */
    public static boolean uploadFile(List<File> fileList) throws IOException {

        FTPUtils ftpUtil = new FTPUtils(FTP_IP,FTP_USER,FTP_PASSWORD,21);

        System.out.println("开始连接ftp服务器");

        return ftpUtil.uploadFile("img",fileList);

    }


    /**
     * 上传到ftp指定文件夹
     */
    public boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        FileInputStream fileInputStream = null;

        if (connectFTPServer(ftpIp,ftpUser,ftpPass)){
            try {
                ftpClient.changeWorkingDirectory(remotePath);
                ftpClient.setBufferSize(1024);
                ftpClient.setControlEncoding("UTF-8");
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();//打开被动传输
                for (File file:fileList
                     ) {
                    fileInputStream = new FileInputStream(file);
                    ftpClient.storeFile(remotePath,fileInputStream);
                }

                return true;

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("文件上传出错");
            }finally {
                fileInputStream.close();
                ftpClient.disconnect();
            }
        }

        return false;

    }

    /**
     * 连接ftp服务器
     */
    FTPClient ftpClient = null;

    public boolean connectFTPServer(String ip,String user,String password){
        ftpClient = new FTPClient();

        try {
            ftpClient.connect(ip);
            return ftpClient.login(user,password);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接ftp服务器异常……");
        }

        return false;

    }



}
