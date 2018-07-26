package mumtaazstudio.newsreader_kotlin.Common

import java.text.SimpleDateFormat
import java.util.*

object ISO8601DateParser{

    fun parse(params:String): Date {
        var input = params
        val dateformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")

        if (input.endsWith("Z"))
            input = input.substring(0, input.length-1)+"GMT-00:00"
        else
        {
            val insert = 6
            val s0 = input.subSequence(0, input.length - insert)
            val s1 = input.substring(input.length - insert, input.length)

            input = StringBuilder(s0).append("GMT").append(s1).toString()
        }
        return dateformat.parse(input)
    }

    fun toString(date:Date):String{
        val dateformat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")
        val timezone = TimeZone.getTimeZone("UTC")

        dateformat.timeZone = timezone

        val output = dateformat.format(date)

        val inset0 = 9
        val inset1 = 6

        val s0 = output.substring(0, output.length-inset0)
        val s1 = output.substring(output.length-inset1, output.length)

        var result = s0+s1

        result = result.replace("UTC".toRegex(), "+00:00")

        return result
    }

}