package com.gestorcerveja;

import org.springframework.context.ApplicationContext;

/**
 * Singleton estático que guarda o {@link ApplicationContext} do Spring.
 *
 * Permite que código JavaFX (que não é gerido pelo Spring) obtenha
 * beans injetados:
 * <pre>
 *   var service = SpringContext.getBean(IngredienteService.class);
 * </pre>
 */
public final class SpringContext {

    private static ApplicationContext context;

    private SpringContext() {}

    public static void setContext(ApplicationContext ctx) {
        context = ctx;
    }

    public static ApplicationContext getContext() {
        if (context == null) throw new IllegalStateException("Spring context ainda não foi inicializado.");
        return context;
    }

    /** Atalho conveniente para obter um bean por tipo. */
    public static <T> T getBean(Class<T> beanClass) {
        return getContext().getBean(beanClass);
    }
}
