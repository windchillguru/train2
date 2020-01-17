package ext.orv.doc.util;

import ext.pi.PIException;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.doc.WTDocument;
import wt.log4j.LogR;
import wt.util.WTException;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生成编码处理类
 *
 * @author 段鑫扬
 */
public class DocCoder implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = DocCoder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final String SEQ_POSTFIX = "_SEQ";
    //文档
    private WTDocument doc;
    //前缀
    private String prefix;
    //流水码长度
    private String seqLength;
    //当前年份
    private String year;


    private StringBuilder errMsg;

    private DocCoder() {
        errMsg = new StringBuilder("");
    }

    public DocCoder(WTDocument doc, String prefix, String seqLength) {
        this();
        this.doc = doc;
        this.prefix = prefix;
        this.seqLength = seqLength;
        SimpleDateFormat df = new SimpleDateFormat("yyyy");//设置日期格式
        this.year = df.format(new Date());
    }

    /**
     * 更新文档编码
     */
    public synchronized void updateDocNumber() {
        if (doc != null) {
            String flowNum = getFlowNum();//获取流水码
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>flowNum= " + flowNum);
            }
            //新编号
            String newNum = this.prefix + this.year + flowNum;
            try {
                PICoreHelper.service.changeIdentity(doc, newNum, "");
            } catch (PIException e) {
                e.printStackTrace();
                this.errMsg.append("更新编码时发生异常，").append(e).append(";");
            }
        }
    }

    /**
     * 获得流水码
     * 查询序列的下一个值
     *
     * @return
     */
    private String getFlowNum() {

        //序列名：配置前缀+年份+后缀
        String seqName = prefix + year + SEQ_POSTFIX;
        //判断序列是否存在
        boolean seqExist = DocCodeUtil.isSeqExist(seqName);

        if (!seqExist) {
            //如果不存在，则重新创建
            DocCodeUtil.createSequence(seqName);
        }
        //查询当前序列的下一个值
        String nextNumber = "";
        int length = Integer.parseInt(seqLength);
        try {
            //已经按配置添加0了
            nextNumber = DocCodeUtil.getSeqNextValue(seqName, length);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> nextNumber=" + nextNumber);
            }
        } catch (WTException e) {
            e.printStackTrace();
            this.errMsg.append("获取序列下一个值出现异常：").append(e).append(";");
        }
        return nextNumber;
    }

    public WTDocument getDoc() {
        return doc;
    }

    public void setDoc(WTDocument doc) {
        this.doc = doc;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSeqLength() {
        return seqLength;
    }

    public void setSeqLength(String seqLength) {
        this.seqLength = seqLength;
    }

    public StringBuilder getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(StringBuilder errMsg) {
        this.errMsg = errMsg;
    }
}
