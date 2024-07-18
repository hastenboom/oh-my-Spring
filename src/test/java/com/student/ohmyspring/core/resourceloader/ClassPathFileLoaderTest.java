package com.student.ohmyspring.core.resourceloader;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class ClassPathFileLoaderTest {

    final String packagePath = "com/student/ohmyspring/demo";
    ClassPathFileLoader classPathFileLoader = new ClassPathFileLoader("com/student/ohmyspring/demo");

    @Test
    void loadFiles() throws IOException {
        List<File> files = classPathFileLoader.getFiles(packagePath, null);
        for (File file : files) {
            String canonicalPath = file.getCanonicalPath();
            System.out.println(canonicalPath);
        }
        System.out.println();
        for (File file : files) {

            String canonicalPath = file.getCanonicalPath();
            String classes = canonicalPath.substring(canonicalPath.lastIndexOf("classes") + "classes".length() + 1);
            System.out.println(classes);
        }

    }

    @Test
    void getClassFileFullPath() throws IOException, ClassNotFoundException {
        List<String> fullPath = classPathFileLoader.getClassFileFullPath(packagePath);
        for (String path : fullPath) {
//            System.out.println(path);
            Class<?> aClass = Class.forName(path);
            System.out.println(aClass.toString());
        }
    }


}