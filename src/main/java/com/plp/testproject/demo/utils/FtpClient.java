package com.plp.testproject.demo.utils;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class FtpClient {

    private String server = GlobalHelper.FTP_SERVER_NAME;
    private int port = GlobalHelper.FTP_SERVER_PORT;
    private String user = GlobalHelper.FTP_USERNAME;
    private String password = GlobalHelper.FTP_PASSWORD;
    private FTPClient ftp;

    public FtpClient() {
    }

    void open() throws IOException {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }
        ftp.login(user, password);
    }

    void close() throws IOException {
        ftp.disconnect();
    }

    void putFileToPath(String path, File file) throws IOException {
        ftp.storeFile(path, new FileInputStream(file));
    }
}
