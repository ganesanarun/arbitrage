package com.flipper.financialcompliance

import com.flipper.models.Order

class ProfitCalculator {
    fun calculateProfit(order: Order, resalePrice: Double): Double {
        // This is a simplified profit calculation. A real implementation would be more complex.
        val fees = order.purchasePrice * 0.1 // 10% platform fees
        val taxes = (resalePrice - order.purchasePrice) * 0.18 // 18% GST on profit
        val shippingCost = 50.0 // Fixed shipping cost
        val totalCost = order.purchasePrice + fees + taxes + shippingCost
        return resalePrice - totalCost
    }
}
