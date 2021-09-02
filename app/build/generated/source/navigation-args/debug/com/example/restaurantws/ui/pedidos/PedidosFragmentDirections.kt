package com.example.restaurantws.ui.pedidos

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavDirections
import com.example.restaurantws.NavGraphDirections
import com.example.restaurantws.R
import com.example.restaurantws.`data`.main.models.pedidos.Pedido
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.String
import kotlin.Suppress

public class PedidosFragmentDirections private constructor() {
  private data class ActionPedidosFragmentToPedidoDetailFragment(
    public val pedido: Pedido,
    public val title: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_pedidosFragment_to_pedidoDetailFragment

    @Suppress("CAST_NEVER_SUCCEEDS")
    public override fun getArguments(): Bundle {
      val result = Bundle()
      if (Parcelable::class.java.isAssignableFrom(Pedido::class.java)) {
        result.putParcelable("pedido", this.pedido as Parcelable)
      } else if (Serializable::class.java.isAssignableFrom(Pedido::class.java)) {
        result.putSerializable("pedido", this.pedido as Serializable)
      } else {
        throw UnsupportedOperationException(Pedido::class.java.name +
            " must implement Parcelable or Serializable or must be an Enum.")
      }
      result.putString("title", this.title)
      return result
    }
  }

  public companion object {
    public fun actionPedidosFragmentToPedidoDetailFragment(pedido: Pedido, title: String):
        NavDirections = ActionPedidosFragmentToPedidoDetailFragment(pedido, title)

    public fun actionGlobalPedidosFragment(): NavDirections =
        NavGraphDirections.actionGlobalPedidosFragment()
  }
}
