package cn.org.bai.service.impl;

import cn.org.bai.entity.dto.RegiserDto;
import cn.org.bai.mapper.RegisterMapper;
import cn.org.bai.service.RegisterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author bzh
 */
@Service
public class RegisterServiceImpl extends ServiceImpl<RegisterMapper, RegiserDto> implements RegisterService {

    @Override
    public RegiserDto getUserByName(String username) {
        QueryWrapper<RegiserDto> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return baseMapper.selectOne(queryWrapper);
    }
}
