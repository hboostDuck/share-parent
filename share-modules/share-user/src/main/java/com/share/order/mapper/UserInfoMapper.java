package com.share.order.mapper;

import java.util.List;

import com.share.order.api.domain.UserCountVo;
import com.share.order.api.domain.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper接口
 *
 * @author atguigu
 * @date 2025-09-07
 */
public interface UserInfoMapper extends BaseMapper<UserInfo>
{

    /**
     * 查询用户列表
     *
     * @param userInfo 用户
     * @return 用户集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    @Select("        SELECT\n" +
            "            DATE_FORMAT(create_time, '%Y-%m') AS registerDate,\n" +
            "            COUNT(id) AS `count`\n" +
            "        FROM\n" +
            "            user_info\n" +
            "        WHERE\n" +
            "            YEAR(create_time) = 2024\n" +
            "        GROUP BY\n" +
            "            DATE_FORMAT(create_time, '%Y-%m')\n" +
            "        ORDER BY\n" +
            "            registerDate")
    List<UserCountVo> selectUserCount();

}
