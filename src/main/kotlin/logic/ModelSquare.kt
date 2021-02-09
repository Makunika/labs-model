package logic

import kotlin.math.*
import kotlin.random.Random

class ModelSquare : BaseModel() {

    private var time = 0L;

    var width = 0.0;
    var height = 0.0;
    var radius = 0.0;
    var N = 300L

    fun squareAnalytic() : Double {
        time = getCurrentTime();
        var answer = 0.0;

        val radiusSquare = sqrt(height * height + width * width) / 2;
        val diameter = radius * 2;

        if (radiusSquare < radius) {
            answer = height * width;
        } else {
            if (width >= diameter && height >= diameter) {
                answer = Math.PI * radius * radius;
            } else if (width > diameter) {
                val bo = height / 2;
                val ab = sqrt(radius * radius - bo * bo);
                val angle = asin(bo / radius);
                val Sq1 = (ab * bo) / 2;
                val Sq2 = (Math.PI * radius * radius * angle) / Math.toRadians(360.0)
                answer = 4 * (Sq1 + Sq2);
            } else if (height > diameter) {
                val bo = width / 2;
                val ab = sqrt(radius * radius - bo * bo);
                val angle = asin(bo / radius);
                val Sq1 = (ab * bo) / 2;
                val Sq2 = (Math.PI * radius * radius * angle) / Math.toRadians(360.0)
                answer = 4 * (Sq1 + Sq2);
            } else  {
                val a = height / 2
                val b = width / 2
                val angleA = acos(a / radius)
                val angleB = acos(b / radius)
                val angleZ = Math.PI / 2.0 - angleA - angleB
                val Sq1 = 0.5 * sin(angleA) * radius * a
                val Sq2 = (Math.PI * radius * radius * angleZ) / Math.toRadians(360.0)
                val Sq3 = 0.5 * sin(angleB) * radius * b
                answer = 4 * (Sq1 + Sq2 + Sq3)
            }
        }
        time = getCurrentTime() - time;
        return answer;
    }

    fun squareImitatic() : Double {
        time = getCurrentTime();
        var answer = 0.0;

        val diapasonX = max(width, radius);
        val diapasonY = max(height, radius);
        var K = 0;
        for (i in 0..N) {
            val x = Random.nextDouble(-diapasonX, diapasonX)
            val y = Random.nextDouble(-diapasonY, diapasonY)
            if (x*x + y*y < radius*radius && x > -width / 2 && x < width / 2 && y < height / 2 && y > -height / 2) {
                K++;
            }
        }
        val S = diapasonX * 2 * diapasonY * 2;
        answer = S * K / N;

        time = getCurrentTime() - time;
        return answer;
    }

    override fun getTime(): Long = time

    override fun validateParam(): Boolean {
        return width > 0 || height > 0 || radius > 0
    }

    override fun fillInLoopParam() {
        print("Введите ширину: ")
        width = readLine()!!.toDouble();
        print("Введите высоту: ")
        height = readLine()!!.toDouble();
        print("Введите радиус: ")
        radius = readLine()!!.toDouble();
        print("Введите количество испытаний: ");
        N = readLine()!!.toLong();
    }
}