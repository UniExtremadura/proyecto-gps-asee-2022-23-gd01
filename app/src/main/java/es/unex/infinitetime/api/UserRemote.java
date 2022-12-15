
package es.unex.infinitetime.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import es.unex.infinitetime.cryptography.Hash;
import es.unex.infinitetime.model.User;

public class UserRemote {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("hash")
    @Expose
    private String hash;

    @SerializedName("salt")
    @Expose
    private String salt;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UserRemote() {
    }

    public UserRemote(String id, String username, String email, String hash, String salt) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserRemote fromUser(User user) {
        return new UserRemote(
                Long.toString(user.getId()),
                user.getUsername(),
                user.getEmail(),
                Hash.arrayByteToString(user.getHash()),
                Hash.arrayByteToString(user.getSalt())
        );
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
