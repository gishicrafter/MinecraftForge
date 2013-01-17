package net.minecraftforge.crafting;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ForgeRecipeUtils {

    public static boolean itemMatches(ItemStack target, ItemStack input)
    {
        return itemMatches(target, input, false);
    }

    public static boolean itemMatches(ItemStack target, ItemStack input, boolean strict)
    {
        if (input == null && target != null || input != null && target == null)
        {
            return false;
        }
        return (target.itemID == input.itemID && ((target.getItemDamage() == -1 && !strict) || target.getItemDamage() == input.getItemDamage()));
    }

    public static boolean itemMatches(ArrayList<ItemStack> targets, ItemStack input)
    {
        return itemMatches(targets, input, false);
    }

    public static boolean itemMatches(ArrayList<ItemStack> targets, ItemStack input, boolean strict)
    {
        if (input == null && (targets != null && targets.size() > 0) || input != null && (targets == null || targets.size() == 0))
        {
            return false;
        }
        if (input == null && (targets == null || targets.size() == 0))
        {
            return true;
        }
        for (ItemStack target : targets)
        {
            if (itemMatches(target, input, strict))
            {
                return true;
            }
        }
        return false;
    }
}
