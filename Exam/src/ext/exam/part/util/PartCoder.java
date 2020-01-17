package ext.exam.part.util;

import ext.lang.PIStringUtils;
import ext.pi.PIException;
import ext.pi.core.PICoreHelper;
import org.apache.log4j.Logger;
import wt.log4j.LogR;
import wt.part.WTPart;
import wt.util.WTException;

/**
 * 生成编码处理类
 *
 * @author 段鑫扬
 */
public class PartCoder {
    private static final long serialVersionUID = 1L;

    private static final String CLASSNAME = PartCoder.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);
    private static final Integer FLOW_NUM_DIGIT = 6;

    private WTPart part;//部件
    private String unitValue;//单位属性的内部值

    private StringBuilder errMsg;

    private PartCoder() {
        errMsg = new StringBuilder("");
    }

    public PartCoder(WTPart part) {
        this();
        this.part = part;
        if (part != null) {
            String unitValue = part.getDefaultUnit().toString();
            this.unitValue = unitValue;
        }
    }

    /**
     * 单位有值，才能编码
     *
     * @return
     */
    public boolean canGenerateNumber() {
        return PIStringUtils.hasText(unitValue);
    }

    /**
     * 更新部件编码
     */
    public synchronized void updatePartNumber() {
        if (part != null) {
            String flowNum = getFlowNum();//获取流水码
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>>flowNum= " + flowNum);
            }
            String newNum = this.unitValue + flowNum;
            try {
                PICoreHelper.service.changeIdentity(part, newNum, "");
            } catch (PIException e) {
                e.printStackTrace();
                this.errMsg.append("更新编码时发生异常，").append(e).append(";");
            }
        }
    }

    /**
     * 获得流水码
     * 默认按照查询系统中的最大编码+1
     *
     * @return
     */
    private String getFlowNum() {
        //查询同分类的系统最大编码
        String likeCode = this.unitValue;
        for (int i = 0; i < FLOW_NUM_DIGIT; i++) {
            likeCode = likeCode + "_";//位数
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">>>>likeCode =  " + likeCode);
        }
        int nextNum = -1;
        String maxNum = "";
        try {
            maxNum = PartCodeUtil.queryMaxNumber(part, likeCode);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> maxNum=" + maxNum);
            }
            if (PIStringUtils.hasText(maxNum)) {
                maxNum = maxNum.substring(this.unitValue.length());
                //获取流水码部分,加1
                nextNum = Integer.parseInt(maxNum) + 1;
            } else {
                //不存在，则从1开始
                nextNum = 1;
            }
        } catch (WTException e) {
            e.printStackTrace();
            this.errMsg.append("查询最大编码时发生异常：").append(e).append(";");
        }
        if (nextNum > 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(">>>> nextNum=" + nextNum);
            }
            return PartCodeUtil.formatValue(nextNum + "", FLOW_NUM_DIGIT);
        }
        return null;
    }

    public WTPart getPart() {
        return part;
    }

    public void setPart(WTPart part) {
        this.part = part;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public StringBuilder getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(StringBuilder errMsg) {
        this.errMsg = errMsg;
    }
}
