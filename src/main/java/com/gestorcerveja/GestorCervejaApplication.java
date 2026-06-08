package com.gestorcerveja;

import com.gestorcerveja.ui.JavaFXApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação.
 *
 * Estratégia de arranque:
 *  1. {@code main()} lança o contexto Spring via {@link SpringApplication}.
 *  2. O bean {@link SpringContext} guarda o contexto num singleton estático.
 *  3. De seguida, lança o JavaFX {@link Application} que usa o contexto
 *     para obter todos os beans (services, controllers, etc.).
 */
@SpringBootApplication
public class GestorCervejaApplication {

    public static void main(String[] args) {
        // Inicializa o contexto Spring
        var ctx = SpringApplication.run(GestorCervejaApplication.class, args);
        SpringContext.setContext(ctx);

        // Arranca o JavaFX na thread própria (não bloqueia o main Spring thread)
        Application.launch(JavaFXApplication.class, args);
    }
}
