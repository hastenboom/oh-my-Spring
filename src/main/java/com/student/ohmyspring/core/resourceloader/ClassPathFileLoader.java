package com.student.ohmyspring.core.resourceloader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Student
 */
public class ClassPathFileLoader {

    private String FILE_PROTOCOL = "file";

    private String packagePath = null;



    public ClassPathFileLoader(String packagePath) {
        this.packagePath = packagePath;
    }


    public List<String> getClassFileFullPath() throws IOException {
       return getClassFileFullPath(packagePath);
    }

    /**
     * Useful in Class.forName();
     *
     * @param packagePath
     * @return something like com.example.path1.path2.AClass
     * @throws IOException
     */
    public List<String> getClassFileFullPath(@NotNull final String packagePath) throws IOException {
        List<String> classFileFullPath = new ArrayList<>();

        List<File> fileList = getFiles(packagePath, null);
        for (File file : fileList) {
            String canonicalPath = file.getCanonicalPath();
            String classesPathWithDash =
                    canonicalPath.substring(canonicalPath.lastIndexOf("classes") + "classes".length() + 1);
            String withClass = classesPathWithDash.replace(File.separator, ".");

            // remove ".class", com.example.path1.path2.AClass
            classFileFullPath.add(withClass.substring(0, withClass.lastIndexOf(".")));
        }
        return classFileFullPath;
    }


    /**
     * @param packagePath    packagePath for dir, com/example/path1/path2, for file,
     *                       com/example/path1/path2/AClass.class; Be aware of the inconsistency of path, when talking
     *                       about the package path, we always use "/"
     * @param fileNameFilter
     * @return
     */
    public List<File> getFiles(
            @NotNull final String packagePath,
            @Nullable final Predicate<String> fileNameFilter)
    {
        @Nullable
        URL url = this.getClass().getClassLoader().getResource(packagePath);
        if (url == null) {
            throw new IllegalArgumentException("package path not found: " + packagePath);
        }

        List<File> fileList = new ArrayList<>();
        if (url.getProtocol().equals(FILE_PROTOCOL)) {
            File file = new File(url.getPath());
            if (file.isFile()) {
                fileList.add(file);
            }
            else {
                extractFilesFromDir(file, fileNameFilter, fileList, packagePath);
            }
        }
        return fileList;
    }

    private void extractFilesFromDir(
            final File dir, @Nullable final Predicate<String> fileNameFilter,
            List<File> fileList, final String packagePath)
    {
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return;
        }

        Predicate<File> completeFileFilter = (File file) -> {
            if (file.isFile()) {
                if (fileNameFilter != null) {
                    return fileNameFilter.test(file.getName());
                }
                else {
                    return true;
                }
            }
            return false;
        };
        Arrays.stream(files).forEach(
                file -> {
                    if (completeFileFilter.test(file)) {
                        fileList.add(file);
                    }
                    else if (file.isDirectory()) {
                        //recursive
                        //FIXME: should consider the depth of recursion
                        extractFilesFromDir(file, fileNameFilter, fileList, packagePath + "/" + file.getName());
                    }
                }
        );
    }

}
