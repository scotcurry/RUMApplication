package org.curryware.rumapplication.datadoghandler

import com.datadog.android.log.Logger
import com.datadog.android.log.Logs
import com.datadog.android.log.LogsConfiguration

class DatadogLogger {

    companion object {

        private var instance: Logger? = null

        fun getLogger(): Logger {
            return if (instance == null) {
                val logsConfig = LogsConfiguration.Builder().build()
                Logs.enable(logsConfig)

                val instance = Logger.Builder()
                    .setLogcatLogsEnabled(true)
                    .setNetworkInfoEnabled(true)
                    .setBundleWithRumEnabled(true)
                    .setBundleWithTraceEnabled(true)
                    .build()
                instance
            } else {
                instance!!
            }
        }

        private val tracedHosts = listOf(
            "datadoghq.com",
            "win2019server.curryware.org"
        )
    }
}