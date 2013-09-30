set PATH = "C:\Program Files\Java\jdk1.7.0_11\bin\";%PATH%

set PATH = "C:\Selenium\";%PATH%

cd C:\Selenium

java -jar selenium-server-standalone-2.34.0.jar -role node -hub http://ramneet-7-64.acl.com:4444/grid/register -Dwebdriver.ie.driver=C:\Selenium\IEDriverServer.exe

