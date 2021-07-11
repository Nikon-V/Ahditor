package com.epita.assistants.ping.Features.Maven;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Class.ProjectClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;

public class Exec extends FeatureClass {
    public Exec() {
        super(Mandatory.Features.Maven.EXEC);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        var mvn = new ProcessBuilder();
        mvn.directory(Path.of(((ProjectClass) project).configuration.executionPath).toFile());

        mvn.redirectOutput(ProcessBuilder.Redirect.PIPE);


        //DIRTY COMMAND LINE BUILDING
        StringBuilder arguments = new StringBuilder();
        ArrayList<String> executionArguments = ((ProjectClass) project).configuration.executionArguments;
        for (var v : executionArguments){
            arguments.append(v.toString());
            arguments.append(" ");
        }
        StringBuilder mainClassArg = new StringBuilder();
        ArrayList<String> cmdl = new ArrayList<>();


        cmdl.add("mvn");
        cmdl.add("exec:java");

        mainClassArg.append("-Dexec.mainClass=\"");

        mainClassArg.append(((ProjectClass) project).configuration.executionMainClass);

        mainClassArg.append("\"");


        cmdl.add(mainClassArg.toString());


        StringBuilder args = new StringBuilder();
        args.append("-Dexec.args=\"");
        args.append(arguments);
        args.append("\"");

        cmdl.add(args.toString());

        ArrayList<String> wrap = new ArrayList<>();
        wrap.add("/bin/sh");
        wrap.add("-c");
        StringBuilder sb = new StringBuilder();
        for (String s : cmdl)
        {
            sb.append(s);
            sb.append(' ');
        }
        wrap.add(sb.toString());

        mvn.command(wrap);
        try {
            System.out.println(wrap.toString());
            var process = mvn.start();
            try{
                process.waitFor();
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
                System.out.println(e.getMessage());
                return new ExecutionReportClass(false);
            }
            System.out.println(process.exitValue());
            byte[] arr = new byte[500];
            while (process.getErrorStream().read(arr) > 0)
            {
                System.out.print(new String(arr));
            }
            while (process.getInputStream().read(arr) > 0)
            {
                System.out.print(new String(arr));
            }

        } catch (Exception e) {
            System.out.println(e.getClass().toString());
            System.out.println(e.getMessage());
            return new ExecutionReportClass(false);
        }
        return new ExecutionReportClass(true);
    }
}
