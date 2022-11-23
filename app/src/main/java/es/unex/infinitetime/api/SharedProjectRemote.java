
package es.unex.infinitetime.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import es.unex.infinitetime.persistence.SharedProject;

public class SharedProjectRemote {

    @SerializedName("project_id")
    @Expose
    private String projectId;
    @SerializedName("user_id")
    @Expose
    private String userId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SharedProjectRemote() {
    }

    /**
     * 
     * @param projectId identificador del proyecto
     * @param userId identificador del usuario
     */
    public SharedProjectRemote(String projectId, String userId) {
        super();
        this.projectId = projectId;
        this.userId = userId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static SharedProjectRemote fromSharedProject(SharedProject sharedProject) {
        return new SharedProjectRemote(
                Long.toString(sharedProject.getProjectId()),
                Long.toString(sharedProject.getUserId()));
    }

}
