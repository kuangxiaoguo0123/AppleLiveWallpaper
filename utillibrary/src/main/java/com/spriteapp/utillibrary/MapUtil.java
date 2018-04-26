package com.spriteapp.utillibrary;

import java.util.Map;

/**
 * Map util
 * Created by kuangxiaoguo on 2017/12/25.
 */

public class MapUtil {

    /**
     * Judge the map is empty or not.
     *
     * @param map The map you want to judge.
     * @return true if map is empty.
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * Judge the position is legal or not in maps.
     *
     * @param map      The compare map.
     * @param position The position you want to judge.
     * @return true if position legal.
     */
    public static boolean isPosition(Map map, int position) {
        return !isEmpty(map) && position >= 0 && position < map.size();
    }
}
