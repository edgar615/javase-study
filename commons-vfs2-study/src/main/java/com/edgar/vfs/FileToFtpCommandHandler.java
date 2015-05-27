package com.edgar.vfs;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.ftp.FtpFileSystemConfigBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FileToFtpCommand的处理类
 */
public class FileToFtpCommandHandler implements CommandHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileToFtpCommandHandler.class);
    private FileSystemManager fileSystemManager;

    private final String ftpPath;

    public FileToFtpCommandHandler(String ftpPath) {
        this.ftpPath = ftpPath;
        FtpFileSystemConfigBuilder builder = FtpFileSystemConfigBuilder.getInstance();
        FileSystemOptions opts = new FileSystemOptions();
        builder.setPassiveMode(opts, true);
        builder.setUserDirIsRoot(opts, true);
        try {
            this.fileSystemManager = VFS.getManager();
        } catch (FileSystemException e) {
            LOGGER.error("failed create fileSystemManager");
            throw new RuntimeException("failed create fileSystemManager");
        }
    }

    @Override
    public void handle(Command command) {
        if (!(command instanceof FileToFtpCommand)) {
            LOGGER.info("UnSupportedType {}", command.getClass());
            throw new RuntimeException("UnSupportedType : " + command.getClass());
        }
        FileToFtpCommand cmd = (FileToFtpCommand) command;
        try {
            FileObject dest = fileSystemManager.resolveFile(ftpPath + cmd.getRelativePath());
            dest.copyFrom(cmd.getSrc(), Selectors.SELECT_SELF);
            LOGGER.info("upload {} to {}", cmd.getSrc().getName().getPath(), dest.getName().getPath());
        } catch (FileSystemException e) {
            LOGGER.error("failed upload {}", cmd.getSrc().getName().getPath());
            throw new RuntimeException("failed upload " + cmd.getSrc().getName().getBaseName(), e.getCause());
        }
    }
}
