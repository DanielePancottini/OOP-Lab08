package it.unibo.oop.lab.advanced;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Main Controller class for the Draw Number Application.
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private static final String CONFIG_NAME = "config.yml";
    private static final int MIN = 0;
    private static final int MAX = 100;
    private static final int ATTEMPTS = 10;
    private final DrawNumber model;
    private final List<DrawNumberView> views;
    private final Map<String, Integer> configMap = new HashMap<>();

    /**
     * Builds a new {@link DrawNumberApp}.
     *
     */
    public DrawNumberApp() {
        this.views = List.of(new DrawNumberViewImpl(), new DrawNumberViewFile(), new DrawNumberViewConsole());

        final boolean isConfigLoaded = this.loadConfigFile();
        this.model = new DrawNumberImpl(this.configMap);

        if (!isConfigLoaded) {
            this.execActionToAllViews("displayError", new Class[] {String.class},
                    "Error during loading of config file, default values loaded");
        }

        this.execActionToAllViews("setObserver", new Class[] {DrawNumberViewObserver.class}, this);
        this.execActionToAllViews("start", new Class[] {});
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.execActionToAllViews("result", new Class[] {DrawResult.class}, result);
        } catch (IllegalArgumentException e) {
            this.execActionToAllViews("numberIncorrect", new Class[] {});
        } catch (AttemptsLimitReachedException e) {
            this.execActionToAllViews("limitsReached", new Class[] {});
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    private boolean loadConfigFile() {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(DrawNumberApp.CONFIG_NAME)) {
            final Properties prop = new Properties();
            prop.load(is);

            this.configMap.put("maximum", Integer.parseInt(prop.get("maximum").toString()));
            this.configMap.put("minimum", Integer.parseInt(prop.get("minimum").toString()));
            this.configMap.put("attempts", Integer.parseInt(prop.get("attempts").toString()));
        } catch (IOException | NullPointerException e) {
            this.configMap.put("maximum", DrawNumberApp.MAX);
            this.configMap.put("minimum", DrawNumberApp.MIN);
            this.configMap.put("attempts", DrawNumberApp.ATTEMPTS);
           return false;
        }
        return true;
    }

    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) {
        new DrawNumberApp();
    }

    private void execActionToAllViews(final String methodName, final Class<?>[] types, final Object... args) {
        for (final DrawNumberView view : this.views) {
            try {
                DrawNumberView.class.getMethod(methodName, types).invoke(view, args);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException
                  | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
