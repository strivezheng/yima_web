package io.renren.modules.app.controller;



import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.generator.service.ShowResService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"app/res"})
@Api("资源接口")
public class ResController {
    @Autowired
    private ShowResService showResService;



    @RequestMapping(
            value = {"/randomPage"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("随机获取资源")
    public R randomPage(@RequestParam(required = false) Map<String, Object> params) {
        PageUtils page = this.showResService.randomPage(params);
        return R.ok().put("page", page);
    }
    @RequestMapping(
            value = {"/updateWeight"},
            method = {RequestMethod.GET}
    )
    @ApiOperation("修改权重")
    public R updateWeight(Integer id,Integer weight) {
        Boolean result = this.showResService.updateWeight(id, weight);
        return R.ok().put("data", result);
    }


}