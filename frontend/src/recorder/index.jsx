import React, { useCallback, useContext, useEffect, useRef, useState } from "react";
import AudioRecorder from "./audioRecorder";
import { IconButton } from "@chakra-ui/react";
import {FiMic, FiMicOff} from "react-icons/fi"
import { Icon } from "@chakra-ui/icons";
import { SocketContext } from "../webSocketWrapper";


function Recorder() {
  const micRefElement = useRef(null)
  const { socket } = useContext(SocketContext);
  const [micOn, setMicOn] = useState(true)

  useEffect(() => {
    
    const setupRecorderAudio = (stream) => {
      let chunks = [];
      let micElm = micRefElement.current
      if (MediaRecorder) {
        let rec = new AudioRecorder(stream, micElm, {
          stt: {
            enabled: true,
          },
        });

        rec.ondataavailable((e) => {
          chunks.push(e.data);
        });

        rec.onstart(() => {
          /* */
        });

        rec.onstop(() => {
          const blob = new Blob(chunks);
          chunks = [];
          rec.enabled = false;

          // Ensure there are some data
          if (blob.size >= 1000) {
            socket.send(blob);
          }
        });
        micElm.addEventListener("click", (e) => {
          e.preventDefault();

          if (rec.enabled === false) {
            rec.start();
            rec.enabled = true;
            setMicOn(false)
          } else {
            rec.stop();
            rec.enabled = false;
            setMicOn(true)
          }
        });
      } else {
        console.error("MediaRecorder is not supported on your browser.");
      }
    };
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
      navigator.mediaDevices
        .getUserMedia({ audio: true })
        .then(setupRecorderAudio)
        .catch((err) => {
          console.error(
            "MediaDevices.getUserMedia() threw the following error:",
            err
          );
        });
    } else {
      console.error(
        "MediaDevices.getUserMedia() is not supported on your browser."
      );
    }
  }, []);

  return (
    <div>
      <IconButton
        bg="#F66439"
        color="#fff"
        aria-label="Speak To Me"
        size="lg"
        icon={
          micOn ? (
            <Icon as={FiMic} width={6} height={6} />
          ) : (
            <Icon as={FiMicOff} width={6} height={6} />
          )
        }
        ref={micRefElement}
        rounded="md"
        outline={"none"}
      />
    </div>
  );
}

export default Recorder;
