package ext.task.attrlinkage.controller;

import ext.task.attrlinkage.bean.SubwayStationBean;
import ext.task.attrlinkage.reader.SubwayStationBeanHelper;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wt.log4j.LogR;
import wt.session.SessionServerHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * 地铁站点 控制层
 *
 * @author 段鑫扬
 */
@Controller
@RequestMapping(value = "/ext/attrlinkage")
public class SubwayStationController {

    private static final String CLASSNAME = SubwayStationController.class.getName();
    private static final Logger LOGGER = LogR.getLogger(CLASSNAME);

    /**
     * spring处理会有编码问题，待解决
     *
     * @param subway
     * @return
     */
    @RequestMapping(value = "/findStationBySubway2", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String findStationBySubway2(String subway) {
        boolean flag = false;
        try {
            //权限处理
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subway = " + subway);
            }
            StringBuffer message = new StringBuffer();
            //返回的结果
            JSONObject resObject = new JSONObject();
            List<String> stationList = SubwayStationBeanHelper.getInstance().getStationListBySubway(subway);
            JSONArray jsonArray = new JSONArray();
            JSONObject blankObj = new JSONObject();
            blankObj.put("name", "");
            blankObj.put("value", "");
            jsonArray.put(blankObj);
            for (String station : stationList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", station);
                jsonObject.put("value", station);
                jsonArray.put(jsonObject);
            }
            resObject.put("STATIONS", jsonArray);
            resObject.put("FLAG", "true");
            message.append(resObject.toString());
            String res = message.toString();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("response = " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.toString());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
        return "";
    }

    /**
     * 返回站点的数据
     *
     * @param subway
     * @param response
     */
    @RequestMapping(value = "/findStationBySubway")
    public void findStationBySubway(String subway, HttpServletResponse response) {
        boolean flag = false;
        PrintWriter p = null;
        try {

            //权限处理
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            response.setCharacterEncoding("UTF-8");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subway = " + subway);
            }
            StringBuffer message = new StringBuffer();
            p = response.getWriter();
            //返回的结果
            JSONObject resObject = new JSONObject();
            List<String> stationList = SubwayStationBeanHelper.getInstance().getStationListBySubway(subway);
            JSONArray jsonArray = new JSONArray();
            JSONObject blankObj = new JSONObject();
            blankObj.put("name", "");
            blankObj.put("value", "");
            jsonArray.put(blankObj);
            for (String station : stationList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", station);
                jsonObject.put("value", station);
                jsonArray.put(jsonObject);
            }
            resObject.put("STATIONS", jsonArray);
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

    /**
     * 返回出口的数据
     *
     * @param station
     * @param response
     */
    @RequestMapping(value = "/findExitByStation")
    public void findExitByStation(String station, HttpServletResponse response) {
        boolean flag = false;
        PrintWriter p = null;
        try {

            //权限处理
            flag = SessionServerHelper.manager.setAccessEnforced(flag);
            response.setCharacterEncoding("UTF-8");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("subway = " + station);
            }
            StringBuffer message = new StringBuffer();
            p = response.getWriter();
            //返回的结果
            JSONObject resObject = new JSONObject();
            List<String> exitList = SubwayStationBeanHelper.getInstance().getExitListByStation(station);
            JSONArray jsonArray = new JSONArray();
            JSONObject blankObj = new JSONObject();
            blankObj.put("name", "");
            blankObj.put("value", "");
            jsonArray.put(blankObj);
            for (String exit : exitList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", exit);
                jsonObject.put("value", exit);
                jsonArray.put(jsonObject);
            }
            resObject.put("EXITS", jsonArray);
            resObject.put("FLAG", "true");
            message.append(resObject.toString());
            String res = message.toString();

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("response = " + res);
            }
            p.print(res);
        } catch (Exception e) {
            e.printStackTrace();
            p.print("findExits error  == > " + e.toString());
            LOGGER.error(e.toString());
        } finally {
            SessionServerHelper.manager.setAccessEnforced(flag);
        }
    }

    @RequestMapping(value = "/char")
    @ResponseBody
    public SubwayStationBean testFilter() {
        SubwayStationBean bean = new SubwayStationBean();
        bean.setExit("测试");
        bean.setStation("测试过滤器");
        bean.setSubway("测试过滤器");
        return bean;
    }
}
