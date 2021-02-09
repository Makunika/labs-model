package logic

abstract class BaseModel {

    fun fillParam() {
        do {
            fillInLoopParam();
        } while (!validateParam())
    }

    protected fun getCurrentTime() = System.currentTimeMillis()

    abstract fun getTime() : Long
    abstract fun validateParam() : Boolean;

    protected abstract fun fillInLoopParam();
}