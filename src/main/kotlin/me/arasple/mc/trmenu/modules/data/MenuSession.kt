package me.arasple.mc.trmenu.modules.data

import me.arasple.mc.trmenu.display.Menu
import me.arasple.mc.trmenu.display.menu.MenuLayout
import me.arasple.mc.trmenu.utils.Msger
import org.bukkit.entity.Player

/**
 * @author Arasple
 * @date 2020/7/6 21:56
 */
class MenuSession(var menu: Menu?, var layout: MenuLayout.Layout?, var page: Int, var fromLayout: MenuLayout.Layout?, var id: Int = 0) {

    constructor() : this(null, null, 0, null)

    fun isNull(): Boolean {
        return menu == null || layout == null
    }

    fun set(menu: Menu?, layout: MenuLayout.Layout?, page: Int) {
        Msger.debug("SESSION", Sessions.getPlayer(this)?.name ?: "null", menu?.id ?: "null", page)

        this.menu = menu
        this.layout = layout
        this.page = page
        this.id++
    }

    fun safeClose(player: Player) {
        if (!isNull()) {
            menu!!.viewers.remove(player)
            set(null, null, -1)
        }
    }

    fun close(player: Player) {
        if (!isNull()) {
            menu!!.close(player, page)
        }
    }

    fun isDifferent(id: Int): Boolean {
        return id != this.id
    }

}