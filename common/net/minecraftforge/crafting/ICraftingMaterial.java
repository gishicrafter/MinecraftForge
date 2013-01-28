package net.minecraftforge.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public interface ICraftingMaterial {

    /**
     * Compares argument ItemStack to this instance.
     * 
     * @param other
     *            ItemStack to compare.
     * @return Returns true if the argument matches this instance.
     */
    boolean isItemEqual(ItemStack other);

    /**
     * Makes a list of ItemStacks that this instance represents.
     * 
     * @return
     */
    ArrayList<ItemStack> getItems();
}
