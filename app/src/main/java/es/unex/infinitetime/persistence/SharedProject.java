package es.unex.infinitetime.persistence;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import es.unex.infinitetime.api.SharedProjectRemote;

@Entity(tableName = "shared_project", primaryKeys = {"project_id","user_id"},
    foreignKeys = {
                @ForeignKey(entity = Project.class,
                        parentColumns = "id",
                        childColumns = "project_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = CASCADE)
    }, indices = {@Index(value = {"user_id", "project_id"})})
public class SharedProject {

    @ColumnInfo(name = "project_id")
    private long projectId;
    @ColumnInfo(name = "user_id")
    private long userId;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Ignore
    public static SharedProject fromRemote(SharedProjectRemote sharedProjectRemote) {
        SharedProject sharedProject = new SharedProject();
        sharedProject.setProjectId(Long.parseLong(sharedProjectRemote.getProjectId()));
        sharedProject.setUserId(Long.parseLong(sharedProjectRemote.getUserId()));
        return sharedProject;
    }
}
