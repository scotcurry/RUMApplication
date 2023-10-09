# Overview - Updated 10/09/2023
* Added a crash routine
* 
## TODO

- Build in Session replay
- There are two sets of Rest API builders (RestAPI and SQLRestApi) because I don't really know how to change the base URL

## Datadog Android SDK Project

This project has been updated to use the version 2.0 SDK libraries.  Libraries have been moved into modules for each of the Datadog functions (RUM, Tracing, Logs).  Key code callouts are:
***api.properties*** This is the secrets / configuration file at the root of the project that is not committed to the repository.  It requires the following fields.
```
DD_API_KEY=xxx
DD_REST_API_KEY=xxx
API_URL=xxx
ANDROID_CLIENT_TOKEN=xxx
ANDROID_APP_ID=xxx
USER_NAME="Scot Curry"
USER_ID="cc4689ec-866f-46f1-8b17-39aead991a48"
USER_EMAIL="scot.curry@datadoghq.com"
CURRYWARE_URL="https://win2019server.curryware.org"
TRACED_URLS="win2019server.curryware.org,datadoghq.com"
```
**MainActivity.kt** - Because this is an Android Compose only code base using the out of the box tracking strategy won't work.  Need to add the following code to get RUM to add the appropriate view names to the RUM data.
Needed [source file](https://github.com/DataDog/dd-sdk-android/blob/develop/integrations/dd-sdk-android-compose/src/main/kotlin/com/datadog/android/compose/Navigation.kt) to understand how to build the Predicate.
```
val navController = rememberNavController().apply { 
    NavigationViewTrackingEffect(navController = this, trackArguments = false, destinationPredicate = AcceptAllNavDestinations())
}
```

MainActivity.kt also has all of the Datadog initialization code.  Might want to move this to a separate file at some point.
**10-92-23 Update** Moved the log configuration builder into a separate call.  It is added "for free" with RUM, but there is
slightly more configuration available with the way it is implemented below.
```
        // Everything is well documented in the source.
        // https://github.com/DataDog/dd-sdk-android/blob/develop/sample/kotlin/src/main/kotlin/com/datadog/android/sample/SampleApplication.kt
        val tracedHosts = listOf(
            "win2019server.curryware.org",
            "datadoghq.com"
        )
        val applicationID = BuildConfig.ANDROID_APP_ID
        val clientToken = BuildConfig.ANDROID_CLIENT_TOKEN
        val configuration = Configuration.Builder(
            clientToken = BuildConfig.ANDROID_CLIENT_TOKEN,
            env = "prod",
            variant = BuildConfig.ANDROID_APP_ID
        )
            .setFirstPartyHosts(tracedHosts)
            .build()

        // Since we have broken functionality into separate libraries, logging and RUM can and in
        // my opinion should have separate configurations.  You do get logging "for free" if you
        // do this configuration and enable logs.
        Datadog.initialize(this, configuration, TrackingConsent.GRANTED)

        val rumConfiguration = RumConfiguration.Builder(applicationID)
             .trackFrustrations(true)
             .trackUserInteractions()
            .setTelemetrySampleRate(100f)
            .build()
        Rum.enable(rumConfiguration)

        Datadog.setVerbosity(Log.INFO)
        val logConfiguration = LogsConfiguration.Builder().build()
        Logs.enable(logsConfiguration = logConfiguration)

        val logger = Logger.Builder()
            .setNetworkInfoEnabled(true)
            .setLogcatLogsEnabled(false)
            .setRemoteSampleRate(100f)
            .setName("AndroidRUM")
            .setService("RUMApplication")
            .build()

        logger.i("Using clientToken $clientToken")
        if (Datadog.isInitialized()) {
            Log.i(TAG,"Datadog Initialized")
            logger.i("Datadog Initialized")
            logger.i("Using clientToken $clientToken")
            logger.d("Debug Message Tester")
        }

        // https://docs.datadoghq.com/tracing/trace_collection/dd_libraries/android/?tab=kotlin
        val tracesConfig = TraceConfiguration.Builder().build()
        Trace.enable(tracesConfig)
        val tracer = AndroidTracer.Builder().build()
        GlobalTracer.registerIfAbsent(tracer)

        if (Datadog.isInitialized()) {
            logger.i("Initialize Datadog Libraries")
            Log.i(TAG, "Client Token: $clientToken")
            Log.i(TAG, "Application ID: $applicationID")
            Datadog.setUserInfo("id:1234", "Scot Curry", "scotcurry4@gmail.com")
        }
```
**DatadogLogger.kt**
A static class to provide Datadog Logging
```
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
```

**RestAPIBuilder.kt**
This is the object that configures the Datadog agent to send trace information to Datadog.  It is part of the 
RestAPI family of classes key code section below:

```
 val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor("datadoghq.com"))
            .addNetworkInterceptor(TracingInterceptor("datadoghq.com"))
            .eventListenerFactory(DatadogEventListener.Factory())
        val builtClient = httpClient.build()

        val apiURL = BuildConfig.API_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(builtClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
```

## Key Android Async UI Concurrency - Rest API Functionality (make the call to get the data for the screen)

The UI components have code that starts the process of getting the data required to populate the screen.  ValidateAPIScreen is the most straight forward so this README will document that.
Everything in the project calls a REST API function.  To to that You need to build the OkHTTP REST object.  The REST API Helper can be thought of as the list of endpoints that this REST API
is responsible for.

**ValidateAPIKeyScreen.kt**
```
    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val validateAPIKeyViewModel: ValidateAPIKeyViewModel = viewModel(factory = ValidateAPIKeyViewModelFactory(restAPIHelper, logger))
    validateAPIKeyViewModel.checkDatadogAPIKey()
    val apiCheckData by validateAPIKeyViewModel.apiCheckReturnValue.observeAsState(initial = null)
```

**RestAPIWorker.kt**

RestAPIWorker is an Interface that uses annotations to define the URLs that will be used by the implementation class
of RestAPIHelper.kt

```
interface RestAPIWorker {

    @GET("/api/v1/validate")
    suspend fun validateAPIKey(@HeaderMap headers: Map<String, String>): Response<ValidateKey>

    @GET("/api/v1/org")
    suspend fun getOrganizationInfo(@HeaderMap headers: Map<String, String>): Response<OrganizationInfo>

    @GET("/api/v1/usage/summary")
    suspend fun getUsageSummary(@HeaderMap headers: Map<String, String>, @QueryMap queryMap: Map<String, String>): Response<UsageSummary>

    @GET("/api/database")
    suspend fun getSQLServerQueryData(@Url url: String): Response<List<StateSalesTax>>
}
```

**RestAPIHelper.kt**

The implementation of the RestAPIWorker functions.

```
class RestAPIHelper(private val restAPIWorker: RestAPIWorker) {

    suspend fun checkAPIKey(headerParameters: Map<String, String>) = restAPIWorker.validateAPIKey(headerParameters)
    suspend fun getOrganizationInfo(headerParameters: Map<String, String>) = restAPIWorker.getOrganizationInfo(headerParameters)
    suspend fun getUsageSummary(headerParameters: Map<String, String>, queryParameters: Map<String, String>) = restAPIWorker.getUsageSummary(headerParameters, queryParameters)

```

**RestRetroFitBuilder**

This is what sets up and calls the URL

```
object RestRetroFitBuilder {

    private val retrofit by lazy {

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(DatadogInterceptor("datadoghq.com"))
            .addNetworkInterceptor(TracingInterceptor("datadoghq.com"))
            .eventListenerFactory(DatadogEventListener.Factory())
        val builtClient = httpClient.build()

        val apiURL = BuildConfig.API_URL

        httpClient.let {
            Retrofit.Builder()
                .baseUrl(apiURL)
                .client(builtClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

    val restAPIWorker: RestAPIWorker by lazy {
        retrofit.create(RestAPIWorker::class.java)
    }
}
```

## Key Android Async UI Concurrency - Populating Data

After building up all of the Rest API you send it into the ViewModel (**ValidateAPIKeyScreen.kt**).  The ViewModel has a function that will return the data
required by the screen.

**ValidateAPIKeyScreen.kt**

The ViewModel has a function (in this case checkDatadogAPIKey()) that is going to return the data to the variable apiCheckData that is what the screen uses.
```
    val restAPIHelper = RestAPIHelper(RestRetroFitBuilder.restAPIWorker)
    val validateAPIKeyViewModel: ValidateAPIKeyViewModel = viewModel(factory = ValidateAPIKeyViewModelFactory(restAPIHelper, logger))
    validateAPIKeyViewModel.checkDatadogAPIKey()
    val apiCheckData by validateAPIKeyViewModel.apiCheckReturnValue.observeAsState(initial = null)
```

**ValidateAPIKeyViewModel.kt**

The key to the ViewModel is the **viewModelScope.launch**.  This populates a class level variable **apiCheckReturnValue** that was in the screen call above
as an observeAsState variable

```
        Log.i(TAG, "Launching ValidateAPIKeyViewModel")
        viewModelScope.launch {
            val headerValues = mutableMapOf<String, String>()
            headerValues["Content-Type"] = "application/json"
            headerValues["DD-API-KEY"] = BuildConfig.DD_API_KEY
            headerValues["DD-APPLICATION-KEY"] = BuildConfig.DD_REST_API_KEY
            val response = apiHandlerRepository.validateAPIKey(headerValues)
            Log.i(TAG, "Response Code: ${response.code()}")
            Log.i(TAG, "Message Body: ${response.body()}")
            if (response.body() != null) {
                val apiCheckDataBody = response.body()!!
                apiCheckReturnValue.postValue(apiCheckDataBody)
            }
        }
    }

    val apiCheckReturnValue = MutableLiveData<ValidateKey>()
}
```
