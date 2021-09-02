package com.example.restaurantws.core.api

import com.example.restaurantws.core.CurrentUser
import com.example.restaurantws.core.api.models.ApiResponse
import com.example.restaurantws.data.auth.models.LoginRequest
import com.example.restaurantws.data.auth.models.LoginResponse
import com.example.restaurantws.data.auth.models.PoliciesResponse
import com.example.restaurantws.data.auth.models.User
import com.example.restaurantws.data.main.models.categorias.CategoryResponse
import com.example.restaurantws.data.main.models.especialidad.SpecialityResponse
import com.example.restaurantws.data.main.models.pedidos.Pedido
import com.example.restaurantws.data.main.models.pedidos.PedidosResponse
import com.example.restaurantws.data.main.models.products.ProductsResponse
import com.google.gson.Gson
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class ApiService {
    private val client = HttpClient(Android) {
        install(JsonFeature)
        {
            serializer = GsonSerializer()
        }

        defaultRequest {
            host = "wsc.fabricasoftware.co/api"
            url {
                protocol = URLProtocol.HTTPS
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }
        }
    }

    private suspend inline fun <reified T> doCall(apiServiceCallback: ApiServiceCallback): T {
        val response = apiServiceCallback.execute()
        val json = String(response.readBytes())

        val apiResponse = Gson().fromJson(json, ApiResponse::class.java)

        if (apiResponse.respuesta != "OK") throw Exception(apiResponse.mensaje)

        return Gson().fromJson(json, T::class.java)
    }

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/clientes") {
                    parameter("correo", loginRequest.email)
                    parameter("contrasena", loginRequest.pwd)
                }
            }
        }

        return doCall(callback)
    }

    suspend fun signUp(user: User): ApiResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.post("/clientes") {
                    body = user
                }
            }
        }

        return doCall(callback)
    }

    suspend fun getPrivacyPolicies(): PoliciesResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/politicas?ver")
            }
        }

        return doCall(callback)
    }

    suspend fun getSpeciality(): SpecialityResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/especialidad")
            }
        }

        return doCall(callback)
    }

    suspend fun getCategorias(): CategoryResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/categorias")
            }
        }

        return doCall(callback)
    }


    suspend fun getProductosByCategory(categoryId: Int): ProductsResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/categorias/$categoryId")
            }
        }

        return doCall(callback)
    }


    suspend fun getPedidos(): PedidosResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/pedidos?cliente=${CurrentUser.idCliente}&token=${CurrentUser.token}")
            }
        }

        return doCall(callback)
    }

    suspend fun enviarPedido(pedido: Pedido): ApiResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.post("/pedidos") {
                    body = pedido
                }
            }
        }

        return doCall(callback)
    }

    suspend fun getAllProducts(): ProductsResponse {
        val callback = object : ApiServiceCallback {
            override suspend fun execute(): HttpResponse {
                return client.get("/productos")
            }
        }

        return doCall(callback)
    }

}