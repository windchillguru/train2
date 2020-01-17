package ext.example.pg.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _PGMemberLink extends wt.fc.ObjectToObjectLink implements java.io.Externalizable {
    static final long serialVersionUID = 1;

    static final String RESOURCE = "ext.example.pg.model.modelResource";
    static final String CLASSNAME = PGMemberLink.class.getName();

    /**
     * @see PGMemberLink
     */
    public static final String COMMENTS = "comments";
    static int COMMENTS_UPPER_LIMIT = -1;
    String comments;

    /**
     * @see PGMemberLink
     */
    public String getComments() {
        return comments;
    }

    /**
     * @see PGMemberLink
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
     * @see PGMemberLink
     */
    public static final String PG_GROUP_ROLE = "pgGroup";

    /**
     * @see PGMemberLink
     */
    public PGGroup getPgGroup() {
        return (PGGroup) getRoleAObject();
    }

    /**
     * @see PGMemberLink
     */
    public void setPgGroup(PGGroup the_pgGroup) throws wt.util.WTPropertyVetoException {
        setRoleAObject((wt.fc.Persistable) the_pgGroup);
    }

    /**
     * @see PGMemberLink
     */
    public static final String PG_MEMBER_ROLE = "pgMember";

    /**
     * @see PGMemberLink
     */
    public wt.fc.WTObject getPgMember() {
        return (wt.fc.WTObject) getRoleBObject();
    }

    /**
     * @see PGMemberLink
     */
    public void setPgMember(wt.fc.WTObject the_pgMember) throws wt.util.WTPropertyVetoException {
        setRoleBObject((wt.fc.Persistable) the_pgMember);
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

    public static final long EXTERNALIZATION_VERSION_UID = 5645733273262640955L;

    public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
        output.writeLong(EXTERNALIZATION_VERSION_UID);

        super.writeExternal(output);

        output.writeObject(comments);
    }

    protected void super_writeExternal_PGMemberLink(java.io.ObjectOutput output) throws java.io.IOException {
        super.writeExternal(output);
    }

    public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        long readSerialVersionUID = input.readLong();
        readVersion((PGMemberLink) this, input, readSerialVersionUID, false, false);
    }

    protected void super_readExternal_PGMemberLink(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        super.readExternal(input);
    }

    public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.writeExternal(output);

        output.setString("comments", comments);
    }

    public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.readExternal(input);

        comments = input.getString("comments");
    }

    boolean readVersion5645733273262640955L(java.io.ObjectInput input, long readSerialVersionUID, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        if (!superDone)
            super.readExternal(input);

        comments = (String) input.readObject();
        return true;
    }

    protected boolean readVersion(PGMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        boolean success = true;

        if (readSerialVersionUID == EXTERNALIZATION_VERSION_UID)
            return readVersion5645733273262640955L(input, readSerialVersionUID, superDone);
        else
            success = readOldVersion(input, readSerialVersionUID, passThrough, superDone);

        if (input instanceof wt.pds.PDSObjectInput)
            wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

        return success;
    }

    protected boolean super_readVersion_PGMemberLink(_PGMemberLink thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
    }

    boolean readOldVersion(java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID=" + readSerialVersionUID + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID);
    }
}
