package com.epita.assistants.ping.Features.Any;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Dist extends FeatureClass {
    public Dist() {
        super(Mandatory.Features.Any.DIST);
    }
    //debugging
//    @Override
//    public Feature.ExecutionReport execute(Project project, Object... params)
//    {
//        var clean = project.getFeature(Mandatory.Features.Any.CLEANUP).get().execute(project, params);
//        var file = project.getRootNode().toString();
//        var real_file = new File(file);
//        ZipOutputStream zos = null;
//        try {
//            zos = new ZipOutputStream(new FileOutputStream(real_file));
//        } catch (FileNotFoundException e) {
//            return new ExecutionReportClass(false);
//        }
//        ZipOutputStream finalZos = zos;
//        try {
//            Files.walkFileTree(Path.of(file), new SimpleFileVisitor<Path>() {
//                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                    finalZos.putNextEntry(new ZipEntry(file.relativize(file).toString()));
//                    Files.copy(file, finalZos);
//                    finalZos.closeEntry();
//                    return FileVisitResult.CONTINUE;
//                }
//            });
//        } catch (IOException exception) {
//            return new ExecutionReportClass(false);
//        }
//        try {
//            finalZos.close();
//        } catch (IOException exception) {
//            return new ExecutionReportClass(false);
//        }
//        return new ExecutionReportClass(true);

//    }


    @Override
    public ExecutionReport execute(Project project, Object... params) {
        project.getFeature(Mandatory.Features.Any.CLEANUP).get().execute(project, params);
        ZipClass zipClass = new ZipClass(project.getRootNode().getPath().toAbsolutePath().toString(), String.format("%s.zip", project.getRootNode().getPath().getFileName().toString()));
        zipClass.genFile(new File(zipClass.getRootPath()));
        zipClass.Compress(zipClass.getOutputFileName());
        return new ExecutionReportClass(true);
    }
}
