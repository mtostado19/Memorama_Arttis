package com.example.memorama_arttis

data class Juego(val host: String?=null, val guest: String?=null, val difficulty: Int?, var currentPlayer: String?= "guest", var hostScore: Int = 0, var guestScore: Int = 0)