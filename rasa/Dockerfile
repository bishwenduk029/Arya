FROM arya/rasa:v0.0.1

# set workdir and copy data files from disk
# note the latter command uses .dockerignore
WORKDIR /app
ENV HOME=/app
COPY . .

## train a new rasa model
RUN rasa train nlu

# set entrypoint for interactive shells
ENTRYPOINT ["rasa"]

EXPOSE 5005

# command to run when container is called to run
CMD ["run", "--enable-api"]