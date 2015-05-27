package com.edgar.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上传文件的命令.
 */
public class FileToFtpCommand implements Command {
    //源文件
    private final FileObject src;
    //源文件相对于上传目录的路径
    private final String relativePath;

    private FileToFtpCommand(FileObject src, String relativePath) {
        this.src = src;
        this.relativePath = relativePath;
    }

    public static FileToFtpCommand newInstance(FileObject src, String relativePath) {
        return new FileToFtpCommand(src, relativePath);
    }

    public String getRelativePath() {
        return relativePath;
    }

    public FileObject getSrc() {
        return src;
    }

}
