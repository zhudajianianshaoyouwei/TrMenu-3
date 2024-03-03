package trplugins.menu.api.receptacle

import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

/**
 * @author Arasple
 * @date 2020/12/5 22:01
 */
enum class ReceptacleClickType(private val mode: Int, private val button: Int, private val type: ClickType? = null, private val action: Any? = null) {

    ALL(-1, -1),

    LEFT(0, 0, ClickType.LEFT),

    RIGHT(0, 1, ClickType.RIGHT),

    SHIFT_LEFT(1, 0, ClickType.SHIFT_LEFT),

    SHIFT_RIGHT(1, 1, ClickType.SHIFT_RIGHT),

    OFFHAND(2, 40, ClickType.SWAP_OFFHAND),

    NUMBER_KEY(2, -1, ClickType.NUMBER_KEY, -1),

    NUMBER_KEY_1(2, 0, ClickType.NUMBER_KEY, 0),

    NUMBER_KEY_2(2, 1, ClickType.NUMBER_KEY, 1),

    NUMBER_KEY_3(2, 2, ClickType.NUMBER_KEY, 2),

    NUMBER_KEY_4(2, 3, ClickType.NUMBER_KEY, 3),

    NUMBER_KEY_5(2, 4, ClickType.NUMBER_KEY, 4),

    NUMBER_KEY_6(2, 5, ClickType.NUMBER_KEY, 5),

    NUMBER_KEY_7(2, 6, ClickType.NUMBER_KEY, 6),

    NUMBER_KEY_8(2, 7, ClickType.NUMBER_KEY, 7),

    NUMBER_KEY_9(2, 8, ClickType.NUMBER_KEY, 8),

    MIDDLE(3, 2, ClickType.MIDDLE),

    // clicked Item will be empty
    DROP(4, 0, ClickType.DROP),

    CONTROL_DROP(4, 1, ClickType.CONTROL_DROP),

    ABROAD_LEFT_EMPTY(4, 0, ClickType.DROP, InventoryAction.DROP_ONE_SLOT),

    ABROAD_RIGHT_EMPTY(4, 1, ClickType.CONTROL_DROP, InventoryAction.DROP_ALL_SLOT),

    ABROAD_LEFT_ITEM(0, 0, ClickType.LEFT, InventoryAction.DROP_ALL_CURSOR),

    ABROAD_RIGHT_ITEM(0, 1, ClickType.RIGHT, InventoryAction.DROP_ONE_CURSOR),

    LEFT_MOUSE_DRAG_ADD(5, 1),

    RIGHT_MOUSE_DRAG_ADD(5, 5),

    MIDDLE_MOUSE_DRAG_ADD(5, 9),

    DOUBLE_CLICK(6, 0, ClickType.DOUBLE_CLICK),

    UNKNOWN(-1, -1);

    fun toBukkitType(): ClickType {
        return this.type ?: ClickType.UNKNOWN
    }

    fun equals(mode: Int, button: Int): Boolean {
        return this.mode == mode && this.button == button
    }

    fun equals(type: ClickType?, action: Any?): Boolean {
        return this.type == type && this.action == action
    }

    fun isRightClick(): Boolean {
        return this == RIGHT || this == SHIFT_RIGHT
    }

    fun isLeftClick(): Boolean {
        return this == LEFT || this == SHIFT_LEFT || this == DOUBLE_CLICK
    }

    private fun isShiftClick(): Boolean {
        return this == SHIFT_LEFT || this == SHIFT_RIGHT
    }

    private fun isKeyboardClick(): Boolean {
        return isNumberKeyClick() || this == DROP || this == CONTROL_DROP
    }

    fun isNumberKeyClick(): Boolean {
        return this.type == ClickType.NUMBER_KEY || this == OFFHAND
    }

    private fun isDoubleClick(): Boolean {
        return this == DOUBLE_CLICK
    }

    private fun isCreativeAction(): Boolean {
        return this == MIDDLE || this == MIDDLE_MOUSE_DRAG_ADD
    }

    fun isItemMoveable(): Boolean {
        return isKeyboardClick() || isShiftClick() || isCreativeAction() || isDoubleClick()
    }

    companion object {

        private val modes = arrayOf("PICKUP", "QUICK_MOVE", "SWAP", "CLONE", "THROW", "QUICK_CRAFT", "PICKUP_ALL")
        private val actions = setOf(InventoryAction.DROP_ALL_CURSOR, InventoryAction.DROP_ONE_CURSOR, InventoryAction.DROP_ONE_SLOT, InventoryAction.DROP_ALL_SLOT)

        private fun matchesFirst(string: String): ReceptacleClickType {
            return values().find { it.name.equals(string, true) } ?: ALL
        }

        fun matches(string: String): Set<ReceptacleClickType> {
            return string.split(",", ";").map { matchesFirst(it) }.toSet()
        }

        fun from(mode: String, button: Int, slot: Int = -1): ReceptacleClickType? {
            return from(modes.indexOf(mode), button, slot)
        }

        fun from(mode: Int, button: Int, slot: Int = -1): ReceptacleClickType? {
            if (slot == -999) {
                return when {
                    LEFT.equals(mode, button) -> ABROAD_LEFT_ITEM
                    RIGHT.equals(mode, button) -> ABROAD_RIGHT_ITEM
                    ABROAD_LEFT_EMPTY.equals(mode, button) -> ABROAD_LEFT_EMPTY
                    ABROAD_RIGHT_EMPTY.equals(mode, button) -> ABROAD_RIGHT_EMPTY
                    else -> UNKNOWN
                }
            }
            return values().find { it.equals(mode, button) }
        }

        fun from(type: ClickType?, action: InventoryAction?, slot: Int = -1): ReceptacleClickType? {
            val act: Any? = if (type == ClickType.NUMBER_KEY) {
                slot
            } else if (actions.contains(action)) {
                action
            } else {
                null
            }
            return values().find { it.equals(type, act) }
        }
    }
}