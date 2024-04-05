@ECHO OFF

:: Adjust all the places in the file marked with TODO!

:: Note the CONFIGURE.bat must be started before Tosca as changes to the environment variables afterwards don't have any
:: effect.

:::::::::::::::::::::::::::::::::::::
::::::: Agent Configuration :::::::::
:::::::::::::::::::::::::::::::::::::

:: The Teamscale Tosca Plugin needs to communicate with the profiler to tell it, which test is currently executed. To do
:: this, there are two different ways that depend on how the system under test is started for the test.

::::: SCENARIO 1: System under test is started once before running the tests

:: Define the port of the Testwise Coverage Server here. In case the system under test consists of multiple Java
:: processes, each Java process must have it's own port.

:: TODO Define multiple port variables here and use them below to specify the agents and their ports in
:: TEAMSCALE_AGENT_URL, if the system consists of multiple processes
:: Also use them in the appropriate RUNME.bat files when starting the individual subcomponents of your application.

SETX TEAMSCALE_AGENT_PORT 8123

:: Expects a comma separated list of server URLs under which the Teamscale JaCoCo agents can be notified. An example
:: would be `http://127.0.0.1:8123/` or `127.0.0.1:8124,127.0.0.1:8125`

:: TODO Add the additional addresses and ports here, if multiple agents are used.
SETX TEAMSCALE_AGENT_URL 127.0.0.1:%TEAMSCALE_AGENT_PORT%

::::: SCENARIO 2: System under test is started and stopped while executing the tests

:: Expects a comma separated list of environment variable names, which instructs the plugin to set those environment
:: variables to the test name that is currently being executed. This can be used in combination with the `test-env`
:: option of the Teamscale JaCoCo agent. An example would be `TEAMSCALE_TEST_PATH`. This is needed when the system under
:: test is started as a step inside the actual test execution and, therefore, the
:: system under test is not yet started when the test begins and is already closed when the test ends.

:: SETX TEAMSCALE_TEST_ENV "TEAMSCALE_TEST_PATH"

:::::::::::::::::::::::::::::::::::
::::::: Report Generation :::::::::
:::::::::::::::::::::::::::::::::::

:: Must point to the root directory of the expanded Teamscale JaCoCo Agent distribution.

SETX TEAMSCALE_JACOCO_AGENT "C:\Users\guy.mcdonald\Documents\jabref-demo\teamscale-jacoco-agent"

:: A comma-separated list of class, jar or war files that represent the system under test.

SETX TEAMSCALE_CLASS_FILES "C:\Users\guy.mcdonald\Documents\jabref-demo\build\classes"

:: optional: The folder into which test details, test execution and log files are written. Default is `%temp%` if not
:: specified.

:: TODO Adjust to point to a directory where Teamscale Tosca Plugin and the Teamsale JaCoCo Agent can store temporary
:: data

SETX TEAMSCALE_TEST_OUTPUT "C:\Users\guy.mcdonald\Documents\jabref-demo\teamscale-jacoco-agent\tempo"

:: A path to the script that should be executed after all tests have been executed, to generate
:: the testwise coverage report and upload it to Teamscale.

SETX TEAMSCALE_POST_TEST_SCRIPT "%~dp0generateAndUploadReport.bat"

SETX TEAMSCALE_REVISION "1c7a2c30a2d3f4c721af14bfcf8913caa7f69df1"

:: optional: As the API does not yet allow us to determine when all tests have finished executing, we wait for a given
:: time after a test case has ended. If no new test has started in the meantime we assume all tests have been executed
:: and we can  start generating the report. This environment variable specifies this value in seconds. The default value
:: is 5 seconds. If the value is 0 [not recommended] a report is generated after every test. To disable report
:: generation set the variable to a negative value.

SETX TEAMSCALE_REPORT_GENERATION_DELAY 10
