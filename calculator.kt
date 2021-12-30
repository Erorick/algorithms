import kotlin.math.*

fun main() {
    val fl=readLine() //Пример"13 + 5 * (2^3 + 1)"
    var infpost = inf2post(fl)
    calcPost(infpost)
    println()
} 
fun inf2post(input: String): List<String> {
    val exp = parseExpression(input)
    val prioritet= mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2, "^" to 3, "sin" to 3)
    val postfixExpression = mutableListOf<String>()
    val list = mutableListOf<String>()
    for (s in exp) {
        val num = s.matches("-?\\d+(\\.\\d+)?".toRegex())
        if (num) postfixExpression.add(s)
        else {
            when (s) {
                "(" -> {
                    list.add(s)
                }
                ")" -> {
                    while (list.last() != "(")
                        postfixExpression.add(list.removeLast())
                    list.removeLast()
                }
                else -> {
                    while ((list.isNotEmpty()) &&
                        (list.last() != "(") &&
                        (prioritet.getValue(s) <= prioritet.getValue(list.last())))
                        postfixExpression.add(list.removeLast())
                    list.add(s)
                }
            }
        }
    }
    while (list.isNotEmpty()) {
        postfixExpression.add(list.removeLast())
    }
 
    print("Результат постфиксного выражения ")
    println(postfixExpression.joinToString(" "))
 
    return postfixExpression.toList()
}
fun calcPost(list: List<String>): Float {
    var res: Float
    var buf: Float
    val stack = mutableListOf<Float>()
    for (i in list) {
        when(i) {
            "+" -> {
                res = stack.removeLast()
                res += stack.removeLast()
                stack.add(res)
            }
            "-" -> {
                buf = stack.removeLast()
                res = stack.removeLast() - buf
                stack.add(res)
            }
            "*" -> {
                res = stack.removeLast()
                res *= stack.removeLast()
                stack.add(res)
            }
            "/" -> {
                buf = stack.removeLast()
                res = stack.removeLast() / buf
                stack.add(res)
            }
            "^" -> {
                buf = stack.removeLast()
                res = stack.removeLast().pow(buf)
                stack.add(res)
            }
            "sin" -> {
                res = sin(stack.removeLast())
                stack.add(res)
            }
            else -> {
                stack.add(i.toFloat())
            }
        }
    }
    print("Финальный результат: ")
    println(stack[0])
    return stack[0]
}
 fun parseExpression(expression: String): List<String> {
    val pattern = "-?\\d+(\\.\\d)?|\\(|\\)|\\+|-|\\*|/|\\^|(sin)".toRegex()
    return pattern.findAll(expression).map { it.value }.toList()
}