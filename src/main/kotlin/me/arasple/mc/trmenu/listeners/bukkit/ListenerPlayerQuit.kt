package me.arasple.mc.trmenu.listeners.bukkit

import io.izzel.taboolib.module.inject.TListener
import me.arasple.mc.trmenu.api.Extends.removeMenuSession
import me.arasple.mc.trmenu.api.Extends.resetCache
import me.arasple.mc.trmenu.display.Menu
import me.arasple.mc.trmenu.modules.inputer.InputCatcher.removeCatcher
import me.arasple.mc.trmenu.utils.Tasks
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author Arasple
 * @date 2020/3/19 11:52
 */
@TListener
class ListenerPlayerQuit : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onQuit(e: PlayerQuitEvent) {
        val player = e.player
        Tasks.task(true) {
            Menu.resetTaskings(player)
            player.removeCatcher()
            player.resetCache()
            player.removeMenuSession()
        }
    }

}