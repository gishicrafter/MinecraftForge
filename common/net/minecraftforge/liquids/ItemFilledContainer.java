package net.minecraftforge.liquids;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemFilledContainer {

    protected LiquidStack liquid;
    protected String name;
    protected int amount;
    protected boolean exact;
    protected ItemStack[] excludes;

    public ItemFilledContainer(String name, int amount)
    {
        this(name, amount, false);
    }

    public ItemFilledContainer(String name, int amount, boolean exact)
    {
        this(name, amount, exact, null);
    }

    public ItemFilledContainer(String name, int amount, ItemStack[] excludes)
    {
        this(name, amount, false, excludes);
    }

    public ItemFilledContainer(String name, int amount, boolean exact, ItemStack[] excludes)
    {
        this(null, name, amount, exact, excludes);
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
        this(liquid, null, liquid.amount, exact, excludes);
    }

    protected ItemFilledContainer(LiquidStack liquid, String name, int amount, boolean exact, ItemStack[] excludes)
    {
        this.liquid = (liquid != null ? liquid.copy() : null);
        this.name = name;
        this.amount = amount;
        this.exact = exact;
        this.excludes = excludes;
    }

    public boolean isItemEqual(ItemStack other)
    {
        if (this.liquid == null && name != null)
        {
            this.liquid = LiquidDictionary.getLiquid(name, amount);
        }

        if (this.liquid == null)
        {
            return false;
        }

        LiquidStack liquid = LiquidContainerRegistry.getLiquidForFilledItem(other);

        return liquid != null && ((exact && liquid.amount == this.liquid.amount) || (!exact && liquid.amount >= this.liquid.amount));
    }

    public ArrayList<ItemStack> getFilledContainers()
    {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        if (liquid == null && name != null)
        {
            liquid = LiquidDictionary.getLiquid(name, amount);
        }

        if (liquid == null)
        {
            return result;
        }

        for (LiquidContainerData data : LiquidContainerRegistry.getRegisteredLiquidContainerData())
        {
            if (data.stillLiquid.isLiquidEqual(liquid)
                    && ((exact && data.stillLiquid.amount == liquid.amount) || (!exact && data.stillLiquid.amount >= liquid.amount)))
            {
                if (excludes != null)
                {
                    boolean include = true;;
                    for (ItemStack item : excludes)
                    {
                        if ((item.getItemDamage() == -1 && item.itemID == data.filled.itemID) || item.isItemEqual(data.filled))
                        {
                            include = false;
                        }
                    }
                    if (include) result.add(data.filled);
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
