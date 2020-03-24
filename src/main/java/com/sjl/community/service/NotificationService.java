package com.sjl.community.service;

import com.sjl.community.dto.NotificationDto;
import com.sjl.community.dto.PaginationDto;
import com.sjl.community.enums.NotificationStatusEnum;
import com.sjl.community.enums.NotificationTypeEnum;
import com.sjl.community.exception.CustomizeErrorCode;
import com.sjl.community.exception.CustomizeException;
import com.sjl.community.mapper.NotificationMapper;
import com.sjl.community.model.Notification;
import com.sjl.community.model.NotificationExample;
import com.sjl.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author song
 * @create 2020/2/23 16:42
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 根绝接收者id查出所有通知
     *
     * @param pageNum
     * @param pageSize
     * @param id
     * @return
     */
    public PaginationDto<NotificationDto> findByReceiverId(Integer pageNum, Integer pageSize, Long id) {
        //计算通知数
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverIdEqualTo(id);
        int totalCount = (int) this.notificationMapper.countByExample(example);
        return getPageInfo(pageNum, pageSize, totalCount, id);
    }

    /**
     * 查询分页信息
     *
     * @param pageNum
     * @param pageSize
     * @param totalCount
     * @param id
     * @return
     */
    public PaginationDto<NotificationDto> getPageInfo(Integer pageNum, Integer pageSize, int totalCount, Long id) {
        //返回的页面信息+分页信息
        PaginationDto<NotificationDto> pageInfo = new PaginationDto<>();
        //如果总记录数为0，直接返回一个空数据
        if (totalCount == 0) {
            return pageInfo;
        }
        //设置分页信息
        pageInfo.setInfo(totalCount, pageNum, pageSize);
        //限定当前页范围
        if (pageNum < 1) {
            pageNum = 1;
        } else if (pageNum > pageInfo.getTotalPage()) {
            pageNum = pageInfo.getTotalPage();
        }
        //获取截取的位置
        int offerIndex = (pageNum - 1) * pageSize;
        //判断id存不存在，来决定分页的方式
        NotificationExample notificationExample = new NotificationExample();
        if (id != null) {
            notificationExample.createCriteria().andReceiverIdEqualTo(id);
        }
        //问题按时间倒序
        notificationExample.setOrderByClause("gmt_create desc");
        //分页查询
        List<Notification> notifications = this.notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offerIndex, pageSize));
        //问题列表信息
        List<NotificationDto> notificationDtos = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setTypeDescription(NotificationTypeEnum.descOf(notification.getType()));
            notificationDtos.add(notificationDto);
        }
        pageInfo.setList(notificationDtos);
        return pageInfo;
    }

    /**
     * 根据接收者id查询未读数
     * @param receiverId
     * @return
     */
    public Long getUnreadCountByReceiverId(Long receiverId){
        NotificationExample example = new NotificationExample();
        example.createCriteria()
                .andReceiverIdEqualTo(receiverId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return this.notificationMapper.countByExample(example);
    }

    /**
     * 读取通知，标记为已读
     * @param id
     * @param user
     */
    public Notification read(Long id, User user) {
        Notification dbNotification = this.notificationMapper.selectByPrimaryKey(id);
        if(dbNotification == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if(!dbNotification.getReceiverId().equals(user.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        //标记已读
        dbNotification.setStatus(NotificationStatusEnum.READ.getStatus());
        this.notificationMapper.updateByPrimaryKeySelective(dbNotification);

        return dbNotification;
    }

    /**
     * 全部已读
     * @param userId
     * @return
     */
    public void readAll(Long userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverIdEqualTo(userId);
        List<Notification> notifications = this.notificationMapper.selectByExample(example);
        for (Notification notification : notifications) {
            //设置为已读
            notification.setStatus(NotificationStatusEnum.READ.getStatus());
            NotificationExample example1 = new NotificationExample();
            example1.createCriteria()
                    .andIdEqualTo(notification.getId())
                    .andReceiverIdEqualTo(notification.getReceiverId());
            this.notificationMapper.updateByExampleSelective(notification, example1);
        }
    }
}
