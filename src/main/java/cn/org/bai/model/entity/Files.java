package cn.org.bai.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件对象 实体类
 *
 * @author LEON
 * @since 2020-06-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("tb_files")
@ApiModel(value = "Files对象", description = "")
public class Files extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 预留字段，如果想把文件存到别的存储服务如MinIO、Mongo时
     * ，该字段就可以存Mongo的ID或其他文件标识
     */
    @ApiModelProperty(value = "目标对象ID")
    private String targetId;

    /**
     * 文件相对路径，相对于系统配置文件中配置的路径
     */
    @ApiModelProperty(value = "文件位置")
    private String filePath;

    @ApiModelProperty(value = "原始文件名")
    private String fileName;

    @ApiModelProperty(value = "文件后缀")
    private String suffix;


}
