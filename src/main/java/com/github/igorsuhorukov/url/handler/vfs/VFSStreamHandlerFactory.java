package com.github.igorsuhorukov.url.handler.vfs;

import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class VFSStreamHandlerFactory implements java.net.URLStreamHandlerFactory{

    private final URLStreamHandlerFactory urlStreamHandlerFactory;

    public VFSStreamHandlerFactory() throws IOException{
        try {
            FileSystemManager fileManager = VFS.getManager();
            urlStreamHandlerFactory = fileManager.getURLStreamHandlerFactory();
        } catch (FileSystemException e) {
            throw new IOException(e);
        }
    }

    public URLStreamHandler createURLStreamHandler(String protocol) {
        return urlStreamHandlerFactory.createURLStreamHandler(protocol);
    }
}
