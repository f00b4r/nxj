package com.nxj.annotation;

import com.nxj.application.Application;
import com.nxj.application.Application;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author stuchl4n3k
 */
public class AnnotationProcessor {

    private Application context;

    public AnnotationProcessor(Application context) {
        this.context = context;
    }

    public void processAnnotations(List objects) {
        for (Object object : objects) {
            processAnnotations(object);
        }
    }

    public void processAnnotations(Object object) {
        Annotation[] annotations = null;
        String anName = null;

        annotations = object.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            try {
                anName = annotation.annotationType().getSimpleName();
                Method processMethod = getClass().getDeclaredMethod("processAnnotation" + anName, Object.class);
                if (processMethod != null) {
                    processMethod.invoke(this, object);
                }
            } catch (IllegalAccessException | IllegalArgumentException |
                    InvocationTargetException | NoSuchMethodException |
                    SecurityException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processAnnotationControlled(Object object) {
        try {
            String objClassName = object.getClass().getAnnotation(Controlled.class).controller();
            Class objClass = Class.forName(objClassName);
            Method method = object.getClass().getMethod("setController", objClass);

            method.invoke(object, context.getController(objClass));
        } catch (IllegalAccessException | IllegalArgumentException |
                InvocationTargetException | NoSuchMethodException |
                SecurityException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
