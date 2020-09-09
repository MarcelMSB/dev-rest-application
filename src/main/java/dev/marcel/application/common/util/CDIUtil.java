package dev.marcel.application.common.util;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InjectionTargetFactory;

public final class CDIUtil {

    private CDIUtil() {
    }

    /**
     * Allows to perform dependency injection for instances which aren't managed
     * by CDI.
     * <p/>
     * Attention:<br/>
     * The resulting instance isn't managed by CDI; only fields annotated with
     *
     * @Inject get initialized.
     *
     * @param instance current instance
     * @param <T> current type
     *
     * @return instance with injected fields (if possible - or null if the given
     * instance is null)
     */
    public static <T> T injectFields(T instance) {
        if (instance == null) {
            return instance;
        }

        BeanManager beanManager = CDI.current().getBeanManager();
        CreationalContext creationalContext = beanManager.createCreationalContext(null);
        AnnotatedType annotatedType = beanManager.createAnnotatedType(instance.getClass());
        InjectionTargetFactory injectionTargetFactory = beanManager.getInjectionTargetFactory(
                annotatedType);
        InjectionTarget injectionTarget = injectionTargetFactory.createInjectionTarget(null);
        injectionTarget.inject(instance, creationalContext);

        return instance;
    }

    /**
     * Get a CDI bean by class. First occurrence.
     *
     * @param <T> Type of bean.
     * @param beanClass Class of bean.
     * @return A instance of bean or null if not found.
     */
    public static <T> T getBean(Class<T> beanClass) {
        BeanManager beanManager = CDI.current().getBeanManager();
        Set<Bean<?>> beans = beanManager.getBeans(beanClass);
        if (!beans.isEmpty()) {
            Bean bean = beans.iterator().next();
            CreationalContext ctx = beanManager.createCreationalContext(bean);
            T t = (T) beanManager.getReference(bean, beanClass, ctx);
            ctx.release();

            return t;
        } else {
            return null;
        }
    }
}
