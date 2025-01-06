# chguirauRubikSolver
Solve rubik cube with kind of beginners method, explicit in code <br>
To run in netbeans, check PrintCube.jsp, with URLs, this jsp includes the calls to code <br>
To run externally, we have to deploy in tomcat, we can run in docker <br>
Use Dockerfile <br>
FROM tomcat:8.0-alpine <br>
<br>
COPY RubikSolver-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ <br>
<br>
Then build: <br>
docker build -t rubiksolver . <br>
Then run: <br>
docker run -itd -p 8080:8080 rubiksolver <br>
And access: <br>
http://localhost:8080/RubikSolver-1.0-SNAPSHOT/PrintCube.jsp <br>
we see the context is changed to the war name <br>
<br>
