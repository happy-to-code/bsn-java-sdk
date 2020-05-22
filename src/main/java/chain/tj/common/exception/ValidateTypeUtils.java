package chain.tj.common.exception;


import chain.tj.common.StatusCode;

/**
 * 校验工具状态码
 */
public class ValidateTypeUtils {

    /**
     * 将校验字段的注解类型对应上状态码
     *
     * @param validateType
     * @return
     */
    public static Integer validateType(String validateType) {
        Integer code;
        switch (validateType) {
            case "Null":
                code = StatusCode.CLIENT_410016.value();
                break;
            case "NotNull":
                code = StatusCode.CLIENT_410001.value();
                break;
            case "AssertTrue":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "AssertFalse":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "Min":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "Max":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "DecimalMin":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "DecimalMax":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "Size":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "Digits":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "Past":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "Future":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "Pattern":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "NotBlank":
                code = StatusCode.CLIENT_410001.value();
                break;
            case "Email":
                code = StatusCode.CLIENT_410002.value();
                break;
            case "Length":
                code = StatusCode.CLIENT_4100301.value();
                break;
            case "NotEmpty":
                code = StatusCode.CLIENT_410001.value();
                break;
            case "Range":
                code = StatusCode.CLIENT_4100301.value();
                break;
            default:
                code = StatusCode.CLIENT_410016.value();
        }
        return code;
    }


}
