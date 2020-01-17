package ext.example.pg.controller;

import ext.example.pg.bean.PGGroupBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ptc1/ext/attrlinkage/findStationBySubway
 *
 * @author 段鑫扬
 * @version 2019/12/30
 */
@RestController
@RequestMapping(value = "/ext/testController")
public class TestController {
    @RequestMapping(value = "/char")
    public PGGroupBean testFilter() {
        PGGroupBean bean = new PGGroupBean();
        bean.setComments("测试");
        bean.setPgGroupName("测试过滤器");
        bean.setRoot(true);
        bean.setEnabled(true);
        return bean;
    }

}
