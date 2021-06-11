package com.janwes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.janwes.pojo.RoleInfo;

import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mapper
 * @date 2021/6/8 11:12
 * @description
 */
public interface RoleInfoMapper extends BaseMapper<RoleInfo> {

    List<RoleInfo> selectRoles(int userId);
}
