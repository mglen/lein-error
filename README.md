
Given a project with the structure
```
project.clj
src/clojure/example/TaskImpl.clj
src/java/example/TaskFactory.java
src/pre/java/example/Task.java
```

And the project configuration:
```
(defproject lein-error "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]]
  :source-paths      ["src/clojure"]
  :java-source-paths ["src/java"]
  :profiles {:precomp {:java-source-paths ^:replace ["src/pre/java"]}}
  :aot :all)
```

`TaskImpl.clj` depends on `Task.java`, and `TaskFactory.java` depends on both.

Standard compilation fails due to this `java -> clojure -> java` dependency:
```
$ lein compile
Compiling 1 source files to ./target/classes
./src/java/example/TaskFactory.java:5: error: cannot find symbol
    public Task task() {
           ^
  symbol:   class Task
  location: class TaskFactory
./src/java/example/TaskFactory.java:6: error: cannot find symbol
        return new TaskImpl();
                   ^
  symbol:   class TaskImpl
  location: class TaskFactory
2 errors
Compilation of Java sources(lein javac) failed.
```

So we first run the `:precomp` profile to compile the interface:
```
$ lein with-profile precomp compile
Compiling 1 source files to ./target/classes
Compiling example.TaskImpl
```

If we then run compile with the default profile, no work is done:
```
$ lein compile
$
```

But `TaskFactory.java` has not been compiled:
```
target/classes/example/Task.class
target/classes/example/TaskImpl$loading__6706__auto____171.class
target/classes/example/TaskImpl.class
target/classes/example/TaskImpl$_init.class
target/classes/example/TaskImpl__init.class
target/classes/example/TaskImpl$fn__173.class
```

Running the `javac` task directly does pick up the work
```
$ lein javac
Compiling 1 source files to ./target/classes
```

But I expected that `javac` would have run with the `compile` task above.
