package Model

import java.sql.DriverManager
import java.sql.Connection

class Connection {

    fun StringConection(): Connection?
    {
        try {
            val ip = "jdbc:oracle:thin:@192.168.1.9:1521:xe"
            val user = "system"
            val password = "JHtt4Vtq"

            val strConnection = DriverManager.getConnection(ip,user,password)
            return  strConnection
        }
        catch (e:Exception)
        {
            println("Error: $e")
            return null
        }
    }
}