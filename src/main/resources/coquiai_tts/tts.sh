#!/usr/bin/env bash

# Run tts --update to update Docker image
# Run tts --gpu to use GPU image

args=()
tag='latest'
docker='docker'

while [[ -n "$1" ]]; do
    if [[ -n "${check_cuda_arg}" ]]; then
        case "$1" in
            'true' | '1' | 'yes')
                # Use GPU version
                docker='nvidia-docker'
                tag='gpu'
                ;;
        esac

        check_cuda_arg=''
    fi

    if [[ "$1" == '--update' ]]; then
        # Update Docker image
        update='1'
    elif [[ "$1" == '--use_cuda' ]]; then
        # Check next argument
        check_cuda_arg='1'
        args+=("$1")
    else
        args+=("$1")
    fi

    shift 1
done

if [[ -n "${update}" ]]; then
    docker pull "synesthesiam/coqui-tts:${tag}"
fi

"${docker}" run \
            -e "HOME=${HOME}" \
            -v "$HOME:${HOME}" \
            -w "${PWD}" \
            --user "$(id -u):$(id -g)" \
            "synesthesiam/coqui-tts:${tag}" \
            "${args[@]}"