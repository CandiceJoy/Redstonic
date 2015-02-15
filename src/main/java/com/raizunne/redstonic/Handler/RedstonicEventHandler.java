package com.raizunne.redstonic.Handler;

import com.raizunne.redstonic.Client.BlockLooking;
import com.raizunne.redstonic.Item.RedstonicDrill;
import com.raizunne.redstonic.Proxy.ClientProxy;
import com.raizunne.redstonic.Redstonic;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by Raizunne as a part of Redstonic
 * on 08/02/2015, 01:34 PM.
 */
public class RedstonicEventHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void RenderGameOverlayEvent(RenderGameOverlayEvent event){
        BlockLooking.init(event);
    }

    @SubscribeEvent
    public void BlazerBlaze(BlockEvent.HarvestDropsEvent event){
        if (event.harvester != null) {
            if (event.harvester.getHeldItem() != null && event.harvester.getHeldItem().getItem() instanceof RedstonicDrill && event.harvester.getHeldItem().stackTagCompound.getInteger("head")==6 &&
                    !(event.block instanceof BlockLog)) {
                ItemStack stack = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(event.block, 1, event.blockMetadata));
                if (stack != null) {
                    event.drops.clear();
                    event.drops.add(stack.copy());
                }
            }
        }
    }

    @SubscribeEvent
    public void LoginEvent(EntityJoinWorldEvent event) {
        if (event.world.isRemote && event.entity == Minecraft.getMinecraft().thePlayer) {
            try {
                ClientProxy.checkVersion();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!ClientProxy.version.equals(Redstonic.VERSION)) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Redstonic " + EnumChatFormatting.WHITE + "Outdated! New version is " + EnumChatFormatting.YELLOW + ClientProxy.version));
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Changes: " + ));
            }else {
                System.out.println("[REDSTONIC] Using latest version.");
            }
        }
    }
}
