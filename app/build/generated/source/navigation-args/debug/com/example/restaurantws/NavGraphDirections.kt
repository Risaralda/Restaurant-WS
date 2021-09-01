package com.example.restaurantws

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections

public class NavGraphDirections private constructor() {
  public companion object {
    public fun actionGlobalPedidosFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_global_pedidosFragment)
  }
}
