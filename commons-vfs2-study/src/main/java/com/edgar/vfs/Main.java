package com.edgar.vfs;

import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.DirectoryEntry;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.WindowsFakeFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by edgar on 15-5-26.
 */
public class Main {

    private final Logger logger = LoggerFactory.getLogger("FtpUploadThread");

    private static final String HOME_DIR = "f:\\";

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pwd";
    private static final String SRC_PATH = "file:////f:/camelinaction-master";
    private static final ExecutorService EXEC = Executors.newCachedThreadPool();
    private BlockingQueue<Command> queue;
    private String ftpPath;
    private FakeFtpServer fakeFtpServer;

    /**
     * 启动FTP
     */
    private void start() {
        queue = new LinkedBlockingDeque<>();

        fakeFtpServer = new FakeFtpServer();
        fakeFtpServer.addUserAccount(new UserAccount(USERNAME, PASSWORD, HOME_DIR));

        FileSystem fileSystem = new WindowsFakeFileSystem();
        fileSystem.add(new DirectoryEntry(HOME_DIR));
        fakeFtpServer.setFileSystem(fileSystem);
        fakeFtpServer.setServerControlPort(21);
        fakeFtpServer.start();
        int port = fakeFtpServer.getServerControlPort();
        ftpPath = "ftp://" + USERNAME + ":" + PASSWORD + "@127.0.0.1:" + port;
    }

    /**
     * 关闭FTP
     */
    private void stop() {
        fakeFtpServer.stop();
        EXEC.shutdown();
    }

    /**
     * 单线程上传
     */
    private void singleUpload() {
        final CommandHandler handler = new FileToFtpCommandHandler(ftpPath);
        //producer
        FileFinder fileFinder = new FileFinder(queue);
        fileFinder.find(SRC_PATH);
        int count = queue.size();
        int uploadedCount = 0;
        //consumer
        while (uploadedCount != count) {
            try {
                Command command = queue.take();
                handler.handle(command);
                uploadedCount ++;
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * 多线程上传
     * @param consumerCount 上传的线程数
     */
    private void multiThreadUpload(int consumerCount) {
        final CommandHandler handler = new FileToFtpCommandHandler(ftpPath);

        //producer
        EXEC.submit(new Runnable() {
            @Override
            public void run() {
                FileFinder fileFinder = new FileFinder(queue);
                fileFinder.find(SRC_PATH);
            }
        });
        //consumer
        for (int i = 0; i < consumerCount; i++) {
            EXEC.execute(new Runnable() {
                private final Logger logger = LoggerFactory.getLogger("FtpUploadThread");

                @Override
                public void run() {
                    while (true) {
                        try {
                            Command command = queue.take();
                            handler.handle(command);
                        } catch (InterruptedException e) {
                            logger.error("failed upload");
                        }
                    }
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main main = new Main();
        main.start();
        main.singleUpload();
//        main.multiThreadUpload();
//        Thread.yield();
        main.stop();
    }
}
