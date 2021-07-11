package com.epita.assistants.ping.Aspects;

import com.epita.assistants.ping.Class.AspectClass;
import com.epita.assistants.ping.Features.Git.Add;
import com.epita.assistants.ping.Features.Git.Commit;
import com.epita.assistants.ping.Features.Git.Pull;
import com.epita.assistants.ping.Features.Git.Push;
import com.jcraft.jsch.Session;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;

import java.io.IOException;
import java.nio.file.Path;

public class GitAspect extends AspectClass {

    protected org.eclipse.jgit.api.Git git_object;

    public GitAspect(Path git_file) {
        super(Mandatory.Aspects.GIT);

        try {
            git_object = org.eclipse.jgit.api.Git.open(git_file.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        SshSessionFactory.setInstance(new JschConfigSessionFactory() {
            public void configure(OpenSshConfig.Host hc, Session session) {
                session.setConfig("StrictHostKeyChecking", "no");
            }
        });

        aspectFeatures.add(new Add(git_object));
        aspectFeatures.add(new Commit(git_object));
        aspectFeatures.add(new Pull(git_object));
        aspectFeatures.add(new Push(git_object));
    }
}

//test
