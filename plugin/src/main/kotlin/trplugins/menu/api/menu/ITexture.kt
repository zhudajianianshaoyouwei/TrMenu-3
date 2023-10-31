package trplugins.menu.api.menu

import org.bukkit.inventory.ItemStack
import trplugins.menu.module.display.MenuSession

/**
 * @author Arasple
 * @date 2021/1/24 11:39
 */
interface ITexture {

    fun generate(session: MenuSession): ItemStack

}