package logic

import kotlin.math.cos
import kotlin.math.sin

class ModelThrow() : BaseModel() {

    var alpha = 0.0;
    var v0 = 0.0;
    var step = 0.0;

    private val g = 9.81
    private var time = 0L;
    val resultMap = HashMap<Double, Double>()
        get() = field

    fun lengthAnalytic() : Double {
        time = getCurrentTime();
        val answer = (v0 * v0 * sin(2 * Math.toRadians(alpha))) / g
        time = getCurrentTime() - time;
        return answer;
    }

    fun lengthImitatic() : Double {
        time = getCurrentTime();
        val vx = v0 * cos(Math.toRadians(alpha));
        var xi = 0.0;
        var yi = 0.0;
        var vyi = v0 * sin(Math.toRadians(alpha));
        var answer = 0.0;
        var i = 0
        while (yi >= 0)
        {
            if (i % 100 == 0) {
                resultMap[xi] = yi;
            }
            answer = xi + ((vx * yi) / (vyi));
            xi += vx * step;
            yi += vyi * step;
            vyi -= g * step;
            i++;
        }
        time = getCurrentTime() - time;
        return answer;
    }

    override fun validateParam() : Boolean {
        if (v0 <= 0) return false;
        if (alpha > 90 || alpha < 0) return false;
        if (step < 0) return false;
        return true;
    }

    override fun fillInLoopParam() {
        print("Введите начальную скорость v0: ")
        v0 = readLine()!!.toDouble();
        print("Введите угол броска в градусах alpha: ")
        alpha = readLine()!!.toDouble();
        print("Введите шаг моделирования: ")
        step = readLine()!!.toDouble();
    }

    override fun getTime(): Long = time
}