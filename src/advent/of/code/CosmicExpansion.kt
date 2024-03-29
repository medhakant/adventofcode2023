package advent.of.code

import java.io.File
import kotlin.math.min
import kotlin.math.max


private fun distance(g1:Pair<Int,Int>,g2:Pair<Int,Int>,row_length:Map<Int,Int>,col_length:Map<Int,Int>):Int{
    val r1 = min(g1.first,g2.first)
    val r2 = max(g1.first,g2.first)
    val c1 = min(g1.second,g2.second)
    val c2 = max(g1.second,g2.second)
    var d = 0
    for(i in r1..<r2){
        d += row_length[i]!!
    }
    for(i in c1..<c2){
        d += col_length[i]!!
    }
    return d
}
private fun processObservations(observations: List<String>,space: Int):Long{
    val row_length_map = mutableMapOf<Int,Int>()
    val col_length_map = mutableMapOf<Int,Int>()
    val loc_galaxy = mutableListOf<Pair<Int,Int>>()
    for (i in observations.indices){
        row_length_map[i]= if (observations[i].contains("#")) 1 else space
    }
    for (i in 0..<observations[0].length){
        col_length_map[i]= if (observations.map{r->r[i]}.any { c-> c == '#' }) 1 else space
    }
    for (i in observations.indices){
        for (j in 0..<observations[0].length){
            if(observations[i][j]=='#')
                loc_galaxy.add(Pair(i,j))
        }
    }

    var ans = 0L
    for(i in 0..<loc_galaxy.size){
        for(j in i+1..<loc_galaxy.size){
            ans += distance(loc_galaxy[i],loc_galaxy[j],row_length_map,col_length_map)
        }
    }

    return ans
}
fun main() {
    val filename = "./data/day11.txt"
    // part 1
    try {
        println(processObservations(File(filename).readLines(),1000000))
    } catch (e: Exception) {
        print(e.message)
    }
}