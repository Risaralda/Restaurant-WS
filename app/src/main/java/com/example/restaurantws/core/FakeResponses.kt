package com.example.restaurantws.core

const val privacyFakeResponse = "{\n" +
        "    \"respuesta\": \"OK\",\n" +
        "    \"datos\": {\n" +
        "        \"politicas\": \"Entiendo que, en consecuencia, el Restaurante es responsable por asegurar la concordancia entre los datos que le han sido suministrado y los que registra/divulga, pero no tiene ninguna responsabilidad por la calidad y veracidad de los datos reportados.\\nEs claro para mí que, por medio de esta consulta de datos, el Restaurante pone a mi alcance los mecanismos necesarios para que pueda ejercer el derecho de habeas data, de acuerdo con lo establecido en el artículo 15 de la Constitución Política de Colombia, la Ley 1581 de 2012 y de acuerdo con la doctrina de la Corte Constitucional de Colombia.\\nPuede consultar nuestra Política de Privacidad y Protección de Datos Personales\"\n" +
        "    }\n" +
        "}"

const val pedidosFakeResponse = "{\n" +
        "    \"respuesta\": \"OK\",\n" +
        "    \"pedidos\": [\n" +
        "        {\n" +
        "            \"id\": 1,\n" +
        "            \"id_cliente\": 1,\n" +
        "            \"json_pedido\": \"[{\\\"id_producto\\\": 17, \\\"cantidad\\\": 3, \\\"precio\\\": 16000}]\",\n" +
        "            \"total\": 36000,\n" +
        "            \"created_at\": \"2021-09-01T20:53:48.000000Z\"\n" +
        "        }\n" +
        "    ]\n" +
        "}"

