package net.minecraftforge.oredict;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemOre {
    
    String name;

    public ItemOre(String name)
    {
        this.name = name;
    }

    public boolean isItemEqual(ItemStack other)
    {
        for(ItemStack item : OreDictionary.getOres(name)){
            if(item.isItemEqual(other)) return true;
        }
        return false;
    }
    
    public ArrayList<ItemStack> getOres()
    {
        return OreDictionary.getOres(name);
    }
}
