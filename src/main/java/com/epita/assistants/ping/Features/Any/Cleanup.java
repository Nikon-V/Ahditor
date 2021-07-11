package com.epita.assistants.ping.Features.Any;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.*;
import java.util.Scanner;

public class Cleanup extends FeatureClass {


    public Cleanup() {
        super(Mandatory.Features.Any.CLEANUP);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try{
            var rootPath = project.getRootNode().getPath().toAbsolutePath();
            FileInputStream fileInputStream = new FileInputStream(String.format("%s/.myideignore", rootPath));
            Scanner scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine())
            {
                deleteFile(new File(String.format("%s/%s", rootPath, scanner.nextLine())));
            }
            return new ExecutionReportClass(true);
        } catch (Exception exception) {
            return new ExecutionReportClass(false);
        }
    }

    void deleteFile(File file) {
        var files= file.listFiles();
        if (files == null) {
            file.delete();
            return;
        }
        for (File f : files)
            deleteFile(f);
        file.delete();
    }

    //    @Override
//    public Feature.ExecutionReport execute(Project project, Object... params)
//    {
//        BufferedReader bufferedReader;
//        try {
//            Node root = project.getRootNode();
//            for (var file: root.getChildren())
//            {
//                if (file.isFile() && file.getPath().getFileName().toString().equals(".myideignore"))
//                {
//                    NodeClass cast = (NodeClass) file;
//                    Node parent = cast.getNodeParent();
//                    NodeClass ignoreNode = new NodeClass(parent, cast.getPath(), Node.Types.FILE);
//                    bufferedReader = new BufferedReader(new FileReader(ignoreNode.getPath().getFileName().toString()));
//                    String line = bufferedReader.readLine();
//                    ArrayList<String> files_to_be_deleted = new ArrayList<>();
//                    while (line != null)
//                    {
//                        files_to_be_deleted.add(line);
//                        line = bufferedReader.readLine();
//                    }
//                    bufferedReader.close();
//                    for (var i : files_to_be_deleted)
//                    {
//                        var res = Search_and_Delete(i, project, root);
//                        if (res == false) {
//                            System.out.println("res = false\n");
//                            return new ExecutionReportClass(false);
//                        }
//                    }
//                    return new ExecutionReportClass(true);
//                }
//            }
//
//        }
//        catch (IOException exception) {
//            return new ExecutionReportClass(false);
//        }
//        return new ExecutionReportClass(false);
//    }

//    public boolean Search_and_Delete(String name_of_the_files_to_be_deleted, Project project, Node root)
//    {
//        var children = root.getChildren();
//        for (var child : children)
//        {
//            if (child.isFile())
//            {
//                if (child.getPath().getFileName().toString().equals(name_of_the_files_to_be_deleted))
//                {
//                    return delete(child);
//                }
//            }
//            else
//            {
//                Search_and_Delete(name_of_the_files_to_be_deleted, project, child);
//            }
//        }
//        return true;
//    }
//
//    public boolean delete(final Node node) {
//        boolean ret;
//        if (node.isFolder())
//        {
//            for (var c : node.getChildren())
//            {
//                ret = this.delete(c);
//                if (!ret)
//                    return false;
//            }
//        }
//        ret = node.getPath().toFile().delete();
//        if (!ret)
//            return false;
//        var parent = ((NodeClass) node).getNodeParent();
//        var siblings  = parent.getChildren();
//        siblings.remove(node);
//        return true;
//    }
}
