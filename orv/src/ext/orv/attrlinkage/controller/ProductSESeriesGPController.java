package ext.orv.attrlinkage.controller;

import ext.orv.attrlinkage.reader.ProductSESeriesGPBeanHelper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 产品系列和产品型号 控制层
 * @author 段鑫扬
 */
@Controller
@RequestMapping(value = "/ext/attrlinkage")
public class ProductSESeriesGPController {

    private static final String CLASSNAME = ProductSESeriesGPController.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * 返回产品型号的数据
     * @param productSE
     * @param response
     */
    @RequestMapping(value = "/findSeriesGPsByProductSE")
    public void findSeriesGPsByProductSE(String productSE, HttpServletResponse response) {
        boolean flag = false;
        PrintWriter p = null;
        try {
            //权限处理
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            response.setContentType("text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("productSE = " + productSE);
            }
            StringBuffer message = new StringBuffer();
            p = response.getWriter();
            //返回的结果
            JSONObject resObject = new JSONObject();
            ProductSESeriesGPBeanHelper helper = ProductSESeriesGPBeanHelper.getInstance();
            List<String> seriesGPList = helper.getSeriesGPListByproductSE(productSE);

            JSONArray jsonArray = new JSONArray();
            JSONObject blankObj = new JSONObject();
            blankObj.put("name","");
            blankObj.put("value","");
            jsonArray.put(blankObj);
            for (String seriesGP : seriesGPList) {
                JSONObject jsonObject = new JSONObject();
                //内部值
                jsonObject.put("value",seriesGP);
                //显示值
                jsonObject.put("name",helper.getDescByInternal(seriesGP));
                jsonArray.put(jsonObject);
            }
            resObject.put("SERIESGPS", jsonArray);
            resObject.put("FLAG", "true");
            message.append(resObject.toString());
            String res = message.toString();

            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("response = " + res);
            }
            p.print(res);
        } catch (Exception e) {
            e.printStackTrace();
            if (p != null) {
                p.print("findSeriesGPs error  == > " +e.toString());
            }
            LOGGER.error(e.toString());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }
}
