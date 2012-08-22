package com.nxj.annotation;

import com.nxj.application.Application;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Copyright 2012 Noblexity Advertising
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
/**
 * @author Petr Stuchl4n3k Stuchlik <stuchl4n3k@gmail.com>
 * @author Milan Felix Sulc <rkfelix@gmail.com>
 *
 * @nxj 0.1
 * @version 1.0
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
