package com.github.adminfaces.showcase.bean;

import org.apache.commons.compress.utils.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by rmpestano on 11/03/17.
 */
@Named
@ApplicationScoped
public class DownloadMB {
    private StreamedContent streamedContent;
    private String baseDir = Paths.get("").toAbsolutePath().toString();
    private final Logger log = Logger.getLogger(getClass().getName());


    public void downloadTheme() throws IOException {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream("target/admin-theme.jar"));
        addDirectory(baseDir + "/src/main/webapp/resources/primefaces-admin", baseDir + "/src/main/webapp/resources/primefaces-admin/", zipFile, true);
        addDirectory(baseDir + "/src/main/webapp/resources/bootstrap", baseDir + "/src/main/webapp/resources/bootstrap/", zipFile, true, Arrays.asList("css"));
        addDirectory(baseDir + "/src/main/webapp/resources/bootstrap", baseDir + "/src/main/webapp/resources/admin-lte/", zipFile, true);

        addDirectory(baseDir + "/src/main/webapp/resources/js", baseDir + "/src/main/webapp/resources/js/", zipFile, true, Arrays.asList("prism.js", "chart.min.js", "admintemplate.js"));
        addEntry(baseDir + "/target/classes/theme-web.xml", "/META-INF/web-fragment.xml", zipFile);
        IOUtils.closeQuietly(zipFile);
        streamedContent = new DefaultStreamedContent(new FileInputStream("target/admin-theme.jar"), "application/java-archive", "admin-theme.jar");
    }

    public void downloadTemplate() throws IOException {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream("target/admin-template.jar"));
        addEntry(baseDir + "/src/main/webapp/403.xhtml", "/META-INF/resources/403.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/404.xhtml", "/META-INF/resources/404.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/500.xhtml", "/META-INF/resources/500.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/expired.xhtml", "/META-INF/resources/expired.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/optimistic.xhtml", "/META-INF/resources/optimistic.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/admin.xhtml", "/META-INF/resources/admin.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/admin-top.xhtml", "/META-INF/resources/admin-top.xhtml", zipFile);
        addEntry(baseDir + "/target/classes/template-web.xml", "/META-INF/web-fragment.xml", zipFile);
        addEntry(baseDir + "/target/classes/admin.taglib.xml", "/META-INF/admin.taglib.xml", zipFile);
        addEntry(baseDir + "/src/main/webapp/WEB-INF/beans.xml", "/META-INF/beans.xml", zipFile);
        addEntry(baseDir + "/src/main/webapp/WEB-INF/faces-config.xml", "/META-INF/faces-config.xml", zipFile);
        addEntry(baseDir + "/src/main/webapp/resources/admin/sidebar.xhtml", "/META-INF/resources/admin/sidebar.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/resources/admin/breadcrumb.xhtml", "/META-INF/resources/admin/breadcrumb.xhtml", zipFile);
        addEntry(baseDir + "/src/main/webapp/resources/js/admintemplate.js", "/META-INF/resources/js/admintemplate.js", zipFile);
        addEntry(baseDir + "/src/main/webapp/resources/js/control-sidebar.js", "/META-INF/resources/js/control-sidebar.js", zipFile);
        addEntry(baseDir + "/src/main/webapp/resources/images/ajaxloadingbar.gif", "/META-INF/resources/images/ajaxloadingbar.gif", zipFile);
        addEntry(baseDir + "/target/classes/config/admin-config.properties", "/config/admin-config.properties", zipFile);
        addEntry(baseDir + "/target/classes/admin.properties", "/admin.properties", zipFile);
        addDirectory(baseDir + "/target/classes/com", baseDir + "/target/classes/com", zipFile, false, Arrays.asList("admin-config.properties", "less", "showcase"));
        IOUtils.closeQuietly(zipFile);
        streamedContent = new DefaultStreamedContent(new FileInputStream("target/admin-template.jar"), "application/java-archive", "admin-template.jar");
    }

    public void downloadProject() throws IOException {
        unzip(getClass().getResourceAsStream("/admin-starter.zip"));
        copyDir(new File("src/main/resources/less/"), new File("target/admin-starter/src/main/resources/less"));
        copyDir(new File("src/main/java/com/github/adminfaces/template"), new File("target/admin-starter/src/main/java/com/github/adminfaces/template"));
        copyFile("src/main/webapp/403.xhtml", "target/admin-starter/src/main/webapp/403.xhtml");
        copyFile("src/main/webapp/404.xhtml", "target/admin-starter/src/main/webapp/404.xhtml");
        copyFile("src/main/webapp/500.xhtml", "target/admin-starter/src/main/webapp/500.xhtml");
        copyFile("src/main/webapp/expired.xhtml", "target/admin-starter/src/main/webapp/expired.xhtml");
        copyFile("src/main/webapp/optimistic.xhtml", "target/admin-starter/src/main/webapp/optimistic.xhtml");
        copyFile("src/main/webapp/admin.xhtml", "target/admin-starter/src/main/webapp/admin.xhtml");
        copyFile("src/main/webapp/admin-top.xhtml", "target/admin-starter/src/main/webapp/admin-top.xhtml");
        copyFile("src/main/resources/admin.properties", "target/admin-starter/src/main/resources/admin.properties");
        copyDir(new File("src/main/webapp/resources/primefaces-admin"), new File("target/admin-starter/src/main/webapp/resources/primefaces-admin"));
        copyFile("src/main/webapp/WEB-INF/admin.taglib.xml", "target/admin-starter/src/main/webapp/WEB-INF/admin.taglib.xml");
        copyDir("src/main/resources/META-INF", "target/admin-starter/src/main/resources/META-INF");

        copyDir(new File("src/main/webapp/resources/js"), new File("target/admin-starter/src/main/webapp/resources/js"));
        copyDir(new File("src/main/webapp/resources/images"), new File("target/admin-starter/src/main/webapp/resources/images"));
        copyDir(new File("src/main/webapp/resources/bootstrap"), new File("target/admin-starter/src/main/webapp/resources/bootstrap"));
        copyDir(new File("src/main/webapp/resources/admin-lte"), new File("target/admin-starter/src/main/webapp/resources/admin-lte"));
        copyDir(new File("src/main/webapp/resources/components"), new File("target/admin-starter/src/main/webapp/resources/components"));
        Files.delete(Paths.get("target/admin-starter/src/main/webapp/resources/js/prism.js"));
        Files.delete(Paths.get("target/admin-starter/src/main/webapp/resources/js/chart.min.js"));


        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream("target/admin-starter.zip"));
        addDirectory(baseDir + "/target/admin-starter", baseDir + "/target/admin-starter/", zipFile, false);

        IOUtils.closeQuietly(zipFile);
        streamedContent = new DefaultStreamedContent(new FileInputStream("target/admin-starter.zip"), "application/zip", "admin-starter.zip");
    }

    private static void copyDir(String src, String dest) throws IOException {
        copyDir(new File(src),new File(dest));
    }

    private static void copyDir(File src, File dest)
            throws IOException {

        if (src.isDirectory()) {

            //if directory not exists, create it
            if (!dest.exists()) {
                dest.mkdirs();
            }

            //list all the directory contents
            String files[] = src.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copyDir(srcFile, destFile);
            }

        } else {
            //if file, then copy it
            copyFile(src, dest);
        }
    }

    private static void copyFile(String src, String dest) throws IOException {
        copyFile(new File(src), new File(dest));
    }

    private static void copyFile(File src, File dest) throws IOException {
        if (!dest.exists() && dest.isDirectory()) {
            dest.mkdirs();
        }
        //Use bytes stream to support all file types
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dest);

        byte[] buffer = new byte[1024];

        int length;
        //copy the file content in bytes
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.close();
    }

    public StreamedContent getFile() {
        return streamedContent;
    }

    private static void addDirectory(String rootDir, String sourceDir, ZipOutputStream out, boolean isResource) throws IOException, FileNotFoundException {
        addDirectory(rootDir, sourceDir, out, isResource, new ArrayList<String>());
    }

    private static void addDirectory(String rootDir, String sourceDir, ZipOutputStream out, boolean isResource, List<String> exclusions) throws IOException, FileNotFoundException {
        for (File file : new File(sourceDir).listFiles()) {
            if (!exclusions.isEmpty() && exclusions.contains(file.getName())) {
                continue;
            }
            if (file.isDirectory()) {
                addDirectory(rootDir, (sourceDir + File.separator + file.getName()).replaceAll("//", "/"), out, isResource, exclusions);
            } else {
                ZipEntry entry = new ZipEntry(((isResource ? "META-INF/resources/" : "") + (sourceDir.replace(rootDir.substring(0, rootDir.lastIndexOf('/')), "") + File.separator + file.getName())).replaceAll("//", "/"));
                out.putNextEntry(entry);
                FileInputStream in = new FileInputStream(sourceDir + File.separator + file.getName());
                IOUtils.copy(in, out);
                IOUtils.closeQuietly(in);
            }
        }
    }

    /**
     * @param out
     * @throws IOException
     */
    public void addEntry(String content, String entryName, ZipOutputStream out) throws IOException {
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);
        FileInputStream in = new FileInputStream(content);
        IOUtils.copy(in, out);
        IOUtils.closeQuietly(in);
    }


    private void unzip(InputStream zipFile) throws IOException {
        String targetDir = baseDir + "/target";
        ZipInputStream zipInputStream = new ZipInputStream(zipFile);
        try {
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            while (zipEntry != null) {
                File destPath = new File(targetDir, zipEntry.getName());
                log.fine(String.format("Unpacking {}.", destPath.getAbsoluteFile()));
                if (!zipEntry.isDirectory()) {
                    FileOutputStream fout = new FileOutputStream(destPath);
                    final byte[] buffer = new byte[8192];
                    int n = 0;
                    while (-1 != (n = zipInputStream.read(buffer))) {
                        fout.write(buffer, 0, n);
                    }
                    fout.close();
                } else {
                    destPath.mkdir();
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        } finally {
            zipInputStream.close();
        }
    }

}
