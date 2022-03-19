package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.entity.Notification;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NotificationMapper {

    NotificationGetDto fromEntity(Notification notification);
}
