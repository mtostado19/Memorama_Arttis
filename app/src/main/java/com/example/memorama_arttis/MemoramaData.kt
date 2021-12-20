package com.example.memorama_arttis

class MemoramaData {
    var icons: Int ? = 0
    var IdPar: Int ? = 0
    var TrueIcon: Int ? = 0
    var unique: Int = 0
    var currentImage: Int?
    var found: Boolean

    constructor(icons: Int?, IdPar: Int?, TrueIcon: Int?, unique: Int, currentImage: Int?, found: Boolean) {
        this.icons = icons
        this.IdPar = IdPar
        this.TrueIcon = TrueIcon
        this.unique = unique
        this.currentImage = currentImage
        this.found = found
    }
}