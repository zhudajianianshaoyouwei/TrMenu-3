package trplugins.menu.api.receptacle

import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryAction

/**
 * @author Arasple
 * @date 2020/12/5 22:01
 */
enum class ReceptacleClickType(
    private val mode: Int,
    private val button: Int,
    private val typeName: String? = null,
    private val action: Any? = null
) {

    /*
    2024/5/25 12:57 注:
    暂时解决下1.12.2无SWAP_OFFHAND的报错，
    我也是不想说了, 也懒得改了。现在用1.12.2用OFFHAND估计还是报错
    这个枚举类本就不该和ClickType粘上点关系。
    https://github.com/Dreeam-qwq/TrMenu/commit/4305477a1fb1f8fa4b59c741a3d035b2e4c8b4fb#diff-2f7d0736d6aa127ec345719795ff496cb5a439df73a835cefee5878e8d3c05d3
    懒得动了, 就这样吧。
     */

    ALL(-1, -1),

    LEFT(0, 0, ClickType.LEFT.name),

    RIGHT(0, 1, ClickType.RIGHT.name),

    SHIFT_LEFT(1, 0, ClickType.SHIFT_LEFT.name),

    SHIFT_RIGHT(1, 1, ClickType.SHIFT_RIGHT.name),

    OFFHAND(2, 40, "SWAP_OFFHAND"),

    NUMBER_KEY(2, -1, ClickType.NUMBER_KEY.name, -1),

    NUMBER_KEY_1(2, 0, ClickType.NUMBER_KEY.name, 0),

    NUMBER_KEY_2(2, 1, ClickType.NUMBER_KEY.name, 1),

    NUMBER_KEY_3(2, 2, ClickType.NUMBER_KEY.name, 2),

    NUMBER_KEY_4(2, 3, ClickType.NUMBER_KEY.name, 3),

    NUMBER_KEY_5(2, 4, ClickType.NUMBER_KEY.name, 4),

    NUMBER_KEY_6(2, 5, ClickType.NUMBER_KEY.name, 5),

    NUMBER_KEY_7(2, 6, ClickType.NUMBER_KEY.name, 6),

    NUMBER_KEY_8(2, 7, ClickType.NUMBER_KEY.name, 7),

    NUMBER_KEY_9(2, 8, ClickType.NUMBER_KEY.name, 8),

    MIDDLE(3, 2, ClickType.MIDDLE.name),

    // clicked Item will be empty
    DROP(4, 0, ClickType.DROP.name),

    CONTROL_DROP(4, 1, ClickType.CONTROL_DROP.name),

    ABROAD_LEFT_EMPTY(4, 0, ClickType.DROP.name, InventoryAction.DROP_ONE_SLOT),

    ABROAD_RIGHT_EMPTY(4, 1, ClickType.CONTROL_DROP.name, InventoryAction.DROP_ALL_SLOT),

    ABROAD_LEFT_ITEM(0, 0, ClickType.LEFT.name, InventoryAction.DROP_ALL_CURSOR),

    ABROAD_RIGHT_ITEM(0, 1, ClickType.RIGHT.name, InventoryAction.DROP_ONE_CURSOR),

    LEFT_MOUSE_DRAG_ADD(5, 1),

    RIGHT_MOUSE_DRAG_ADD(5, 5),

    MIDDLE_MOUSE_DRAG_ADD(5, 9),

    DOUBLE_CLICK(6, 0, ClickType.DOUBLE_CLICK.name),

    UNKNOWN(-1, -1);

    fun toBukkitType(): ClickType {
        return typeName?.let { ClickType.valueOf(it) } ?: ClickType.UNKNOWN
    }

    fun equals(mode: Int, button: Int): Boolean {
        return this.mode == mode && this.button == button
    }

    fun equals(type: ClickType?, action: Any?): Boolean {
        return toBukkitType() == type && this.action == action
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
        return toBukkitType() == ClickType.NUMBER_KEY || this == OFFHAND
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
        private val actions = setOf(
            InventoryAction.DROP_ALL_CURSOR,
            InventoryAction.DROP_ONE_CURSOR,
            InventoryAction.DROP_ONE_SLOT,
            InventoryAction.DROP_ALL_SLOT
        )

        private fun matchesFirst(string: String): ReceptacleClickType {
            return entries.find { it.name.equals(string, true) } ?: ALL
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
            return entries.find { it.equals(mode, button) }
        }

        fun from(type: ClickType?, action: InventoryAction?, slot: Int = -1): ReceptacleClickType? {
            val act: Any? = if (type == ClickType.NUMBER_KEY) {
                slot
            } else if (actions.contains(action)) {
                action
            } else {
                null
            }
            return entries.find { it.equals(type, act) }
        }

    }
}