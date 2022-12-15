package es.unex.infinitetime.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import es.unex.infinitetime.api.UserRemote;
import es.unex.infinitetime.cryptography.Hash;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "hash", typeAffinity = ColumnInfo.BLOB)
    private byte[] hash;

    @ColumnInfo(name = "salt", typeAffinity = ColumnInfo.BLOB)
    private byte[] salt;

    public long getId() {
        return id;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setId(long id) {
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

    @Ignore
    public static User userFromRemote(UserRemote userRemote) {
        User user = new User();
        user.setId(Long.parseLong(userRemote.getId()));
        user.setUsername(userRemote.getUsername());
        user.setEmail(userRemote.getEmail());
        user.setHash(Hash.stringToArrayByte(userRemote.getHash()));
        user.setSalt(Hash.stringToArrayByte(userRemote.getSalt()));
        return user;
    }

}
