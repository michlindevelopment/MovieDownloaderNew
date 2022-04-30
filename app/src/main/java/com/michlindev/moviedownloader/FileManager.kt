package com.michlindev.moviedownloader

import java.io.*
import java.util.ArrayList

object FileManager {
    private const val RSS_HEADER =
        "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:content=\"http://purl.org/rss/1.0/modules/content/\" xmlns:atom=\"http://www.w3.org/2005/Atom\" version=\"2.0\">"
    private const val RSS_TITLE = "<title>Movies RSS</title>"
    private const val FILE_NAME = "my1.rss"


    private val path = MovieDownloader.applicationContext().filesDir.path

    private fun writeToRssFile(fileContent: List<String?>) {
        try {
            val directory = File("$path/MoviesSync")
            val file = File(directory, FILE_NAME)
            val fOut = FileOutputStream(file)
            val osw = OutputStreamWriter(fOut)
            for (line in fileContent) {
                osw.append(line)
                osw.append('\n')
            }
            osw.flush()
            osw.close()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

    fun createFile() {
        val directory = File("$path/MoviesSync")
        val file = File(directory, FILE_NAME)
        try {
            if (!directory.exists()) directory.mkdir()
            if (!file.exists()) {
                file.createNewFile()
                writeToRssFile(createNewList())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            DLog.e("Error $e")

        }
    }

    fun readFromRssFile(): List<String> {
        val fileContent: MutableList<String> = ArrayList()
        val directory = File("$path/MoviesSync")
        val file = File(directory, FILE_NAME)
        try {
            if (!directory.exists()) directory.mkdir()
            if (!file.exists()) {
                file.createNewFile()
                writeToRssFile(createNewList())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            val br = BufferedReader(FileReader(file))
            var line: String
            while (br.readLine().also { line = it } != null) {
                fileContent.add(line)
            }
            br.close()
        } catch (e: IOException) {
        }
        return fileContent
    }

    fun clearFile(): ArrayList<String?> {
        val initialList = createNewList()
        writeToRssFile(initialList)
        return initialList
    }

    private fun createNewList(): ArrayList<String?> {
        val rssDataList = ArrayList<String?>()
        rssDataList.add(RSS_HEADER)
        rssDataList.add("<channel>")
        rssDataList.add(RSS_TITLE)
        rssDataList.add("</channel>")
        rssDataList.add("</rss>")
        return rssDataList
    }
}