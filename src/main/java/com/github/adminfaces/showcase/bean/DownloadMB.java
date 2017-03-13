package com.github.adminfaces.showcase.bean;

import org.apache.commons.compress.utils.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by rmpestano on 11/03/17.
 */
@Named
@ApplicationScoped
public class DownloadMB {
    private StreamedContent streamedContent;
    private String baseDir = Paths.get("").toAbsolutePath().toString();


    public void downloadTheme() throws IOException {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream("target/admin-theme.jar"));
        addDirectory(baseDir + "/target/showcase/resources/primefaces-admin", baseDir + "/target/showcase/resources/primefaces-admin/", zipFile, true);
        addDirectory(baseDir + "/target/showcase/resources/bootstrap", baseDir + "/target/showcase/resources/bootstrap/", zipFile, true, Arrays.asList("css"));
        addDirectory(baseDir + "/target/showcase/resources/js", baseDir + "/target/showcase/resources/js/", zipFile, true, Arrays.asList("prism.js", "chart.min.js", "admintemplate.js"));
        addEntry(baseDir + "/target/classes/theme-web.xml", "/META-INF/web-fragment.xml", zipFile);
        IOUtils.closeQuietly(zipFile);
        streamedContent = new DefaultStreamedContent(new FileInputStream("target/admin-theme.jar"), "application/java-archive", "admin-theme.jar");
    }

    public void downloadTemplate() throws IOException {
        ZipOutputStream zipFile = new ZipOutputStream(new FileOutputStream("target/admin-template.jar"));
        addEntry(baseDir + "/target/showcase/403.xhtml", "/META-INF/resources/403.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/404.xhtml", "/META-INF/resources/404.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/500.xhtml", "/META-INF/resources/500.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/expired.xhtml", "/META-INF/resources/expired.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/optimistic.xhtml", "/META-INF/resources/optimistic.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/admin.xhtml", "/META-INF/resources/admin.xhtml", zipFile);
        addEntry(baseDir + "/target/classes/template-web.xml", "/META-INF/web-fragment.xml", zipFile);
        addEntry(baseDir + "/target/classes/admin.taglib.xml", "/META-INF/admin.taglib.xml", zipFile);
        addEntry(baseDir + "/target/showcase/WEB-INF/beans.xml", "/META-INF/beans.xml", zipFile);
        addEntry(baseDir + "/target/showcase/WEB-INF/faces-config.xml", "/META-INF/faces-config.xml", zipFile);
        addEntry(baseDir + "/target/showcase/resources/admin/sidebar.xhtml", "/META-INF/resources/admin/sidebar.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/resources/admin/breadcrumb.xhtml", "/META-INF/resources/admin/breadcrumb.xhtml", zipFile);
        addEntry(baseDir + "/target/showcase/resources/js/admintemplate.js", "/META-INF/resources/js/admintemplate.js", zipFile);
        addEntry(baseDir + "/target/classes/config/admin-config.properties", "/config/admin-config.properties", zipFile);
        addEntry(baseDir + "/target/classes/admin.properties", "/admin.properties", zipFile);
        addDirectory(baseDir + "/target/classes/com", baseDir + "/target/classes/com", zipFile, false, Arrays.asList("admin-config.properties", "less", "showcase"));
        IOUtils.closeQuietly(zipFile);
        streamedContent = new DefaultStreamedContent(new FileInputStream("target/admin-template.jar"), "application/java-archive", "admin-template.jar");
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

}
