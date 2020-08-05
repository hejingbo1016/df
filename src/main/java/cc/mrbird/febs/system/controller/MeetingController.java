package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Meeting;
import cc.mrbird.febs.system.service.IMeetingService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author Hejingbo
 * @date 2020-08-05 23:40:11
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class MeetingController extends BaseController {

    private final IMeetingService meetingService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "meeting")
    public String meetingIndex(){
        return FebsUtil.view("meeting/meeting");
    }

    @GetMapping("meeting")
    @ResponseBody
    @RequiresPermissions("meeting:list")
    public FebsResponse getAllMeetings(Meeting meeting) {
        return new FebsResponse().success().data(meetingService.findMeetings(meeting));
    }

    @GetMapping("meeting/list")
    @ResponseBody
    @RequiresPermissions("meeting:list")
    public FebsResponse meetingList(QueryRequest request, Meeting meeting) {
        Map<String, Object> dataTable = getDataTable(this.meetingService.findMeetings(request, meeting));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Meeting", exceptionMessage = "新增Meeting失败")
    @PostMapping("meeting")
    @ResponseBody
    @RequiresPermissions("meeting:add")
    public FebsResponse addMeeting(@Valid Meeting meeting) {
        this.meetingService.createMeeting(meeting);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Meeting", exceptionMessage = "删除Meeting失败")
    @GetMapping("meeting/delete")
    @ResponseBody
    @RequiresPermissions("meeting:delete")
    public FebsResponse deleteMeeting(Meeting meeting) {
        this.meetingService.deleteMeeting(meeting);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Meeting", exceptionMessage = "修改Meeting失败")
    @PostMapping("meeting/update")
    @ResponseBody
    @RequiresPermissions("meeting:update")
    public FebsResponse updateMeeting(Meeting meeting) {
        this.meetingService.updateMeeting(meeting);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Meeting", exceptionMessage = "导出Excel失败")
    @PostMapping("meeting/excel")
    @ResponseBody
    @RequiresPermissions("meeting:export")
    public void export(QueryRequest queryRequest, Meeting meeting, HttpServletResponse response) {
        List<Meeting> meetings = this.meetingService.findMeetings(queryRequest, meeting).getRecords();
        ExcelKit.$Export(Meeting.class, response).downXlsx(meetings, false);
    }
}
