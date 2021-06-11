package com.janwes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.janwes.pojo.PermissionInfo;

import java.util.List;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.mapper
 * @date 2021/6/8 11:45
 * @description
 */
public interface PermissionInfoMapper extends BaseMapper<PermissionInfo> {

    List<PermissionInfo> selectAll(int roleId);
}
