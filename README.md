# shelebrate
A start of the year bash

## Ideas

```
// Files
FileSystem fs = new VirtualFileSystem();
Seq<Char> charStream = fs.get("~/myFile").read();
String contents = charStream.join();

Seq<String> writeStream = MyClass();
File.at("~/myFile").write(writeStream);  

// Async
Future<Integer> myIntFuture Async.go(object::myMethod);
int i = myIntFuture.await();

```