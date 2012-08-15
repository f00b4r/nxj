package com.nxj.application;

import com.nxj.application.listeners.ShutdownListener;
import com.nxj.annotation.AnnotationProcessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javassist.Modifier;
import org.reflections.Reflections;

/**
 *
 * @author stuchl4n3k
 */
public class Application {

    protected Config config;
    protected List<Controller> controllers;
    protected List<View> views;
    protected List<Module> modules;
    private List<ShutdownListener> shutdownListeners;
    private static Application instance;
    private static final Logger logger = Logger.getLogger(Application.class.getName());
    private Reflections reflections;
    private AnnotationProcessor annotationProcessor;

    /**
     * Private constructor for Singleton pattern.
     */
    private Application() {
        controllers = new ArrayList<>();
        views = new ArrayList<>();
        modules = new ArrayList<>();
        shutdownListeners = new ArrayList<>();
        reflections = new Reflections("");
        annotationProcessor = new AnnotationProcessor(this);
    }

    /**
     * Universal Singleton/context getter.
     *
     * @return Application context.
     */
    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    /**
     * Bootstraps this Application. Bootstrapping initalizes Config, loads all
     * the Modules and instantiates the Controllers.
     *
     * @param appBundlePath
     * @param defBundlePath
     */
    public void bootstrap(String appBundlePath, String defBundlePath) {
        config = new Config(appBundlePath, defBundlePath);
        bootstrapModules();
        bootstrapControllers();
    }

    /**
     * View creator method. Makes it possible to use Dependency injection in
     * Views and Annotation processing.
     *
     * @param viewClass View class to create.
     * @return Initialized view object.
     */
    public <V extends View> V createView(Class<? extends View> viewClass) {
        try {
            View view = null;
            view = viewClass.newInstance();
            // After the View has been initialized, process the annotaions.
            annotationProcessor.processAnnotations(view);

            // Add the view to the collection.
            views.add(view);

            return (V) view;
        } catch (InstantiationException | IllegalAccessException ex) {
        }
        throw new NoClassDefFoundError("Could not find the specified view " + viewClass);
    }

    /**
     * Gets a Config instance.
     *
     * @return
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Locates a Controller by given name. If the name argument is e.g. "Main",
     * then this method would look for a class "MainController".
     *
     * @param name Name of the Controller to find.
     * @return Instantiated controller.
     */
    public Controller getController(String name) {
        String className = null;
        for (Controller ctrl : controllers) {
            className = ctrl.getClass().getSimpleName();
            if (className.equalsIgnoreCase(name + "Controller")) {
                return ctrl;
            }
        }
        throw new NoClassDefFoundError("Controller of name " + name + " could not be found!");
    }

    /**
     * Locates a Controller by given class.
     *
     * @param ctrlClass A controller class to find.
     * @return Controller of class ctrlClass.
     */
    public <C extends Controller> C getController(Class<? extends Controller> ctrlClass) {
        for (Controller ctrl : controllers) {
            if (ctrl.getClass() == ctrlClass) {
                return (C) ctrl;
            }
        }
        throw new NoClassDefFoundError("Could not find the specified controller " + ctrlClass);
    }

    /**
     * Returns a list of all active Modules.
     *
     * @return Module list.
     */
    public List<Module> getModules() {
        return modules;
    }

    public void shutdown() {
        while (!shutdownListeners.isEmpty()) {
            shutdownListeners.remove(shutdownListeners.size() - 1).onShutdown();
        }
        System.exit(0);
    }

    public void addShutdownListener(ShutdownListener listener) {
        if (!shutdownListeners.contains(listener)) {
            shutdownListeners.add(listener);
        }
    }

    public void removeShutdownListener(ShutdownListener listener) {
        shutdownListeners.remove(listener);
    }

    /**
     * A helper method which initializes all the Controllers, performs
     * Dependency injection and Annotation processing.
     */
    protected void bootstrapControllers() {
        // Create controller instances.
        Set<Class<? extends Controller>> classes = reflections.getSubTypesOf(Controller.class);
        for (Class<? extends Controller> cls : classes) {
            try {
                Controller ctrl = cls.newInstance();
                ctrl.setContext(this);
                controllers.add(ctrl);
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        // After the controllers have been initialized, process the annotaions.
        annotationProcessor.processAnnotations(controllers);

        // Register all controllers as shutdownListeners.
        shutdownListeners.addAll(controllers);

        // Finally bootstrap all controllers.
        for (Controller ctrl : controllers) {
            ctrl.onBootstrap();
        }
    }

    /**
     * A helper method which initializes all the Modules, performs Dependency
     * injection.
     */
    protected void bootstrapModules() {
        Set<Class<? extends Module>> classes = reflections.getSubTypesOf(Module.class);
        for (Class<? extends Module> cls : classes) {
            // Instantiate only non-abstract Module-subclasses.
            if (!Modifier.isAbstract(cls.getModifiers())) {
                try {
                    Module mod = cls.newInstance();
                    mod.setContext(this);
                    modules.add(mod);
                } catch (InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
