package net.mivina.firstmod;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.mivina.firstmod.block.ModBlocks;
import net.mivina.firstmod.item.ModItems;
import org.slf4j.Logger;


@Mod(FirstMod.MOD_ID)// The value here should match an entry in the META-INF/mods.toml file
public class FirstMod {
    public static final String MOD_ID = "firstmod"; // Define mod id in a common place for everything to reference

    public static final Logger LOGGER = LogUtils.getLogger();// Directly reference a slf4j logger

    public FirstMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);// Register the commonSetup method for modloading
        MinecraftForge.EVENT_BUS.register(this);// Register ourselves for server and other game events we are interested in

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);


        modEventBus.addListener(this::addCreative);// Register the item to a creative tab
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)// Add the example block item to the building blocks tab
    {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.TOMATO);
            event.accept(ModItems.ROTTEN_TOMATO);
        }
        if (event.getTabKey()==CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.BASKET_OF_TOMATOES);
        }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) // You can use SubscribeEvent and let the Event Bus discover methods to call
    {

    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT) // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
