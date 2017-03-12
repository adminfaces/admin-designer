package com.github.adminfaces.showcase.bean;

import org.apache.commons.compress.utils.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by rmpestano on 11/03/17.
 */
@Named
@ApplicationScoped
public class DownloadMB {
    private StreamedContent file;


    public void downloadTheme() throws IOException {
        compressZipfile("target/theme.jar");
        file = new DefaultStreamedContent(new FileInputStream("target/theme.jar"), "application/java-archive", "theme.jar");
    }

    public StreamedContent getFile() {
        return file;
    }

    public static void compressZipfile(String outputFile) throws IOException, FileNotFoundException {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream(outputFile));
        compressDirectoryToZipfile( Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/primefaces-admin", Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/primefaces-admin/", zipFile);
        compressDirectoryToZipfile( Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/bootstrap", Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/bootstrap/", zipFile);
        compressDirectoryToZipfile( Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/js", Paths.get("").toAbsolutePath().toString()+"/target/showcase/resources/js/", zipFile);
        IOUtils.closeQuietly(zipFile);
    }

    private static void compressDirectoryToZipfile(String rootDir, String sourceDir, ZipOutputStream out) throws IOException, FileNotFoundException {
        for (File file : new File(sourceDir).listFiles()) {
            if (file.isDirectory()) {
                compressDirectoryToZipfile(rootDir, (sourceDir + File.separator + file.getName()).replaceAll("//","/"), out);
            } else {
                ZipEntry entry = new ZipEntry(("META-INF/resources/"+(sourceDir.replace(rootDir.substring(0,rootDir.lastIndexOf('/')),"") + File.separator+ file.getName())).replaceAll("//","/"));
                out.putNextEntry(entry);
                FileInputStream in = new FileInputStream(sourceDir + File.separator + file.getName());
                IOUtils.copy(in, out);
                IOUtils.closeQuietly(in);
            }
        }
    }

}
