package fuse.services;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private Map<Class<?>, Service> services;

    public ServiceManager() {
        this.services = new HashMap<>();
    }

    public void registerService(Service service) {
        if (service instanceof Service) {
            ((Service) service).onRegister();
            this.services.put(service.getClass(), (Service) service);
            service.onRegister();
        }
    }

    public void unregisterService(Service service) {
        if (service instanceof Service) {
            ((Service) service).onUnregister();
            this.services.remove(service.getClass());
            service.onUnregister();
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> clazz) {
        return (T) this.services.get(clazz);
    }

    public <T> T getServiceOfType(Class<T> clazz) {
        for (Service service : this.services.values()) {
            if (clazz.isInstance(service)) {
                return clazz.cast(service);
            }
        }

        return null;
    }
}
