package advent.of.code

import java.io.File

private fun parseMap(almanac: List<String>, start: String): Map<Long, Pair<Long, Long>> {
    val map = mutableMapOf<Long, Pair<Long, Long>>()
    var i = 0
    while (i < almanac.size && !almanac[i].startsWith(start)) i++
    i++
    while (i < almanac.size && almanac[i].trim().isNotEmpty()) {
        val mapping = Regex("\\d+").findAll(almanac[i]).map { v -> v.value.toLong() }.toList()
        map[mapping[1]] = Pair(mapping[0], mapping[2])
        i++
    }
    return map
}

private fun getMapping(map: Map<Long, Pair<Long, Long>>, key: Long): Long {
    val ans = key
    for (k in map.keys) {
        if (key - k in 0..<(map[k]?.second ?: 0)) {
            return (map[k]?.first ?: 0) + key - k
        }
    }
    return ans
}

private fun parseAlmanac1(almanac: List<String>): List<Long> {
    val seeds =
        almanac.findLast { line -> line.startsWith("seeds:") }?.split("seeds:")?.get(1)
            ?.let { Regex("\\d+").findAll(it) }?.map { s -> s.value.toLong() }?.toList()
    val seed2soil = parseMap(almanac, "seed-to-soil")
    val soil2fertilizer = parseMap(almanac, "soil-to-fertilizer")
    val fertilizer2water = parseMap(almanac, "fertilizer-to-water")
    val water2light = parseMap(almanac, "water-to-light")
    val light2temperature = parseMap(almanac, "light-to-temperature")
    val temperature2humidity = parseMap(almanac, "temperature-to-humidity")
    val humidity2location = parseMap(almanac, "humidity-to-location")
    if (seeds != null) {
        return seeds.asSequence().map { seed -> getMapping(seed2soil, seed) }
            .map { soil -> getMapping(soil2fertilizer, soil) }
            .map { fertilizer -> getMapping(fertilizer2water, fertilizer) }
            .map { water -> getMapping(water2light, water) }
            .map { light -> getMapping(light2temperature, light) }
            .map { temperature -> getMapping(temperature2humidity, temperature) }
            .map { humidity -> getMapping(humidity2location, humidity) }.toList()
    }
    return listOf<Long>()
}

private fun parseAlmanac2(almanac: List<String>): Long {
    val seeds = mutableListOf<Pair<Long, Long>>()
    val temp = almanac.findLast { line -> line.startsWith("seeds:") }?.split("seeds:")?.get(1)
        ?.let { Regex("\\d+").findAll(it) }?.map { s -> s.value.toLong() }?.toList()
    if (temp != null) {
        for (i in 0..<temp.size step 2) {
            seeds.add(Pair(temp[i], temp[i + 1]))
        }
    }
    val seed2soil = parseMap(almanac, "seed-to-soil")
    val soil2fertilizer = parseMap(almanac, "soil-to-fertilizer")
    val fertilizer2water = parseMap(almanac, "fertilizer-to-water")
    val water2light = parseMap(almanac, "water-to-light")
    val light2temperature = parseMap(almanac, "light-to-temperature")
    val temperature2humidity = parseMap(almanac, "temperature-to-humidity")
    val humidity2location = parseMap(almanac, "humidity-to-location")

    return seeds.parallelStream().map {
        val seed = it.first
        val range = it.second
        var minloc = Long.MAX_VALUE
        for (i in 0..<range) {
            val current = seed + i
            val soil = getMapping(seed2soil, current)
            val fertilizer = getMapping(soil2fertilizer, soil)
            val water = getMapping(fertilizer2water, fertilizer)
            val light = getMapping(water2light, water)
            val temperature = getMapping(light2temperature, light)
            val humidity = getMapping(temperature2humidity, temperature)
            val location = getMapping(humidity2location, humidity)
            if (location < minloc) {
                minloc = location
            }
        }
        minloc
    }.toList().min()
}

fun main() {
    try {
        val fileName = "./data/day5.txt"
        println(parseAlmanac1(File(fileName).readLines()).min())
    } catch (e: Exception) {
        println(e.message)
    }
    try {
        val fileName = "./data/day5.txt"
        println(parseAlmanac2(File(fileName).readLines()))
    } catch (e: Exception) {
        println(e.message)
    }

}