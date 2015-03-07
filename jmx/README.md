# JMX

Java Management Extensions.

Java IPC standard based directly on classes. TODO check.

`jconsole` contains a built-in GUI implementation, which allows you to asynchronously:

- observe object state
- call methods with a button click

Relies on reflection naming conventions like:

- interfaces named `XXXMBean` represent beans
- methods named `getXXX` represent monitored fields
- methods named `setXXX` indicate that a field can be modified
- other methods are actions that can be taken on button click

This directory is currently based on the official Oracle tutorial.
