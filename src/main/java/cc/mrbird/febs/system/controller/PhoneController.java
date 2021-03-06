package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.entity.Phone;
import cc.mrbird.febs.system.service.IPhoneService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author Hejingbo
 * @date 2020-08-04 22:24:45
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("phone")
public class PhoneController extends BaseController {

    private final IPhoneService phoneService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "phone")
    public String phoneIndex() {
        return FebsUtil.view("phone/phone");
    }

    @GetMapping
    public FebsResponse getAllPhones(Phone phone) {
        return new FebsResponse().success().data(phoneService.findPhones(phone));
    }

    @GetMapping("list")
    @RequiresPermissions("phone:view")
    public FebsResponse phoneList(QueryRequest request, Phone phone) {
        Map<String, Object> dataTable = getDataTable(this.phoneService.findPhones(request, phone));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Phone", exceptionMessage = "新增Phone失败")
    @PostMapping
    @RequiresPermissions("phone:add")
    public FebsResponse addPhone(@Valid Phone phone) {
        this.phoneService.createPhone(phone);
        return new FebsResponse().success();
    }


    @GetMapping("delete/{phoneIds}")
    @RequiresPermissions("phone:delete")
    @ControllerEndpoint(operation = "删除Phone", exceptionMessage = "删除Phone失败")
    public FebsResponse deletePhones(@NotBlank(message = "{required}") @PathVariable String phoneIds) {
        this.phoneService.deletePhones(phoneIds);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Phone", exceptionMessage = "修改Phone失败")
    @PostMapping("update")
    @RequiresPermissions("phone:update")
    public FebsResponse updatePhone(Phone phone) {
        this.phoneService.updatePhone(phone);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    @GetMapping("excel")
    @RequiresPermissions("phone:export")
    public void export(QueryRequest queryRequest, Phone phone, HttpServletResponse response) {
        List<Phone> phones = this.phoneService.findPhones(queryRequest, phone).getRecords();
        ExcelKit.$Export(Phone.class, response).downXlsx(phones, false);
    }

}
