package ext.example.pg.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _PGInformation extends wt.fc.WTObject implements java.io.Externalizable {
    static final long serialVersionUID = 1;

    static final String RESOURCE = "ext.example.pg.model.modelResource";
    static final String CLASSNAME = PGInformation.class.getName();

    /**
     * 员工工号
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public static final String EMPLOYEE_NO = "employeeNo";
    static int EMPLOYEE_NO_UPPER_LIMIT = -1;
    String employeeNo;

    /**
     * 员工工号
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public String getEmployeeNo() {
        return employeeNo;
    }

    /**
     * 员工工号
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public void setEmployeeNo(String employeeNo) throws wt.util.WTPropertyVetoException {
        employeeNoValidate(employeeNo);
        this.employeeNo = employeeNo;
    }

    void employeeNoValidate(String employeeNo) throws wt.util.WTPropertyVetoException {
        if (employeeNo == null || employeeNo.trim().length() == 0)
            throw new wt.util.WTPropertyVetoException("wt.fc.fcResource", wt.fc.fcResource.REQUIRED_ATTRIBUTE,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeeNo")},
                    new java.beans.PropertyChangeEvent(this, "employeeNo", this.employeeNo, employeeNo));
        if (EMPLOYEE_NO_UPPER_LIMIT < 1) {
            try {
                EMPLOYEE_NO_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("employeeNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                EMPLOYEE_NO_UPPER_LIMIT = 200;
            }
        }
        if (employeeNo != null && !wt.fc.PersistenceHelper.checkStoredLength(employeeNo.toString(), EMPLOYEE_NO_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeeNo"), String.valueOf(Math.min(EMPLOYEE_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "employeeNo", this.employeeNo, employeeNo));
    }

    /**
     * 姓名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public static final String EMPLOYEE_NAME = "employeeName";
    static int EMPLOYEE_NAME_UPPER_LIMIT = -1;
    String employeeName;

    /**
     * 姓名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * 姓名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public void setEmployeeName(String employeeName) throws wt.util.WTPropertyVetoException {
        employeeNameValidate(employeeName);
        this.employeeName = employeeName;
    }

    void employeeNameValidate(String employeeName) throws wt.util.WTPropertyVetoException {
        if (EMPLOYEE_NAME_UPPER_LIMIT < 1) {
            try {
                EMPLOYEE_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("employeeName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                EMPLOYEE_NAME_UPPER_LIMIT = 200;
            }
        }
        if (employeeName != null && !wt.fc.PersistenceHelper.checkStoredLength(employeeName.toString(), EMPLOYEE_NAME_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeeName"), String.valueOf(Math.min(EMPLOYEE_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "employeeName", this.employeeName, employeeName));
    }

    /**
     * 用户名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public static final String EMPLOYEE_USER_NAME = "employeeUserName";
    static int EMPLOYEE_USER_NAME_UPPER_LIMIT = -1;
    String employeeUserName;

    /**
     * 用户名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public String getEmployeeUserName() {
        return employeeUserName;
    }

    /**
     * 用户名
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public void setEmployeeUserName(String employeeUserName) throws wt.util.WTPropertyVetoException {
        employeeUserNameValidate(employeeUserName);
        this.employeeUserName = employeeUserName;
    }

    void employeeUserNameValidate(String employeeUserName) throws wt.util.WTPropertyVetoException {
        if (EMPLOYEE_USER_NAME_UPPER_LIMIT < 1) {
            try {
                EMPLOYEE_USER_NAME_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("employeeUserName").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                EMPLOYEE_USER_NAME_UPPER_LIMIT = 200;
            }
        }
        if (employeeUserName != null && !wt.fc.PersistenceHelper.checkStoredLength(employeeUserName.toString(), EMPLOYEE_USER_NAME_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeeUserName"), String.valueOf(Math.min(EMPLOYEE_USER_NAME_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "employeeUserName", this.employeeUserName, employeeUserName));
    }

    /**
     * 邮箱
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public static final String EMPLOYEE_EMAIL = "employeeEmail";
    static int EMPLOYEE_EMAIL_UPPER_LIMIT = -1;
    String employeeEmail;

    /**
     * 邮箱
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * 邮箱
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public void setEmployeeEmail(String employeeEmail) throws wt.util.WTPropertyVetoException {
        employeeEmailValidate(employeeEmail);
        this.employeeEmail = employeeEmail;
    }

    void employeeEmailValidate(String employeeEmail) throws wt.util.WTPropertyVetoException {
        if (EMPLOYEE_EMAIL_UPPER_LIMIT < 1) {
            try {
                EMPLOYEE_EMAIL_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("employeeEmail").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                EMPLOYEE_EMAIL_UPPER_LIMIT = 200;
            }
        }
        if (employeeEmail != null && !wt.fc.PersistenceHelper.checkStoredLength(employeeEmail.toString(), EMPLOYEE_EMAIL_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeeEmail"), String.valueOf(Math.min(EMPLOYEE_EMAIL_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "employeeEmail", this.employeeEmail, employeeEmail));
    }

    /**
     * 电话
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public static final String EMPLOYEE_PHONE = "employeePhone";
    static int EMPLOYEE_PHONE_UPPER_LIMIT = -1;
    String employeePhone;

    /**
     * 电话
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public String getEmployeePhone() {
        return employeePhone;
    }

    /**
     * 电话
     * <p>
     * <b>Supported API: </b>true
     *
     * @see PGInformation
     */
    public void setEmployeePhone(String employeePhone) throws wt.util.WTPropertyVetoException {
        employeePhoneValidate(employeePhone);
        this.employeePhone = employeePhone;
    }

    void employeePhoneValidate(String employeePhone) throws wt.util.WTPropertyVetoException {
        if (EMPLOYEE_PHONE_UPPER_LIMIT < 1) {
            try {
                EMPLOYEE_PHONE_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("employeePhone").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                EMPLOYEE_PHONE_UPPER_LIMIT = 200;
            }
        }
        if (employeePhone != null && !wt.fc.PersistenceHelper.checkStoredLength(employeePhone.toString(), EMPLOYEE_PHONE_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "employeePhone"), String.valueOf(Math.min(EMPLOYEE_PHONE_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "employeePhone", this.employeePhone, employeePhone));
    }

    /**
     * @see PGInformation
     */
    public static final String COMMENTS = "comments";
    static int COMMENTS_UPPER_LIMIT = -1;
    String comments;

    /**
     * @see PGInformation
     */
    public String getComments() {
        return comments;
    }

    /**
     * @see PGInformation
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
     * @see PGInformation
     */
    public static final String EXPERIENCED = "experienced";
    Boolean experienced = false;

    /**
     * @see PGInformation
     */
    public Boolean getExperienced() {
        return experienced;
    }

    /**
     * @see PGInformation
     */
    public void setExperienced(Boolean experienced) throws wt.util.WTPropertyVetoException {
        experiencedValidate(experienced);
        this.experienced = experienced;
    }

    void experiencedValidate(Boolean experienced) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGInformation
     */
    public static final String RESUME_INFO = "resumeInfo";
    wt.fc.ObjectReference resumeInfo;

    /**
     * @see PGInformation
     */
    public wt.fc.ObjectReference getResumeInfo() {
        return resumeInfo;
    }

    /**
     * @see PGInformation
     */
    public void setResumeInfo(wt.fc.ObjectReference resumeInfo) throws wt.util.WTPropertyVetoException {
        resumeInfoValidate(resumeInfo);
        this.resumeInfo = resumeInfo;
    }

    void resumeInfoValidate(wt.fc.ObjectReference resumeInfo) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGInformation
     */
    public static final String INFORMATION_SOURCE = "informationSource";
    InformationSource informationSource = InformationSource.getInformationSourceDefault();

    /**
     * @see PGInformation
     */
    public InformationSource getInformationSource() {
        return informationSource;
    }

    /**
     * @see PGInformation
     */
    public void setInformationSource(InformationSource informationSource) throws wt.util.WTPropertyVetoException {
        informationSourceValidate(informationSource);
        this.informationSource = informationSource;
    }

    void informationSourceValidate(InformationSource informationSource) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGInformation
     */
    public static final String INFORMATION_NO = "informationNo";
    static int INFORMATION_NO_UPPER_LIMIT = -1;
    String informationNo;

    /**
     * @see PGInformation
     */
    public String getInformationNo() {
        return informationNo;
    }

    /**
     * @see PGInformation
     */
    public void setInformationNo(String informationNo) throws wt.util.WTPropertyVetoException {
        informationNoValidate(informationNo);
        this.informationNo = informationNo;
    }

    void informationNoValidate(String informationNo) throws wt.util.WTPropertyVetoException {
        if (INFORMATION_NO_UPPER_LIMIT < 1) {
            try {
                INFORMATION_NO_UPPER_LIMIT = (Integer) wt.introspection.WTIntrospector.getClassInfo(CLASSNAME).getPropertyDescriptor("informationNo").getValue(wt.introspection.WTIntrospector.UPPER_LIMIT);
            } catch (wt.introspection.WTIntrospectionException e) {
                INFORMATION_NO_UPPER_LIMIT = 200;
            }
        }
        if (informationNo != null && !wt.fc.PersistenceHelper.checkStoredLength(informationNo.toString(), INFORMATION_NO_UPPER_LIMIT, true))
            throw new wt.util.WTPropertyVetoException("wt.introspection.introspectionResource", wt.introspection.introspectionResource.UPPER_LIMIT,
                    new Object[]{new wt.introspection.PropertyDisplayName(CLASSNAME, "informationNo"), String.valueOf(Math.min(INFORMATION_NO_UPPER_LIMIT, wt.fc.PersistenceHelper.DB_MAX_SQL_STRING_SIZE / wt.fc.PersistenceHelper.DB_MAX_BYTES_PER_CHAR))},
                    new java.beans.PropertyChangeEvent(this, "informationNo", this.informationNo, informationNo));
    }

    /**
     * @see PGInformation
     */
    public static final String LEADER = "leader";
    Boolean leader = false;

    /**
     * @see PGInformation
     */
    public Boolean getLeader() {
        return leader;
    }

    /**
     * @see PGInformation
     */
    public void setLeader(Boolean leader) throws wt.util.WTPropertyVetoException {
        leaderValidate(leader);
        this.leader = leader;
    }

    void leaderValidate(Boolean leader) throws wt.util.WTPropertyVetoException {
    }

    /**
     * @see PGInformation
     */
    public static final String PG_NAME = "pgName";
    static int PG_NAME_UPPER_LIMIT = -1;
    String pgName;

    /**
     * @see PGInformation
     */
    public String getPgName() {
        return pgName;
    }

    /**
     * @see PGInformation
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

    public static final long EXTERNALIZATION_VERSION_UID = 4683106991836117141L;

    public void writeExternal(java.io.ObjectOutput output) throws java.io.IOException {
        output.writeLong(EXTERNALIZATION_VERSION_UID);

        super.writeExternal(output);

        output.writeObject(comments);
        output.writeObject(employeeEmail);
        output.writeObject(employeeName);
        output.writeObject(employeeNo);
        output.writeObject(employeePhone);
        output.writeObject(employeeUserName);
        output.writeObject(experienced);
        output.writeObject(informationNo);
        output.writeObject((informationSource == null ? null : informationSource.getStringValue()));
        output.writeObject(leader);
        output.writeObject(pgName);
        output.writeObject(resumeInfo);
    }

    protected void super_writeExternal_PGInformation(java.io.ObjectOutput output) throws java.io.IOException {
        super.writeExternal(output);
    }

    public void readExternal(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        long readSerialVersionUID = input.readLong();
        readVersion((PGInformation) this, input, readSerialVersionUID, false, false);
    }

    protected void super_readExternal_PGInformation(java.io.ObjectInput input) throws java.io.IOException, ClassNotFoundException {
        super.readExternal(input);
    }

    public void writeExternal(wt.pds.PersistentStoreIfc output) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.writeExternal(output);

        output.setString("comments", comments);
        output.setString("employeeEmail", employeeEmail);
        output.setString("employeeName", employeeName);
        output.setString("employeeNo", employeeNo);
        output.setString("employeePhone", employeePhone);
        output.setString("employeeUserName", employeeUserName);
        output.setBooleanObject("experienced", experienced);
        output.setString("informationNo", informationNo);
        output.setString("informationSource", (informationSource == null ? null : informationSource.toString()));
        output.setBooleanObject("leader", leader);
        output.setString("pgName", pgName);
        output.writeObject("resumeInfo", resumeInfo, wt.fc.ObjectReference.class, true);
    }

    public void readExternal(wt.pds.PersistentRetrieveIfc input) throws java.sql.SQLException, wt.pom.DatastoreException {
        super.readExternal(input);

        comments = input.getString("comments");
        employeeEmail = input.getString("employeeEmail");
        employeeName = input.getString("employeeName");
        employeeNo = input.getString("employeeNo");
        employeePhone = input.getString("employeePhone");
        employeeUserName = input.getString("employeeUserName");
        experienced = input.getBooleanObject("experienced");
        informationNo = input.getString("informationNo");
        String informationSource_string_value = (String) input.getString("informationSource");
        if (informationSource_string_value != null) {
            informationSource = (InformationSource) wt.introspection.ClassInfo.getConstrainedEnum(getClass(), "informationSource", informationSource_string_value);
            if (informationSource == null)  // hard-coded type
                informationSource = InformationSource.toInformationSource(informationSource_string_value);
        }
        leader = input.getBooleanObject("leader");
        pgName = input.getString("pgName");
        resumeInfo = (wt.fc.ObjectReference) input.readObject("resumeInfo", resumeInfo, wt.fc.ObjectReference.class, true);
    }

    boolean readVersion4683106991836117141L(java.io.ObjectInput input, long readSerialVersionUID, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        if (!superDone)
            super.readExternal(input);

        comments = (String) input.readObject();
        employeeEmail = (String) input.readObject();
        employeeName = (String) input.readObject();
        employeeNo = (String) input.readObject();
        employeePhone = (String) input.readObject();
        employeeUserName = (String) input.readObject();
        experienced = (Boolean) input.readObject();
        informationNo = (String) input.readObject();
        String informationSource_string_value = (String) input.readObject();
        try {
            informationSource = (InformationSource) wt.fc.EnumeratedTypeUtil.toEnumeratedType(informationSource_string_value);
        } catch (wt.util.WTInvalidParameterException e) {
            // Old Format
            informationSource = InformationSource.toInformationSource(informationSource_string_value);
        }
        leader = (Boolean) input.readObject();
        pgName = (String) input.readObject();
        resumeInfo = (wt.fc.ObjectReference) input.readObject();
        return true;
    }

    protected boolean readVersion(PGInformation thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        boolean success = true;

        if (readSerialVersionUID == EXTERNALIZATION_VERSION_UID)
            return readVersion4683106991836117141L(input, readSerialVersionUID, superDone);
        else
            success = readOldVersion(input, readSerialVersionUID, passThrough, superDone);

        if (input instanceof wt.pds.PDSObjectInput)
            wt.fc.EvolvableHelper.requestRewriteOfEvolvedBlobbedObject();

        return success;
    }

    protected boolean super_readVersion_PGInformation(_PGInformation thisObject, java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        return super.readVersion(thisObject, input, readSerialVersionUID, passThrough, superDone);
    }

    boolean readOldVersion(java.io.ObjectInput input, long readSerialVersionUID, boolean passThrough, boolean superDone) throws java.io.IOException, ClassNotFoundException {
        throw new java.io.InvalidClassException(CLASSNAME, "Local class not compatible: stream classdesc externalizationVersionUID=" + readSerialVersionUID + " local class externalizationVersionUID=" + EXTERNALIZATION_VERSION_UID);
    }
}
