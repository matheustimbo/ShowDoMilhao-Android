package br.lucas.appshowdomilho.model

data class User(
    var username: String,
    var bestShot: Int,
    var lastShot: Int
) {
    constructor() : this("", -1, -1)
}