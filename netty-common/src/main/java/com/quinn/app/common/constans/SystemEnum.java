package com.quinn.app.common.constans;

/**
 * Created by yinzhiqiang on 2017/5/26.
 */
public class SystemEnum {

    /**
     * 支付单类型
     */
    public enum PAY_STATUS_TYPE implements BaseEnum {
        PAY_STATUS_10(10, "验证成功待付款"), PAY_STATUS_11(11, "银行验证失败"), PAY_STATUS_12(12, "合同号重复"),
        PAY_STATUS_15(15, "取消放款"), PAY_STATUS_20(20, "付款中"),
        PAY_STATUS_25(25, "付款中待确认"), PAY_STATUS_30(30, "付款成功过"), PAY_STATUS_40(40, "交易失败"),
        PAY_STATUS_50(50, "取消");
        private Integer code;
        private String message;

        PAY_STATUS_TYPE(Integer code, String message) {
            this.code = code;
            this.message = message;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

    /**
     * 获取当前枚举
     * @param bb
     * @param code
     * @return
     */
    public static BaseEnum getEnum(BaseEnum[] bb, Integer code) {
        for (BaseEnum b : bb) {
            if (b.getCode().equals(code)) {
                return b;
            }
        }
        return null;
    }

    /**
     * 获取当前枚举的名称
     * @param bb
     * @param code
     * @return
     */
    public static String getEnumMessage(BaseEnum[] bb, Integer code) {
        for (BaseEnum b : bb) {
            if (b.getCode().equals(code)) {
                return b.getMessage();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(getEnumMessage(PAY_STATUS_TYPE.values(), 10));

    }
}
