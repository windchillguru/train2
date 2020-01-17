package ext.example.pg.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _PGGroup extends wt.fc.WTObject implements java.io.Externalizable {
    static final long serialVersionUID = 1;

    static final String RESOURCE = "ext.example.pg.model.modelResource";
    static final String CLASSNAME = PGGroup.class.getName();

    /**
     * 组名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGGroup
     */
    public static final String PG_GROUP_NAME = "pgGroupName";
    static int PG_GROUP_NAME_UPPER_LIMIT = -1;
    String pgGroupName;

    /**
     * 组名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGGroup
     */
    public String getPgGroupName() {
        return pgGroupName;
    }

    /**
     * 组名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGGroup
     */
    public void setPgGroupName(String pgGroupName) throws wt.util.WTPropertyVetoException {
        pgGroupNameValidate(pgGroupName);
        this.pgGroupName = pgGroupName;
    }

    void pgGroupNameValidate(String pgGroupName) throws wt.util.WTPropertyVetoException {
        if (pgGroupName == null || pgGroupName.trim().length() == 0)
            throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "pgGroupName")},
                    new java.beans.PropertyChangeEvent(this, "pgGroupName", this.pgGroupName, pgGroupName));
        if (PG_GROUP_NAME_UPPER_LIMIT < 1) {
            try {
                PG_GROUP_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pgGroupName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                PG_GROUP_NAME_UPPER_LIMIT = 200;
            }
        }
        if (pgGroupName != null && !wt.fc.PersistenceHelper.checkStoredLength(pgGroupName.toString(), PG_GROUP_NAME_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "pgGroupName"), String.valueOf(Math.min(PG_GROUP_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "pgGroupName", this.pgGroupName, pgGroupName));
    }

    /**
     * @see PGGroup
     */
    public static final String COMMENTS = "comments";
    static int COMMENTS_UPPER_LIMIT = -1;
    String comments;

    /**
     * @see PGGroup
     */
    public String getComments() {
        return comments;
    }

    /**
     * @see PGGroup
     */
    public void setComments(String comments) throws wt.util.WTPropertyVetoException {
        commentsValidate(comments);
        this.comments = comments;
    }

    void commentsValidate(String comments) throws wt.util.WTPropertyVetoException {
        if (COMMENTS_UPPER_LIMIT < 1) {
            try {
                COMMENTS_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("comments").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                COMMENTS_UPPER_LIMIT = 4000;
            }
        }
        if (comments != null && !wt.fc.PersistenceHelper.checkStoredLength(comments.toString(), COMMENTS_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "comments"), String.valueOf(Math.min(COMMENTS_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "comments", this.comments, comments));
    }

    /**
     * @see PGGroup
     */
    public static final String ENABLED = "enabled";
    Boolean enabled = true;

    /**
     * @see PGGroup
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @see PGGroup
     */
    public void setEnabled(Boolean enabled) throws wt.util.WTPropertyVetoException {
        enabledValidate(enabled);
        this.enabled = enabled;
    }

    void enabledValidate(Boolean enabled) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGGroup
     */
    public static final String ROOT = "root";
    Boolean root = false;

    /**
     * @see PGGroup
     */
    public Boolean getRoot() {
        return root;
    }

    /**
     * @see PGGroup
     */
    public void setRoot(Boolean root) throws wt.util.WTPropertyVetoException {
        rootValidate(root);
        this.root = root;
    }

    void rootValidate(Boolean root) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGGroup
     */
    public static final String PG_NAME = "pgName";
    static int PG_NAME_UPPER_LIMIT = -1;
    String pgName;

    /**
     * @see PGGroup
     */
    public String getPgName() {
        return pgName;
    }

    /**
     * @see PGGroup
     */
    public void setPgName(String pgName) throws wt.util.WTPropertyVetoException {
        pgNameValidate(pgName);
        this.pgName = pgName;
    }

    void pgNameValidate(String pgName) throws wt.util.WTPropertyVetoException {
        if (PG_NAME_UPPER_LIMIT < 1) {
            try {
                PG_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("pgName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                PG_NAME_UPPER_LIMIT = 200;
            }
        }
        if (pgName != null && !wt.fc.PersistenceHelper.checkStoredLength(pgName.toString(), PG_NAME_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "pgName"), String.valueOf(Math.min(PG_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "pgName", this.pgName, pgName));
    }

    public String getConceptualClassname() {
        return CLASSNAME;
    }

    public wt.introspection.ClassInfo getClassInfo() throws wt.introspection.WTIntrospectionException {
        return wt.introspection.WTIntrospector.getClassInfo(getConceptualClassname());
    }

    public String getType() {
        try {
            return getClassInfo().getDisplayName();
        } catch (wt.introspection.WTIntrospectionException wte) {
            return wt.util.WTStringUtilities.tail(getConceptualClassname(), '.');
        }
    }

    public static final long EXTERNALIZATION_VERSION_UID = -6760671693403725462L;

    public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
        output.writeLong(EXTERNALIZATION_VERSION_UID);

        super.writeExternal(output);

        output.writeObject(comments);
        output.writeObject(enabled);
        output.writeObject(pgGroupName);
        output.writeObject(pgName);
        output.writeObject(root);
    }

    protected void super_writeExternal_PGGroup(java.io.ObjectOutput output) throws java.io.IOException {
        super.writeExternal(output);
    }

    public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        long readSerialVersionUID = input.readLong();
        readVersion((PGGroup) this, input, readSerialVersionUID, false, false);
    }

    protected void super_readExternal_PGGroup(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        super.readExternal(input);
    }

    public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.writeExternal(output);

        output.setString("comments", comments);
        output.setBooleanObject("enabled", enabled);
        output.setString("pgGroupName", pgGroupName);
        output.setString("pgName", pgName);
        output.setBooleanObject("root", root);
    }

    public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.readExternal(input);

        comments = input.getString("comments");
        enabled = input.getBooleanObject("enabled");
        pgGroupName = input.getString("pgGroupName");
        pgName = input.getString("pgName");
        root = input.getBooleanObject("root");
    }

    boolean readVersion_6760671693403725462L(java.io.ObjectInput input, long readSerialVersionUID, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        if (!superDone)
            super.readExternal(input);

        comments = (String) input.readObject();
        enabled = (Boolean) input.readObject();
        pgGroupName = (String) input.readObject();
        pgName = (String) input.readObject();
        root = (Boolean) input.readObject();
        return true;
    }

    protected boolean readVersion(PGGroup thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        boolean success = true;

        if (readSerialVersionUID == EXTERNALIZATION_VERSION_UID)
            return readVersion_6760671693403725462L(input, readSerialVersionUID, superDone);
        else
            success = readOldVersion(input, readSerialVersionUID, passThrough, superDone);

        if (input instanceof wt.pds.PDSObjectInput)
            wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

        return success;
    }

    protected boolean super_readVersion_PGGroup(_PGGroup thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
    }

    boolean readOldVersion(java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID=" + readSerialVersionUID + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID);
    }
}
