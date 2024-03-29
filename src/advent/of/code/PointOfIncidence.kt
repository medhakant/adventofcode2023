package advent.of.code

import java.io.File

private fun processPattern1(pattern:List<String>):Int{
    val rows=pattern.size
    val cols=pattern[0].length
    for(i in 0..<cols-1){
        //assuming mirror between i and i+1
        var flag = true
        var shift = 0
        while(i-shift>=0 && i+shift+1<cols){
            if(pattern.map{l->l[i-shift]}.joinToString("") !=pattern.map{l->l[i+shift+1]}.joinToString("")){
                flag=false
                break
            }
            shift++
        }
        if(flag)return i+1
    }

    for(i in 0..<rows-1){
        //assuming mirror between i and i+1
        var flag = true
        var shift = 0
        while(i-shift>=0 && i+shift+1<rows){
            if(pattern[i-shift]!=pattern[i+shift+1]){
                flag=false
                break
            }
            shift++
        }
        if(flag)return (i+1)*100
    }
    return 0
}

private fun stringDiffCount(a:String,b:String):Int{
    var count = 0
    for(i in 0..<a.length){
        if(a[i]!=b[i]){
            count++
        }
    }
    return count
}

private fun processPattern2(pattern:List<String>):Int{
    val rows=pattern.size
    val cols=pattern[0].length
    for(i in 0..<cols-1){
        //assuming mirror between i and i+1
        var count = 0
        var shift = 0
        while(i-shift>=0 && i+shift+1<cols){
            count += stringDiffCount(pattern.map{l->l[i-shift]}.joinToString(""),pattern.map{l->l[i+shift+1]}.joinToString(""))
            if(count>1) break
            shift++
        }
        if(count==1)return i+1
    }

    for(i in 0..<rows-1){
        //assuming mirror between i and i+1
        var count = 0
        var shift = 0
        while(i-shift>=0 && i+shift+1<rows){
            count+= stringDiffCount(pattern[i-shift],pattern[i+shift+1])
            if(count>1) break
            shift++
        }
        if(count==1)return (i+1)*100
    }
    return 0
}
private fun processPatterns(patterns: List<String>): Int{
    var start = 0
    var total = 0
    for(i in patterns.indices){
        if(patterns[i].trim().isEmpty()){
            total += processPattern2(patterns.subList(start,i))
            start = i+1
        }
    }
    total += processPattern2(patterns.subList(start,patterns.size))
    return total
}
fun main() {
    try {
        val fileName = "./data/day13.txt"
        println(processPatterns(File(fileName).readLines()))
    } catch (e: Exception) {
        println(e.message)
    }

}