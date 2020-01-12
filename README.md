# shelebrate
A start of the year bash

## Ideas

```
// Files
Seq<Char> charStream = File.at("~/myFile").read();
String contents = charStream.join();

Seq<String> writeStream = MyClass();
File.at("~/myFile").write(writeStream);  

// Async
Future<Integer> myIntFuture Async.go(object::myMethod);
int i = myIntFuture.await();

```