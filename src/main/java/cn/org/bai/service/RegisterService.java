package cn.org.bai.service;

import cn.org.bai.model.dto.RegiserDto;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author bzh
 */

public interface RegisterService extends IService<RegiserDto> {
    RegiserDto getUserByName(String username);
}
