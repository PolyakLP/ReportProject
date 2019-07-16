//package com.plp.testproject.demo.utils;
//
//import jdk.nashorn.internal.ir.LiteralNode;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.mockftpserver.fake.FakeFtpServer;
//import org.mockftpserver.fake.UserAccount;
//import org.mockftpserver.fake.filesystem.DirectoryEntry;
//import org.mockftpserver.fake.filesystem.FileEntry;
//import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URISyntaxException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.nio.file.Files;
//
//public class FtpClientIntegrationTest {
//    private FakeFtpServer fakeFtpServer;
//
//    private FtpClient ftpClient;
//
//    @Before(value = "FakeFTP")
//    public void setup() throws IOException {
//        fakeFtpServer = new FakeFtpServer();
//        fakeFtpServer.addUserAccount(new UserAccount("root", "root", "/data"));
//
//        UnixFakeFileSystem fileSystem = new UnixFakeFileSystem();
//        fileSystem.add(new DirectoryEntry("/data"));
//        fileSystem.add(new FileEntry("/data/reportTest.txt", "abcdef 1234567890"));
//        fakeFtpServer.setFileSystem(fileSystem);
//        fakeFtpServer.setServerControlPort(0);
//
//        fakeFtpServer.start();
//
//        ftpClient = new FtpClient("192.168.0.101", fakeFtpServer.getServerControlPort(), "root", "root");
//        ftpClient.open();
//    }
//
//    @After(value = "FakeFTP down")
//    public void teardown() throws IOException {
//        ftpClient.close();
//        fakeFtpServer.stop();
//    }
//
//    @Test
//    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
//        String ftpUrl = String.format(
//                "ftp://root:root@192.168.1.101:%d/reportTest.txt", fakeFtpServer.getServerControlPort());
//
//        URLConnection urlConnection = new URL(ftpUrl).openConnection();
//        InputStream inputStream = urlConnection.getInputStream();
//        Files.copy(inputStream, new File("reportTest.txt").toPath());
//        inputStream.close();
//
//       // assertThat(new File("reportTest.txt")).exists();
//
//        new File("reportTest.txt").delete();
//    }
//
//    @Test
//    public void givenLocalFile_whenUploadingIt_thenItExistsOnRemoteLocation()
//            throws URISyntaxException, IOException {
//
//        File file = new File(getClass().getClassLoader().getResource("fel.txt").toURI());
//        //ftpClient.putFileToPath(file, "/felment.txt");
//       // assertThat(fakeFtpServer.getFileSystem().exists("/fel.txt").isTrue());
//
//    }
//
//
//}
