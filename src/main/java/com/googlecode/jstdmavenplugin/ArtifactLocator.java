package com.googlecode.jstdmavenplugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

/**
 * Copyright 2009-2011, Burke Webster (burke.webster@gmail.com)
 */
// todo improve this
public class ArtifactLocator
{
    private MavenProject project;

    public ArtifactLocator(MavenProject project)
    {
        this.project = project;
    }

    public Artifact findArtifact(String groupId, String artifactId)
    {
        for (Object object : project.getArtifacts())
        {
            Artifact artifact = (Artifact) object;
            if (artifact.getGroupId().equals(groupId) && matchesArtifactId(artifactId, artifact))
            {
                return artifact;
            }
        }

        Log log = MojoLogger.getInstance().getLog();
        log.error(String.format("Failed to locate %s:%s", groupId, artifactId));
        log.error("This probably means that you didn't specify it as a dependency in the pom.xml file");
        
        throw new RuntimeException(String.format("Failed to locate %s:%s", groupId, artifactId));
    }

    private boolean matchesArtifactId(String artifactId, Artifact artifact) {
        return artifact.getArtifactId().equals(artifactId) ||
                artifact.getArtifactId().equals(String.format("maven-%s-plugin", artifactId)) ||
                artifact.getArtifactId().equals(String.format("%s-maven-plugin", artifactId));
    }
}
