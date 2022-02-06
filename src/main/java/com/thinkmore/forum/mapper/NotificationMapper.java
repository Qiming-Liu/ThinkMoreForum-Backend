package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.dto.notification.NotificationPostDto;
import com.thinkmore.forum.dto.notification.NotificationPutDto;
import com.thinkmore.forum.entity.Notification;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface NotificationMapper {
    Notification toEntity(NotificationPostDto notificationPostDto);

    NotificationGetDto fromEntity(Notification notification);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(NotificationPutDto notificationPutDto, @MappingTarget Notification notification);
}
