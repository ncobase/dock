package com.ncobase.framework.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * stream 流工具类
 *
 * @author Lion Li
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    /**
     * 将 collection 过滤
     *
     * @param collection 需要转化的集合
     * @param function   过滤方法
     * @return 过滤后的 list
     */
    public static <E> List<E> filter(Collection<E> collection, Predicate<E> function) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        // 注意此处不要使用 .toList() 新语法 因为返回的是不可变 List 会导致序列化问题
        return collection.stream().filter(function).collect(Collectors.toList());
    }

    /**
     * 找到流中满足条件的第一个元素
     *
     * @param collection 需要查询的集合
     * @param function   过滤方法
     * @return 找到符合条件的第一个元素，没有则返回 null
     */
    public static <E> E findFirst(Collection<E> collection, Predicate<E> function) {
        if (CollUtil.isEmpty(collection)) {
            return null;
        }
        return collection.stream().filter(function).findFirst().orElse(null);
    }

    /**
     * 找到流中任意一个满足条件的元素
     *
     * @param collection 需要查询的集合
     * @param function   过滤方法
     * @return 找到符合条件的任意一个元素，没有则返回 null
     */
    public static <E> Optional<E> findAny(Collection<E> collection, Predicate<E> function) {
        if (CollUtil.isEmpty(collection)) {
            return Optional.empty();
        }
        return collection.stream().filter(function).findAny();
    }

    /**
     * 将 collection 拼接
     *
     * @param collection 需要转化的集合
     * @param function   拼接方法
     * @return 拼接后的 list
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function) {
        return join(collection, function, StringUtils.SEPARATOR);
    }

    /**
     * 将 collection 拼接
     *
     * @param collection 需要转化的集合
     * @param function   拼接方法
     * @param delimiter  拼接符
     * @return 拼接后的 list
     */
    public static <E> String join(Collection<E> collection, Function<E, String> function, CharSequence delimiter) {
        if (CollUtil.isEmpty(collection)) {
            return StringUtils.EMPTY;
        }
        return collection.stream().map(function).filter(Objects::nonNull).collect(Collectors.joining(delimiter));
    }

    /**
     * 将 collection 排序
     *
     * @param collection 需要转化的集合
     * @param comparing  排序方法
     * @return 排序后的 list
     */
    public static <E> List<E> sorted(Collection<E> collection, Comparator<E> comparing) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        // 注意此处不要使用 .toList() 新语法 因为返回的是不可变 List 会导致序列化问题
        return collection.stream().filter(Objects::nonNull).sorted(comparing).collect(Collectors.toList());
    }

    /**
     * 将 collection 转化为类型不变的 map<br>
     * <B>{@code Collection<V>  ---->  Map<K,V>}</B>
     *
     * @param collection 需要转化的集合
     * @param key        V 类型转化为 K 类型的 lambda 方法
     * @param <V>        collection 中的泛型
     * @param <K>        map 中的 key 类型
     * @return 转化后的 map
     */
    public static <V, K> Map<K, V> toIdentityMap(Collection<V> collection, Function<V, K> key) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toMap(key, Function.identity(), (l, r) -> l));
    }

    /**
     * 将 Collection 转化为 map(value 类型与 collection 的泛型不同)<br>
     * <B>{@code Collection<E> -----> Map<K,V>  }</B>
     *
     * @param collection 需要转化的集合
     * @param key        E 类型转化为 K 类型的 lambda 方法
     * @param value      E 类型转化为 V 类型的 lambda 方法
     * @param <E>        collection 中的泛型
     * @param <K>        map 中的 key 类型
     * @param <V>        map 中的 value 类型
     * @return 转化后的 map
     */
    public static <E, K, V> Map<K, V> toMap(Collection<E> collection, Function<E, K> key, Function<E, V> value) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toMap(key, value, (l, r) -> l));
    }

    /**
     * 将 collection 按照规则 (比如有相同的班级 id) 分类成 map<br>
     * <B>{@code Collection<E> -------> Map<K,List<E>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key        分类的规则
     * @param <E>        collection 中的泛型
     * @param <K>        map 中的 key 类型
     * @return 分类后的 map
     */
    public static <E, K> Map<K, List<E>> groupByKey(Collection<E> collection, Function<E, K> key) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream().filter(Objects::nonNull)
            .collect(Collectors.groupingBy(key, LinkedHashMap::new, Collectors.toList()));
    }

    /**
     * 将 collection 按照两个规则 (比如有相同的年级 id，班级 id) 分类成双层 map<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,List<E>>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key1       第一个分类的规则
     * @param key2       第二个分类的规则
     * @param <E>        集合元素类型
     * @param <K>        第一个 map 中的 key 类型
     * @param <U>        第二个 map 中的 key 类型
     * @return 分类后的 map
     */
    public static <E, K, U> Map<K, Map<U, List<E>>> groupBy2Key(Collection<E> collection, Function<E, K> key1, Function<E, U> key2) {
        if (CollUtil.isEmpty(collection)) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream().filter(Objects::nonNull)
            .collect(Collectors.groupingBy(key1, LinkedHashMap::new, Collectors.groupingBy(key2, LinkedHashMap::new, Collectors.toList())));
    }

    /**
     * 将 collection 按照两个规则 (比如有相同的年级 id，班级 id) 分类成双层 map<br>
     * <B>{@code Collection<E>  --->  Map<T,Map<U,E>> } </B>
     *
     * @param collection 需要分类的集合
     * @param key1       第一个分类的规则
     * @param key2       第二个分类的规则
     * @param <T>        第一个 map 中的 key 类型
     * @param <U>        第二个 map 中的 key 类型
     * @param <E>        collection 中的泛型
     * @return 分类后的 map
     */
    public static <E, T, U> Map<T, Map<U, E>> group2Map(Collection<E> collection, Function<E, T> key1, Function<E, U> key2) {
        if (CollUtil.isEmpty(collection) || key1 == null || key2 == null) {
            return MapUtil.newHashMap();
        }
        return collection
            .stream().filter(Objects::nonNull)
            .collect(Collectors.groupingBy(key1, LinkedHashMap::new, Collectors.toMap(key2, Function.identity(), (l, r) -> l)));
    }

    /**
     * 将 collection 转化为 List 集合，但是两者的泛型不同<br>
     * <B>{@code Collection<E>  ------>  List<T> } </B>
     *
     * @param collection 需要转化的集合
     * @param function   collection 中的泛型转化为 list 泛型的 lambda 表达式
     * @param <E>        collection 中的泛型
     * @param <T>        List 中的泛型
     * @return 转化后的 list
     */
    public static <E, T> List<T> toList(Collection<E> collection, Function<E, T> function) {
        if (CollUtil.isEmpty(collection)) {
            return CollUtil.newArrayList();
        }
        return collection
            .stream()
            .map(function)
            .filter(Objects::nonNull)
            // 注意此处不要使用 .toList() 新语法 因为返回的是不可变 List 会导致序列化问题
            .collect(Collectors.toList());
    }

    /**
     * 将 collection 转化为 Set 集合，但是两者的泛型不同<br>
     * <B>{@code Collection<E>  ------>  Set<T> } </B>
     *
     * @param collection 需要转化的集合
     * @param function   collection 中的泛型转化为 set 泛型的 lambda 表达式
     * @param <E>        collection 中的泛型
     * @param <T>        Set 中的泛型
     * @return 转化后的 Set
     */
    public static <E, T> Set<T> toSet(Collection<E> collection, Function<E, T> function) {
        if (CollUtil.isEmpty(collection) || function == null) {
            return CollUtil.newHashSet();
        }
        return collection
            .stream()
            .map(function)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    }


    /**
     * 合并两个相同 key 类型的 map
     *
     * @param map1  第一个需要合并的 map
     * @param map2  第二个需要合并的 map
     * @param merge 合并的 lambda，将 key  value1 value2 合并成最终的类型，注意 value 可能为空的情况
     * @param <K>   map 中的 key 类型
     * @param <X>   第一个 map 的 value 类型
     * @param <Y>   第二个 map 的 value 类型
     * @param <V>   最终 map 的 value 类型
     * @return 合并后的 map
     */
    public static <K, X, Y, V> Map<K, V> merge(Map<K, X> map1, Map<K, Y> map2, BiFunction<X, Y, V> merge) {
        if (MapUtil.isEmpty(map1) && MapUtil.isEmpty(map2)) {
            return MapUtil.newHashMap();
        } else if (MapUtil.isEmpty(map1)) {
            map1 = MapUtil.newHashMap();
        } else if (MapUtil.isEmpty(map2)) {
            map2 = MapUtil.newHashMap();
        }
        Set<K> key = new HashSet<>();
        key.addAll(map1.keySet());
        key.addAll(map2.keySet());
        Map<K, V> map = new HashMap<>();
        for (K t : key) {
            X x = map1.get(t);
            Y y = map2.get(t);
            V z = merge.apply(x, y);
            if (z != null) {
                map.put(t, z);
            }
        }
        return map;
    }

}
