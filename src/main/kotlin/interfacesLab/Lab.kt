package interfacesLab

enum class Lab(val numberStep: Int, val list: Array<Int>, val description: String, val value: String) {
    Lab1(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 1", ""),
    Lab2_1_1(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: font-style = italic", "italic"),
    Lab2_1_2(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: font-weight = bold", "bold"),
    Lab2_1_3(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: text-decoration = underline", "true"),
    Lab2_2_1(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: 10pt", "10"),
    Lab2_2_2(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: 11pt", "11"),
    Lab2_2_3(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: 12pt", "12"),
    Lab2_2_4(5, arrayOf(2,3,4,5,6,7,8,9), "LAB 2: 16pt", "16");

    //2,3,4,5,6,7,8,9
    //5

    private var current = 0

    fun next() {
        if (current == list.size - 1) {
            throw IllegalCallerException("current > list.size")
        }
        current++
    }

    fun getCurrent() = list[current]

    fun default() {
        current = 0
    }

    fun hasNext() = values().indexOf(this) + 1 < values().size

    fun nextLab() : Lab {
        val nextIndex = values().indexOf(this) + 1;
        if (nextIndex >= values().size) {
            throw IllegalCallerException("end Lab!")
        }
        return values()[nextIndex]
    }

    override fun toString(): String {
        return "Lab(description='$description', value='$value', current=$current)"
    }


}