FROM ubuntu:latest
LABEL authors="levar"

ENTRYPOINT ["top", "-b"]