# Introduction

I manage multiple users  with multiple repositories in my local system. This utility will help me manage different repositories with minimal cross concerns.
This utility basically pushes your last commit , but you have explicitly add your username and password while pushing.


# CI/CD

We will describe how to package the utility

## Environment

Output of `mvn -v`

```bash
Maven home: C:\Users\tanmay.majumdar\Downloads\Code_Dependencies\ToadEdgeUtils\apache-maven-3.6.3\bin\..
Java version: 17.0.6, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17                 
Default locale: en_US, platform encoding: Cp1252                                                        
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

```

## Creating jar

```bash
mvn package
```

This should create a jar file

# Running the jar

You need at least java 11 to run this

` java -jar <generated_jar> <username> <password> <repo_path> `

This should print the result in this format

`RemoteRefUpdate[remoteName=refs/heads/main, OK, 24eaf094c361fa78d17567b6e96514cfdea6aa0e...5bbf4e101d128f7ff8d99034350a9272b7e4e8f9, fastForward, srcRef=refs/heads/main, message=null]`

To understand what each status mean go to the Jgit `RemoteRefUpdate` [documentation](https://archive.eclipse.org/jgit/docs/jgit-2.0.0.201206130900-r/apidocs/org/eclipse/jgit/transport/RemoteRefUpdate.Status.html).

