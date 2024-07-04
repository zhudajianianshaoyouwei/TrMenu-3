package trplugins.menu.module.internal.hook.impl

import org.bukkit.entity.Player
import trplugins.menu.api.receptacle.provider.PlatformProvider

import trplugins.menu.module.internal.hook.HookAbstract

/**
 * @author Arasple
 * @date 2021/2/4 20:12
 */
class HookFloodgate : HookAbstract() {

    fun isBedrockPlayer(player: Player): Boolean {
       return if (checkHooked()) PlatformProvider.isBedrockPlayer(player) else false
    }

    override fun getPluginName(): String {
        return "floodgate"
    }

}