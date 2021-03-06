package me.arasple.mc.trmenu.modules.packets.impl

import io.izzel.taboolib.module.lite.SimpleReflection
import me.arasple.mc.trmenu.api.Extends.getMenuSession
import me.arasple.mc.trmenu.modules.packets.PacketsHandler
import net.minecraft.server.v1_12_R1.*
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

/**
 * @author Arasple
 * @date 2020/3/5 19:41
 */
class ImplPacketsHandler12 : PacketsHandler() {

    init {
        SimpleReflection.checkAndSave(
            PacketPlayOutOpenWindow::class.java,
            PacketPlayOutSetSlot::class.java
        )
    }

    override fun sendOpenWindow(player: Player, windowId: Int, inventoryType: InventoryType, size: Int, inventoryTitle: String) {
        sendPacket(
            player, PacketPlayOutOpenWindow(
                windowId,
                "minecraft:${inventoryType.name.toLowerCase()}",
                ChatComponentText(inventoryTitle),
                size
            )
        )
    }

    override fun sendCloseWindow(player: Player, windowId: Int) = sendPacket(player, PacketPlayOutCloseWindow(windowId))

    override fun sendOutSlot(player: Player, windowId: Int, slot: Int, itemStack: ItemStack) = sendPacket(player, PacketPlayOutSetSlot::class.java, PacketPlayOutSetSlot(), mapOf(Pair("a", windowId), Pair("b", slot), Pair("c", asNMSItem(itemStack))))

    override fun sendRemoveSlot(player: Player, windowId: Int, slot: Int) = sendPacket(player, PacketPlayOutSetSlot::class.java, PacketPlayOutSetSlot(), mapOf(Pair("a", windowId), Pair("b", slot), Pair("c", null)))

    override fun clearInventory(player: Player, startSlot: Int, windowId: Int) {
        val session = player.getMenuSession()
        val slots = session.menu?.getOccupiedSlots(player, session.page) ?: return

        for (i in (startSlot..startSlot + 35).filter { !slots.contains(it) }) {
            sendRemoveSlot(player, windowId, i)
        }
    }

    override fun asNMSItem(itemStack: ItemStack): Any = CraftItemStack.asNMSCopy(itemStack)

    override fun asBukkitItem(itemStack: Any): ItemStack? = CraftItemStack.asBukkitCopy(itemStack as net.minecraft.server.v1_12_R1.ItemStack?)

    override fun getClickTypeIndex(clickType: Any): Int = InventoryClickType.values().indexOf(clickType)

    override fun getPlayerTexture(player: Player): String = (player as CraftPlayer).profile.properties["textures"].iterator().let {
        return@let if (it.hasNext()) it.next().value else ""
    }

}