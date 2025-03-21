package org.javaboy.vhr.converter;

import org.javaboy.vhr.utils.BaseContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.beancontext.BeanContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @时间 2019-11-20 8:08
 */
@Component
public class DateConverter implements Converter<String, Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date convert(String source) {
        try {
            //注意这个方法会将所有需要保存date类型的数据都转为为yyyy-MM-dd格式
            //所以如果数据库中的数据需要是yyyy-MM-dd HH:mm:ss 可以修改将实体类中的Date类型改为LocalDateTime
            //也可以在同一个请求中使用LocalTread存一个时间格式标记，然后在这里根据标记判断是哪个格式
            Object dateFormat = BaseContext.get("dateFormat");
            if (dateFormat != null) {
                sdf = new SimpleDateFormat((String) dateFormat);
            }
            return sdf.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
