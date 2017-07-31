FROM golang:1.8

ENV GOPATH /go

COPY src/coderockr/glide.lock /go/src/coderockr/
COPY src/coderockr/glide.yaml /go/src/coderockr/
COPY src/coderockr/main.go /go/src/coderockr/
COPY src/coderockr/artigo.db /go

RUN cd /go/src/coderockr \
    && curl https://glide.sh/get | sh \
    && glide install \
    && go build -o /go/artigo-imasters main.go



EXPOSE 8082

CMD ["/go/artigo-imasters"]
