#基础镜像jre8
FROM openjdk:8-jre
#挂载的路径
VOLUME /tmp
#将jar打入镜像之中，第一个参数为jar包名称，第二个参数为自定义一个新的jar包名称
ADD CarBooking-0.0.1-SNAPSHOT.jar app.jar
#设置docker容器时区为UTC+8
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
#入口命令，执行jar，这里的jar包名称是我们上面定义的新的jar包名称
ENTRYPOINT  ["nohup","java","-jar","/app.jar","&"]