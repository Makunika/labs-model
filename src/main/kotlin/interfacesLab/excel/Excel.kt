package interfacesLab.excel

import interfacesLab.Experiment
import org.apache.poi.xssf.usermodel.XSSFRow
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.*

/**
 * @author Максим Пшибло
 */
class Excel(private val filename: String) {
    private var sheet: XSSFSheet? = null
    private var wb: XSSFWorkbook? = null

    fun saveExperiment(
        nameColumn1: String,
        nameColumn2: String,
        list: List<Experiment>,
        columnStart: Int,
        description: String
    ) {
        val rowTop1: XSSFRow = getOrCreateRow(0)
        rowTop1.createCell(columnStart).setCellValue(description)
        val rowTop2: XSSFRow = getOrCreateRow(1)
        rowTop2.createCell(columnStart).setCellValue(nameColumn1)
        rowTop2.createCell(columnStart + 1).setCellValue(nameColumn2)
        var i = 2
        for (ex in list) {
            val row: XSSFRow = getOrCreateRow(i)
            row.createCell(columnStart).setCellValue(ex.numberPanel.toDouble())
            row.createCell(columnStart + 1).setCellValue(ex.time.toDouble())
            i++
        }
    }

    fun saveAndClose() {
        try {
            val fileOut: OutputStream = FileOutputStream(filename)
            wb!!.write(fileOut)
            fileOut.close()
            val input: InputStream = FileInputStream(filename)
            wb = XSSFWorkbook(input)
            sheet = wb!!.getSheetAt(0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getOrCreateRow(rownum: Int): XSSFRow {
        return sheet!!.getRow(rownum) ?: sheet!!.createRow(rownum)
    }

    companion object {
        val instance = Excel("src/main/resources/lab.xlsx")
    }

    init {
        try {
            val input: InputStream = FileInputStream(filename)
            wb = XSSFWorkbook(input)
            sheet = wb!!.getSheetAt(0)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}