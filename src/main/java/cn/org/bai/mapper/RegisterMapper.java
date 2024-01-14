package cn.org.bai.mapper;

import cn.org.bai.model.dto.RegiserDto;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author bzh
 */
@Mapper
public interface RegisterMapper extends BaseMapper<RegiserDto> {
}
