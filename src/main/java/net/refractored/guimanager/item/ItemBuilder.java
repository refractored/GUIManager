package net.refractored.guimanager.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * A helper class for creating or modifying ItemStacks.
 * <br>
 * The class wraps an ItemStack object and provides convenient chainable, 'builder-pattern' methods for
 * manipulating the stack's metadata.
 * <br>
 * The intention is that this class will be used in builder form - for example;
 * <pre><code>
 * new ItemBuilder(Material.SPONGE).miniName("<red>&lt;Almighty sponge").amount(21).build();
 * </code></pre>
 *
 * @author SamJakob
 * @version 2.0.0
 * @see ItemStack
 */
public class ItemBuilder {

    /**
     * The item stack being built.
     */
    private final ItemStack stack;

    /* CONSTRUCT */

    /**
     * Creates an {@link ItemStack} and {@link ItemBuilder} wrapper for a new stack with the
     * given type.
     *
     * @param material The {@link Material} to use when creating the stack.
     */
    public ItemBuilder (Material material) {
        this.stack = new ItemStack(material);
    }

    /**
     * Creates an ItemBuilder wrapper for a given stack.
     * @param stack The ItemStack to wrap.
     */
    public ItemBuilder (ItemStack stack) {
        this.stack = stack;
    }

    /* MANIPULATE / READ */

    /**
     * Returns the type ({@link Material}) of the ItemStack.
     *
     * @return The {@link Material} of the stack.
     */
    public Material getType() {
        return stack.getType();
    }

    /**
     * Sets the display name of the item.
     *
     * @param name The desired display name of the item stack.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder name(Component name) {
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.displayName(name);
        stack.setItemMeta(stackMeta);
        return this;
    }

    /**
     * Gets a copy of the Itemstack's {@link ItemMeta}.
     *
     * @return A copy of the Itemstack's {@link ItemMeta}
     */
    public ItemMeta getItemMeta() {
        return stack.getItemMeta();
    }

    /**
     * Sets the Itemstack's {@link ItemMeta}.
     */
    public void setItemMeta(ItemMeta itemMeta) {
        stack.setItemMeta(itemMeta);
    }

    /**
     * Sets the display name of the item.
     * This method uses minimessage to parse the name.
     *
     * @param name The desired display name of the item stack.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder miniName(String name) {
        return name(MiniMessage.miniMessage().deserialize((name)));
    }

    /**
     * Returns either the display name of the item, if it exists, or null if it doesn't.
     * <br>
     *
     * @return The item's display name as returned from its {@link ItemMeta}.
     */
    public Component getName() {
        if (!stack.hasItemMeta() || !stack.getItemMeta().hasDisplayName()) return null;
        return stack.getItemMeta().displayName();
    }

    /**
     * Sets the amount of items in the {@link ItemStack}.
     *
     * @param amount The new amount.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder amount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    /**
     * Returns the amount of items in the {@link ItemStack}.
     *
     * @return The amount of items in the stack.
     */
    public int getAmount() {
        return stack.getAmount();
    }

    /**
     * Sets the lore of the item. This method is a var-args alias for the
     * {@link #lore(List)} method.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder lore(Component... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Sets the lore of the item. This method is a var-args alias for the
     * {@link #lore(List)} method. This method uses minimessage to parse the lore.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder miniLore(String... lore) {
        return miniLore(Arrays.asList(lore));
    }

    /**
     * Sets the lore of the item.
     * <br>
     * Lines will not be automatically wrapped or truncated, so it is recommended you take
     * some consideration into how the item will be rendered with the lore.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder miniLore(List<String> lore) {
        List<Component> loreComponents = lore.stream()
                .map(s -> MiniMessage.miniMessage().deserialize(s))
                .toList();
        return lore(loreComponents);
    }

    /**
     * Sets the lore of the item.
     * <br>
     * Lines will not be automatically wrapped or truncated, so it is recommended you take
     * some consideration into how the item will be rendered with the lore.
     *
     * @param lore The desired lore of the item, with each line as a separate string.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder lore(List<Component> lore) {
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.lore(lore);
        stack.setItemMeta(stackMeta);
        return this;
    }

    /**
     * Gets the lore of the item as a list of strings. Each string represents a line of the
     * item's lore in-game.
     * <br>
     * As with {@link #name(Component)}, it should be noted that color-coded lore lines will
     * be returned with the colors codes already translated.
     *
     * @return The lore of the item.
     */
    public List<Component> getLore() {
        if (!stack.hasItemMeta() || !stack.getItemMeta().hasLore()) return null;
        return stack.getItemMeta().lore();
    }

    /**
     * An alias for {@link #durability(short)}.
     *
     * @param data The desired data-value (durability) of the item.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder data(short data) {
        return durability(data);
    }

    /**
     * Sets the durability (data value) of the item.
     *
     * @param durability The desired durability of the item.
     * @return The updated {@link ItemBuilder} object.
     */
    public ItemBuilder durability(short durability) {
        Damageable itemMeta = (Damageable) stack.getItemMeta();
        itemMeta.setDamage(durability);
        stack.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Returns the durability or data value of the item.
     *
     * @return The durability of the item.
     */
    public int getDurability() {
        Damageable itemMeta = (Damageable) stack.getItemMeta();
       return itemMeta.getDamage();
    }

    /**
     * Adds the specified enchantment to the stack.
     * <br>
     * This method uses {@link ItemStack#addUnsafeEnchantment(Enchantment, int)} rather than {@link ItemStack#addEnchantment(Enchantment, int)}
     * to avoid the associated checks of whether level is within the range for the enchantment.
     *
     * @param enchantment The enchantment to apply to the item.
     * @param level The level of the enchantment to apply to the item.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder enchant(Enchantment enchantment, int level) {
        stack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Removes the specified enchantment from the stack.
     *
     * @param enchantment The enchantment to remove from the item.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder unenchant(Enchantment enchantment) {
        stack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Accepts a variable number of {@link ItemFlag}s to apply to the stack.
     *
     * @param flag A variable-length argument containing the flags to be applied.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder flag(ItemFlag ...flag) {
        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(flag);
        stack.setItemMeta(meta);
        return this;
    }

    /**
     * Accepts a variable number of {@link ItemFlag}s to remove from the stack.
     *
     * @param flag A variable-length argument containing the flags to be removed.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder deflag(ItemFlag ...flag) {
        ItemMeta meta = stack.getItemMeta();
        meta.removeItemFlags(flag);
        stack.setItemMeta(meta);
        return this;
    }

    /**
     * If the item has {@link SkullMeta} (i.e. if the item is a skull), this can
     * be used to set the skull's owner (i.e. the player the skull represents.)
     * <br>
     *
     * @param player The name of the player the skull item should resemble.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder skullOwner(OfflinePlayer player) {
        if (!(stack.getItemMeta() instanceof SkullMeta meta)) return this;
        meta.setOwningPlayer(player);
        stack.setItemMeta(meta);

        return this;
    }

    /**
     * If the item has {@link SkullMeta} (i.e. if the item is a skull), this can
     * be used to set the skull's owner (i.e. the player the skull represents.)
     * <br>
     *
     * @param base64 The base64 string of the skin to apply to the skull.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder skullTexture(String base64) {
        if (!(stack.getItemMeta() instanceof SkullMeta meta)) return this;

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", base64));
        stack.setItemMeta(meta);

        return this;
    }

    /* PREDICATE MANIPULATION */

    /**
     * This is used to, inline, perform an operation if a given condition is true.
     * <br>
     * The {@link ItemBuilder} instance is supplied to both the predicate (condition) and result function.
     * The result of <code>then</code> is ignored as the ItemBuilder reference is passed to it.
     * <br>
     * Example:
     * <pre><code>
     * // Renames the ItemStack, if and only if, the stack's type is Acacia Doors.
     * ifThen(stack -&gt; stack.getType() == Material.ACACIA_DOOR, stack -&gt; stack.name("&amp;aMagic Door"));
     * </code></pre>
     *
     * @param ifTrue The condition upon which, <code>then</code> should be performed.
     * @param then The action to perform if the predicate, <code>ifTrue</code>, is true.
     * @return The {@link ItemBuilder} instance.
     */
    public ItemBuilder ifThen(Predicate<ItemBuilder> ifTrue, Function<ItemBuilder, Object> then) {
        if (ifTrue.test(this))
            then.apply(this);

        return this;
    }

    /* BUILD */

    /**
     * An alias for {@link #get()}.
     * @return See {@link #get()}.
     */
    public ItemStack build() {
        return get();
    }

    /**
     * Returns the {@link ItemStack} that the {@link ItemBuilder} instance represents.
     * <br>
     * The modifications are performed as they are called, so this method simply returns
     * the class's private stack field.
     *
     * @return The manipulated ItemStack.
     */
    public ItemStack get() {
        return stack;
    }

}
