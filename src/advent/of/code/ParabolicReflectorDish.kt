package advent.of.code

import java.io.File

private fun tiltAndCalculateLoad(terrain:List<CharArray>):Int{

    for(i in 1..<terrain.size){
        for(j in 0..<terrain[i].size){
            if(terrain[i][j]=='O'){
                var final_row=i
                while(final_row>0 && terrain[final_row-1][j]=='.') final_row--
                if(terrain[final_row][j]=='.'){
                    terrain[final_row][j]='O'
                    terrain[i][j]='.'
                }
            }
        }
    }

    var load = 0
    for(i in 0..<terrain.size){
        load += (terrain.size-i)*terrain[i].filter { c->c=='O' }.count()
    }
    return load
}
fun main() {
    val filename = "./data/day14.txt"
    // part 1
    try {
        println(tiltAndCalculateLoad(File(filename).readLines().map { l->l.toCharArray() }))
    } catch (e: Exception) {
        print(e.message)
    }
}