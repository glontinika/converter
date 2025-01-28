package converter

import kotlin.system.exitProcess

fun main() {
    startConvertor()
}

fun startConvertor(){
    val (num, um) = askForInput()
    val unitOfMeasure = getUnitOfMeasure(num, um)
    printOutput (unitOfMeasure, num)
}

enum class UnitOfMeasure(val single: String, val plural: String, val conv: Double, val keywords: List<String>){
    Meter("meter", "meters", 1.0, listOf("m","meter","meters")),
    Kilometer("kilometer", "kilometers", 1.0, listOf("km","kilometer","kilometers")),
    Centimeter("centimeter", "centimeters", 0.01, listOf("cm","centimeter","centimeters")),
    Millimeter("millimeter", "millimeters", 0.001, listOf("mm","millimeter","millimeters")),
    Mile("mile", "miles", 1609.35, listOf("mi","mile","miles")),
    Yard("yard", "yards", 0.9144, listOf("yd","yard","yards")),
    Foot("foot", "feet", 0.3048, listOf("ft","foot","feet")),
    Inch("inch", "inches", 0.0254, listOf("in","inch","inches"))
}

fun askForInput(): Pair<Double, String> {
    println("Enter a number and a measure: ")
    val input = readln()
    val (nu, um) = input.split(" ")
    val num = nu.toDouble()
    return Pair(num, um)
}

fun getUnitOfMeasure(num: Double, um: String): UnitOfMeasure {
    lateinit var theOutput: UnitOfMeasure
    for (unit in UnitOfMeasure.entries){
        if (um in unit.keywords) theOutput = unit
    }
    return theOutput // aq dasamatebelia wrong input-is erori
}

fun printOutput(unitOfMeasure: UnitOfMeasure, num: Double){
    val unName = if (num != 1.0) unitOfMeasure.plural else unitOfMeasure.single
    val outputNum = num * unitOfMeasure.conv
    println("$num $unName is $outputNum meter${if (outputNum!=1.0) "s" else ""}")
}