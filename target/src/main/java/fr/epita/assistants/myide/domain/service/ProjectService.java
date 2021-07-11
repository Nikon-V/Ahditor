package fr.epita.assistants.myide.domain.service;

import com.epita.assistants.ping.Class.ProjectClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;

import java.nio.file.Path;
import javax.validation.constraints.NotNull;

/**
 * You will handle your projects through this service.
 */
public abstract class ProjectService {


    public static Project project = null;
    /**
     * Load a {@link Project} from a path.
     *
     * @param root Path of the root of the project to load.
     * @return New project.
     */
    public static @NotNull Project load(@NotNull final Path root)  {
        project = new ProjectClass(root);
        return project;
    }

    public static @NotNull Project load(@NotNull final Path root, @NotNull final Path configPath)  {
        project = new ProjectClass(root, configPath);
        return project;
    }


    /**
     * Execute the given feature on the given project.
     *
     * @param project Project for which the features is executed.
     * @param featureType Type of the feature to execute.
     * @param params Parameters given to the features.
     * @return Execution report of the feature.
     */
    public static @NotNull Feature.ExecutionReport execute(@NotNull final Project project,@NotNull final Feature.Type featureType, final Object... params) {
        if (project.getFeature(featureType).isPresent())
            return project.getFeature(featureType).get().execute(project, params);
        return new ExecutionReportClass(false);
    }



}
