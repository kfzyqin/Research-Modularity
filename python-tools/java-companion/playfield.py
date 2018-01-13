import jpype
jpype.startJVM('/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/MacOS/libjli.dylib', '-ea')

javaClass = jpype.JClass("/Users/qin/Software-Engineering/Chin-GA-Project/src/main/java/experiments.experiment6/TestField12")
javaObject = javaClass()
jpype.shutdownJVM()

