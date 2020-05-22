package chain.tj.model.pojo.query;

import lombok.Data;

/**
 * @Describe:
 * @Author: zhangyifei
 * @Date: 2020/5/18 17:11
 */
@Data
public class NewTxQueryDto {

    /**
     * ip地址
     */
    private String addr;

    /**
     * memberID
     */
    private String memberId;

    /**
     * 操作类型，0-修改/新增；1-删除
     */
    private Integer opType;

    /**
     * rpc地址
     */
    private String peer;


    /**
     * 链的类型  类型(0-共识;1-非共识)
     */
    private Integer peerType;

    /**
     * 端口
     */
    private Integer rpcPort;

    //========================================
    /**
     * 类型
     */
    private Integer type;

    /**
     * 子类型
     */
    private Integer subType;

    /**
     * 展示的名称
     */
    private String shownName;


    public NewTxQueryDto() {
        // 默认是新增或者修改
        this.opType = 0;
    }

}
