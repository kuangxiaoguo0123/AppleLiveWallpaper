package com.spriteapp.utillibrary;

import java.util.Collection;

/**
 * Collection util
 * Created by kuangxiaoguo on 2017/12/25.
 */

public class CollectionUtil {

    /**
     * Judge the collection is empty or not.
     *
     * @param c The collection you want to judge
     * @return true if empty
     */
    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    /**
     * Judge the position is legal or not in collections.
     *
     * @param c        The compare collection.
     * @param position The position you want to judge.
     * @return true if position legal
     */
    public static boolean isPositionLegal(Collection c, int position) {
        return !isEmpty(c) && position >= 0 && position < c.size();
    }
}
