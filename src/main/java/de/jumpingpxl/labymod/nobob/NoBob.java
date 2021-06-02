package de.jumpingpxl.labymod.nobob;

import de.jumpingpxl.labymod.nobob.listener.LoginServerListener;
import de.jumpingpxl.labymod.nobob.listener.MessageSendListener;
import de.jumpingpxl.labymod.nobob.listener.TickListener;
import de.jumpingpxl.labymod.nobob.util.Settings;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

import java.util.List;

/**
 * @author Nico (JumpingPxl) Middendorf
 * @date 02.06.2021
 * @project LabyMod-Addon: NoBob-1.8.9
 */

public class NoBob extends LabyModAddon {

	public static final String VERSION = "2";

	private Settings settings;

	@Override
	public void onEnable() {
		settings = new Settings(this);

		getApi().getEventManager().registerOnJoin(new LoginServerListener(this));
		getApi().getEventManager().register(new MessageSendListener(settings));
		getApi().registerForgeListener(new TickListener(settings));

		notifyAboutDeactivatedBobbing();
	}

	@Override
	public void loadConfig() {
		settings.loadConfig();
	}

	@Override
	protected void fillSettings(List<SettingsElement> settingsElements) {
		settings.fillSettings(settingsElements);
	}

	public void notifyAboutDeactivatedBobbing() {
		if (!settings.isEnabled()) {
			return;
		}

		Minecraft minecraft = Minecraft.getMinecraft();
		if (!minecraft.gameSettings.viewBobbing) {
			IChatComponent textComponent = new ChatComponentText(
					"§7[§eNoBob§7] §4§lIMPORTANT: §cIn order for NoBob to work, the following setting has to"
							+ " be enabled: ");
			IChatComponent settingComponent = new ChatComponentText(
					"§cOptions -> Video Settings -> View Bobbing §l[CLICK]");

			ChatStyle style = settingComponent.getChatStyle();
			style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					new ChatComponentText("§cClick to enable bobbing")));
			style.setChatClickEvent(
					new ClickEvent(ClickEvent.Action.RUN_COMMAND, "+nobob disablebobbing"));
			settingComponent.setChatStyle(style);
			textComponent.appendSibling(settingComponent);
			minecraft.ingameGUI.getChatGUI().printChatMessage(textComponent);
		}
	}
}
