package com.edgar.vfs;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.vfs2.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by edgar on 15-5-26.
 */
public class FileFinder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileFinder.class);

    private final BlockingQueue<Command> queue;

    public FileFinder(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    public void find(String folderPath) {

        try {
            FileSystemManager fileSystemManager = VFS.getManager();
            FileObject srcFolder = fileSystemManager.resolveFile(folderPath);
            if (!FileType.FOLDER.equals(srcFolder.getType())) {
                LOGGER.error("{} must be dictory", folderPath);
                throw new IllegalArgumentException(folderPath + " must be dictory");
            }
            findFile(srcFolder, srcFolder);
        } catch (FileSystemException e) {
            LOGGER.error("failed resolve file : {}", folderPath);
            throw new RuntimeException("failed resolve file :" + folderPath, e.getCause());
        } catch (InterruptedException e) {
            LOGGER.error("failed put element in queue");
            throw new RuntimeException("failed put element in queue");
        }
    }

    private void findFile(FileObject srcFolder,FileObject folderPath) throws FileSystemException, InterruptedException {
        FileObject[] children = srcFolder.getChildren();
        for (FileObject fo : children) {
            if (FileType.FILE.equals(fo.getType())) {
                queue.put(FileToFtpCommand.newInstance(fo, StringUtils.substringAfter(fo.getName().getPath(), folderPath.getName().getPath())));
                LOGGER.info("put {} in queue", fo.getName());
            } else if (FileType.FOLDER.equals(fo.getType())) {
                findFile(fo, folderPath);
            } else {
                LOGGER.info("UnSupportedType {}", fo.getName());
            }
        }
    }

}
