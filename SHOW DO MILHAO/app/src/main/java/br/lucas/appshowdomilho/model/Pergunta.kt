package br.lucas.appshowdomilho.model

data class Pergunta(
    var Alternativas: List<String>,
    var Resposta: Int,
    var pergunta: String
) {
    constructor() : this(listOf(), -1, "")
}