package ninja.egg82.events;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.proxy.ProxyServer;

public class Events {
    private Events() {}

    public static <T> VelocityEventSubscriber<T> subscribe(Object plugin, ProxyServer proxy, Class<T> event, PostOrder order) { return new VelocityEventSubscriber<>(plugin, proxy, event, order); }

    public static void call(ProxyServer proxy, Object event) { proxy.getEventManager().fireAndForget(event); }

    public static void callAsync(Object plugin, ProxyServer proxy, Object event) { proxy.getScheduler().buildTask(plugin, () -> call(proxy, event)); }

    public static <T> MergedVelocityEventSubscriber<T> merge(Object plugin, ProxyServer proxy, Class<T> commonClass) { return new MergedVelocityEventSubscriber<>(plugin, proxy, commonClass); }

    public static <T> MergedVelocityEventSubscriber<T> merge(Object plugin, ProxyServer proxy, Class<T> superclass, Class<? extends T>... events) { return merge(plugin, proxy, superclass, PostOrder.NORMAL, events); }

    public static <T> MergedVelocityEventSubscriber<T> merge(Object plugin, ProxyServer proxy, Class<T> superclass, PostOrder order, Class<? extends T>... events) {
        MergedVelocityEventSubscriber<T> subscriber = new MergedVelocityEventSubscriber<>(plugin, proxy, superclass);
        for (Class<? extends T> clazz : events) {
            subscriber.bind(clazz, order, e -> e);
        }
        return subscriber;
    }
}
