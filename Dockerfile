#将war包或者jar和Dockerfile至于同一目录

#基于镜像
FROM azul/zulu-openjdk:8

#挂载目录，用于指定外部配置文件
#VOLUME /etc/resources

#将执行文件置于根目录
ADD *.war app.war

RUN bash -c 'touch /app.jar'

#指定端口
EXPOSE 8080

CMD ["java","-jar","app.war"]