package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.HotelName;
import cc.mrbird.febs.system.entity.Meeting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  Mapper
 *
 * @author Hejingbo
 * @date 2020-08-05 23:40:11
 */
public interface MeetingMapper extends BaseMapper<Meeting> {


    Long countMeeting(@Param("meeting") Meeting meeting);

    void auditMeeting(Meeting meeting);

    List<HotelName> weChatHotelsByMeetingId(@Param("meetingId") Long meetingId);

    int deleteMeetingByIds(@Param("ids") String[] ids);
}
