package net.dries007.holoInventory.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.dries007.holoInventory.client.Renderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class HoloGlasses  extends Item implements IBauble
{

    public HoloGlasses()
    {
        super();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        setCreativeTab(CreativeTabs.tabTools);
    }

    public IIcon icon;
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister ir) {
        icon = ir.registerIcon("holoinventory:hologlasses");
    }
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        return icon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,List par3List) {
        par3List.add(new ItemStack(this,1,0));
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if(!par2World.isRemote) {
            InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(par3EntityPlayer);
            for(int i = 0; i < baubles.getSizeInventory(); i++)
                if(baubles.getStackInSlot(i) == null && baubles.isItemValidForSlot(i, par1ItemStack)) {
                    baubles.setInventorySlotContents(i, par1ItemStack.copy());
                    if(!par3EntityPlayer.capabilities.isCreativeMode){
                        par3EntityPlayer.inventory.setInventorySlotContents(par3EntityPlayer.inventory.currentItem, null);
                    }
                    onEquipped(par1ItemStack, par3EntityPlayer);
                    break;
                }
        }

        return par1ItemStack;
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        Renderer.enable = true;
    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        Renderer.enable = false;
    }

    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

}
