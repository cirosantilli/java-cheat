# JMX

Java Management Extensions.

Initially based on the official Oracle tutorial: <http://docs.oracle.com/javase/tutorial/jmx/>

- [Main.java](Main.java)
- [Hello.java](Hello.java)
- [HelloMBean.java](HelloMBean.java)
- [QueueSampler.java](QueueSampler.java)
- [QueueSamplerMXBean.java](QueueSamplerMXBean.java)

## Usage

    make run

On another terminal:

    jconsole

On the GUI, go:

- `Main`
- `MBeans`
- `DefaultDomain`
- `Hello`
- `Operations`
- `sayHello()` button

The first terminal should print:

    Hello, World!

## Introduction

Java IPC standard based directly on classes. TODO check.

`jconsole` contains a built-in GUI implementation, which allows you to asynchronously:

- observe object state
- call methods with a button click

Relies on reflection naming conventions like:

- interfaces named `XXXMBean` represent beans
- methods named `getXXX` represent monitored fields
- methods named `setXXX` indicate that a field can be modified
- other methods are actions that can be taken on button click
