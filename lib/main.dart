import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
        // This makes the visual density adapt to the platform that you run
        // the app on. For desktop platforms, the controls will be smaller and
        // closer together (more dense) than on mobile platforms.
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class Todo {
  final int userId;
  final int id;
  final String title;
  final bool completed;

  Todo({
    @required this.userId,
    @required this.id,
    @required this.title,
    @required this.completed,
  });

  factory Todo.fromMap(Map<dynamic, dynamic> map) {
    final rawCompleted = map['completed'];
    bool completed;
    if (rawCompleted is int) {
      completed = rawCompleted == 1;
    } else {
      completed = rawCompleted;
    }
    return new Todo(
      userId: map['userId'],
      id: map['id'],
      title: map['title'],
      completed: completed,
    );
  }
}

class _MyHomePageState extends State<MyHomePage> {
  List<Todo> _todos = [];

  @override
  void initState() {
    super.initState();

    initTodos();
  }

  Future initTodos() async {
    final List<dynamic> todos =
        await MethodChannel("api").invokeMethod("getMessage");
    setState(() {
      _todos = todos.map((todo) => Todo.fromMap(todo)).toList();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: ListView(
          children: _todos
              .map((todo) => ListTile(
                    title: Text("ID: " + todo.id.toString() + " " + todo.title),
                    subtitle: Text("User ID: " + todo.userId.toString()),
                    enabled: !todo.completed,
                  ))
              .toList(),
        ),
      ),
    );
  }
}
