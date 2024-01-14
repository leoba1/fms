package cn.org.bai.model.entity;

import lombok.Data;

/**
 * @author bzh
 */
@Data
public class User {

    private String uid;
    /**
     * 账户
     */
    private String uname;

    /**
     * 密码
     */
    private String pwd;

}
