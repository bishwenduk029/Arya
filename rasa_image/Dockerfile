# use a python container as a starting point
FROM python:3.8.2

RUN python -m pip install --upgrade pip

# install dependencies of interest
RUN python -m pip install rasa[spacy] && \
    python -m spacy download en_core_web_lg

RUN python -m pip uninstall sanic -y
RUN python -m pip install sanic==21.9.3