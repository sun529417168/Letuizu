package letuizu.com.moduleim.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table GROUPS.
 */
public class Groups extends UserInfoBean {
    private String displayName;
    private String role;
    private String bulletin;
    private String timestamp;
    private String nameSpelling;

    public Groups() {
        super();
    }

    public Groups(String groupsId) {
        super(groupsId);
    }

    public Groups(String groupsId, String name, String portraitUri, String displayName, String role, String bulletin, String timestamp) {
        super(groupsId, name, portraitUri);
        this.displayName = displayName;
        this.role = role;
        this.bulletin = bulletin;
        this.timestamp = timestamp;
    }

    public Groups(String timestamp, String role, String displayName, String portraitUri, String name, String groupsId) {
        super(groupsId, name, portraitUri);
        this.timestamp = timestamp;
        this.role = role;
        this.displayName = displayName;
    }

    public Groups(String groupsId, String name, String portraitUri, String role) {
        super(groupsId, name, portraitUri);
        this.role = role;
    }

    public Groups(String groupsId, String name, String portraitUri) {
        super(groupsId, name, portraitUri);
    }

    public Groups(String groupsId, String name, String portraitUri, String displayName, String role, String bulletin, String timestamp, String nameSpelling) {
        super(groupsId, name, portraitUri);
        this.displayName = displayName;
        this.role = role;
        this.bulletin = bulletin;
        this.timestamp = timestamp;
        this.nameSpelling = nameSpelling;
    }
    /** Not-null value. */
    public String getGroupsId() {
        return getUserId();
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setGroupsId(String groupsId) {
        setUserId(groupsId);
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBulletin() {
        return bulletin;
    }

    public void setBulletin(String bulletin) {
        this.bulletin = bulletin;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNameSpelling() {
        return nameSpelling;
    }

    public void setNameSpelling(String nameSpelling) {
        this.nameSpelling = nameSpelling;
    }

}
