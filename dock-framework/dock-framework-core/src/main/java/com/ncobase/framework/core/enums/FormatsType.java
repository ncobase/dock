package com.ncobase.framework.core.enums;

import com.ncobase.framework.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * 日期格式
 * "yyyy"：4 位数的年份，例如：2023 年表示为"2023"。
 * "yy"：2 位数的年份，例如：2023 年表示为"23"。
 * "MM"：2 位数的月份，取值范围为 01 到 12，例如：7 月表示为"07"。
 * "M"：不带前导零的月份，取值范围为 1 到 12，例如：7 月表示为"7"。
 * "dd"：2 位数的日期，取值范围为 01 到 31，例如：22 日表示为"22"。
 * "d"：不带前导零的日期，取值范围为 1 到 31，例如：22 日表示为"22"。
 * "EEEE"：星期的全名，例如：星期三表示为"Wednesday"。
 * "E"：星期的缩写，例如：星期三表示为"Wed"。
 * "DDD" 或 "D"：一年中的第几天，取值范围为 001 到 366，例如：第 200 天表示为"200"。
 * 时间格式
 * "HH"：24 小时制的小时数，取值范围为 00 到 23，例如：下午 5 点表示为"17"。
 * "hh"：12 小时制的小时数，取值范围为 01 到 12，例如：下午 5 点表示为"05"。
 * "mm"：分钟数，取值范围为 00 到 59，例如：30 分钟表示为"30"。
 * "ss"：秒数，取值范围为 00 到 59，例如：45 秒表示为"45"。
 * "SSS"：毫秒数，取值范围为 000 到 999，例如：123 毫秒表示为"123"。
 */

/**
 * 日期格式与时间格式枚举
 */
@Getter
@AllArgsConstructor
public enum FormatsType {

    /**
     * 例如：2023 年表示为"23"
     */
    YY("yy"),

    /**
     * 例如：2023 年表示为"2023"
     */
    YYYY("yyyy"),

    /**
     * 例例如，2023 年 7 月可以表示为 "2023-07"
     */
    YYYY_MM("yyyy-MM"),

    /**
     * 例如，日期 "2023 年 7 月 22 日" 可以表示为 "2023-07-22"
     */
    YYYY_MM_DD("yyyy-MM-dd"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分"，则可以表示为 "2023-07-22 15:30"
     */
    YYYY_MM_DD_HH_MM("yyyy-MM-dd HH:mm"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分 45 秒"，则可以表示为 "2023-07-22 15:30:45"
     */
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"),

    /**
     * 例如：下午 3 点 30 分 45 秒，表示为 "15:30:45"
     */
    HH_MM_SS("HH:mm:ss"),

    /**
     * 例例如，2023 年 7 月可以表示为 "2023/07"
     */
    YYYY_MM_SLASH("yyyy/MM"),

    /**
     * 例如，日期 "2023 年 7 月 22 日" 可以表示为 "2023/07/22"
     */
    YYYY_MM_DD_SLASH("yyyy/MM/dd"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分 45 秒"，则可以表示为 "2023/07/22 15:30:45"
     */
    YYYY_MM_DD_HH_MM_SLASH("yyyy/MM/dd HH:mm"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分 45 秒"，则可以表示为 "2023/07/22 15:30:45"
     */
    YYYY_MM_DD_HH_MM_SS_SLASH("yyyy/MM/dd HH:mm:ss"),

    /**
     * 例例如，2023 年 7 月可以表示为 "2023.07"
     */
    YYYY_MM_DOT("yyyy.MM"),

    /**
     * 例如，日期 "2023 年 7 月 22 日" 可以表示为 "2023.07.22"
     */
    YYYY_MM_DD_DOT("yyyy.MM.dd"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分"，则可以表示为 "2023.07.22 15:30"
     */
    YYYY_MM_DD_HH_MM_DOT("yyyy.MM.dd HH:mm"),

    /**
     * 例如，当前时间如果是 "2023 年 7 月 22 日下午 3 点 30 分 45 秒"，则可以表示为 "2023.07.22 15:30:45"
     */
    YYYY_MM_DD_HH_MM_SS_DOT("yyyy.MM.dd HH:mm:ss"),

    /**
     * 例如，2023 年 7 月可以表示为 "202307"
     */
    YYYYMM("yyyyMM"),

    /**
     * 例如，2023 年 7 月 22 日可以表示为 "20230722"
     */
    YYYYMMDD("yyyyMMdd"),

    /**
     * 例如，2023 年 7 月 22 日下午 3 点可以表示为 "2023072215"
     */
    YYYYMMDDHH("yyyyMMddHH"),

    /**
     * 例如，2023 年 7 月 22 日下午 3 点 30 分可以表示为 "202307221530"
     */
    YYYYMMDDHHMM("yyyyMMddHHmm"),

    /**
     * 例如，2023 年 7 月 22 日下午 3 点 30 分 45 秒可以表示为 "20230722153045"
     */
    YYYYMMDDHHMMSS("yyyyMMddHHmmss");

    /**
     * 时间格式
     */
    private final String timeFormat;

    public static FormatsType getFormatsType(String str) {
        for (FormatsType value : values()) {
            if (StringUtils.contains(str, value.getTimeFormat())) {
                return value;
            }
        }
        throw new RuntimeException("'FormatsType' not found By " + str);
    }
}
