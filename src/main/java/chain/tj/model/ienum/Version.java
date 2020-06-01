package chain.tj.model.ienum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Describe:版本信息
 * @Author: zhangyifei
 * @Date: 2020/6/1 11:24
 */
@Getter
@AllArgsConstructor
public enum Version {

    VersionZero(0, "版本0"),
    VersionOne(1, "版本1"),
    VersionTwo(2, "版本w");

    /**
     * 值
     */
    private Integer value;

    /**
     * 描述
     */
    private String desc;
}
