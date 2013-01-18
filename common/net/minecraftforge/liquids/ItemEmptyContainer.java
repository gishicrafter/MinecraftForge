package net.minecraftforge.liquids;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;

public class ItemEmptyContainer {

    protected LiquidStack filter;
    protected String name;
    protected int amount;
    protected boolean exact;
    protected ItemStack[] excludes;

    public ItemEmptyContainer()
    {
        this(false);
    }

    public ItemEmptyContainer(boolean exact)
    {
        this(null, exact);
    }

    public ItemEmptyContainer(String name, int amount)
    {
        this(name, amount, false);
    }

    public ItemEmptyContainer(String name, int amount, boolean exact)
    {
        this(name, amount, exact, null);
    }

    public ItemEmptyContainer(String name, int amount, ItemStack[] excludes)
    {
        this(name, amount, false, excludes);
    }

    public ItemEmptyContainer(String name, int amount, boolean exact, ItemStack[] excludes)
    {
        this(null, name, amount, exact, excludes);
    }

    public ItemEmptyContainer(LiquidStack filter)
    {
        this(filter, false);
    }

    public ItemEmptyContainer(LiquidStack filter, boolean exact)
    {
        this(filter, exact, null);
    }

    public ItemEmptyContainer(LiquidStack filter, ItemStack[] excludes)
    {
        this(filter, false, excludes);
    }

    public ItemEmptyContainer(LiquidStack filter, boolean exact, ItemStack[] excludes)
    {
        this(filter, null, filter != null ? filter.amount : 0, exact, excludes);
    }

    protected ItemEmptyContainer(LiquidStack filter, String name, int amount, boolean exact, ItemStack[] excludes)
    {
        this.filter = (filter != null ? filter.copy() : null);
        this.name = name;
        this.amount = amount;
        this.exact = exact;
        this.excludes = excludes;
    }

    public ItemStack getFilledContainer(LiquidStack liquid, ItemStack container)
    {
        if (filter == null && name != null)
        {
            filter = LiquidDictionary.getLiquid(name, amount);
        }

        if (filter != null || name != null)
        {
            if (filter == null)
            {
                return null;
            }
            else if (!filter.isLiquidEqual(liquid) || !((exact && filter.amount == liquid.amount) || (!exact && filter.amount <= liquid.amount)))
            {
                return null;
            }
        }

        ItemStack filled = LiquidContainerRegistry.fillLiquidContainer(liquid, container);

        if (filled != null)
        {
            if (!exact)
            {
                return filled;
            }
            else
            {
                LiquidStack filledLiquid = LiquidContainerRegistry.getLiquidForFilledItem(filled);
                return filledLiquid.amount == liquid.amount ? filled : null;
            }
        }

        return null;
    }

    public ArrayList<ItemStack> getFilledContainers(LiquidStack liquid)
    {
        ArrayList<ItemStack> result = new ArrayList<ItemStack>();

        if (filter == null && name != null)
        {
            filter = LiquidDictionary.getLiquid(name, amount);
        }

        if (filter != null || name != null)
        {
            if (filter == null)
            {
                return result;
            }
            else if (!filter.isLiquidEqual(liquid) || !((exact && filter.amount == liquid.amount) || (!exact && filter.amount <= liquid.amount)))
            {
                return result;
            }
        }

        for (LiquidContainerData data : LiquidContainerRegistry.getRegisteredLiquidContainerData())
        {
            if (data.stillLiquid.isLiquidEqual(liquid)
                    && ((exact && data.stillLiquid.amount == liquid.amount) || (!exact && data.stillLiquid.amount <= liquid.amount)))
            {
                if (excludes != null)
                {
                    boolean include = true;;
                    for (ItemStack item : excludes)
                    {
                        if ((item.getItemDamage() == -1 && item.itemID == data.container.itemID) || item.isItemEqual(data.container))
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

    public ArrayList<LiquidContainerData> getLiquidContainerData(LiquidStack liquid)
    {
        ArrayList<LiquidContainerData> result = new ArrayList<LiquidContainerData>();

        if (filter == null && name != null)
        {
            filter = LiquidDictionary.getLiquid(name, amount);
        }

        if (filter != null || name != null)
        {
            if (filter == null)
            {
                return result;
            }
            else if (!filter.isLiquidEqual(liquid) || !((exact && filter.amount == liquid.amount) || (!exact && filter.amount <= liquid.amount)))
            {
                return result;
            }
        }

        for (LiquidContainerData data : LiquidContainerRegistry.getRegisteredLiquidContainerData())
        {
            if (data.stillLiquid.isLiquidEqual(liquid)
                    && ((exact && data.stillLiquid.amount == liquid.amount) || (!exact && data.stillLiquid.amount <= liquid.amount)))
            {
                if (excludes != null)
                {
                    boolean include = true;;
                    for (ItemStack item : excludes)
                    {
                        if ((item.getItemDamage() == -1 && item.itemID == data.container.itemID) || item.isItemEqual(data.container))
                        {
                            include = false;
                        }
                    }
                    if (include) result.add(data);
                }
                else
                {
                    result.add(data);
                }
            }
        }

        return result;
    }
}
