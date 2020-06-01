package chain.tj.common;

/**
 * @author zhangyifei
 * @date 2020/5/915:23
 **/
public enum StatusCode {
    /**
     * 列表
     */
    RESULT_OK(0, "请求成功"),

    SEND_MESSAGE_OK(100000, "消息发送成功"),

    SERVER_200001(200001, "部分未成功"),

    CLIENT_400001(400001, "您尚未授权此地址权限"),
    CLIENT_400002(400002, "TOKEN不存在"),
    CLIENT_400003(400003, "被禁止访问"),
    CLIENT_400004(400004, "请求地址不存在"),
    CLIENT_400005(400005, "请求方法不允许"),
    CLIENT_400006(400006, "无法处理请求附带的媒体格式"),
    CLIENT_400007(400007, "TOKEN在黑名单之中，拒绝访问"),
    CLIENT_400008(400008, "TOKEN过期"),
    CLIENT_400009(400009, "参数类型错误"),
    CLIENT_400010(400010, "TOKEN已失效,但又被刷新"),

    CLIENT_410001(410001, "字段不能为空"),
    CLIENT_410002(410002, "参数格式错误"),
    CLIENT_410003(410003, "超过最大长度"),
    CLIENT_4100301(41000301, "数据应在正确范围"),
    CLIENT_410004(410004, "非法字符"),
    CLIENT_410005(410005, "验证错误"),
    CLIENT_410006(410006, "根记录不可操作"),
    CLIENT_410007(410007, "字段在数据库中已经存在"),
    CLIENT_410008(410008, "字段组合在数据库中已经存在"),
    CLIENT_410009(410009, "JSON转换异常"),
    CLIENT_410010(410010, "上传文件过大"),
    CLIENT_410011(410011, "id非法"),
    CLIENT_410012(410012, "字典编码code不可以修改"),
    CLIENT_410013(410013, "数字转换异常"),
    CLIENT_410014(410014, "数据不存在"),
    CLIENT_410015(410015, "数据严重偏离实际"),
    CLIENT_410016(410016, "字段应该为空"),
    CLIENT_410017(410017, "自定义数据校验异常"),
    CLIENT_410018(410018, "等级超过规定层级"),
    CLIENT_410019(410019, "权限不足"),
    CLIENT_410020(410020, "发送指令异常"),
    CLIENT_410021(410021, "获取秘钥异常"),

    SERVER_500000(500000, "未知异常"),
    // 暂留
    SERVER_510000(510000, "展现层异常"),

    SERVER_520000(520000, "业务逻辑异常"),
    SERVER_520001(520001, "验证码超时"),
    SERVER_520002(520002, "验证码错误"),
    SERVER_520004(520004, "二维码生成异常"),
    SERVER_520005(520005, "新增交易失败！"),

    SERVER_530000(530000, "数据处理异常"),

    SERVER_540000(540000, "数据库异常"),

    SERVER_900001(900001, "记录不存在"),
    SERVER_900002(900002, "记录重复"),
    SERVER_90000201(90000201, "记录重复但强制覆盖"),
    SERVER_900003(900003, "定时任务异常"),
    SERVER_900004(900004, "获取用户错误");


    private final Integer code;

    private final String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public int value() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
