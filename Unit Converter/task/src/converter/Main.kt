package converter

import kotlin.system.exitProcess

fun main() {
    val convertor = Convertor()
    convertor.start()
}

enum class UnitOfMeasure(val single: String, val plural: String, val conv: Double, val keywords: List<String>) {
    Meter("meter", "meters", 1.0, listOf("m", "meter", "meters")),
    Kilometer("kilometer", "kilometers", 1000.0, listOf("km", "kilometer", "kilometers")),
    Centimeter("centimeter", "centimeters", 0.01, listOf("cm", "centimeter", "centimeters")),
    Millimeter("millimeter", "millimeters", 0.001, listOf("mm", "millimeter", "millimeters")),
    Mile("mile", "miles", 1609.35, listOf("mi", "mile", "miles")),
    Yard("yard", "yards", 0.9144, listOf("yd", "yard", "yards")),
    Foot("foot", "feet", 0.3048, listOf("ft", "foot", "feet")),
    Inch("inch", "inches", 0.0254, listOf("in", "inch", "inches"))
}

class Convertor {
    private lateinit var umInput: String
    private var numberDouble: Double = 0.0
    lateinit var unitOfMeasure: UnitOfMeasure

    fun start(){
        askForInput()
        getUnitOfMeasure()
        printOutput()
    }

    private fun askForInput() {
        println("Enter a number and a measure of length: ")
        val input = readln()
        val (numberInput, umTxt) = input.split(" ")
        umInput = umTxt
        numberDouble = numberInput.toDouble()
    }

    private fun getUnitOfMeasure() {
        for (unit in UnitOfMeasure.entries) {
            if (umInput.lowercase() in unit.keywords) {
                unitOfMeasure = unit
                break
            }
        }
        if (!::unitOfMeasure.isInitialized) {
            println("Wrong input. Unknown unit $umInput")
            exitProcess(0)
        }
    }

    private fun printOutput() {
        val unName = if (numberDouble != 1.0) unitOfMeasure.plural else unitOfMeasure.single
        val outputNum = numberDouble * unitOfMeasure.conv
        println("$numberDouble $unName is $outputNum meter${if (outputNum != 1.0) "s" else ""}")
    }
}

