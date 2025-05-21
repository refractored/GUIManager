package net.refractored.guimanager.toolbar;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.refractored.guimanager.SpiGUI;
import net.refractored.guimanager.buttons.SGButton;
import net.refractored.guimanager.item.ItemBuilder;
import net.refractored.guimanager.menu.SGMenu;
import org.bukkit.Material;
import org.bukkit.event.Event;

/**
 * The default implementation of {@link SGToolbarBuilder}.
 * <br>
 * This class is used by default by SpiGUI, but you can override this class by
 * extending it and passing your custom implementation to
 * {@link SpiGUI#setDefaultToolbarBuilder(SGToolbarBuilder)}
 * (or to use it for a specific menu, pass it to
 * {@link SGMenu#setToolbarBuilder(SGToolbarBuilder)}).
 */
public class SGDefaultToolbarBuilder implements SGToolbarBuilder {

    /**
     * Construct the default implementation of {@link SGToolbarBuilder}.
     */
    public SGDefaultToolbarBuilder() {}

    @Override
    public SGButton buildToolbarButton(int slot, int page, SGToolbarButtonType type, SGMenu menu) {
        switch (type) {
            case PREV_BUTTON:
                if (menu.getCurrentPage() > 0) return new SGButton(new ItemBuilder(Material.ARROW)
                        .name(MiniMessage.miniMessage().deserialize("<green><bold>← Previous Page"))
                        .lore(
                                MiniMessage.miniMessage().deserialize("<green>Click to move back to"),
                                MiniMessage.miniMessage().deserialize("<green>page " + menu.getCurrentPage() + "."))
                        .build()
                ).withListener(event -> {
                    event.setResult(Event.Result.DENY);
                    menu.previousPage(event.getWhoClicked());
                });
                else return null;

            case CURRENT_BUTTON:
                return new SGButton(new ItemBuilder(Material.NAME_TAG)
                        .name(MiniMessage.miniMessage().deserialize("<gray><bold>Page " + (menu.getCurrentPage() + 1) + " of " + menu.getMaxPage()))
                        .lore(
                                MiniMessage.miniMessage().deserialize("&7You are currently viewing"),
                                MiniMessage.miniMessage().deserialize( "&7page " + (menu.getCurrentPage() + 1) + ".")
                        ).build()
                ).withListener(event -> event.setResult(Event.Result.DENY));

            case NEXT_BUTTON:
                if (menu.getCurrentPage() < menu.getMaxPage() - 1) return new SGButton(new ItemBuilder(Material.ARROW)
                        .miniName("&a&lNext Page →")
                        .miniLore(
                                "&aClick to move forward to",
                                "&apage " + (menu.getCurrentPage() + 2) + "."
                        ).build()
                ).withListener(event -> {
                    event.setResult(Event.Result.DENY);
                    menu.nextPage(event.getWhoClicked());
                });
                else return null;

            case UNASSIGNED:
            default:
                return null;
        }
    }

}
