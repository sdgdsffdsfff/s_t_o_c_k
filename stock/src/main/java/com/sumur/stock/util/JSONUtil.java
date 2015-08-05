package com.sumur.stock.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.PropertyFilter;

import com.sumur.stock.exception.BizException;


@SuppressWarnings("rawtypes")
public class JSONUtil {

	public static String toString(List list) {
		return toString(list, new String());
	}

	public static String toString(List list, String filterPropertie) {
		return toString(list, new String[] { filterPropertie });
	}

	public static String toString(List list, String[] filterProperties) {
		return JSONArray.fromObject(list, setFilterPropertie(filterProperties)).toString();
	}

	@SuppressWarnings("unchecked")
	public static String toString(Set set) {
		List list = new ArrayList();
		list.addAll(set);
		return toString(list);
	}

	@SuppressWarnings("unchecked")
	public static String toString(Set set, String filterPropertie) {
		List list = new ArrayList();
		list.addAll(set);
		return toString(list, filterPropertie);
	}

	@SuppressWarnings("unchecked")
	public static String toString(Set set, String[] filterProperties) {
		List list = new ArrayList();
		list.addAll(set);
		return JSONArray.fromObject(list, setFilterPropertie(filterProperties)).toString();
	}

	public static String toString(Object object) {
		return toString(object, new String());
	}

	public static String toString(Object object, String filterPropertie) {
		return toString(object, new String[] { filterPropertie });
	}

	public static String toString(Object object, String[] filterPropertie) {
		return JSONObject.fromObject(object, setFilterPropertie(filterPropertie)).toString();
	}

	public static List toBean(String jsonString, Class objectClass) {
		return toBean(jsonString, objectClass, new String());
	}

	public static List toBean(String jsonString, Class objectClass, String filterPropertie) {
		JSONArray array = JSONArray.fromObject(jsonString, setFilterPropertie(filterPropertie));
		return (List) JSONArray.toCollection(array, objectClass);
	}

	public static Object toBean(String jsonString, Class objectClass, Map classMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return JSONObject.toBean(jsonObject, objectClass, classMap);
	}

	public static Object toBean(String jsonString, Class objectClass, String... filterPropertie) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString, setFilterPropertie(filterPropertie));
		return JSONObject.toBean(jsonObject, objectClass);
	}
	
	public static Object toBean(String jsonString, Class objectClass,Map classMap, String... filterPropertie) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString,setFilterPropertie(filterPropertie));
		return JSONObject.toBean(jsonObject, objectClass, classMap);
	}

	public static JsonConfig setFilterPropertie(final String filterPropertie) {
		String[] str = new String[] { filterPropertie };
		return setFilterPropertie(str);
	}

	public static JsonConfig setFilterPropertie(final String[] filterProperties) {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				boolean bool = false;
				for (int i = 0; i < filterProperties.length; i++) {
					if (name.equals(filterProperties[i])) {
						bool = true;
						break;
					}
				}
				return bool;
			}
		});
		
		//时间格式
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		return config;
	}
}

class DateJsonValueProcessor implements JsonValueProcessor {
	
    @Override
    public Object processArrayValue(Object value, JsonConfig jsonConfig)  {
        String[] obj = {};  
        if (value instanceof Date[]) {   
            Date[] dates = (Date[]) value;  
            obj = new String[dates.length];  
            for (int i = 0; i < dates.length; i++) {  
                try {
					obj[i] = DateUtil.formatDate(dates[i], "yyyy-MM-dd'T'HH:mm:ss");
				} catch (BizException e) {
					
				}
            }  
        }  
        return obj;  
    }
     
    /* (non-Javadoc)
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        if (value instanceof Date) {  
            String str = "";
			try {
				str = DateUtil.formatDate((Date)value, "yyyy-MM-dd HH:mm:ss");
			} catch (BizException e) {
				
			}
            return str;  
        }  
        return value;  
    }  
}