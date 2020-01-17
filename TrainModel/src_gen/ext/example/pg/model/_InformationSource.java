package ext.example.pg.model;

@SuppressWarnings({"cast", "deprecation", "rawtypes", "unchecked"})
public abstract class _InformationSource extends wt.fc.EnumeratedType {
    static final long serialVersionUID = 1;

    static final String RESOURCE = "ext.example.pg.model.modelResource";
    static final String CLASSNAME = new InformationSource().getClass().getName();

    static final String CLASS_RESOURCE = "ext.example.pg.model.InformationSourceRB";
    static java.util.Hashtable localeSets;

    private static volatile wt.fc.EnumeratedType[] valueSet;

    static wt.fc.EnumeratedType[] _valueSet() {
        if (valueSet == null) synchronized (_InformationSource.class) {
            try {
                if (valueSet == null) valueSet = initializeLocaleSet(null);
            } catch (Throwable t) {
                throw new ExceptionInInitializerError(t);
            }
        }
        return valueSet;
    }

    public static InformationSource newInformationSource(int secretHandshake) throws IllegalAccessException {
        validateFriendship(secretHandshake);
        return new InformationSource();
    }

    public static InformationSource toInformationSource(String internal_value) throws wt.util.WTInvalidParameterException {
        return (InformationSource) toEnumeratedType(internal_value, _valueSet());
    }

    public static InformationSource getInformationSourceDefault() {
        return (InformationSource) defaultEnumeratedType(_valueSet());
    }

    public static InformationSource[] getInformationSourceSet() {
        InformationSource[] set = new InformationSource[_valueSet().length];
        System.arraycopy(valueSet, 0, set, 0, valueSet.length);
        return set;
    }

    public wt.fc.EnumeratedType[] getValueSet() {
        return getInformationSourceSet();
    }

    protected wt.fc.EnumeratedType[] valueSet() {
        return _valueSet();
    }

    protected wt.fc.EnumeratedType[] getLocaleSet(java.util.Locale locale) {
        wt.fc.EnumeratedType[] request = null;

        if (localeSets == null) localeSets = new java.util.Hashtable();
        else request = (wt.fc.EnumeratedType[]) localeSets.get(locale);

        if (request == null) {
            try {
                request = initializeLocaleSet(locale);
            } catch (Throwable t) { /* snuff, since generation of class ensures that exception will not be thrown */ }
            localeSets.put(locale, request);
        }

        return request;
    }

    static wt.fc.EnumeratedType[] initializeLocaleSet(java.util.Locale locale) throws Throwable {
        return instantiateSet(InformationSource.class.getMethod("newInformationSource", new Class<?>[]{Integer.TYPE}), CLASS_RESOURCE, locale);
    }
}
