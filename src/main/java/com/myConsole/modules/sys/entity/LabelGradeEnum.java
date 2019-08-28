package com.myConsole.modules.sys.entity;

import java.util.Objects;

public enum LabelGradeEnum {
    //银行汇票
    标题栏级(1, "标题栏级"),
    //银行转账
    文章分类级(2, "文章分类级");

    int index;
    String type;

    LabelGradeEnum(int index, String type) {
        this.index = index;
        this.type = type;
    }

    public int getIndex() {
        return index;
    }

    public String getType() {
        return type;
    }

    /**
     * 通过index获取value
     *
     * @param index 枚举索引
     * @return 枚举值
     */
    public static String getValue(Integer index) {
        for (LabelGradeEnum c : LabelGradeEnum.values()) {
            if (Objects.equals(c.getIndex(), index)) {
                return c.type;
            }
        }
        return null;
    }

    /**
     * 通过value获取index
     *
     * @param type 枚举值
     * @return 枚举索引
     */
    public static String getIndex(String type) {
        for (LabelGradeEnum c : LabelGradeEnum.values()) {
            if (Objects.equals(c.getType(), type)) {
                return c.index + "";
            }
        }
        return "error";
    }
}
