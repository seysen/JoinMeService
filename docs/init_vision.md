###Цель данного модуля:
1) Обеспечивать логику приложения;
2) Обрабатывать REST запросы клиентов;
3) Хранить состояния сущностей.

###Функционал, обеспеиваемый модулем
1) Работа с БД приложения (создание и отправка запросов, обработка ответов);
2) Обрабатывать данные, предоставляемые пользователем средствами UI и возвращать результаты обработки этих данных;
3) Корректно менять состояние приложения;
4) Обеспечивать авторизацию пользователей;
5) Ведение логирования работы приложения и действий пользователя.

###Возможные проблемы
1) Если делать монолитное ядро, то оно будет слишком обширным и сложнее будет проводить изменения/понять как тот или иной функционал работает;