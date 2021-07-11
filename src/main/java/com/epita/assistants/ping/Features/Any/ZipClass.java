package com.epita.assistants.ping.Features.Any;

import org.apache.commons.compress.archivers.zip.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipClass {
    private String outputFileName = new String();
    private String rootPath = new String();
    private List<String> fileList;

    public ZipClass(String rootPath, String outputFileName) {
        this.rootPath = rootPath;
        this.outputFileName = outputFileName;
        fileList = new ArrayList<String>();
    }

    public void Compress(String zipFileName) {
        String source = new File(rootPath).getName();
        byte[] buff = new byte[1024];
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFileName);
            zos = new ZipOutputStream(fos);
            FileInputStream in = null;

            for (String file: this.fileList) {
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(rootPath + File.separator + file);
                    int len;
                    while ((len = in .read(buff)) > 0) {
                        zos.write(buff, 0, len);
                    }
                } finally {
                    in.close();
                }
            }
            zos.closeEntry();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                zos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void genFile(File node) {
        if (node.isFile()) {
            fileList.add(genCompress(node.toString()));
        }
        if (node.isDirectory()) {
            String[] children = node.list();
            for (String filename: children) {
                genFile(new File(node, filename));
            }
        }
    }

    private String genCompress(String file) {
        return file.substring(rootPath.length() + 1, file.length());
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
