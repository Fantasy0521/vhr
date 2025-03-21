package org.javaboy.vhr.utils;

import org.javaboy.vhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @时间 2019-10-24 8:11
 */
public class HrUtils {
    public static Hr getCurrentHr() {
        Hr currentHr = null;
        try {
            currentHr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }catch (Exception e){
        }
        return currentHr;
    }
}
