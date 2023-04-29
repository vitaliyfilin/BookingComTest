# BookingComTest
1.	Установите JDK
2.	Установите IntelliJ IDEA
3.	Установите Allure
•	Откройте PowerShell
•	Установите Allure, выполнив команду: scoop install allure
•	Проверьте установку Allure, выполнив команду: allure –version
•	Также проверьте установку Maven, выполнив команду: mvn –version
4.	Запустите тест в IntelliJ IDEA
•	Через кнопку запуска теста либо через консоль IDEA с командой mvn clean test
5.	Сгенерируйте отчет Allure
•	После каждого запуска json Allure сохраняются в папке target/allure-results. CSV файл с последним json генерируется автоматически в папке проекта. 
•	Для генерации отчета Allure в виде html в папке проекта, где находится allure-results, запустите cmd и введите allure serve

Примечание: пример сгенерированного отчета прикреплен к проекту в папке allure-report-example

