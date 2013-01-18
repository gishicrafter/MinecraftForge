package net.minecraftforge.crafting.tags;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.crafting.ICraftingMaterial;
import net.minecraftforge.oredict.OreDictionary;

public class ItemOre implements ICraftingMaterial {

    String name;

    public ItemOre(String name)
    {
        this.name = name;
    }

    @Override
    public boolean isItemEqual(ItemStack other)
    {
        if (other == null)
        {
            return false;
        }
        ArrayList<ItemStack> items = OreDictionary.getOres(name);
        if (items == null || items.isEmpty())
        {
            return false;
        }
        for (ItemStack item : items)
        {
            if (item.isItemEqual(other))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<ItemStack> getItems()
    {
        // TODO Auto-generated method stub
        return OreDictionary.getOres(name);
    }
}
