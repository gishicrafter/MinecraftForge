package net.minecraftforge.crafting.tags;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.crafting.ICraftingMaterial;
import net.minecraftforge.liquids.LiquidContainerData;
import net.minecraftforge.liquids.LiquidContainerRegistry;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class ItemFilledContainer implements ICraftingMaterial {

    protected LiquidStack liquid;
    protected String name;
    protected int amount;
    protected boolean exact;
    protected ItemStack[] excludes;
    protected ItemStack defaultItem;

    public ItemFilledContainer(String name, int amount)
    {
        this(name, amount, false, (ItemStack) null);
    }

    public ItemFilledContainer(String name, int amount, Block defaultItem)
    {
        this(name, amount, false, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, Item defaultItem)
    {
        this(name, amount, false, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, ItemStack defaultItem)
    {
        this(name, amount, false, defaultItem);
    }

    public ItemFilledContainer(String name, int amount, boolean exact)
    {
        this(name, amount, exact, (ItemStack) null);
    }

    public ItemFilledContainer(String name, int amount, boolean exact, Block defaultItem)
    {
        this(name, amount, exact, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, boolean exact, Item defaultItem)
    {
        this(name, amount, exact, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack defaultItem)
    {
        this(name, amount, exact, null, defaultItem);
    }

    public ItemFilledContainer(String name, int amount, ItemStack[] excludes)
    {
        this(name, amount, excludes, (ItemStack) null);
    }

    public ItemFilledContainer(String name, int amount, ItemStack[] excludes, Block defaultItem)
    {
        this(name, amount, excludes, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, ItemStack[] excludes, Item defaultItem)
    {
        this(name, amount, excludes, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, ItemStack[] excludes, ItemStack defaultItem)
    {
        this(name, amount, false, excludes, defaultItem);
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack[] excludes)
    {
        this(name, amount, exact, excludes, (ItemStack) null);
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack[] excludes, Block defaultItem)
    {
        this(name, amount, exact, excludes, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack[] excludes, Item defaultItem)
    {
        this(name, amount, exact, excludes, new ItemStack(defaultItem));
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack[] excludes, ItemStack defaultItem)
    {
        this(null, name, amount, exact, excludes, defaultItem);
    }

    public ItemFilledContainer(LiquidStack liquid)
    {
        this(liquid, false);
    }

    public ItemFilledContainer(LiquidStack liquid, boolean exact)
    {
        this(liquid, exact, null);
    }

    public ItemFilledContainer(LiquidStack liquid, ItemStack[] excludes)
    {
        this(liquid, false, excludes);
    }

    public ItemFilledContainer(LiquidStack liquid, boolean exact, ItemStack[] excludes)
    {
        this(liquid, null, liquid.amount, exact, excludes, null);
    }

    protected ItemFilledContainer(LiquidStack liquid, String name, int amount, boolean exact, ItemStack[] excludes, ItemStack defaultItem)
    {
        this.liquid = liquid != null ? liquid.copy() : null;
        this.name = name;
        this.amount = amount;
        this.exact = exact;
        if(excludes != null){
            this.excludes = new ItemStack[excludes.length];
            for(int i = 0; i < excludes.length; ++i)
            {
                this.excludes[i] = excludes[i].copy();
            }
        }else{
            this.excludes = null;
        }
        this.defaultItem = defaultItem!=null?defaultItem.copy():null;
    }

    @Override
    public boolean isItemEqual(ItemStack other)
    {
        if(other == null) return false;
        
        if (this.liquid == null && name != null)
        {
            this.liquid = LiquidDictionary.getLiquid(name, amount);
        }

        if (this.liquid == null)
        {
            return defaultItem != null && defaultItem.isItemEqual(other);
        }

        LiquidStack liquid = LiquidContainerRegistry.getLiquidForFilledItem(other);

        return liquid != null && (exact && liquid.amount == this.liquid.amount || !exact && liquid.amount >= this.liquid.amount);
    }

    @Override
    public ArrayList<ItemStack> getItems()
    {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        if (liquid == null && name != null)
        {
            liquid = LiquidDictionary.getLiquid(name, amount);
        }

        if (liquid == null)
        {
            if(defaultItem != null)
            {
                result.add(defaultItem);
            }
            return result;
        }

        for (LiquidContainerData data : LiquidContainerRegistry.getRegisteredLiquidContainerData())
        {
            if (data.stillLiquid.isLiquidEqual(liquid)
                    && (exact && data.stillLiquid.amount == liquid.amount || !exact && data.stillLiquid.amount >= liquid.amount))
            {
                if (excludes != null)
                {
                    boolean include = true;;
                    for (ItemStack item : excludes)
                    {
                        if (item.getItemDamage() == -1 && item.itemID == data.filled.itemID || item.isItemEqual(data.filled))
                        {
                            include = false;
                        }
                    }
                    if (include)
                    {
                        result.add(data.filled);
                    }
                }
                else
                {
                    result.add(data.filled);
                }
            }
        }

        return result;
    }

}
