SET TESTWISE_COVERAGE_REPORT=""C:\Users\guy.mcdonald\Documents\jabref-demo\teamscale-jacoco-agent\tempo\execution-127_0_0_1-8123.json""

:: Upload to Teamscale
:: TODO Adjust to match your Teamscale instance and user
:: The user must have the "View Project" and "Perform External Uploads" permissions on the project
SET TEAMSCALE_URL=https://engine.deltaproact.com/
SET TEAMSCALE_PROJECT=jabref
SET TEAMSCALE_USER=guywmcd
SET TEAMSCALE_ACCESS_TOKEN=ZHiEdQi1n4YbRlvqw3TO8YCysuPmTUz9
SET TEAMSCALE_PARTITION=Tosca
SET TEAMSCALE_COMMIT=main:%TEAMSCALE_REVISION% :: TODO In production, this should point to the git commit timestamp

curl --request POST --user %TEAMSCALE_USER%:%TEAMSCALE_ACCESS_TOKEN% --form "report=@%TESTWISE_COVERAGE_REPORT%" "%TEAMSCALE_URL%api/projects/%TEAMSCALE_PROJECT%/external-analysis/session/auto-create/report?format=TESTWISE_COVERAGE&partition=Tosca%2Tests&message=Tosca%2Test%2Coverage"
