package interfacesLab.excel

import interfacesLab.Experiment
//import org.apache.poi.xssf.usermodel.XSSFRow
//import org.apache.poi.xssf.usermodel.XSSFSheet
//import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*

/**
 * @author Максим Пшибло
 */
class Excel(private val filename: String) {
//    private var sheet: XSSFSheet? = null
//    private var wb: XSSFWorkbook? = null

    fun saveExperiment(nameColumn1: String, nameColumn2: String, list: List<Experiment>, columnStart: Int) {
//        val rowTop: XSSFRow = getOrCreateRow(0)
//        rowTop.createCell(columnStart).setCellValue(nameColumn1)
//        rowTop.createCell(columnStart + 1).setCellValue(nameColumn2)
//        var i = 1
//        for (ex in list) {
//            val row: XSSFRow = getOrCreateRow(i)
//            row.createCell(columnStart).setCellValue(ex.numberPanel.toDouble())
//            row.createCell(columnStart + 1).setCellValue(ex.time.toDouble())
//            i++
//        }
        for (ex in list) {
            println("${ex.time} ${ex.numberPanel}")
        }
    }

    fun saveAndClose() {
//        try {
//            val fileOut: OutputStream = FileOutputStream(filename)
//            wb!!.write(fileOut)
//            fileOut.close()
//            val input: InputStream = FileInputStream(filename)
//            wb = XSSFWorkbook(input)
//            sheet = wb!!.getSheetAt(0)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }

//    private fun getOrCreateRow(rownum: Int): XSSFRow {
//        return sheet!!.getRow(rownum) ?: sheet!!.createRow(rownum)
//    }

    companion object {
        val instance = Excel("lab.xlsx")
    }

    init {
//        try {
//            val input: InputStream = FileInputStream(filename)
//            wb = XSSFWorkbook(input)
//            sheet = wb!!.getSheetAt(0)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
    }
}