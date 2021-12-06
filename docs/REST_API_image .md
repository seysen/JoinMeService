POST /events/image/


request body

{
"photo": <binary_file>
}

response

https://s3.console.aws.amazon.com/s3/object/litvinovaprogrammersblog-storage?region=us-east-2&prefix=7bf515ef-ec73-453e-9bf8-56293f733154_%D0%B8%D1%81%D0%BA%D0%BB%D1%8E%D1%87%D0%B5%D0%BD%D0%B8%D1%8F.png


** при выборе фото при нажатии кнопки ок (прицепить картинку к форме) происходит запрос на бек, сохранение фото, ответ урлом фото
далее запрос на сохранение события в котором мы уже отправляем ссылку в текстовом виде