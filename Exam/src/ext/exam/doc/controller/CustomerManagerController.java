package ext.exam.doc.controller;

import ext.exam.doc.reader.CustomerManagerBeanHelper;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 客户经理 控制层
 *
 * @author 段鑫扬
 */
@Controller
@RequestMapping(value = "/ext/attrlinkage")
public class CustomerManagerController {

    private static final String CLASSNAME = CustomerManagerController.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);


    /**
     * 返回经理的数据
     *
     * @param customerName
     * @param response
     */
    @RequestMapping(value = "/findManagerByCustomer")
    public void findManagerByCustomer(String customerName, HttpServletResponse response) {
        boolean flag = false;
        PrintWriter p = null;
        try {
            //权限处理
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            response.setCharacterEncoding("UTF-8");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("customerName = " + customerName);
            }
            StringBuffer message = new StringBuffer();
            p = response.getWriter();
            //返回的结果
            JSONObject resObject = new JSONObject();
            String belongManager = CustomerManagerBeanHelper.getInstance().getBelongManager(customerName);

            resObject.put("manager", belongManager);
            resObject.put("FLAG", "true");
            message.append(resObject.toString());
            String res = message.toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("response = " + res);
            }
            p.print(res);
        } catch (Exception e) {
            e.printStackTrace();
            p.print("findStations error  == > " + e.toString());
            LOGGER.error(e.toString());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }

}
