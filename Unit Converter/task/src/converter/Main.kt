package converter

import kotlin.system.exitProcess

fun main() {
    val convertor = Convertor()
    convertor.start()
}

enum class UnitOfMeasure(
    val type: String,
    val single: String,
    val plural: String,
    val conv: Double,
    val keywords: List<String>
) {
    Meter("Length", "meter", "meters", 1.0, listOf("m", "meter", "meters")),
    Kilometer("Length", "kilometer", "kilometers", 1000.0, listOf("km", "kilometer", "kilometers")),
    Centimeter("Length", "centimeter", "centimeters", 0.01, listOf("cm", "centimeter", "centimeters")),
    Millimeter("Length", "millimeter", "millimeters", 0.001, listOf("mm", "millimeter", "millimeters")),
    Mile("Length", "mile", "miles", 1609.35, listOf("mi", "mile", "miles")),
    Yard("Length", "yard", "yards", 0.9144, listOf("yd", "yard", "yards")),
    Foot("Length", "foot", "feet", 0.3048, listOf("ft", "foot", "feet")),
    Inch("Length", "inch", "inches", 0.0254, listOf("in", "inch", "inches")),
    Gram("Weight", "gram", "grams", 1.0, listOf("g", "gram", "grams")),
    Kilogram("Weight", "kilogram", "kilograms", 1000.0, listOf("kg", "kilogram", "kilograms")),
    Milligram("Weight", "milligram", "milligrams", 0.001, listOf("mg", "milligram", "milligrams")),
    Pound("Weight", "pound", "pounds", 453.592, listOf("lb", "pound", "pounds")),
    Ounce("Weight", "ounce", "ounces", 28.3495, listOf("oz", "ounce", "ounces")),
    Celsius("Temperature", "degree Celsius", "degrees Celsius", 0.0, listOf("degree celsius", "degrees celsius", "celsius", "dc", "c")),
    Fahrenheit("Temperature", "degree Fahrenheit", "degrees Fahrenheit", 0.0, listOf("degree fahrenheit", "degrees fahrenheit", "fahrenheit", "df", "f")),
    Kelvin("Temperature", "kelvin", "kelvins", 0.0, listOf("kelvin", "kelvins", "k")),

    BLANK("0", "???", "???", 0.0, listOf())
}

class Convertor {
    private lateinit var umFrom: String
    private lateinit var umTo: String
    private var numberDouble: Double = 0.0
    private lateinit var unitOfMeasureFrom: UnitOfMeasure
    private lateinit var unitOfMeasureTo: UnitOfMeasure


    fun start() {
        do {
            try {
                askForInput()
                processData()
                convertAndPrintOutput()
            } catch (e: Exception) {
                println("Parse error\n")
            }
        } while (true)
    }

    private fun askForInput() {
        print("Enter what you want to convert (or exit): ")
        val input = readln()
        if (input == "exit") stop()
        val texts = input.split(" ")
        numberDouble = texts[0].toDouble()
        when (texts.size){
            4 -> {
                umFrom = texts[1]
                umTo = texts[3]
            }
            5 -> {
                if (checkIfDegree(texts[1])){
                    umFrom = "${texts[1]} ${texts[2]}"
                    umTo = texts[4]
                } else if (checkIfDegree(texts[3])){
                    umFrom = texts[1]
                    umTo = "${texts[3]} ${texts[4]}"
                }
            }
            6 -> {
                if (!checkIfDegree(texts[1]) || !checkIfDegree(texts[4])) throw Exception()
                umFrom = "${texts[1]} ${texts[2]}"
                umTo = "${texts[4]} ${texts[5]}"
            }
            else -> throw Exception()
        }
    }

    private fun checkIfDegree(txt : String) : Boolean{
        return txt.substring(0..5).lowercase() == "degree"
    }

    private fun processData() {
        unitOfMeasureFrom = getUnitOfMeasure(umFrom)
        unitOfMeasureTo = getUnitOfMeasure(umTo)

        if (unitOfMeasureFrom == UnitOfMeasure.BLANK
            || unitOfMeasureTo == UnitOfMeasure.BLANK
            || unitOfMeasureFrom.type != unitOfMeasureTo.type) {
            println("Conversion from ${unitOfMeasureFrom.plural} to ${unitOfMeasureTo.plural} is impossible\n")
            start()
        }
        
        if (numberDouble < 0 && unitOfMeasureFrom.type in listOf("Weight","Length")){
            println("${unitOfMeasureFrom.type} shouldn't be negative")
            start()
        }
    }

    private fun getUnitOfMeasure(umString: String): UnitOfMeasure {
        var internalObject = UnitOfMeasure.BLANK
        for (unit in UnitOfMeasure.entries) {
            if (umString.lowercase() in unit.keywords) {
                internalObject = unit
                break
            }
        }
        return internalObject
    }

    private fun convertAndPrintOutput() {
        val fromTxt = if (numberDouble != 1.0) unitOfMeasureFrom.plural else unitOfMeasureFrom.single
        val outputNum = calcOutput()
        val toTxt = if (outputNum != 1.0) unitOfMeasureTo.plural else unitOfMeasureTo.single
        println("$numberDouble $fromTxt is $outputNum $toTxt\n")
    }

    private fun calcOutput() : Double{
        var outputNum = 0.0
        val from = unitOfMeasureFrom.name
        val to = unitOfMeasureTo.name
        if (unitOfMeasureFrom.type in listOf("Weight","Length")) {
            outputNum = numberDouble * unitOfMeasureFrom.conv / unitOfMeasureTo.conv
        } else {
            when {
                from == "Fahrenheit" && to == "Celsius" -> {outputNum = (numberDouble-32)*5/9}
                from == "Celsius" && to == "Fahrenheit" -> {outputNum = numberDouble*9/5+32}
                from == "Celsius" && to == "Kelvin" -> {outputNum = numberDouble + 273.15}
                from == "Kelvin" && to == "Celsius" -> {outputNum = numberDouble - 273.15}
                from == "Kelvin" && to == "Fahrenheit" -> {outputNum = numberDouble*9/5-459.67}
                from == "Fahrenheit" && to == "Kelvin" -> {outputNum = (numberDouble+459.67)*5/9}
            }
        }

        return outputNum
    }


    private fun stop() {
        exitProcess(0)
    }
}

