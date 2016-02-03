package com.github.igorsuhorukov.url.handler.vfs;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class VFSStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    public static final String VFS_PROTOCOL = "vfs";

    public URLStreamHandler createURLStreamHandler(String protocol) {
        if(VFS_PROTOCOL.equals(protocol)){
            return new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(URL url) throws IOException {
                    try {
                        FileSystemManager fileManager = VFS.getManager();
                        FileObject fileObject = fileManager.resolveFile(getPath(url), configureDefaultOptions());
                        return fileObject.getURL().openConnection();
                    } catch (Exception e){
                        throw new IOException(e);
                    }
                }

                private FileSystemOptions configureDefaultOptions() {
                    FileSystemOptions fileSystemOptions = new FileSystemOptions();
                    SftpFileSystemConfigBuilder.getInstance().setUserDirIsRoot(fileSystemOptions, false);
                    return fileSystemOptions;
                }

                private String getPath(URL url) {
                    String path = url.getFile();
                    return path!=null && path.startsWith("/") && path.length()>1 ? path.substring(1) : path;
                }
            };
        }
        return null;
    }
}
