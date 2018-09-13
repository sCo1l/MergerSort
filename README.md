# MergerSort
Программа сортировки слиянием файлов.

Параметры программы (указывать через пробел):
1. Режим сортировки, необязательный (по умолчанию сортируем по возрастанию) "-a" - по возрастанию, "-d" - по убыванию;
2. Тип данных, обязательный, "-i" - целочисленный, "-s" - строковый;
3. Имя выходного файла, обязательный (ВАЖНО указать этот параметр самым последним);
4. Имена входных файлов, обязательный.

Запускать программу с консоли, либо же используя любую IDE. Скачать JDK с официального сайта Oracle и установить.

Пример запуска программы с консоли (сортировка двух файлов, по умолчанию - по возрастанию, с целочисленными данными):
1. Компиляция: javac MergerSort.java (из директории программы)
2. Запуск: java MergerSort -i in1.txt in2.txt out.txt
В директории программы создастся выходной файл out.txt с отсортированными данными.

Если входные файлы находятся в директории программы, полный путь указывать необязательно.