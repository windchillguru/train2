package ext.example.pg.model;

import com.ptc.windchill.annotations.metadata.GenAsEnumeratedType;

/**
 * 信息来源，枚举
 *
 * @author LiShuYan
 * @version 2019年5月20日
 */
@GenAsEnumeratedType
public class InformationSource extends _InformationSource {
    static final long serialVersionUID = 1;

    public static final InformationSource JOBSITE = toInformationSource("JobSite");//招聘网站
    public static final InformationSource INTERNALRECOMMAND = toInformationSource("InternalRecommand");//内部推荐
    public static final InformationSource TRAINING = toInformationSource("Training");//培训
    public static final InformationSource INTERN = toInformationSource("Intern");//实习生

}
