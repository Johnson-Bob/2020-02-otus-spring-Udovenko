<H4>Домашнее задание 1</H4>
Программа по проведению тестирования студентов
Цель: Цель задания: научиться создавать приложение с помощью Spring IoC. Результат: простое приложение, сконфигурированное XML-контекстом.
Описание задание:

В ресурсах хранятся вопросы и различные ответы к ним в виде CSV файла (5 вопрсов).
Программа должна спросить у пользователя фамилию и имя, спросить 5 вопросов из CSV-файла и вывести результат тестирования.
Вопросы могут быть с выбором из нескольких вариантов или со свободным ответом - на Ваше желание и усмотрение.

Все сервисы в программе должны решать строго определённую задачу.
Контекст описывается XML-файлом.
Все зависимости должны быть настроены в IoC контейнере.
Имя ресурса с вопросами (CSV-файла) необходимо захардкодить в XML-файле с контекстом.
CSV с вопросами читается именно как ресурс, а не как файл.
Scanner и стандартные типы в контекст класть не нужно!

*Опционально: сервисы, по возможности, покрыть Unit-тестами

<u>Схема csv-файла:</u><br/>
<i>Поля:</i> id,text,answers<br/>
<i>Поле answers</i> содержит все варианты ответов рзделенных ";"<br/>
<i>Answer</i> - это объект, состоящий из полей letter, answerText, isCorrect. 
В файле они разделены знаком "|".<br/>
<i>Поле isCorrect</i> может иметь значения "0" или "1"ю