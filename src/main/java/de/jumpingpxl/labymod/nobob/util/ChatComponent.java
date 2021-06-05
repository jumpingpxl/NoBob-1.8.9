package de.jumpingpxl.labymod.nobob.util;

import com.google.common.collect.Lists;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import java.util.List;
import java.util.Objects;

public class ChatComponent {

	private final ChatComponent parent;
	private final ChatStyle chatStyle;
	private final String text;
	private final List<ChatComponent> siblings;

	private ChatComponent(ChatComponent parent, String text) {
		this.parent = parent;
		this.text = text;

		chatStyle = new ChatStyle();
		siblings = Lists.newArrayList();
	}

	public static ChatComponent create(String text) {
		return new ChatComponent(null, text);
	}

	public ChatComponent append(String text) {
		return append(new ChatComponent(this, text));
	}

	public ChatComponent setColor(Color color) {
		chatStyle.setColor(color.getColor());
		return this;
	}

	public ChatComponent setBold(boolean bold) {
		chatStyle.setBold(bold);
		return this;
	}

	public ChatComponent setHoverText(String text) {
		chatStyle.setChatHoverEvent(
				new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(text)));
		return this;
	}

	public ChatComponent setClickCommand(String command) {
		chatStyle.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		return this;
	}

	private ChatComponent append(ChatComponent chatComponent) {
		if (Objects.nonNull(parent)) {
			return parent.append(chatComponent);
		}

		siblings.add(chatComponent);
		return chatComponent;
	}

	public IChatComponent build() {
		if (Objects.nonNull(parent)) {
			return parent.build();
		}

		IChatComponent parentComponent = new ChatComponentText(text);
		parentComponent.setChatStyle(chatStyle);
		for (ChatComponent sibling : siblings) {
			IChatComponent siblingComponent = new ChatComponentText(sibling.text);
			siblingComponent.setChatStyle(sibling.chatStyle);
			parentComponent.appendSibling(siblingComponent);
		}

		return parentComponent;
	}

	public enum Color {
		BLACK,
		DARK_BLUE,
		DARK_GREEN,
		DARK_AQUA,
		DARK_RED,
		DARK_PURPLE,
		GOLD,
		GRAY,
		DARK_GRAY,
		BLUE,
		GREEN,
		AQUA,
		RED,
		LIGHT_PURPLE,
		YELLOW,
		WHITE,
		OBFUSCATED,
		BOLD,
		STRIKETHROUGH,
		UNDERLINE,
		ITALIC,
		RESET;

		public EnumChatFormatting getColor() {
			return EnumChatFormatting.getValueByName(name());
		}
	}
}
