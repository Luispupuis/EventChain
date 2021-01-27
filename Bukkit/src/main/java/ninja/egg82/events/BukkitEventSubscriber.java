package ninja.egg82.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BukkitEventSubscriber<T extends Event> extends AbstractPriorityEventSubscriber<EventPriority, T> implements Listener {
    public BukkitEventSubscriber(@NotNull Plugin plugin, @NotNull Class<T> event, @NotNull EventPriority priority) {
        super(event);

        plugin.getServer().getPluginManager().registerEvent(event, this, priority, (l, e) -> {
            try {
                call((T) e, priority);
            } catch (PriorityEventException ex) {
                throw new RuntimeException("Could not call event subscriber.", ex);
            }
        }, plugin, false);
    }

    public void cancel() {
        super.cancel();
        HandlerList.unregisterAll(this);
    }
}
