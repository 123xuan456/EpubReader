package com.reeching.epub.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 在litepal框架基础上暂时解决LitePal的表不可使用String作为主键问题
 */

public class DataSupportCompat <T extends DataSupportCompat> extends DataSupport implements Serializable {

    @SuppressWarnings("WeakerAccess")
    public long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 根据你自己定义的主键到数据库中查找这个实体<p></p>
     * 需要将主键作为实体类的第一个字段，就可以通过反射获得主键名和主键值
     *
     * @return 泛型实体类对象
     */
    @SuppressWarnings({"unchecked", "ReflectionForUnavailableAnnotation"})
    private T find() {
        try {
            Field pk = null;
            Class clazz = getGenericType();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                PrimaryKey pkAnnotation = field.getAnnotation(PrimaryKey.class);
                if (pkAnnotation != null) {
                    pk = field;
                    break;
                }
            }
            if (pk == null) {
                return null;
            }
            pk.setAccessible(true);
            String pkValue = pk.get(this) + "";
            if (pkValue.isEmpty()) {
                return null;
            }
            List<T> list = DataSupport.where(pk.getName() + " = ?", pkValue).find(clazz);
            if (list == null || list.size() == 0) {
                return null;
            } else if (list.size() > 1) {//不会出现这种情况
                //删除全部，保存1个
                for (int i = 1; i < list.size(); i++) {
                    list.get(i).delete();
                }
            }
            return list.get(0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 在LitePal数据库框架中，
     * Id只允许long或int类型，
     * 所以自定义的String类型的studentId、coureId，都不唯一，
     * 会导致存储多个相同的数据。
     * 需要手动查重。
     * s
     *
     * @return 是否插入成功（忽略）
     */
    @Override
    public synchronized boolean save() {
        T t = find();//根据主键名和主键值获取数据库中实例
        if (t == null || t.id == 0) {//不存在
            return super.save();//save
        } else {
            this.id = t.id;//更新字段
            return update(id) > 0;
        }
    }

    /**
     * 获得泛型类型
     * <p></p>
     * 注：其中DBSupport<T>是泛型类
     * 在父类中声明getGenericType
     * 子类继承具体的DBSupport<Person>
     * 那么在子类中就可以通过getGenericType()获取到Person的class.
     *
     * @return T.class
     */
    private Class getGenericType() {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[0];
    }
}
