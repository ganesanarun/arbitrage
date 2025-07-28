package com.flipper.inventorylisting

import com.flipper.models.Product

class InventoryManager {
    private val inventory = mutableMapOf<String, Int>()

    fun addProduct(productId: String, quantity: Int) {
        inventory[productId] = inventory.getOrDefault(productId, 0) + quantity
    }

    fun removeProduct(productId: String, quantity: Int) {
        val currentQuantity = inventory.getOrDefault(productId, 0)
        if (currentQuantity >= quantity) {
            inventory[productId] = currentQuantity - quantity
        }
    }

    fun getInventory(): Map<String, Int> {
        return inventory
    }
}
