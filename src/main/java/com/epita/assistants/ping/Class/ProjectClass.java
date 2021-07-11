package com.epita.assistants.ping.Class;

import com.epita.assistants.ping.Aspects.AnyAspect;
import com.epita.assistants.ping.Aspects.GitAspect;
import com.epita.assistants.ping.Aspects.MavenAspect;
import fr.epita.assistants.myide.domain.entity.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {

    private final Node rootNode;
    private final Set<Aspect> aspectSet;
    public ProjectConfiguration configuration = null;

    public ProjectClass(Path rootPath) {
        if (!rootPath.toFile().isDirectory()) {
            throw new RuntimeException("Root Path is assumed to be only possible as a directory, yet it was a file!");
        }

        this.rootNode = new NodeClass(null, rootPath, Node.Types.FOLDER);
        ((NodeClass) this.rootNode).initiateDiscovery();
        this.aspectSet = new HashSet<>();

        Path gitRoot = Path.of(rootPath.toString(), ".git");
        Path mavenConfig = Path.of(rootPath.toString(), "pom.xml");


        this.aspectSet.add(new AnyAspect());
        if (gitRoot.toFile().exists())
            this.aspectSet.add(new GitAspect(gitRoot));
        if (mavenConfig.toFile().exists())
            this.aspectSet.add(new MavenAspect());

        Path configPath = Path.of(rootPath.toString(), "Ahditor_config.xml");
        if (configPath.toFile().exists())
            configuration = new ProjectConfiguration(configPath);
        else {
            configuration = new ProjectConfiguration();
            System.err.println("Warning: Couldn't find project configuration file: " + configPath.toString());
        }

    }
    public ProjectClass(Path rootPath, Path configPath) {
        configuration = new ProjectConfiguration(configPath);

        if (!rootPath.toFile().isDirectory()) {
            throw new RuntimeException("Root Path is assumed to be only possible as a directory, yet it was a file!");
        }

        this.rootNode = new NodeClass(null, rootPath, Node.Types.FOLDER);
        ((NodeClass) this.rootNode).initiateDiscovery();
        this.aspectSet = new HashSet<>();

        Path gitRoot = Path.of(rootPath.toString(), ".git");
        Path mavenConfig = Path.of(rootPath.toString(), "pom.xml");

        this.aspectSet.add(new AnyAspect());
        if (gitRoot.toFile().exists())
            this.aspectSet.add(new GitAspect(gitRoot));
        if (mavenConfig.toFile().exists())
            this.aspectSet.add(new MavenAspect());

    }

    @Override
    public Node getRootNode() {
        return rootNode;
    }

    @Override
    public Set<Aspect> getAspects() {
        return aspectSet;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        for (var aspect : aspectSet)
        {
            var featureList = aspect.getFeatureList();
            for (var feature : featureList)
            {
                if (feature.type() == featureType)
                    return Optional.of(feature);
            }
        }
        return Optional.empty();
    }

    public void tree()
    {
        _treeDfs(rootNode, 0);
    }

    private void _treeDfs(Node node, int depth)
    {
        _treePrint(node, depth);
        if (node.isFolder()) {
            var children = node.getChildren();
            for (var child : children)
                _treeDfs(child, depth + 1);
        }
    }

    private void _treePrint(Node node, int depth)
    {
        String fileName = node.getPath().getFileName().toString();
        String completedString = " ".repeat(Math.max(0, depth)) +
                fileName;
        System.out.println(completedString);
    }

    @Override
    public List<@NotNull Feature> getFeatures() {
        return Project.super.getFeatures();
    }
}
