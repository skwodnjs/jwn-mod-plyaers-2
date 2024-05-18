package net.jwn.mod.event;

import net.jwn.mod.Main;
import net.jwn.mod.command.AcceptCommand;
import net.jwn.mod.command.GetOpponentCommand;
import net.jwn.mod.command.ResetOpponentCommand;
import net.jwn.mod.command.SetOpponentCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SetOpponentCommand(event.getDispatcher());
        new GetOpponentCommand(event.getDispatcher());
        new ResetOpponentCommand(event.getDispatcher());
        new AcceptCommand(event.getDispatcher());
    }
}
