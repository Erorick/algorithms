fun main(){ 
    val list = mutableListOf<String>()
    var flag = true
    val a = readLine()
    loop@for (i in a.orEmpty()){
        if (i in "{[(") {
            list.add("$i")
        }
        if (i in ")]}") {
            if (list.isEmpty()){
                flag=false
                break@loop
            }
            var br=list.removeLast()
            if (br=="{" && i in "}") continue@loop
            if (br=="[" && i in "]") continue@loop
            if (br=="(" && i in ")") continue@loop
            flag=false
            break@loop
        }
    }
    if (flag==true && list.isEmpty()) println("Все правильно")  else println("Ошибка")
}