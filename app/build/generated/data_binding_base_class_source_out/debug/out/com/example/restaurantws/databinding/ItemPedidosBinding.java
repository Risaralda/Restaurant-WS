// Generated by view binder compiler. Do not edit!
package com.example.restaurantws.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.restaurantws.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemPedidosBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imgDeletePedido;

  @NonNull
  public final ImageView imgSendPedido;

  @NonNull
  public final TextView pedidoDesc;

  @NonNull
  public final TextView pedidoPrice;

  @NonNull
  public final TextView pedidoTitle;

  private ItemPedidosBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imgDeletePedido,
      @NonNull ImageView imgSendPedido, @NonNull TextView pedidoDesc, @NonNull TextView pedidoPrice,
      @NonNull TextView pedidoTitle) {
    this.rootView = rootView;
    this.imgDeletePedido = imgDeletePedido;
    this.imgSendPedido = imgSendPedido;
    this.pedidoDesc = pedidoDesc;
    this.pedidoPrice = pedidoPrice;
    this.pedidoTitle = pedidoTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemPedidosBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemPedidosBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_pedidos, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemPedidosBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imgDeletePedido;
      ImageView imgDeletePedido = ViewBindings.findChildViewById(rootView, id);
      if (imgDeletePedido == null) {
        break missingId;
      }

      id = R.id.imgSendPedido;
      ImageView imgSendPedido = ViewBindings.findChildViewById(rootView, id);
      if (imgSendPedido == null) {
        break missingId;
      }

      id = R.id.pedidoDesc;
      TextView pedidoDesc = ViewBindings.findChildViewById(rootView, id);
      if (pedidoDesc == null) {
        break missingId;
      }

      id = R.id.pedidoPrice;
      TextView pedidoPrice = ViewBindings.findChildViewById(rootView, id);
      if (pedidoPrice == null) {
        break missingId;
      }

      id = R.id.pedidoTitle;
      TextView pedidoTitle = ViewBindings.findChildViewById(rootView, id);
      if (pedidoTitle == null) {
        break missingId;
      }

      return new ItemPedidosBinding((ConstraintLayout) rootView, imgDeletePedido, imgSendPedido,
          pedidoDesc, pedidoPrice, pedidoTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
