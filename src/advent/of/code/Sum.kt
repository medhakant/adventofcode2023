package advent.of.code

fun main(args: Array<String>){
  val array = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

  // Create a list of coroutines that will process each element of the array in parallel.
  val results = array.parallelStream().map {
    val x = it*2
    "$x is odd"
  }.toList()
  print(results)
}
